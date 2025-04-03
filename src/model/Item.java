package model;

/**
 * Represents an item in the game.
 */
public class Item {
  private String name;
  private int weight;
  private int max_uses;
  private int uses_remaining;
  private int value;
  private String when_used;
  private String description;
  private String picture;

  /**
   * Default constructor. Initializes an item with default values.
   */
  public Item() {
    this.name = "Unknown model.Item";
    this.weight = 1;
    this.max_uses = 1;
    this.uses_remaining = 1;
    this.value = 0;
    this.when_used = "";
    this.description = "No description provided.";
    this.picture = "";
  }

  // Getters

  public String getName() {

    return name;
  }

  public int getWeight() {

    return weight;
  }

  public int getMax_uses() {

    return max_uses;
  }

  public int getUses_remaining() {

    return uses_remaining;
  }

  public int getValue() {

    return value;
  }

  public String getWhen_used() {

    return when_used;
  }

  public String getDescription() {

    return description;
  }

  public String getPicture() {

    return picture;
  }


  /**
   * Sets the item's name.
   * @param name name of the item.
   */
  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      this.name = "Unknown model.Item";
    } else {
      this.name = name.trim();
    }
  }

  /**
   * Sets the item's weight.
   * @param weight weight value.
   */
  public void setWeight(int weight) {
    this.weight = Math.max(weight, 0);
  }

  /**
   * Sets the item's maximum uses.
   * If necessary, updates uses_remaining.
   * @param max_uses maximum uses value.
   */
  public void setMax_uses(int max_uses) {
    this.max_uses = Math.max(max_uses, 0);
    this.uses_remaining = Math.min(this.uses_remaining, this.max_uses);
  }

  /**
   * Sets the remaining uses, making sure it's within valid range.
   * @param uses_remaining remaining uses.
   */
  public void setUses_remaining(int uses_remaining) {
    this.uses_remaining = Math.min(Math.max(uses_remaining, 0), this.max_uses);
  }

  /**
   * Sets the item's value.
   * @param value value of the item.
   */
  public void setValue(int value) {
    this.value = Math.max(value, 0);
  }

  /**
   * Sets the text displayed when the item is used.
   * @param when_used use message.
   */
  public void setWhen_used(String when_used) {
    if (when_used != null) {
      this.when_used = when_used;
    } else {
      this.when_used = "";
    }
  }

  /**
   * Sets the item's description.
   * @param description item description.
   */
  public void setDescription(String description) {
    if (description != null) {
      this.description = description;
    } else {
      this.description = "";
    }
  }

  /**
   * Sets the item's picture reference.
   * @param picture picture reference.
   */
  public void setPicture(String picture) {
    if (picture != null) {
      this.picture = picture;
    } else {
      this.picture = "";
    }
  }

  /**
   * Uses the item once if possible.
   * @return true if item was used successfully, false if no uses remaining.
   */
  public boolean use() {
    if (uses_remaining > 0) {
      uses_remaining--;
      return true;
    }
    return false;
  }

  /**
   * Repairs the item.
   */
  public void repair() {
    this.uses_remaining = this.max_uses;
  }

  /**
   * Checks if no uses remaining.
   * @return true if no uses remain, false if usable.
   */
  public boolean is_broken() {
    return uses_remaining <= 0;
  }



  /**
   * Returns a string representation of the model.Item.
   * @return A formatted string that shows the item attributes.
   */
  @Override
  public String toString() {
    return "model.Item{"
            + "name='" + name + '\''
            + ", weight=" + weight
            + ", max_uses=" + max_uses
            + ", uses_remaining=" + uses_remaining
            + ", value=" + value
            + ", when_used='" + when_used + '\''
            + ", description='" + description + '\''
            + ", picture='" + picture + '\''
            + '}';
  }

  /**
   * Compares items by name.
   * @param obj Object to compare with.
   * @return true if items have same name.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Item item = (Item) obj;
    return name.equalsIgnoreCase(item.name);
  }


}