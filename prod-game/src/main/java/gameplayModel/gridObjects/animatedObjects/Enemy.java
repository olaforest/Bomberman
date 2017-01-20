package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import lombok.Getter;
import lombok.Setter;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;
import static java.util.Arrays.asList;
import static utilities.Position.create;

@Getter
public class Enemy extends AnimatedObject {
	public enum AnimationType {right, left, death}

	public final int SPEED_MULTIPLIER = 1;

	protected final int points;
	protected final int speed;
	protected final int smartness;
	protected final boolean isWallpass;
	@Setter protected int direction;

	public Enemy(Enemies enemyType, Position position) {
		this(enemyType, position, (int) (random() * 3));
	}

	public Enemy(Enemies enemyType, Position position, int direction) {
		super(position);
		points = enemyType.getPoints();
		speed = enemyType.getSpeed();
		smartness = enemyType.getSmartness();
		isWallpass = enemyType.isWallpass();
		animationList = generateAnimationList(asList(AnimationType.values()), enemyType.getAnimParam(), 2);
		this.direction = direction;
	}

	public static Enemy createEnemy(Enemies type, int xPosition, int yPosition) {
		final Position position = create(xPosition, yPosition);
		return new Enemy(type, position);
	}

	public void generateAnimationList() {
//		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 2);
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
