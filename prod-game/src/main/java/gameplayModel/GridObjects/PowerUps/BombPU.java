package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class BombPU extends PowerUp {
	private static final int[] IMAGE_PARAM = {163, 259};

	public BombPU(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = true;
	}
}
