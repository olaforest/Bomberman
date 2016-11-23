package dtos;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@XmlType(propOrder = {"gameTime", "livesLeft", "score", "level", "actualLevel", "endGameEnemiesSpawned", "gridMap"})
@XmlAccessorType(XmlAccessType.FIELD)
public class GameContextDto {

	private int gameTime, livesLeft, score, level, actualLevel;
	private boolean endGameEnemiesSpawned;
	@XmlElement(required = true)
	private GridMapDto gridMap;
}
