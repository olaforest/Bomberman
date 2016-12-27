package gameplayModel.GridObjects.Factories;

import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.*;
import utilities.Position;

import static utilities.Position.create;

public class PowerUpFactory {
	public static PowerUp createPowerUp(int type, int xPosition, int yPosition) {
		final Position position = create(xPosition, yPosition);
		switch (type) {
			case 1:
				return new BombPU(position);
			case 2:
				return new Flames(position);
			case 3:
				return new Speed(position);
			case 4:
				return new Wallpass(position);
			case 5:
				return new Detonator(position);
			case 6:
				return new Bombpass(position);
			case 7:
				return new Flamepass(position);
			case 8:
				return new Mystery(position);
			default:
				return null;
		}
	}
}
