package dtos;

import dtos.gridObjects.ConcreteDto;
import dtos.gridObjects.ExitwayDto;
import dtos.gridObjects.PowerUpDto;
import dtos.gridObjects.animatedObjects.BombDto;
import dtos.gridObjects.animatedObjects.BombermanDto;
import dtos.gridObjects.animatedObjects.BrickDto;
import dtos.gridObjects.animatedObjects.EnemyDto;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"spawnTimer", "levelSpec", "concreteLayout", "bricks", "bombs", "enemies", "exitway", "powerUp", "bomberman"})
public class GridMapDto {

	@XmlElement(name = "SpawnTimer", required = true)
	private int spawnTimer;
	@XmlElement(name = "LevelSpec", required = true)
	private int[] levelSpec;

	@XmlElementWrapper(name = "ConcreteLayout", required = true)
	@XmlElement(name = "Concrete")
	private List<ConcreteDto> concreteLayout;
	private List<BrickDto> bricks;
	private List<BombDto> bombs;
	private List<EnemyDto> enemies;
	private ExitwayDto exitway;
	private PowerUpDto powerUp;
	private BombermanDto bomberman;
}
