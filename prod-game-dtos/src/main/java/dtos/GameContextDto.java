package dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "gameContext")
@XmlType(propOrder = {"gameTime", "livesLeft", "score", "level", "actualLevel", "endGameEnemiesSpawned", "gridMap"})
@XmlAccessorType(XmlAccessType.FIELD)
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	private boolean endGameEnemiesSpawned;
	@XmlElement(required = true)
	private GridMapDto gridMap;
}
