package model;

/**
 * Representation of model.Puzzle class that extends the model.Challenge class.
 * It represents the game's puzzle logic.
 * Puzzles can be solved by using items or text-based magic word.
 */
public class Puzzle extends Challenge {


  /**
   * Attempts to solve the puzzle using an item.
   *
   * @param item the item to solve the puzzle
   * @return  SOLVE_WRONG_TYPE if the solution is a text and not an item.
   *          SOLVE_SUCCESS if the correct item is used.
   *          SOLVE_FAIL if the item doesn't match the puzzle's solution.
   */
  @Override
  public Integer solve(Item item) {
    if (solution.startsWith("'") && solution.endsWith("'")) {
      // "solution is a text, not an item"
      return SOLVE_WRONG_TYPE;
      // if solution is a text instead of a item ,then return
    } else if (solution.equalsIgnoreCase(item.getName())) {

      //check if solution equal to item name,if solution is a item
      this.active = false;
      //"model.Puzzle solved using the correct item!"
      return SOLVE_SUCCESS;
    } else {
      // "The item does not match the puzzle's solution."
      return SOLVE_FAIL;
    }
  }

  /**
   * Attempts to solve the puzzle using a magic word.
   *
   * @param magicWord the text input used to solve the puzzle.
   * @return SOLVE_WRONG_TYPE if the solution is a item not a text.
   *         SOLVE_SUCCESS if the correct magic word is used.
   *         SOLVE_FAIL if the magic word doesn't match the puzzle's solution.
   */
  @Override
  public Integer solve(String magicWord) {

    // delete all the punctions white space
    String processedSolution = solution.replaceAll(
            "[\\p{Punct}\\s]", "");
    String processedMagicWord = magicWord.replaceAll(
            "[\\p{Punct}\\s]", "");

    if (!(solution.startsWith("'") || solution.endsWith("'"))) {
      // "solution is a item not a text"
      return SOLVE_WRONG_TYPE;
      // if solution is a item instead of a string
    } else if (processedSolution.equalsIgnoreCase(processedMagicWord)) {
      // check if solution equal to magic word
      this.active = false;
      // "model.Puzzle solved using the correct magic word!"
      return SOLVE_SUCCESS;
    } else {
      // "The magicword does not match the puzzle's solution."
      return SOLVE_FAIL;
    }
  }
}