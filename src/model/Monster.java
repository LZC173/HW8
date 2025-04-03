package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a monster, which extends the model.Challenge class.
 * Monsters can attack players causing damage and can be defeated.
 * Monsters can be defeated using either specific items or magic words.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Monster extends Challenge {

  private int damage;
  private boolean can_attack;
  private String attack;

  /**
   * Damage dealt by the monster.
   * @return amount of damage as an integer.
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Sets the damage value of the monster.
   * @param damage the damage value to set.
   */
  public void setDamage(int damage) {
    this.damage = damage;
  }

  /**
   * Checks if the monster can attack.
   * @return true if the monster can attack, otherwise return false.
   */
  public boolean isCan_attack() {
    return can_attack;
  }

  /**
   * Sets whether the monster can attack.
   * @param can_attack true if the model.Monster can attack, false to disable.
   */
  public void setCan_attack(boolean can_attack) {
    this.can_attack = can_attack;
  }

  /**
   * Gets description of the attack the monster can perform.
   * @return the attack description as a String.
   */
  public String getAttack() {
    return attack;
  }

  /**
   * Sets the attack type the model.Monster can perform.
   * @param attack The attack description to set.
   */
  public void setAttack(String attack) {
    this.attack = attack;
  }

  /**
   * Attacks a player, damaging their health by the monsters damage value.
   * @param player current player.
   * @return true if attack was successful, otherwise false if attack failed.
   */
  public boolean attackPlayer(Player player) {
    int playerHealth = player.getHealth();
    return player.setHealth(playerHealth + this.damage);
  }

  @Override
  public Integer solve(Item item) {
    if (solution.startsWith("'") && solution.endsWith("'")) {
      return SOLVE_WRONG_TYPE;
    } else if (solution.equalsIgnoreCase(item.getName())) {
      this.active = false;
      return SOLVE_SUCCESS;
    } else {
      return SOLVE_FAIL;
    }
  }


  @Override
  public Integer solve(String magicWord) {
    // delete all the punctuations white space
    String processedSolution = solution.replaceAll(
            "[\\p{Punct}\\s]", "");
    String processedMagicWord = magicWord.replaceAll(
            "[\\p{Punct}\\s]", "");

    if (!(solution.startsWith("'") || solution.endsWith("'"))) {
      return SOLVE_WRONG_TYPE;
    } else if (processedSolution.equalsIgnoreCase(processedMagicWord)) {
      this.active = false;
      return SOLVE_SUCCESS;
    } else {
      return SOLVE_FAIL;
    }
  }


  @Override
  public String toString() {
    return "model.Monster{"
            + "name='" + name + '\''
            + ", active=" + active
            + ", affects_target=" + affects_target
            + ", affects_player=" + affects_player
            + ", solution='" + solution + '\''
            + ", value=" + value
            + ", description='" + description + '\''
            + ", effects='" + effects + '\''
            + ", damage=" + damage
            + ", target='" + target + '\''
            + ", can_attack=" + can_attack
            + ", attack='" + attack + '\''
            + ", picture='" + picture + '\''
            + '}';
  }
}