package gameplayModel.gridObjects.powerUps;

import gameplayModel.gridObjects.PowerUp;
import utilities.Position;

public class Bombpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {216, 259};

	public Bombpass(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}