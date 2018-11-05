package gameplayModel.gridObjects.animatedObjects;

import static gameplayView.AnimationType.*;
import static java.lang.Math.random;
import static java.util.Arrays.asList;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimationType;
import lombok.Getter;
import lombok.Setter;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Enemy extends AnimatedObject {
	public static final List<AnimationType> ANIMATION_TYPES = asList(Left, Right, Death);

	private final int points;
	private final int speed;
	private final int smartness;
	private final boolean isWallpass;
	@Setter private int direction;

	private Enemy(EnemyType enemyType, Position position) {
		this(enemyType, position, (int) (random() * 3));
	}

	public Enemy(EnemyType enemyType, Position position, int direction) {
		super(position, enemyType.getAnimParams());
		points = enemyType.getPoints();
		speed = enemyType.getSpeed();
		smartness = enemyType.getSmartness();
		isWallpass = enemyType.isWallpass();
		this.direction = direction;
	}

	public static Enemy createEnemy(EnemyType type, Position position) {
		return new Enemy(type, position);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		entryList.add(Integer.toString(direction));
		return entryList;
	}
}
