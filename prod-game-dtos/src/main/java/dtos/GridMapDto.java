package dtos;

import dtos.GridObjects.ConcreteDto;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlType(propOrder = {"spawnTimer", "levelSpec", "concreteLayout"})
@XmlAccessorType(XmlAccessType.FIELD)
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
