package gameplayModel.gridObjects.powerUps;

import gameplayModel.gridObjects.PowerUp;
import utilities.Position;

public class Wallpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 241};

	public Wallpass(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}