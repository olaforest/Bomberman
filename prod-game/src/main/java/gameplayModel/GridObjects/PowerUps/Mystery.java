package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Mystery extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 223};

	public Mystery(int x, int y) {
		super(x, y, IMAGE_PARAM);
		isPermanent = false;
	}
}