package dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"gameTime", "livesLeft", "score", "level", "actualLevel", "gridMap"})
@XmlRootElement(name = "gameContext")
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	@XmlAttribute(name = "endGameEnemiesSpawned")
	private boolean endGameEnemiesSpawned;
	@XmlElement(name = "gridMap", required = true)
	private GridMapDto gridMap;
}
