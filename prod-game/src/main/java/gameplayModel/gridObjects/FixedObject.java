package gameplayModel.gridObjects;

import gameplayModel.GridObject;
import utilities.Position;

public abstract class FixedObject extends GridObject {

	FixedObject(Position position) {
		super(position);
	}

	@Override
	public void setXPosition(int xPos) {
		position.setX(xPos);
	}

	@Override
	public void setYPosition(int yPos) {
		position.setY(yPos);
	}
}
