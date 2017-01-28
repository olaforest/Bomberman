package utilities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIMENSION;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Position {
	private int x;
	private int y;

	public void incrementX(int delta) {
		x += delta;
	}

	public void incrementY(int delta) {
		y += delta;
	}

	public void decrementX(int delta) {
		x -= delta;
	}

	public void decrementY(int delta) {
		y -= delta;
	}

	public static Position create(int x, int y) {
		return new Position(x, y);
	}

	public static Position modulus(int x, int y) {
		return new Position(x * EFFECTIVE_PIXEL_DIMENSION, y * EFFECTIVE_PIXEL_DIMENSION);
	}

	public boolean isSame(Position position) {
		return isSameX(position.getX()) && isSameY(position.getY());
	}

	private boolean isSameY(int y) {
		return this.y == y;
	}

	private boolean isSameX(int x) {
		return this.x == x;
	}
}
