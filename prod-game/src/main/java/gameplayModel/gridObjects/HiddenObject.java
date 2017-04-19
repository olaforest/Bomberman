package gameplayModel.gridObjects;

import static java.lang.Math.random;

public interface HiddenObject {

	static int generateIndex(int size) {
		return (int) (random() * size);
	}
}
