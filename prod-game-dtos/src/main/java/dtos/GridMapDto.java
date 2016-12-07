package dtos;

import dtos.gridObjects.ConcreteDto;
import dtos.gridObjects.ExitwayDto;
import dtos.gridObjects.PowerUpDto;
import dtos.gridObjects.animatedObjects.BombDto;
import dtos.gridObjects.animatedObjects.BombermanDto;
import dtos.gridObjects.animatedObjects.BrickDto;
import dtos.gridObjects.animatedObjects.EnemyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"spawnTimer", "levelSpec", "concreteLayout", "bricks", "bombs", "enemies", "exitway", "powerUp", "bomberman"})
public class GridMapDto {

	@XmlElement(required = true)
	private int spawnTimer;
	@XmlElement(required = true)
	private int[] levelSpec;

	@XmlElementWrapper(name = "concreteLayout", required = true)
	@XmlElement(name = "concrete")
	private List<ConcreteDto> concreteLayout;

	@XmlElementWrapper(name = "bricks", required = true)
	@XmlElement(name = "brick")
	private List<BrickDto> bricks;

	@XmlElementWrapper(name = "bombs", required = true)
	@XmlElement(name = "bomb")
	private List<BombDto> bombs;

	@XmlElementWrapper(name = "enemies", required = true)
	@XmlElement(name = "enemy")
	private List<EnemyDto> enemies;

	@XmlElement
	private ExitwayDto exitway;
	@XmlElement
	private PowerUpDto powerUp;
	@XmlElement
	private BombermanDto bomberman;
}
