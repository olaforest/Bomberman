package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Speed extends PowerUp {
	private static final int[] IMAGE_PARAM = {180, 259};

	public Speed(int x, int y) {
		super(x, y, IMAGE_PARAM);
		isPermanent = true;
	}
}