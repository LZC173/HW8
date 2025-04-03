package model;

import java.util.List;

/**
 * Class Represents a player in the game along with its attributes.
 */
public class Player {
  private String name; // no exits in model.Player class in UML - need discussion
  private Integer score;
  private Integer health;
  private List<Item> inventory;
  private Integer capacity;
  // max capacity you can carry
  private Room currentRoom;

  private final int minimum = 0;
  private final int roomnumberinvalid = 0;
  private final int maximumremaininguses = 1;
  private final int maximumhealth = 100;
  private final int maximumcapacity = 13;

  /**
   * Constructs a new Player with specified parameters.
   * Initializes score to 0, health to maximum, and capacity to maximum.
   *
   * @param name player's name
   * @param inventory initial inventory of items
   * @param currentRoom starting room for the player
   */
  public Player(String name, List<Item> inventory, Room currentRoom) {
    this.name = name;
    this.score = minimum;
    this.health = maximumhealth;
    this.inventory = inventory;
    this.capacity = maximumcapacity;
    this.currentRoom = currentRoom;
  }

  /**
   * Attempts to pick up an item and add to player's inventory.
   * @param pickedItem item to pick up.
   * @return return true if item picked up, otherwise false if capacity exceeded.
   */
  public boolean pickUpItem(Item pickedItem) {
    int sum = minimum;
    // sum the weight of all items in the inventory
    for (Item item : inventory) {
      sum += item.getWeight();
    }
    // add the weight of the picked item
    sum += pickedItem.getWeight();
    // check if adding the new item exceeds the capacity
    if (sum > capacity) {
      // exit the method if capacity is exceeded
      return false;
    } else {
      inventory.add(pickedItem);
      return true;
    }
  }

  /**
   * Placeholder for picking up a fixture.
   * @param fixture The fixture to pick up.
   * @return Always returns false.
   */
  public boolean pickUpItem(Fixture fixture) {
    return false;
  }


  /**
   * Drop item to the room.
   * @param droppedItem drop item
   *@return return true means success, otherwise false
   */
  public boolean dropItem(Item droppedItem) {
    // Check if the inventory contains the dropped item
    if (inventory.contains(droppedItem)) {
      // add item to model.Room
      this.currentRoom.getItem().add(droppedItem);
      // Remove the item from the inventory
      inventory.remove(droppedItem);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Getter function - get the name of the player.
   * @return name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * Setter function - set the name of the player.
   * @param name name of the player
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter function - get the score of the player.
   * @return the score of the function
   */
  public Integer getScore() {
    return score;
  }

  /**
   * Setter function - set the score of the player.
   * @param score of the player
   */
  public boolean setScore(Integer score) {
    if (score >= minimum) {
      this.score = score;
      return true;
    }
    return false;
  }

  /**
   * Getter function - get the health of the player.
   * @return the health of the player
   */
  public Integer getHealth() {
    return health;
  }

  /**
   * get the health status of the player.
   *
   * @return health status
   */
  public HealthStatus getHealthStatus() {
    if (this.health <= 0) {
      return HealthStatus.SLEEP;
    }
    if (this.health <= 20) {
      return HealthStatus.WOOZY;
    }
    if (this.health <= 70) {
      return HealthStatus.FATIGUED;
    }
    return HealthStatus.AWAKE;
  }


  /**
   * Set the health of the player.
   * @param health health should range from 0 to 100
   * @return boolean - status of the setting
   */
  public boolean setHealth(Integer health) {
    if (health >= minimum && health <= maximumhealth) {
      this.health = health;
      return true;
    } else {
      // If health of the player is out of range it's false
      this.health = minimum;
      return false;
    }
  }

  /**
   * Checks if the player runs out of health.
   * @param health Integer
   * @return boolean depending on whether the player is asleep
   */
  public boolean isAsleep(Integer health) {
    return health == minimum;
  }

  /**
   * Getter function - get the capacity.
   * @return the items that the player can pick
   */
  public Integer getCapacity() {
    return capacity;
  }

  /**
   * Getter function - get the capacity.
   * @return the items that the player can pick
   */
  public boolean setCapacity(Integer capacity) {
    if (capacity >= minimum && capacity <= maximumcapacity) {
      this.capacity = capacity;
      return true;
    }
    return false;
  }

  /**
   * Getter function - get the room player located.
   * @return the room player currently at
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }

  /**
   * Getter function - get the room player located.
   * @return the room player currently at
   */
  public boolean setCurrentRoom(Room currentRoom) {
    if (currentRoom != null) {
      this.currentRoom = currentRoom;
      return true;
    }
    return false;
  }

  /**
   * Getter function - get the inventory of the player.
   * @return the inventory of the player
   */
  public List<Item> getInventory() {
    return inventory;
  }

  /**
   * Setter function - set the inventory of the player.
   * @param inventory of the player
   */
  public void setInventory(List<Item> inventory) {
    this.inventory = inventory;
  }

  /**
   * Solve the puzzle with item.
   * @param item item the player uses
   * @param puzzle puzzle that player facing
   * @return common status code
   */
  public Integer solvePuzzle(Item item, Puzzle puzzle) {
    if (puzzle == null || item == null) {
      return Challenge.SOLVE_ERROR;
    }
    if (item.getUses_remaining() < maximumremaininguses) {
      return Challenge.SOLVE_VERY_FAIL;
      //when item remaining less than 1 return -3
    }
    Integer result = puzzle.solve(item);
    if (result == Challenge.SOLVE_SUCCESS && !puzzle.isActive()) {
      this.score += puzzle.getValue();
      item.setUses_remaining(item.getUses_remaining() - maximumremaininguses);
      this.currentRoom.setRoomToPassable();
      if (item.getUses_remaining() < maximumremaininguses) {
        inventory.remove(item);
      }
    }
    return result;
  }

  /**
   * Solve the puzzle with item.
   * @param magicWords magic words that player input
   * @param puzzle puzzle that player facing
   * @return common status code
   */
  public Integer solvePuzzle(String magicWords, Puzzle puzzle) {
    if (puzzle == null || magicWords == null || magicWords.isEmpty()) {
      // "not a valid puzzle or magic words"
      return Challenge.SOLVE_ERROR;
    }
    Integer result = puzzle.solve(magicWords);
    if (result == Challenge.SOLVE_SUCCESS && !puzzle.isActive()) {
      this.score += puzzle.getValue();
      // set room to passable for all direction
      // once the puzzle or monster being solved
      this.currentRoom.setRoomToPassable();
    }
    return result;
  }

  /**
   * Fight with the monster using item.
   * @param item used to fight the monster
   * @param monster monster player facing right now
   * @return integer code shows that whether it works for the monster
   */
  public Integer solveMonster(Item item, Monster monster) {
    if (monster == null || item == null) {
      return Challenge.SOLVE_ERROR;
    }
    if (item.getUses_remaining() < maximumremaininguses) {
      return Challenge.SOLVE_VERY_FAIL;
      //when item remaining less than 1 return -3
    }
    Integer result = monster.solve(item);
    if (result == Challenge.SOLVE_SUCCESS && !monster.isActive()) {
      this.score += monster.getValue();
      item.setUses_remaining(item.getUses_remaining() - maximumremaininguses);
      this.currentRoom.setRoomToPassable();
      if (item.getUses_remaining() < maximumremaininguses) {
        inventory.remove(item);
      }
    }
    return result;
  }

  /**
   * Fight with the monster using magic words.
   * @param magicWords used to fight the monster
   * @param monster monster player facing right now
   * @return integer code shows that whether it works for the monster
   */
  public Integer solveMonster(String magicWords, Monster monster) {
    if (monster == null || magicWords == null || magicWords.isEmpty()) {
      // "not a valid puzzle or magic words"
      return Challenge.SOLVE_ERROR;
    }
    int result = monster.solve(magicWords);
    if (result == Challenge.SOLVE_SUCCESS && !monster.isActive()) {
      this.score += monster.getValue();
      // set room to passable for all direction
      // once the puzzle or monster being solved
      this.currentRoom.setRoomToPassable();
    }
    return result;
  }

  /**
   * Tries to move the player in the direction specified.
   *
   * @param direction direction to move
   * @param map game map containing all rooms
   * @return status code: 1 = success, 0 = blocked,
   *         -1 = blocked by puzzle/monster, -2 = unexpected error
   */
  public Integer move(String direction, Map map) {
    int nextRoomNumber = getNextRoomNumber(direction);

    if (nextRoomNumber > roomnumberinvalid) {
      Room nextRoom = findRoomByNumber(nextRoomNumber, map);
      if (nextRoom != null) {
        this.currentRoom = nextRoom;
        return 1; // move successfully
      }
      return -2; // unexpected error (valid room number but no room)
    } else if (nextRoomNumber == roomnumberinvalid) {
      return 0; // permanently blocked
    } else {
      return -1; // puzzle/monster in the room
    }
  }

  /**
   * Gets the room number from current room.
   * @param direction direction to check
   * @return the room number in that direction and -2 for invalid direction
   */
  public int getNextRoomNumber(String direction) {
    return switch (direction) {
      case "n", "north" -> this.currentRoom.getN();
      case "e", "east"  -> this.currentRoom.getE();
      case "s", "south" -> this.currentRoom.getS();
      case "w", "west"  -> this.currentRoom.getW();
      default           -> -2; // unexpected error
    };
  }


  /**
   * Locating a room by its number in the game map.
   *
   * @param roomNumber number of the room to find
   * @param map game map to search
   * @return the Room object if found, otherwise null
   */
  public Room findRoomByNumber(int roomNumber, Map map) {
    for (Room room : map.getRooms()) {
      if (room.getRoom_number() == roomNumber) {
        return room;
      }
    }
    return null; // unexpected error
  }
}