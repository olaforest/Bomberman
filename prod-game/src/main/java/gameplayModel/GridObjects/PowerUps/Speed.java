package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Speed extends PowerUp {
	private static final int[] IMAGE_PARAM = {180, 259};

	public Speed(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = true;
	}
}