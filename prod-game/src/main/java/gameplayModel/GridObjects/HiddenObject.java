package gameplayModel.GridObjects;

import gameplayModel.GridObject;
import lombok.Getter;
import utilities.Position;

@Getter
public abstract class HiddenObject extends GridObject {

	HiddenObject(Position position) {
		super(position);
	}

	public static int generateIndex(int size) {
		return (int) (Math.random() * size);
	}
}
