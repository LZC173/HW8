package enginedriver;

import controller.GameController;
import controller.GameData;
import controller.LoadGameData;
import controller.PlayerLoad;
import controller.SaveGameData;
import controller.SavePlayerData;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Class represents a game engine which choose which file you want to use which
 * game you want to play.
 *
 */
public class GameEngineApp {

  /**
   * Start the game choose file to play and PlayerName.
   *
   */
  public void start(String path) {
    try {
      // get the path to the saves directory
      String savesDirectory = Paths.get(path).toString();

      // list all json files
      File savesFolder = new File(savesDirectory);
      File[] files = savesFolder.listFiles((dir, name) -> name.endsWith(".json"));

      // handle if no json files are found
      if (files == null || files.length == 0) {
        System.out.println("No JSON files found in the directory.");
        return;
      }

      // display available files
      System.out.println("Available game json files:");
      for (int i = 0; i < files.length; i++) {
        System.out.println((i + 1) + ". " + files[i].getName());
      }

      // prompt the user to select a file
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the number of the file you want to use:");

      String input = scanner.nextLine().trim();
      int selectedIndex = Integer.parseInt(input) - 1;

      // validate the selected index
      if (selectedIndex >= 0 && selectedIndex < files.length) {
        String jsonPath = files[selectedIndex].getPath();

        // initialize the controller.GameController with the selected file path

        String mode = "Graphic";//yijie

        GameController controller = new GameController(jsonPath,mode);
        controller.gameLoop();
      } else {
        System.out.println("Invalid selection. Exiting. Please restart the game");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
