package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Flames extends PowerUp {
	private static final int[] IMAGE_PARAM = {145, 259};

	public Flames(int x, int y) {
		super(x, y, IMAGE_PARAM);
		isPermanent = true;
	}
}