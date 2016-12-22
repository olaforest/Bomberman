package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class Detonator extends PowerUp {
	private static final int[] IMAGE_PARAM = {198, 259};

	public Detonator(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}