package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;

import controller.GameController;

/**
 * Class representation of view class which handles user interactions.
 */
public class View implements ViewParent {
  private Scanner scanner;
  private String[] inputs;
  private String playerName;
  JFrame frame; // the frame that we would use!!!!


  /**
   * Constructor initializes the scanner for user input.
   */
  public View() {
    scanner = new Scanner(System.in);
  }

  /**
   * Gets the player to input their name.
   *
   * @return players name as string
   */
    public String getPlayerName() {
    System.out.println("\n===== Welcome to the Game! =====");
    System.out.println("Enter your name: ");
    playerName = scanner.nextLine().trim();
    return playerName;
  }



  /**
   * Displays the game menu with comments.
   */
  public void displayMenu() {
    System.out.println("\n===== Game Menu=====");
    System.out.println("To move, enter: (N)orth, (S)outh, (E)ast, or (W)est.");
    System.out.println("Other actions: ");
    System.out.println(" - (I)nventory: Check your items.");
    System.out.println(" - (L)ook: Look around your location.");
    System.out.println(" - (U)se: Use an item.");
    System.out.println(" - (T)ake: Take an item.");
    System.out.println(" - (D)rop: Drop an item.");
    System.out.println(" - (X)amine: Examine something closely.");
    System.out.println(" - (A)nswer: Answer a question or provide a text solution.");
    System.out.println(" - Save: Save the game.(enter full word)");
    System.out.println(" - Load: load the game.(enter full word)");
    System.out.println(" - (Q)uit: Exit the game.");
    System.out.println("===================================");
  }

  /**
   * get the action choice from user.
   *
   * @return user input as a string
   */
  public String[] getInput() {
    System.out.println("Your choice: ");
    String input = scanner.nextLine().trim().toLowerCase();

    String[] partition = input.split(" ", 2); // split the action and the item(if any
    String action = partition[0]; // get action
    String item; // get item if any
    if (partition.length > 1) {
      item = partition[1];
    } else {
      item = "";
    }

    return new String[]{action, item};
  }


  /**
   * print out the message of the result of player action.
   *
   * @param message message of player action
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * close scanner when game ends.
   */
  public void closeScanner() {
    scanner.close();
  }



}
