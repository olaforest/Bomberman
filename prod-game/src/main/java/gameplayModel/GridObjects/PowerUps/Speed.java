package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class Speed extends PowerUp {
	private static final int[] IMAGE_PARAM = {180, 259};

	public Speed(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = true;
	}
}