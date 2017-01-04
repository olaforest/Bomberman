package gameplayModel.gridObjects.powerUps;

import gameplayModel.gridObjects.PowerUp;
import utilities.Position;

public class Flames extends PowerUp {
	private static final int[] IMAGE_PARAM = {145, 259};

	public Flames(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = true;
	}
}