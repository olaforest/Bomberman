package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class Mystery extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 223};

	public Mystery(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}