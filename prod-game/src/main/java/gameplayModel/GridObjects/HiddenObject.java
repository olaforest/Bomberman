package gameplayModel.GridObjects;

import gameplayModel.GridObject;
import lombok.Getter;

@Getter
public abstract class HiddenObject extends GridObject {
	private final int brickIndex;

	HiddenObject(int x, int y, int index) {
		super(x, y);
		this.brickIndex = index;
	}

	public static int generateIndex(int size) {
		return (int) (Math.random() * size);
	}
}
