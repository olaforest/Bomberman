package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Detonator extends PowerUp {
	private static final int[] IMAGE_PARAM = {198, 259};

	public Detonator(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = false;
	}
}