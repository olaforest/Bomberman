package dtos;

import dtos.GridObjects.ConcreteDto;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"spawnTimer", "levelSpec", "concreteLayout"})
public class GridMapDto {

	@XmlElement(name = "SpawnTimer", required = true)
	private int spawnTimer;
	@XmlElement(name = "LevelSpec", required = true)
	private int[] levelSpec;

	@XmlElementWrapper(name = "ConcreteLayout", required = true)
	@XmlElement(name = "Concrete")
	private List<ConcreteDto> concreteLayout;
//	private List<Brick> bricks;
//	private List<Bomb> bombs;
//	private List<Enemy> enemies;
//	private Exitway exitway;
//	private PowerUp powerUp;
//	private Bomberman bomberman;
}
