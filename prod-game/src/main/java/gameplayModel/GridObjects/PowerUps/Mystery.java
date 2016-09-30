package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Mystery extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 223};

	public Mystery(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = false;
	}
}