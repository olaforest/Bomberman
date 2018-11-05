package gameplayModel;

import static gameplayModel.GridMap.MAP_HEIGHT;
import static gameplayModel.GridMap.MAP_WIDTH;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.lang.Math.abs;

import gameplayModel.gridObjects.animatedObjects.Bomb;
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

	protected boolean isInSameColumnAs(GridObject other) {
		return abs(getX() - other.getX()) < MISALIGNMENT_ALLOWED;
	}

	protected boolean isInSameRowAs(GridObject other) {
		return abs(getY() - other.getY()) < MISALIGNMENT_ALLOWED;
	}

	protected boolean isSameHorizPos(GridObject object) {
		return getX() == object.getX();
	}

	protected boolean isSameVertPos(GridObject object) {
		return getY() == object.getY();
	}

	public boolean isInRangeOf(Bomb bomb) {
		return isInHorizontalRangeOf(bomb) || isInVerticalRangeOf(bomb);
	}

	public boolean isInRightRangeOf(Bomb bomb) {
		return bomb.getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM >= getX() + 1 && bomb.getX() < getX();
	}

	public boolean isInLeftRangeOf(Bomb bomb) {
		return bomb.getX() - (bomb.getLeftRange() + 1) * EFFECTIVE_PIXEL_DIM <= getX() - 1 && bomb.getX() > getX();
	}

	public boolean isInDownRangeOf(Bomb bomb) {
		return bomb.getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM >= getY() + 1 && bomb.getY() < getY();
	}

	public boolean isInUpRangeOf(Bomb bomb) {
		return bomb.getY() - (bomb.getUpRange() + 1) * EFFECTIVE_PIXEL_DIM <= getY() - 1 && bomb.getY() > getY();
	}

	private boolean isInHorizontalRangeOf(Bomb bomb) {
		return isInSameRowAs(bomb) && (isInRightRangeOf(bomb) || isInLeftRangeOf(bomb));
	}

	private boolean isInVerticalRangeOf(Bomb bomb) {
		return isInSameColumnAs(bomb) && (isInDownRangeOf(bomb) || isInUpRangeOf(bomb));
	}
}
