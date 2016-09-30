package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Bombpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {216, 259};

	public Bombpass(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = false;
	}
}