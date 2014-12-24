
package gameplayModel;

import java.util.ArrayList;

import gameplayController.GameplayController;

/**
 * This class manages the user interface as well as high level components of the gameplay. Aspects such as Score,
 * Time, current level, and the generation of the gridMap are all controlled and modified through internal
 * methods.
 * 
 * @author Olivier Laforest
 *
 */
public class GameContext {

	public final int MAX_GAME_TIME = 200 * 1000;
	public final int INITIAL_LIVES_LEFT = 2;
	
	private int[][] levelSpec = {	{6, 0, 0, 0, 0, 0, 0, 0, 2},
									{3, 3, 0, 0, 0, 0, 0, 0, 1},
									{2, 2, 2, 0, 0, 0, 0, 0, 5},
									{1, 1, 2, 2, 0, 0, 0, 0, 3},
									{0, 4, 3, 0, 0, 0, 0, 0, 1},
									{-1, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 2, 3, 2, 0, 0, 0, 0, 1},
									{0, 2, 3, 0, 2, 0, 0, 0, 2},
									{0, 1, 2, 4, 0, 0, 0, 0, 5},
									{0, 1, 1, 4, 1, 0, 0, 0, 6},
									{0, 1, 1, 1, 3, 1, 0, 0, 4},
									{0, -1, 0, 0, 0, 0, 0, 0, 0},
									{0, 1, 2, 3, 1, 1, 0, 0, 1},
									{0, 1, 1, 1, 4, 1, 0, 0, 1},
									{0, 0, 3, 3, 3, 0, 0, 0, 5},
									{0, 0, 0, 0, 0, 7, 1, 0, 6},
									{0, 0, 1, 3, 3, 0, 1, 0, 2},
									{0, 0, -1, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 3, 4, 0, 1, 0, 4},
									{0, 0, 5, 0, 2, 0, 1, 0, 1},
									{3, 3, 0, 0, 0, 0, 2, 0, 6},
									{1, 1, 3, 0, 0, 1, 2, 0, 1},
									{0, 1, 1, 1, 2, 1, 2, 0, 5},
									{0, 0, 0, -1, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 4, 3, 2, 0, 6},
									{0, 0, 4, 3, 1, 0, 1, 0, 5},
									{0, 0, 2, 2, 2, 2, 1, 0, 1},
									{0, 0, 1, 1, 4, 2, 1, 0, 5},
									{0, 2, 1, 1, 2, 2, 1, 0, 6},
									{0, 0, 0, 0, -1, 0, 0, 0, 0},
									{1, 1, 1, 1, 2, 1, 1, 0, 8},
									{1, 1, 0, 0, 5, 1, 1, 0, 2},
									{0, 1, 3, 3, 1, 0, 1, 0, 1},
									{0, 0, 0, 0, 2, 5, 2, 0, 5},
									{0, 0, 3, 2, 1, 2, 1, 0, 7},
									{0, 0, 0, 0, -1, 0, 0, 0, 0},
									{0, 2, 2, 2, 2, 2, 0, 0, 4},
									{0, 1, 1, 3, 4, 0, 1, 0, 1},
									{0, 0, 2, 2, 3, 1, 2, 0, 5},
									{0, 0, 2, 3, 3, 0, 2, 0, 8},
									{0, 0, 2, 1, 3, 1, 2, 0, 6},
									{0, 0, 0, 0, 0, 0, -1, 0, 0},
									{0, 0, 2, 2, 3, 0, 3, 0, 7},
									{0, 0, 2, 1, 3, 1, 3, 0, 5},
									{0, 0, 2, 2, 3, 0, 3, 0, 2},
									{0, 0, 1, 1, 2, 2, 4, 0, 4},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 1, 2, 3, 0, 4, 0, 8},
									{0, 0, 1, 1, 3, 1, 4, 0, 5},
									{0, 0, 0, 1, 3, 1, 5, 0, 4},
									{0, 0, 0, 1, 2, 1, 6, 0, 6},
									{0, 0, 0, 1, 2, 1, 6, 0, 5},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 0, 0, 2, 2, 6, 0, 8},
									{0, 0, 0, 0, 2, 2, 6, 0, 4},
									{0, 0, 0, 0, 2, 2, 6, 0, 6},
									{0, 0, 0, 0, 2, 1, 6, 1, 5},
									{0, 0, 0, 0, 1, 2, 6, 1, 7},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 0, 0, 1, 2, 5, 2, 8}};
	
	private int gameTime, livesLeft, score, level, actualLevel;
	
	private boolean endGameEnemiesSpawned;
	
	private GridMap gridMap;
	
	/**
	 * Constructs a new default game at level 1 with starting base parameters
	 */
	public GameContext() {
		gameTime = MAX_GAME_TIME;
		livesLeft = INITIAL_LIVES_LEFT;
		score = 0;
		level = 0;
		actualLevel = 0;
		
		endGameEnemiesSpawned = false;
		
		gridMap = new GridMap(levelSpec[level]);

	}
	
	/**
	 * Constructs a new default game at selectedLevel with starting base parameters
	 * @param selectedLevel The selected starting level of a new game
	 */
	public GameContext(int selectedLevel) {
		gameTime = MAX_GAME_TIME;
		livesLeft = INITIAL_LIVES_LEFT;
		score = 0;
		actualLevel = selectedLevel;
		level = computeLevel();
				
		endGameEnemiesSpawned = false;
		
		gridMap = new GridMap(levelSpec[level]);

	}
	
	/**
	 * Constructs a specified game from a save
	 * @param time The remaining amount of time on the game timer
	 * @param lives The number of remaining lives
	 * @param score The current score of the save
	 * @param level The current gameplay level of the save 
	 * @param gridMap positions and conditions of all gridMap objects from the save
	 */
	public GameContext(int time, int lives, int score, int level, GridMap gridMap) {
		gameTime = time;
		livesLeft = lives;
		this.score = score;
		this.level = level;
		
		computeActualLevel();
		
		endGameEnemiesSpawned = false;
		
		this.gridMap = gridMap;

	}
	
	/**
	 * Resets the current level
	 */
	public void restartMap() {
		gridMap = new GridMap(levelSpec[level]);
	}
	
	/**
	 * @return The remaining time left on the game timer
	 */
	public int getGameTime() {
		return gameTime;
	}
	
	/**
	 * 
	 */
	public void decreaseGameTime() {
		if (gameTime > 0)
			gameTime -= GameplayController.TIMEOUT;
	}
	
	/**
	 * Resets the game timer to the maximum default starting time
	 */
	public void initializeGameTime() {
		gameTime = MAX_GAME_TIME;
	}
	
	/**
	 * @return Remaining lives left for current game session
	 */
	public int getLivesLeft() {
		return livesLeft;
	}
	
	/**
	 * Increases the number of lives by one
	 */
	public void increaseLivesLeft() {
		livesLeft++;
	}
	
	/**
	 * Decreases the number of remaining lives by one
	 */
	public void decreaseLivesLeft() {
		if (livesLeft > 0)
			livesLeft--;
	}
	
	/**
	 * @return Current gameplay score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @param additionnalPoints extra bonus point added to the score
	 */
	public void increaseScore(int additionnalPoints) {
		score += additionnalPoints;
	}
	
	/**
	 * @return an array containing the gripMap level layout of enemies and bricks
	 */
	public int[] getLevelSpecification() {
		return levelSpec[level];
	}
	
	/**
	 * @return the actual level of the current game not counting the special levels
	 */
	public int getActualLevel() {
		return actualLevel;
	}
	
	/**
	 * Increases current level by one
	 */
	public void increaseLevel() {
		level++;
		computeActualLevel();
	}
	
	/**
	 * @param setlevel 
	 */
	public void setLevel(int setlevel){
		level = setlevel;
		computeActualLevel();
	}
	
	/**
	 * @return the current level of the current game with speical levels included
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * @return the current positions of all objects on the gameplay grid
	 */
	public GridMap getGridMap() {
		return gridMap;
	}
	
	/**
	 * @return a boolean to check if the timer has reached 0 and if end of level enemies have spawned
	 */
	public boolean getEndGameEnemiesStatus() {
		return endGameEnemiesSpawned;
	}
	
	/**
	 * @param status a boolean to determine whether to spawn end of level enemies 
	 */
	public void setEndGameEnemiesStatus(boolean status) {
		endGameEnemiesSpawned = status;
	}
	
	/**
	 * Writes the current GameContext to the CSV
	 * @return an ArrayList containing the current GameContext
	 */
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(gameTime));
		entryList.add(Integer.toString(livesLeft));
		entryList.add(Integer.toString(score));
		entryList.add(Integer.toString(level));
		entryList.add("GridMap");
		
		for (String token : gridMap.toCSVEntry())
			entryList.add(token);
		
		return entryList; 
	}
	//This method calculates the level of the current game not including the special levels
	private void computeActualLevel() {
		
		int specialLevel = 0;
		
		for (int i = 0 ; i <= level ; i++) {
			if (levelSpec[i][8] == 0) {
				specialLevel++;
			}
		}
		actualLevel = level - specialLevel;
	}
	//This method calculates the level of the current game with special levels 
	private int computeLevel() {
		
		int specialLevel = 0;
		
		for (int i = 0 ; i <= actualLevel ; i++) {
			if (levelSpec[i][8] == 0) {
				specialLevel++;
			}
		}
		return actualLevel + specialLevel;
	}
}


