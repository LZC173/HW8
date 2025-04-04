package view;

import controller.GameController;

public interface ViewParent {
  String getPlayerName();
  void displayMessage(String message);
  void displayMenu();
  String[] getInput();
  void setController(GameController controller);
}