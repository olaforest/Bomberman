package gameplayModel;

import gameplayController.GameplayController;
import gameplayModel.gridObjects.animatedObjects.EnemyType;
import lombok.Getter;
import lombok.Setter;
import utilities.Position;

import java.util.*;

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

	public void updateBombermanStatus(ArrayDeque<Integer> activeDirectionKeys) {
		gridMap.updateBombermanStatus(activeDirectionKeys);
	}

	public void updateEnemiesAnim() {
		gridMap.updateEnemiesAnim();
	}

	public void updateBombsStatus() {
		gridMap.updateBombsStatus();
	}

	public void updateBricksStatus() {
		gridMap.updateBricksStatus();
	}

	public void destroyObjectInExplodedBombsRange(boolean isBonusLevel, EnemyType hardestEnemyType) {
		gridMap.destroyObjectInExplodedBombsRange(isBonusLevel, hardestEnemyType);
	}

	public void checkCollisionBtwBombermanAndBricks() {
		gridMap.checkCollisionBtwBombermanAndBricks();
	}

	public void checkCollisionBtwBombermanAndEnemies() {
		gridMap.checkCollisionBtwBombermanAndEnemies();
	}

	public boolean checkCollisionBtwBombermanAndExitway() {
		return gridMap.checkCollisionBtwBombermanAndExitway();
	}

	public void checkCollisionBtwBombermanAndPowerUp() {
		gridMap.checkCollisionBtwBombermanAndPowerUp();
	}

	public void addBomb() {
		gridMap.addBomb();
	}

	public Position getViewPortPosition() {
		return gridMap.getViewPortPosition();
	}

	public void detonateBomb() {
		gridMap.detonateBomb();
	}

	public boolean isGameOver() {
		return gridMap.isGameOver();
	}

	public void removePowerUps() {
		gridMap.removePowerUps();
	}

	public void restartMap() {
//		gridMap = new GridMap(gridMap.getLevel());
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
		List<String> entryList = new ArrayList<>(asList(Integer.toString(gameTime), Integer.toString(livesLeft), Integer.toString(score), "GridMap"));
		entryList.addAll(gridMap.toCSVEntry());
		return entryList;
	}
}
