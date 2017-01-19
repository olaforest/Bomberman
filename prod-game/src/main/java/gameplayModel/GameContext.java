package gameplayModel;

import gameplayController.GameplayController;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static gameplayModel.LevelManager.importLevels;
import static java.util.Arrays.asList;

@Getter
public class GameContext {
	public static final int MAX_GAME_TIME = 200 * 1000;
	public static final int INITIAL_LIVES_LEFT = 2;

	@Setter
	private boolean endGameEnemiesSpawned;
	private int gameTime, livesLeft, score;
	private GridMap gridMap;

	public GameContext() {
		gameTime = MAX_GAME_TIME;
		livesLeft = INITIAL_LIVES_LEFT;
		score = 0;
		endGameEnemiesSpawned = false;
		gridMap = new GridMap(importLevels().get(0));
	}

	public GameContext(int time, int lives, int score, GridMap gridMap) {
		gameTime = time;
		livesLeft = lives;
		this.score = score;
		endGameEnemiesSpawned = false;
		this.gridMap = gridMap;
	}

	public void restartMap() {
		gridMap = new GridMap(gridMap.getLevel());
	}

	public void decreaseGameTime() {
		if (gameTime > 0)
			gameTime -= GameplayController.TIMEOUT;
	}

	public void initializeGameTime() {
		gameTime = MAX_GAME_TIME;
	}

	public void increaseLivesLeft() {
		livesLeft++;
	}

	public void decreaseLivesLeft() {
		if (livesLeft > 0)
			livesLeft--;
	}

	public void increaseScore(int additionnalPoints) {
		score += additionnalPoints;
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>(asList(Integer.toString(gameTime), Integer.toString(livesLeft),
				Integer.toString(score), "GridMap"));
		gridMap.toCSVEntry().forEach(entryList::add);
		return entryList;
	}
}
