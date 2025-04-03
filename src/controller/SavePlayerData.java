package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
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
 * Handles conversion of player data to JSON format.
 */
public class SavePlayerData {

  /**
   * Saves the player data into a JSON file using Jackson.
   * Inventory =
   * currentRoom =
   *
   * @param player     the model.Player object to be saved.
   * @param outputPath the file path to save the JSON data.
   */
  public static void savePlayer(Player player, String outputPath) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode root = mapper.createObjectNode();

    // save basic
    root.put("name", player.getName());
    root.put("score", player.getScore());
    root.put("health", player.getHealth());
    root.put("capacity", player.getCapacity());


    ArrayNode invArray = mapper.createArrayNode();
    if (player.getInventory() != null) {
      for (Item item : player.getInventory()) {
        invArray.add(item.getName());
      }
    }
    root.set("inventory", invArray);

    // save current room as string
    if (player.getCurrentRoom() != null) {
      root.put("currentRoom", String.valueOf(player.getCurrentRoom().getRoom_number()));
    } else {
      root.put("currentRoom", "");
    }

    try {
      mapper.writeValue(new File(outputPath), root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}