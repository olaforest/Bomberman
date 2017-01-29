package gameplayView;

import lombok.Getter;

@Getter
class AnimParam {
	private final int xCoordinate;
	private final int yCoordinate;
	private final int numOfFrame;

	AnimParam(int xCoordinate, int yCoordinate, int numOfFrame) {
		validateInputs(xCoordinate, yCoordinate, numOfFrame);
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.numOfFrame = numOfFrame;
	}

	private void validateInputs(int xCoordinate, int yCoordinate, int numOfFrame) {
		validateCoordinate(xCoordinate, yCoordinate);
		validateNumberOfFrame(numOfFrame);
	}

	private void validateCoordinate(int xCoordinate, int yCoordinate) {
		if (xCoordinate < 0 || yCoordinate < 0)
			throw new IllegalArgumentException("(x, y) coordinates cannot be negative.");
	}

	private void validateNumberOfFrame(int numOfFrame) {
		if (numOfFrame < 2)
			throw new IllegalArgumentException("Animation should have at least 2 frames.");
	}
}
