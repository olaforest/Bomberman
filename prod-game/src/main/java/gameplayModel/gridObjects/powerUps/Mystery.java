package gameplayModel.gridObjects.powerUps;

import gameplayModel.gridObjects.PowerUp;
import utilities.Position;

public class Mystery extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 223};

	public Mystery(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}