# HW-8

For HW 7, we had a very general and broad idea of what classes, interfaces, and sequences our group wanted for our implementation of our project. We initially had very basic methods planned out for each of our classes/interfaces. For HW 8, we added some additional classes and methods that we thought would be more useful to make our test cases work. We also expanded our UML from HW 7 to match our most recent design of the project.

model.Item (class): We initially had model.Item designed as an interface for multiple "item" classes, but for HW 8 we ended up deciding to make it an individual class to make things simpler. We also added extra variable names and methods (mostly getters and setters) to calculate the remaining number of uses, its value, and determining whether or not the item is currently being used by the player.

model.Player: Generally, we added more methods than what we had for HW 7. This is so that the player class can move the player by assigning the avatar to different rooms, determining whether or not the avatar is asleep, getting the room number of the "next room" about to be entered, "solving" or defeating monsters, picking up or dropping an item, and checking the avatar's inventory.

controller.GameController: This class determines how the user's input with the use of the standard MVC architectural pattern. The input is determined by each of the methods in the class (e.g. (M) for movePlayer (String direction), which moves the player to a different room depending on the given direction).

controller.GameData: This class basically takes data used in the controller.GameController class in and stores it in different lists.

model.Challenge: This class determines the specifics of each challenge. Generally, it keeps track of the value, target, solution, and whether or not it's active for both puzzles and monsters.

model.Puzzle: We initially made this class as just an extension to the model.Room class but then decided to also make it an extension of the model.Challenge abstract class to further specify what it is and what it does.

model.Monster: We initially made this class to have similar stats to the player class but eventually we decided to connect it to the model.Challenge abstract class instead to further specify what it is and what it does.

model.Fixture: Designed to be a type of room that also takes in a specific puzzle.

model.Room: Designed to be a type of room that also determines it's location based on direction (North, East, West, or South) and what may be in the room (e.g. monster, puzzle).

view.View: This class reads and displays the user's input given by the game controller after every "turn".

model.Map: This class gets the data of the rooms and stores the rooms themselves in a list.
