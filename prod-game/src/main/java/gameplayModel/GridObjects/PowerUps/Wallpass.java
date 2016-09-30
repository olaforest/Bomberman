package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Wallpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 241};

	public Wallpass(int x, int y, int index) {
		super(x, y, index, IMAGE_PARAM);
		isPermanent = false;
	}
}