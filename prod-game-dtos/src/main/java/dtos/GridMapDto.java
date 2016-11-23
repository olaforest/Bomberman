package dtos;

import dtos.GridObjects.ConcreteDto;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Getter
@XmlType(propOrder = {"spawnTimer", "levelSpec", "concreteLayout"})
public class GridMapDto {
	@XmlElement(required = true)
	private int spawnTimer;
	@XmlElement(required = true)
	private int[] levelSpec;

	@XmlElementWrapper(name = "concreteLayout", required = true)
	@XmlElement(name = "concrete")
	private List<ConcreteDto> concreteLayout;
//	private List<Brick> bricks;
//	private List<Bomb> bombs;
//	private List<Enemy> enemies;
//	private Exitway exitway;
//	private PowerUp powerUp;
//	private Bomberman bomberman;
}
