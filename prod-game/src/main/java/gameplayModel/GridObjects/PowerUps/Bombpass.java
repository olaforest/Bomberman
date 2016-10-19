package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class Bombpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {216, 259};

	public Bombpass(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}