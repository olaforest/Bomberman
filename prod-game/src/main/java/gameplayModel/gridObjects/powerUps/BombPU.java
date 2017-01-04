package gameplayModel.gridObjects.powerUps;

import gameplayModel.gridObjects.PowerUp;
import utilities.Position;

public class BombPU extends PowerUp {
	private static final int[] IMAGE_PARAM = {163, 259};

	public BombPU(Position position) {
		super(position, IMAGE_PARAM);
		isPermanent = true;
	}
}
