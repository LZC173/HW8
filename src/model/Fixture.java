package model;

/**
 * The class represents the model.Fixture Class.
 */
public class Fixture {
  public String name;
  // Fixtures are immovable, so anything greater than 200 is acceptable.
  public int weight;
  public String puzzle;
  public String states;
  public String description;
  public String picture;

  /**
   * Getter function - get the name of the fixture.
   * @return the name of the fixture
   */
  public String getName() {
    return name;
  }

  /**
   * Setter func - set the name of the fixture.
   * @param name of the fixture
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter function - get the weight of the fixture.
   * Fixtures are immovable, so anything greater than 200 is acceptable.
   * @return the weight of the fixture
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Setter func - set the name of the fixture.
   * Fixtures are immovable, so anything greater than 200 is acceptable.
   * @param weight of the fixture
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }

  /**
   * Getter function - get the puzzle of the fixture.
   * @return the puzzle of the fixture
   */
  public String getPuzzle() {
    return puzzle;
  }

  /**
   * Setter func - set the puzzle of the fixture.
   * @param puzzle of the fixture
   */
  public void setPuzzle(String puzzle) {
    this.puzzle = puzzle;
  }

  /**
   * Getter function - get the states of the fixture.
   * @return the states of the fixture
   */
  public String getStates() {
    return states;
  }

  /**
   * Setter func - set the states of the fixture.
   * @param states of the fixture
   */
  public void setStates(String states) {
    this.states = states;
  }

  /**
   * Getter function - get the description of the fixture.
   * @return the description of the fixture
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter func - set the description of the fixture.
   * @param description of the fixture
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Getter function - get the picture of the fixture.
   * @return the picture url of the fixture
   */
  public String getPicture() {
    return picture;
  }

  /**
   * Setter func - set the picture of the fixture.
   * @param picture of the fixture
   */
  public void setPicture(String picture) {
    this.picture = picture;
  }
}