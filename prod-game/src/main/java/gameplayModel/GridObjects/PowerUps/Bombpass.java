package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Bombpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {216, 259};

	public Bombpass(int x, int y) {
		super(x, y, IMAGE_PARAM);
		isPermanent = false;
	}
}