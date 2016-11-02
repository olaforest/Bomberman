package dtos;

import gameplayModel.GridMap;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class GameContextDto {
	private int gameTime, livesLeft, score, level, actualLevel;
	private boolean isEndGameEnemiesSpawned;
	private GridMap gridMap;
}
