package gameplayModel.GridObjects;

import gameplayModel.GridObject;
import lombok.Getter;

@Getter
public abstract class HiddenObject extends GridObject {

	HiddenObject(int x, int y) {
		super(x, y);
	}

	public static int generateIndex(int size) {
		return (int) (Math.random() * size);
	}
}
