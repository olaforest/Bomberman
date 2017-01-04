package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utilities.Position;

public class Flamepass extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 204};

	public Flamepass(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}