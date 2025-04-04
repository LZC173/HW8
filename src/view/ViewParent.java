package view;
public interface ViewParent {
  String getPlayerName();
  void displayMessage(String message);
  void displayMenu();
  String[] getInput();
}