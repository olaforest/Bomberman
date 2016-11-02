package dtos;

import gameplayModel.GridMap;

public class GameContextDto {
	private int gameTime, livesLeft, score, level, actualLevel;
	private boolean isEndGameEnemiesSpawned;
	private GridMap gridMap;
}
