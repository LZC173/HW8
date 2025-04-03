package model;

/**
 * Class representation of a challenge that a player can try to solve.
 * This can be a puzzle or an encounter with a monster.
 */
public abstract class Challenge {
  // common code represents the status of the operation
  public static final int SOLVE_SUCCESS = 1;
  // The attempt was valid but incorrect (e.g. wrong item used)
  // The player made a valid attempt, but the solution did not match.
  public static final int SOLVE_FAIL = -1;
  // Wrong input type (e.g. item vs text)
  public static final int SOLVE_WRONG_TYPE = 0;
  // An invalid or unexpected condition occurred, such as a null puzzle or item.
  public static final int SOLVE_ERROR = -2;
  public static final int SOLVE_VERY_FAIL = -3;
  //when item remaining use is less than 1

  // Common attributes for model.Puzzle and model.Monster
  protected String name;
  protected boolean active;
  protected boolean affects_target;
  protected boolean affects_player;
  protected String solution;
  protected int value;
  protected String description;
  protected String effects;
  protected String target;
  protected String picture;


  // Getters
  public String getName() {
    return name;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isAffects_target() {
    return affects_target;
  }

  public boolean isAffects_player() {
    return affects_player;
  }

  public String getSolution() {
    return solution;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }

  public String getEffects() {
    return effects;
  }

  public String getTarget() {
    return target;
  }

  public String getPicture() {
    return picture;
  }

  // Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setAffects_target(boolean affects_target) {
    this.affects_target = affects_target;
  }

  public void setAffects_player(boolean affects_player) {
    this.affects_player = affects_player;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEffects(String effects) {
    this.effects = effects;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  /**
   * An attempt to solve the challenge using an item.
   * @param item the item used in the attempt to solve challenge.
   * @return an integer representing the result of the attempt.
   */
  public abstract Integer solve(Item item);

  /**
   * An attempts to solve the challenge using a magic word.
   * @param magicWord the magic word used in the attempt.
   * @return an integer representing the result of the attempt.
   */
  public abstract Integer solve(String magicWord);
}