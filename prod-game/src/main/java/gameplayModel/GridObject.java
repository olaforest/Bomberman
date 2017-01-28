package gameplayModel;

import gameplayVisual.ImageManager;
import lombok.Getter;
import utilities.Position;

import static gameplayModel.GridMap.MAPHEIGHT;
import static gameplayModel.GridMap.MAPWIDTH;

@Getter
public class GridObject {

	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int ADJUSTMENT = 4;
	protected static final int MIN_Y_POSITION = ImageManager.EFFECTIVE_PIXEL_DIMENSION;
	protected static final int MIN_X_POSITION = ImageManager.EFFECTIVE_PIXEL_DIMENSION;
	protected static final int MAX_X_POSITION = ImageManager.EFFECTIVE_PIXEL_DIMENSION * (MAPWIDTH - 2);
	protected static final int MAX_Y_POSITION = ImageManager.EFFECTIVE_PIXEL_DIMENSION * (MAPHEIGHT - 2);

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
		final int yError = (position.getY() - ImageManager.EFFECTIVE_PIXEL_DIMENSION) % (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInXRange = (xPos >= ImageManager.EFFECTIVE_PIXEL_DIMENSION) && (xPos <= ImageManager.EFFECTIVE_PIXEL_DIMENSION * (MAPWIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

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
		final int xError = (position.getX() - ImageManager.EFFECTIVE_PIXEL_DIMENSION) % (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInYRange = (yPos >= ImageManager.EFFECTIVE_PIXEL_DIMENSION) && (yPos <= ImageManager.EFFECTIVE_PIXEL_DIMENSION * (GridMap.MAPHEIGHT - 2));
		boolean isAlignedWithColumn = ((xError) == 0);
		boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = (xError) >= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

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

	public boolean isSamePosition(int xPosition, int yPosition) {
		return position.getX() == xPosition && position.getY() == yPosition;
	}
}
