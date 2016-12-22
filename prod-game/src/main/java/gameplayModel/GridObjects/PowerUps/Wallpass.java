package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class Wallpass extends PowerUp {
	private static final int[] IMAGE_PARAM = {217, 241};

	public Wallpass(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = false;
	}
}