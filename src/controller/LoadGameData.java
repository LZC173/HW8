package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import model.Challenge;
import model.Fixture;
import model.HealthStatus;
import model.Item;
import model.Map;
import model.Monster;
import model.Player;
import model.Puzzle;
import model.Room;
import view.View;

/**
 * Handles loading game data from JSON files.
 */
public class LoadGameData {

  /**
   * Loads the model.Map object (rooms, name, version) from the given JSON file.
   *
   * @param pathname The path to the game data JSON file.
   * @return A fully constructed model.Map object.
   * @throws IOException If reading the file fails.
   */
  public static Map loadMap(String pathname) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    // 1. Read the controller.GameData object (which has name, version, items, etc.)
    GameData gameData = objectMapper.readValue(new File(pathname), GameData.class);

    // 2. Parse the "rooms" array from the JSON
    JsonNode rootNode = objectMapper.readTree(Files.readString(new File(pathname).toPath()));
    JsonNode roomsNode = rootNode.get("rooms");

    // 3. Create the list of model.Room objects
    List<Room> roomList = parseRooms(roomsNode, gameData);

    // 4. Return a new model.Map object
    return new Map(roomList, gameData.getName(), gameData.getVersion());
  }

  /**
   * Parses the "rooms" array to construct a list of model.Room objects.
   */
  private static List<Room> parseRooms(JsonNode roomsNode, GameData gameData) {
    List<Room> roomList = new ArrayList<>();
    for (JsonNode roomNode : roomsNode) {
      Room room = createRoom(roomNode, gameData);
      roomList.add(room);
    }
    return roomList;
  }

  /**
   * Creates a single model.Room object from a JSON node.
   */
  private static Room createRoom(JsonNode roomNode, GameData gameData) {
    Puzzle puzzle = new Puzzle();
    if (gameData.getPuzzles() != null) {
      puzzle = findPuzzle(roomNode.get("puzzle").asText(null), gameData);
    }
    Monster monster = new Monster();
    if (gameData.getMonsters() != null) {
      monster = findMonster(roomNode.get("monster").asText(null), gameData);
    }
    List<Item> roomItems = new ArrayList<>();
    if (gameData.getItems() != null) {
      roomItems = findItems(roomNode.get("items").asText(null), gameData);
    }
    List<Fixture> roomFixtures = new ArrayList<>();
    if (gameData.getFixtures() != null) {
      roomFixtures = findFixtures(roomNode.get("fixtures").asText(null), gameData);
    }
    String picture = "";
    if (roomNode.get("picture") != null) {
      picture = roomNode.get("picture").asText();
    }
    String name = roomNode.get("room_name").asText();
    int number = roomNode.get("room_number").asInt();
    String description = roomNode.get("description").asText();
    int n = roomNode.get("N").asInt();
    int s = roomNode.get("S").asInt();
    int e = roomNode.get("E").asInt();
    int w = roomNode.get("W").asInt();
    return new Room(name, number, description, n, s, e, w,
            puzzle, monster, roomItems, roomFixtures, picture);
  }

  /**
   * Finds a model.Puzzle by name in the gameData object.
   */
  private static Puzzle findPuzzle(String puzzleName, GameData gameData) {
    if (puzzleName == null) {
      return null;
    }
    return gameData.getPuzzles().stream()
            .filter(p -> p.getName().equalsIgnoreCase(puzzleName))
            .findFirst()
            .orElse(null);
  }

  /**
   * Finds a model.Monster by name in the gameData object.
   */
  private static Monster findMonster(String monsterName, GameData gameData) {
    if (monsterName == null) {
      return null;
    }
    return gameData.getMonsters().stream()
            .filter(m -> m.getName().equalsIgnoreCase(monsterName))
            .findFirst()
            .orElse(null);
  }

  /**
   * Finds one or more Items by name in the gameData object.
   */
  private static List<Item> findItems(String itemNames, GameData gameData) {
    List<Item> roomItems = new ArrayList<>();
    if (itemNames == null) {
      return roomItems;
    }

    for (String itemName : itemNames.split(",")) {
      gameData.getItems().stream()
              .filter(item -> item.getName().equalsIgnoreCase(itemName.trim()))
              .findFirst()
              .ifPresent(roomItems::add);
    }
    return roomItems;
  }

  /**
   * Finds one or more Fixtures by name in the gameData object.
   */
  private static List<Fixture> findFixtures(String fixtureNames, GameData gameData) {
    List<Fixture> roomFixtures = new ArrayList<>();
    if (fixtureNames == null) {
      return roomFixtures;
    }

    for (String fixtureName : fixtureNames.split(",")) {
      gameData.getFixtures().stream()
              .filter(fixture -> fixture.getName().equalsIgnoreCase(fixtureName.trim()))
              .findFirst()
              .ifPresent(roomFixtures::add);
    }
    return roomFixtures;
  }
}