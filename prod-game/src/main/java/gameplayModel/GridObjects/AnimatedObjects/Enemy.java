package gameplayModel.GridObjects.AnimatedObjects;

import gameplayModel.GridObjects.AnimatedObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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

	public Enemy(int x, int y) {
		super(x, y);
		direction = (int) (Math.random() * 3);
	}

	public Enemy(int x, int y, int dir) {
		super(x, y);
		direction = dir;
	}

	public abstract void generateAnimationList();

	public ArrayList<String> toCSVEntry() {
		ArrayList<String> entryList = new ArrayList<>();
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(direction));
		return entryList;
	}
}
