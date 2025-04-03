package model;

/**
 * an enum for health status.
 */
public enum HealthStatus {
  SLEEP("You're soundly asleep."), // health <= 0
  WOOZY("Your health is very low, and you're WOOZY!"), // health <= 20
  FATIGUED("You're feeling fatigued, take care!"), // health <= 70
  AWAKE("You're still healthy and wide awake."); // otherwise

  private final String message;

  HealthStatus(String message) {
    this.message = message;
  }

  public String getHealthMessage() {
    return message;
  }
}