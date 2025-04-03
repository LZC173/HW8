package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Challenge;
import model.Fixture;
import model.HealthStatus;
import model.Item;
import model.Map;
import model.Monster;
import model.Player;
import model.Puzzle;
import model.Room;

/**
 * Handles conversion of game state to JSON format.
 * This class provides functionality to save the current game state.
 */
public class SaveGameData {

  /**
   * Saves the game data into a JSON file in a format same with original
   * data is obtained exclusively from the model.Map object's rooms.
   *
   * @param map         The map object containing room data, game name, and version.
   * @param outputPath  The file path to save the JSON data.
   */
  public static void saveGameData(Map map, String outputPath) {
    ObjectMapper mapper = new ObjectMapper();
    // create the root node and save the game name and version
    ObjectNode root = mapper.createObjectNode();
    root.put("name", map.getName());
    root.put("version", map.getVersion());

    // Construct the "rooms" array (from map)

    ArrayNode roomsArray = mapper.createArrayNode();
    List<Room> rooms = map.getRooms();
    for (int i = 0; i < rooms.size(); i++) {
      Room room = rooms.get(i);
      ObjectNode roomNode = mapper.createObjectNode();
      // save room properties as strings to match the original format
      roomNode.put("room_name", room.getRoom_name());
      roomNode.put("room_number", String.valueOf(room.getRoom_number()));
      roomNode.put("description", room.getOriginalDescription());
      // get orginal description !!!! here
      roomNode.put("N", String.valueOf(room.getN()));
      roomNode.put("S", String.valueOf(room.getS()));
      roomNode.put("E", String.valueOf(room.getE()));
      roomNode.put("W", String.valueOf(room.getW()));
      // for puzzle: if exists, save its name; otherwise, null
      if (room.getPuzzles() != null) {
        roomNode.put("puzzle", room.getPuzzles().getName());
      } else {
        roomNode.put("puzzle", (String) null);
      }
      // for monster: if exists, save its name; otherwise, null
      if (room.getMonsters() != null) {
        roomNode.put("monster", room.getMonsters().getName());
      } else {
        roomNode.put("monster", (String) null);
      }
      // for items: join the list into a comma separated string
      List<Item> roomItems = room.getItem();
      if (roomItems != null && roomItems.size() > 0) {
        StringBuilder sb = new StringBuilder();
        // create a string build ,because we need multiple item name
        for (int j = 0; j < roomItems.size(); j++) {
          sb.append(roomItems.get(j).getName());
          if (j < roomItems.size() - 1) {
            sb.append(",");
            // add "," here becasue we want split
          }
        }
        roomNode.put("items", sb.toString());
      } else {
        roomNode.put("items", (String) null);
      }
      // for fixtures: join the list into a comma  separated string
      List<Fixture> roomFixtures = room.getFixtures();
      if (roomFixtures != null && roomFixtures.size() > 0) {
        StringBuilder sb2 = new StringBuilder();
        for (int j = 0; j < roomFixtures.size(); j++) {
          sb2.append(roomFixtures.get(j).getName());
          if (j < roomFixtures.size() - 1) {
            sb2.append(",");
          }
        }
        roomNode.put("fixtures", sb2.toString());
      } else {
        roomNode.put("fixtures", (String) null);
      }
      roomNode.put("picture", room.getPicture());
      roomsArray.add(roomNode);
    }
    root.set("rooms", roomsArray);
    // set rooms for all constructed json format


    // construct the "items" array (from all rooms)

    Set<Item> itemSet = new HashSet<>();
    for (int i = 0; i < rooms.size(); i++) {
      List<Item> roomItems = rooms.get(i).getItem();
      if (roomItems != null) {
        itemSet.addAll(roomItems);
      }
    }
    // convert set to list for classic loop iteration
    List<Item> itemList = new ArrayList<>(itemSet);
    ArrayNode itemsArray = mapper.createArrayNode();
    for (int i = 0; i < itemList.size(); i++) {
      Item item = itemList.get(i);
      ObjectNode itemNode = mapper.createObjectNode();
      itemNode.put("name", item.getName());
      itemNode.put("weight", String.valueOf(item.getWeight()));
      itemNode.put("max_uses", String.valueOf(item.getMax_uses()));
      itemNode.put("uses_remaining", String.valueOf(item.getUses_remaining()));
      itemNode.put("value", String.valueOf(item.getValue()));
      itemNode.put("when_used", item.getWhen_used());
      itemNode.put("description", item.getDescription());
      itemNode.put("picture", item.getPicture());
      itemsArray.add(itemNode);
    }
    root.set("items", itemsArray);

    // construct the "fixtures" array (from all rooms)
    Set<Fixture> fixtureSet = new HashSet<>();
    for (int i = 0; i < rooms.size(); i++) {
      List<Fixture> roomFixtures = rooms.get(i).getFixtures();
      if (roomFixtures != null) {
        fixtureSet.addAll(roomFixtures);
      }
    }
    List<Fixture> fixtureList = new ArrayList<>(fixtureSet);
    ArrayNode fixturesArray = mapper.createArrayNode();
    for (int i = 0; i < fixtureList.size(); i++) {
      Fixture fixture = fixtureList.get(i);
      ObjectNode fixtureNode = mapper.createObjectNode();
      fixtureNode.put("name", fixture.getName());
      fixtureNode.put("weight", String.valueOf(fixture.getWeight()));
      // puzzle and states are NULL in hw8
      fixtureNode.put("puzzle", (String) null);
      fixtureNode.put("states", (String) null);
      fixtureNode.put("description", fixture.getDescription());
      fixtureNode.put("picture", fixture.getPicture());
      fixturesArray.add(fixtureNode);
    }
    root.set("fixtures", fixturesArray);

    // Construct the "monsters" array (from all rooms)
    Set<Monster> monsterSet = new HashSet<>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getMonsters() != null) {
        monsterSet.add(rooms.get(i).getMonsters());
      }
    }
    List<Monster> monsterList = new ArrayList<>(monsterSet);
    ArrayNode monstersArray = mapper.createArrayNode();
    for (int i = 0; i < monsterList.size(); i++) {
      Monster monster = monsterList.get(i);
      ObjectNode monsterNode = mapper.createObjectNode();
      monsterNode.put("name", monster.getName());
      monsterNode.put("active", String.valueOf(monster.isActive()));
      monsterNode.put("affects_target", String.valueOf(monster.isAffects_target()));
      monsterNode.put("affects_player", String.valueOf(monster.isAffects_player()));
      monsterNode.put("solution", monster.getSolution());
      monsterNode.put("value", String.valueOf(monster.getValue()));
      monsterNode.put("description", monster.getDescription());
      monsterNode.put("effects", monster.getEffects());
      monsterNode.put("damage", monster.getDamage());
      monsterNode.put("target", monster.getTarget());
      monsterNode.put("can_attack", String.valueOf(monster.isCan_attack()));
      monsterNode.put("attack", monster.getAttack());
      monsterNode.put("picture", monster.getPicture());
      monstersArray.add(monsterNode);
    }
    root.set("monsters", monstersArray);

    // construct the "puzzles" array (from all rooms)
    Set<Puzzle> puzzleSet = new HashSet<>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getPuzzles() != null) {
        puzzleSet.add(rooms.get(i).getPuzzles());
      }
    }
    List<Puzzle> puzzleList = new ArrayList<>(puzzleSet);
    ArrayNode puzzlesArray = mapper.createArrayNode();
    for (int i = 0; i < puzzleList.size(); i++) {
      Puzzle puzzle = puzzleList.get(i);
      ObjectNode puzzleNode = mapper.createObjectNode();
      puzzleNode.put("name", puzzle.getName());
      puzzleNode.put("active", String.valueOf(puzzle.isActive()));
      puzzleNode.put("affects_target", String.valueOf(puzzle.isAffects_target()));
      puzzleNode.put("affects_player", String.valueOf(puzzle.isAffects_player()));
      puzzleNode.put("solution", puzzle.getSolution());
      puzzleNode.put("value", String.valueOf(puzzle.getValue()));
      puzzleNode.put("description", puzzle.getDescription());
      puzzleNode.put("effects", puzzle.getEffects());
      puzzleNode.put("target", puzzle.getTarget());
      puzzleNode.put("picture", puzzle.getPicture());
      puzzlesArray.add(puzzleNode);
    }
    root.set("puzzles", puzzlesArray);

    // write the constructed JSON object to the file
    try {
      mapper.writeValue(new File(outputPath), root);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}