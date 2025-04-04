package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import model.Fixture;
import model.HealthStatus;
import model.Item;
import model.Map;
import model.Monster;
import model.Player;
import model.Puzzle;
import model.Room;
import view.View;
import view.ViewGraphic;
import view.ViewParent;


/**
 * The GameController class which manages the game's functionality.
 * Functionality includes initializing the game, handling player movements,
 * and managing game objects.
 */
public class GameController {
  private Map map;
  private Player player;
  private ViewParent view;
  private static final String SAVE_DIRECTORY = "save"; // relative path for saving
  private String originalPath;

  /**
   * Constructor of a GameController object.
   *
   * @param pathname path to the game data file.
   * @throws IOException if an error occurs while reading the file.
   */
  public GameController(String pathname, String mode) throws IOException {
    String gameFile = Paths.get(pathname).toString();
    this.map = LoadGameData.loadMap(gameFile);
    // create player by default here
    this.originalPath = pathname;
    mode = mode.toLowerCase();

    if ("text".equals(mode)){
      this.view = new View();
    }
    if ("graphic".equals(mode)){
      this.view = new ViewGraphic();
      view.setController(this); 
    }

    String playername = view.getPlayerName();
    createPlayer(playername);
  }



  /**
   * Constructor helper method, create a player for base setting.
   */
  public void createPlayer(String playerName) {
    if (map.getRooms() == null || map.getRooms().isEmpty()) {
      throw new IllegalStateException("empty map, can't create player");
    }
    Room startingRoom = map.getRooms().get(0);
    this.player = new Player(playerName, new ArrayList<>(), startingRoom);
    this.player.setCapacity(13);
  }

  /**
   * Returns the current game map.
   *
   * @return the map associated with this object
   */
  public Map getMap() {
    return this.map;
  }

  /**
   * Saves the current game state, including game data and player data.
   *
   * @param gameDataOutputPath   the file path to save the game data.
   * @param playerDataOutputPath the file path to save the player data.
   */
  public void save(String gameDataOutputPath, String playerDataOutputPath) {
    SaveGameData.saveGameData(this.map, gameDataOutputPath);
    SavePlayerData.savePlayer(this.player, playerDataOutputPath);
  }

  // view put inside controller
  /**
   * control the movement for player.
   *
   * @param direction of the moving
   */
  public void movePlayer(String direction) {
    int moveResult = player.move(direction, map);
    HealthStatus healthStatus = player.getHealthStatus();
    Room nextRoom = player.findRoomByNumber(player.getNextRoomNumber(direction), map);

    String message = "";
    // control the view display according to the result of the player move
    switch (moveResult) {
      case 1:
        message = ("You enter the " + player.getCurrentRoom().getRoom_name());
        break;
      case 0:
        message = (" >> You cannot go into that direction! \n" + healthStatus.getHealthMessage());
        break;
      case -1:
        message = "The path is blocked " + nextRoom.getDescription();
        break;
      case -2:
        message = "Seems like there's no room....";
        break;
      default:
        message = "Hmmm... you're trying to walk into the void? That doesn't seem right.";
        break;
    }
    view.displayMessage(message);
  }

  /**
   * Returns the adjacent Room in the given direction from the current room.
   * Valid directions include: "n", "north", "s", "south", "e", "east", "w", "west".
   * If the direction is invalid or no matching room is found, returns null.
   *
   * @param currentRoom the current Room from which the direction is evaluated
   * @param direction the direction to move in
   * @return the Room located in the given direction, or null if not found
   */
  private Room getNextRoom(Room currentRoom, String direction) {
    int nextRoomNumber = switch (direction) {
      case "n", "north" -> currentRoom.getN();
      case "w", "west" -> currentRoom.getW();
      case "s", "south" -> currentRoom.getS();
      case "e", "east" -> currentRoom.getE();
      default -> -1;
    };
    if (nextRoomNumber < 0) {
      for (Room room : map.getRooms()) {
        if (room.getRoom_number() == Math.abs(nextRoomNumber)) {
          return room;
        }
      }
    }
    return null;
  }

  /**
   * Displays detailed information about the current room to the player.
   */
  private void lookAround() {
    Room currentRoom = player.getCurrentRoom();
    view.displayMessage("\n===== You are currently in: " + currentRoom.getRoom_name() + "\n"
            + currentRoom.getDescription() + " =====\n");

    // get items from rooms
    view.displayMessage("Items available in this room:");
    for (int i = 0; i < currentRoom.getItem().size(); i++) {
      view.displayMessage("  • " + currentRoom.getItem().get(i).getName());
    }

    // get fixures from rooms
    view.displayMessage("\nFixtures present in the room:");
    for (int i = 0; i < currentRoom.getFixtures().size(); i++) {
      view.displayMessage("  • " + currentRoom.getFixtures().get(i).getName());
    }

    // get ACTIVE!!!!! puzzle from rooms
    view.displayMessage("\nPuzzles:");
    if (currentRoom.getPuzzles() != null) {
      if (currentRoom.getPuzzles().isActive()) {
        view.displayMessage("  → " + currentRoom.getPuzzles().getName());
      }
    }

    // get ACTIVE!!! MONSTER from rooms
    view.displayMessage("\nMonsters:");
    if (currentRoom.getMonsters() != null) {
      if (currentRoom.getMonsters().isActive()) {
        view.displayMessage("  → " + currentRoom.getMonsters().getName());
      }
    }
  }

  /**
   * Examines an item, fixture, or monster in the current room or player's inventory.
   *
   * @param stuff name of the item, fixture, or monster to examine.
   */
  public void examine(String stuff) {
    Room currentRoom = player.getCurrentRoom();

    // check room have this stuff item
    for (int i = 0; i < currentRoom.getItem().size(); i++) {
      if (currentRoom.getItem().get(i).getName().equalsIgnoreCase(stuff)) {
        view.displayMessage("\nItem: " + currentRoom.getItem().get(i).getDescription());
        view.displayMessage("Uses remaining: " + currentRoom.getItem().get(i).getUses_remaining());
      }
    }

    // check did player has this item
    for (int i = 0; i < player.getInventory().size(); i++) {
      if (player.getInventory().get(i).getName().equalsIgnoreCase(stuff)) {
        view.displayMessage("\nItem: " + player.getInventory().get(i).getDescription());
        view.displayMessage("Uses remaining: " + player.getInventory().get(i).getUses_remaining());
      }
    }

    // check current room has this fixure
    for (int i = 0; i < currentRoom.getFixtures().size(); i++) {
      if (currentRoom.getFixtures().get(i).getName().equalsIgnoreCase(stuff)) {
        view.displayMessage("\nFixture: "
                + currentRoom.getFixtures().get(i).getDescription());
      }
    }

    // check room has this puzzle
    if (currentRoom.getPuzzles() != null
            && currentRoom.getPuzzles().getName().equalsIgnoreCase(stuff)) {
      view.displayMessage("\nPuzzle: "
              + currentRoom.getPuzzles().getDescription());
    }

    // check room has this monster
    if (currentRoom.getMonsters() != null
            && currentRoom.getMonsters().getName().equalsIgnoreCase(stuff)) {
      view.displayMessage("\nMonster: "
              + currentRoom.getMonsters().getDescription());
    }
  }

  /**
   * Enables the player to take an item from the current room and add to inventory.
   *
   * @param itemName name of the item to take.
   */
  public void takeItem(String itemName) {
    Room currentRoom = player.getCurrentRoom();
    List<Item> roomItems = currentRoom.getItem();

    for (Iterator<Item> it = roomItems.iterator(); it.hasNext();) {
      Item item = it.next();
      if (item.getName().equalsIgnoreCase(itemName)) {
        boolean result = player.pickUpItem(item);
        if (result) {
          it.remove();
          view.displayMessage(itemName + " added to your inventory.");
        } else {
          view.displayMessage("Weight exceeds capacity, cannot pick up " + itemName + ".");
        }
        return;
      }
    }
    // Determine if the item player inputted is fixture
    for (Fixture fixture : currentRoom.getFixtures()) {
      if (fixture.getName().equalsIgnoreCase(itemName)) {
        view.displayMessage(itemName + " is a immovable fixture. You can't pick that up.");
        return;
      }
    }

    // Nothing found in fixtures and items list
    view.displayMessage("No item or fixture named '" + itemName + "' found in this room.");
  }

  /**
   * Enables the player to drop an item from their inventory into the current room.
   *
   * @param itemName name of the item to drop.
   */
  public void dropItem(String itemName) {
    List<Item> inventory = player.getInventory();
    for (Iterator<Item> it = inventory.iterator();
         it.hasNext();) {
      Item item = it.next();
      if (item.getName().equalsIgnoreCase(itemName)) {
        boolean result = player.dropItem(item);
        if (result) {
          view.displayMessage(itemName + " dropped here in "
                  + player.getCurrentRoom().getRoom_name());
        } else {
          view.displayMessage("Drop failed: item '"
                  + itemName
                  + "' could not be removed from inventory.");
        }
        return;
      }
    }
    view.displayMessage("You don't have an item named '" + itemName + "' in your inventory.");
  }

  /**
   * Solve a monster encounter using an item.
   *
   * @param item item used to solve the monster.
   * @param monster monster being solved.
   */
  public void solveMonster(Item item, Monster monster) {
    int result = player.solveMonster(item, monster);
    if (result == -2) {
      view.displayMessage("Not a valid monster or item.");
    } else if (result == 0) {
      view.displayMessage("The solution is text, not an item.");
    } else if (result == -1) {
      view.displayMessage("The item does not match the Monster's solution.");
    } else if (result == 1) {
      view.displayMessage("Monster solved using the correct item! "
              + "(Item usage decreased by 1)");
    } else if (result == -3) {
      view.displayMessage("Item remaining_use less than 1");
    }
  }

  /**
   * Solve a monster encounter using magic words.
   *
   * @param magicWords magic words used to solve the monster.
   * @param monster monster being solved.
   */
  public void solveMonster(String magicWords, Monster monster) {
    int result = player.solveMonster(magicWords, monster);
    if (result == -2) {
      view.displayMessage("Not a valid monster or magic word.");
    } else if (result == 0) {
      view.displayMessage("The solution is an item, not text.");
    } else if (result == -1) {
      view.displayMessage("The magic word does not match the Monster's solution.");
    } else if (result == 1) {
      view.displayMessage("Monster solved using the correct magic word!");
    }
  }

  /**
   * Attempts to solve the given puzzle using an item.
   * Displays messages based on whether the item is valid.
   * matches the solution, or has remaining uses.
   *
   * @param item the item used to solve the puzzle
   * @param puzzle the puzzle to be solved
   */
  private void solvePuzzle(Item item, Puzzle puzzle) {
    int result = player.solvePuzzle(item, puzzle);
    if (result == -2) {
      view.displayMessage("Not a valid puzzle or item.");
    } else if (result == 0) {
      view.displayMessage("The solution is text, not an item.");
    } else if (result == -1) {
      view.displayMessage("The item does not match the Puzzle's solution.");
    } else if (result == 1) {
      view.displayMessage("Puzzle solved using the correct item! "
              + "(Item usage decreased by 1)");
    } else if (result == -3) {
      view.displayMessage("Item remaining_use less than 1");
    }
  }

  /**
   * Solve a puzzle using magic words.
   *
   * @param magicWords magic words used to solve the puzzle.
   * @param puzzle puzzle being solved.
   */
  public void solvePuzzle(String magicWords, Puzzle puzzle) {
    int result = player.solvePuzzle(magicWords, puzzle);
    if (result == -2) {
      view.displayMessage("Not a valid puzzle or magic word.");
    } else if (result == 0) {
      view.displayMessage("The solution is an item, not a magic word.");
    } else if (result == -1) {
      view.displayMessage("The magic word does not match the Puzzle's solution.");
    } else if (result == 1) {
      view.displayMessage("Puzzle solved using the correct magic word!");
    }
  }

  /**
   * display the inventory to the user.
   */
  public void showInventory() {
    List<Item> inventory = player.getInventory();

    String inventoryMessage;
    if (inventory.isEmpty()) {
      inventoryMessage = "There's nothing in your inventory yet.";
    } else {
      StringBuilder sb = new StringBuilder();
      for (Iterator<Item> it = inventory.iterator(); it.hasNext();) {
        Item item = it.next();
        sb.append(item.getName());
        if (it.hasNext()) {
          sb.append(", ");
        }
      }
      inventoryMessage = "Items in your inventory: " + sb.toString();
    }

    view.displayMessage(inventoryMessage + "\n" + player.getHealthStatus().getHealthMessage());
  }

  /**
   * answer the puzzle by using a magic.
   *
   * @param answer string answer of user input
   */
  public void answerPuzzle(String answer) {
    if (answer == null) {
      view.displayMessage("So what's your answer?: ");
    }
    Puzzle puzzle = player.getCurrentRoom().getPuzzles();
    if (puzzle == null) {
      view.displayMessage("There's no puzzle to be solve.");
      return;
    }
    solvePuzzle(answer, puzzle);
  }

  /**
   * answer the puzzle by using an item.
   *
   * @param item string answer of user input
   */
  public void answerPuzzle_Item(String item) {
    if (item == null) {
      view.displayMessage("So what's item you want to use?: ");
    }
    Puzzle puzzle = player.getCurrentRoom().getPuzzles();
    if (puzzle == null) {
      view.displayMessage("There's no puzzle to be solve.");
      return;
    }
    for (int i = 0; i < player.getInventory().size(); i++) {
      String name = player.getInventory().get(i).getName().trim().toLowerCase();
      item = item.trim().toLowerCase();
      if (name.equals(item)) {
        solvePuzzle(player.getInventory().get(i), puzzle);
        return;
      }
    }
    view.displayMessage("you don't have this item");
  }

  /**
   * answer the puzzle by using a magic.
   *
   * @param answer string answer of user input
   */
  public void answerMonster(String answer) {
    if (answer == null) {
      view.displayMessage("So what's your answer?: ");
    }
    Monster monster = player.getCurrentRoom().getMonsters();
    if (monster == null) {
      view.displayMessage("There's no puzzle to be solve.");
      return;
    }
    solveMonster(answer, monster);
  }

  /**
   * answer the puzzle by using an item.
   *
   * @param item string answer of user input
   */
  public void answerMonster_Item(String item) {
    if (item == null) {
      view.displayMessage("So what's item you want to use?: ");
    }
    Monster monster = player.getCurrentRoom().getMonsters();
    if (monster == null) {
      view.displayMessage("There's no puzzle to be solve.");
      return;
    }
    for (int i = 0; i < player.getInventory().size(); i++) {
      String name = player.getInventory().get(i).getName().trim().toLowerCase();
      item = item.trim().toLowerCase();
      if (name.equals(item)) {
        solveMonster(player.getInventory().get(i), monster);
        return;
      }
    }
    view.displayMessage("you don't have this item");
  }

  /**
   * quit method.
   */
  public void quit() {
    String ranking = ranking();
    int score = player.getScore();
    for (Item item : player.getInventory()) {
      score += item.getValue();
    }
    view.displayMessage("Thanks for playing!\nPlayer name: " + player.getName()
            + "\nYour score is " + score + "\n" + ranking);
    System.exit(0);
  }

  /**
   * Calculates the player's final score and returns a letter ranking with feedback.
   *
   * @return a string representing the player's rank and a short message
   */
  private String ranking() {
    int score = player.getScore();
    for (Item item : player.getInventory()) {
      score += item.getValue();
    }
    if (score > 2000) {
      return "You got an A! You're a true explorer";
    } else if (score > 1500) {
      return "You got an B! Great job!";
    } else if (score > 1000) {
      return "You got an C! That's good!";
    } else if (score > 500) {
      return "You got an D! Nice!";
    } else {
      return "You got an F......at least you enjoyed!";
    }
  }

  /**
   * process the command.
   *
   * @param command a string list of command
   */
  public void getCommand(String[] command) {
    String action = command[0];//take
    //take item
    String stuff = command[1];//item

    // if got defeated, game over
    if (handleMonsterEncounter()) {
      return;
    }

    switch (action) {
      case "n", "north", "s", "south", "e", "east", "w", "west": // the neal
        movePlayer(action);
        break;
      case "i", "inventory":  // AMY,inspect(),dont' delete inventory but add inspect()
        showInventory();
        break;
      case "t", "take": // Amy
        takeItem(stuff);
        break;
      case "d", "drop": // AMy
        dropItem(stuff);
        break;
      case "x", "examine": // Amy
        examine(stuff);
        break;
      case "l", "look"://no longer need for graphic but need for text mode
        lookAround();
        break;
      case "u", "use": //  Yijie
        handleUseCommand(stuff);
        break;
      case "a", "answer": //Neal
        handleAnswerCommand(stuff);
        break;
      case "q", "quit": //neal
        quit();
        break;
      case "save": //neal
        saveGame();
        break;
      case "load": //neal
        loadGame();
        break;
      default:
        view.displayMessage("Invalid command: " + action);
        break;
    }
  }

  /**
   * Handle monster encounters before executing any commands.
   */
  private boolean handleMonsterEncounter() {
    Monster monster = player.getCurrentRoom().getMonsters();
    // only where there's monster and is active
    if (monster != null && monster.isActive()) {
      monster.attackPlayer(player);
      view.displayMessage(monster.getEffects() + "\n" + monster.getName().toUpperCase() + " "
              + monster.getAttack() + "\nPlayer takes " + monster.getDamage() + " damage!\n"
              + player.getHealthStatus().getHealthMessage());
      view.displayMessage("player health: " + player.getHealth());
      if (player.getHealthStatus() == HealthStatus.SLEEP) {
        view.displayMessage("Go to sleep, soldier...");
        quit();
        return true;
      }
    }
    return false;
  }

  /**
   *  When user input "use" for solving puzzles/monsters.
   */
  private void handleUseCommand(String item) {
    Room currentRoom = player.getCurrentRoom();
    if (currentRoom.getPuzzles() != null) {
      answerPuzzle_Item(item);
    } else if (currentRoom.getMonsters() != null) {
      answerMonster_Item(item);
    } else {
      view.displayMessage("No puzzles nor monsters found.");
    }
  }

  /**
   * When user input "answer" for solving puzzles/monsters.
   */
  private void handleAnswerCommand(String answer) {
    Room currentRoom = player.getCurrentRoom();
    if (currentRoom.getPuzzles() != null) {
      answerPuzzle(answer);
    } else if (currentRoom.getMonsters() != null) {
      answerMonster(answer);
    } else {
      view.displayMessage("No puzzles nor monsters found.");
    }
  }

  /**
   * Save game.
   */
  private void saveGame() {
    File saveDir = new File(SAVE_DIRECTORY);
    // ensure directory exists
    if (!saveDir.exists()) {
      saveDir.mkdirs();
    }

    String safeName = player.getName();
    String gameFile = Paths.get(SAVE_DIRECTORY, "game_data_" + safeName + ".json").toString();
    String playerFile = Paths.get(SAVE_DIRECTORY, "player_data_" + safeName + ".json").toString();

    save(gameFile, playerFile);
    view.displayMessage("Game saved to " + SAVE_DIRECTORY + " directory.");
  }

  /**
   * Load game.
   */
  private void loadGame() {
    Scanner scanner = new Scanner(System.in);
    boolean gameLoaded = false;

    while (!gameLoaded) {
      try {
        System.out.println("PLS enter the name of the player you want to load\n"
                + "(for example if last time your name is alex, you should use alex to load)");
        String Player_name = scanner.nextLine().trim();

        String gameFile = Paths.get(SAVE_DIRECTORY, "game_data_"
                + Player_name + ".json").toString();
        String playerFile = Paths.get(SAVE_DIRECTORY, "player_data_"
                + Player_name + ".json").toString();

        this.map = LoadGameData.loadMap(gameFile);
        Player loadedPlayer = PlayerLoad.loadPlayer(playerFile, originalPath, this.map);

        this.player = loadedPlayer;
        view.displayMessage("Game loaded successfully!");
        // break when load
        gameLoaded = true;
      } catch (IOException e) {
        e.printStackTrace();
        view.displayMessage("Error loading game. Please enter a correct name.\n");
      }
    }
  }
  
  /**
   * While the game is not over, continue to fetch command from user input.
   */
  public void gameLoop() {
    boolean gameOver = false;

    // show only one
    if (view instanceof ViewGraphic) {
      view.displayMenu();
    }

    while (!gameOver) {
      // show many times
      if (!(view instanceof ViewGraphic)) {
        view.displayMenu();
      }

      String[] command = view.getInput();
      getCommand(command);
    }
  }

  public Player getPlayer() {
    return player;
  }



  // amy
  public List<String> renderInventory() {
    List<Item> inventory = player.getInventory();

    String inventoryMessage;
    if (inventory.isEmpty()) {
      inventoryMessage = "There's nothing in your inventory yet.";
    } else {
      StringBuilder sb = new StringBuilder();
      for (Iterator<Item> it = inventory.iterator(); it.hasNext(); ) {
        Item item = it.next();
        sb.append(item.getName());
        if (it.hasNext()) {
          sb.append(", ");
        }
      }
      inventoryMessage = "Items in your inventory: " + sb.toString();
    }

    return null;
    }

}

