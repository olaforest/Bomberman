package dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"gameTime", "livesLeft", "score", "level", "actualLevel", "endGameEnemiesSpawned", "gridMap"})
@XmlRootElement(name = "gameContext")
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	@XmlAttribute
	private boolean endGameEnemiesSpawned;
	@XmlElement(required = true)
	private GridMapDto gridMap;
}
