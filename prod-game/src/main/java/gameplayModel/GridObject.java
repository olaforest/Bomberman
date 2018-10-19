package gameplayModel;

import static gameplayModel.GridMap.MAP_HEIGHT;
import static gameplayModel.GridMap.MAP_WIDTH;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.lang.Math.abs;

import lombok.Getter;
import utilities.Position;

@Getter
public class GridObject {
	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int ADJUSTMENT = 4;
	protected static final int MIN_Y_POSITION = EFFECTIVE_PIXEL_DIM;
	protected static final int MIN_X_POSITION = EFFECTIVE_PIXEL_DIM;
	protected static final int MAX_X_POSITION = EFFECTIVE_PIXEL_DIM * (MAP_WIDTH - 2);
	protected static final int MAX_Y_POSITION = EFFECTIVE_PIXEL_DIM * (MAP_HEIGHT - 2);

	protected final Position position;
	protected boolean isConcreteCollision;

	public GridObject(Position position) {
		this.position = position;
		isConcreteCollision = false;
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}

	public void setXPosition(int xPos) {
		isConcreteCollision = false;
		final int yError = (position.getY() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2);

		boolean isInXRange = (xPos >= EFFECTIVE_PIXEL_DIM) && (xPos <= EFFECTIVE_PIXEL_DIM * (MAP_WIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			position.setX(xPos);
		} else if (isAboveRow && isInXRange) {
			position.setX(xPos);
			position.incrementY(ADJUSTMENT);
		} else if (isBelowRow && isInXRange) {
			position.setX(xPos);
			position.decrementY(ADJUSTMENT);
		} else
			isConcreteCollision = true;
	}

	public void setYPosition(int yPos) {
		isConcreteCollision = false;
		final int xError = (position.getX() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2);

		boolean isInYRange = (yPos >= EFFECTIVE_PIXEL_DIM) && (yPos <= EFFECTIVE_PIXEL_DIM * (GridMap.MAP_HEIGHT - 2));
		boolean isAlignedWithColumn = ((xError) == 0);
		boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			position.setY(yPos);
		} else if (isRightFromColumn && isInYRange) {
			position.setY(yPos);
			position.decrementX(ADJUSTMENT);
		} else if (isLeftFromColumn && isInYRange) {
			position.setY(yPos);
			position.incrementX(ADJUSTMENT);
		} else
			isConcreteCollision = true;
	}

	public boolean isSamePosition(GridObject object) {
		return position.isSame(object.getPosition());
	}

	public boolean isSamePosition(Position position) {
		return this.position.isSame(position);
	}

	public boolean isInSameColumnAs(GridObject other) {
		return abs(getX() - other.getX()) < EFFECTIVE_PIXEL_DIM;
	}

	public boolean isInSameRowAs(GridObject other) {
		return abs(getY() - other.getY()) < EFFECTIVE_PIXEL_DIM;
	}
}
