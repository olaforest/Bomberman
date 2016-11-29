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
@XmlRootElement(name = "GameContext")
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	@XmlAttribute(name = "EndGameEnemiesSpawned")
	private boolean endGameEnemiesSpawned;
	@XmlElement(name = "GridMap", required = true)
	private GridMapDto gridMap;
}
