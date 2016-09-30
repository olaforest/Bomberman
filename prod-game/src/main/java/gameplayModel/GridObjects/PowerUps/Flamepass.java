package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Flamepass extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 204};

	public Flamepass(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = false;
	}
}