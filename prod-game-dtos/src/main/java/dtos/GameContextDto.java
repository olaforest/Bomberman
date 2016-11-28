package dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"gameTime", "livesLeft", "score", "level", "actualLevel", "endGameEnemiesSpawned", "gridMap"})
@XmlRootElement(name = "GameContext")
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	@XmlAttribute(name = "EndGameEnemiesSpawned")
	private boolean endGameEnemiesSpawned;
	@XmlElement(name = "GridMap", required = true)
	private GridMapDto gridMap;
}
