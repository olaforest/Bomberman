package menuModel;

import gameplayModel.GameContext;

import java.util.ArrayList;

/**
 * This class stores all data for a saved game. This includes the name of the game save,
 * the date when created, and all gameplay objects within the game itself at the time of generation.
 *
 * @author Eric Liou
 * @author Olivier Laforest
 */
public class SavedGame {

	private String gameName, gameDate;

	private GameContext gameContext;

	/**
	 * Constructs a new save game
	 *
	 * @param name    the name assigned to the save
	 * @param date    the date when the save was generated
	 * @param context the position and condition of all gameplay objects
	 */
	public SavedGame(String name, String date, GameContext context) {

		gameName = name;
		gameDate = date;
		gameContext = context;

	}

	/**
	 * @return The name of the save file
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @return The date when the save was created
	 */
	public String getGameDate() {
		return gameDate;
	}

	/**
	 * @return All gameplay data including positions and characteristics of all objects
	 */
	public GameContext getGameContext() {
		return gameContext;
	}

	/**
	 * This method stores all data of the save into the CSV
	 *
	 * @return An ArrayList of save game parameters
	 */
	public ArrayList<String> toCSVEntry() {

		ArrayList<String> entryList = new ArrayList<String>();

		entryList.add(gameName);
		entryList.add(gameDate);
		entryList.add("GameContext");

		for (String token : gameContext.toCSVEntry())
			entryList.add(token);

		return entryList;
	}
}
