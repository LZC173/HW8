import enginedriver.GameEngineApp;
import java.io.IOException;
import java.util.Locale;

/**
 * Smoke test to run our game.
 */
public class SmokeTest {
  /**
   * main method.
   * @param args user input
   * @throws IOException exception
   */


  public static void main(String[] args) throws IOException {
    Locale.setDefault(Locale.ENGLISH);
    GameEngineApp gameEngineApp = new GameEngineApp();
    gameEngineApp.start("src/json_data");
  }
}