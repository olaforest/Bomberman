package utilities;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

	public int getModX() {
		return x / EFFECTIVE_PIXEL_DIM;
	}

	public int getModY() {
		return y / EFFECTIVE_PIXEL_DIM;
	}

	public boolean isSame(Position position) {
		return isSameX(position.getX()) && isSameY(position.getY());
	}

	public static Position create(int x, int y) {
		return new Position(x, y);
	}

	public static Position create(Position position) {
		return new Position(position.getX(), position.getY());
	}

	public static Position modulus(int x, int y) {
		return new Position(x * EFFECTIVE_PIXEL_DIM, y * EFFECTIVE_PIXEL_DIM);
	}

	private boolean isSameY(int y) {
		return this.y == y;
	}

	private boolean isSameX(int x) {
		return this.x == x;
	}
}
