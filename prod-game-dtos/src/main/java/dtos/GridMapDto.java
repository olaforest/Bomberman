package dtos;

import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import gameplayModel.GridObjects.Concrete;
import gameplayModel.GridObjects.Exitway;
import gameplayModel.GridObjects.PowerUp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class GridMapDto {
	@XmlElement(required = true)
	private int spawnTimer;
	@XmlElement
	private int[] levelSpec;

	private List<Concrete> concreteLayout;
	private List<Brick> bricks;
	private List<Bomb> bombs;
	private List<Enemy> enemies;
	private Exitway exitway;
	private PowerUp powerUp;
	private Bomberman bomberman;
}
