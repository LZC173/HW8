package model;

import java.util.List;

/**
 * Class represents a game map containing a list of rooms.
 */
public class Map {
  private List<Room> rooms;
  private String name;
  private String version;


  /**
   * Constructs a map with the given rooms, name, and version.
   * @param rooms list of rooms.
   * @param name name of the map.
   * @param version version of the map.
   */
  public Map(List<Room> rooms, String name, String version) {
    this.rooms = rooms;
    this.name = name;
    this.version = version;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
