package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import lombok.Getter;
import lombok.Setter;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Enemy extends AnimatedObject {
	public enum AnimationType {right, left, death}

	public final int SPEED_MULTIPLIER = 1;

	protected int points;
	protected int speed;
	protected int smartness;
	protected boolean isWallpass;
	@Setter
	protected int direction;

	public Enemy(Position position) {
		super(position);
		direction = (int) (Math.random() * 3);
	}

	public Enemy(Position position, int dir) {
		super(position);
		direction = dir;
	}

	public abstract void generateAnimationList();

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		entryList.add(Integer.toString(direction));
		return entryList;
	}
}
