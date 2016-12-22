package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;
import utility.Position;

public class BombPU extends PowerUp {
	private static final int[] IMAGE_PARAM = {163, 259};

	public BombPU(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = true;
	}
}
