package utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Position {
	private int x;
	private int y;

	public static Position newPosition(int x, int y) {
		return new Position(x, y);
	}

	public static Position newModulusPosition(int x, int y) {
		return new Position(x * EFFECTIVE_PIXEL_DIMENSION, y * EFFECTIVE_PIXEL_DIMENSION);
	}
}
