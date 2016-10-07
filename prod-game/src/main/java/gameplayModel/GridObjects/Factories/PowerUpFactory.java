package gameplayModel.GridObjects.Factories;

import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.*;

public class PowerUpFactory {
	public static PowerUp createPowerUp(int type, int xPosition, int yPosition) {
		switch (type) {
			case 1:
				return new BombPU(xPosition, yPosition);
			case 2:
				return new Flames(xPosition, yPosition);
			case 3:
				return new Speed(xPosition, yPosition);
			case 4:
				return new Wallpass(xPosition, yPosition);
			case 5:
				return new Detonator(xPosition, yPosition);
			case 6:
				return new Bombpass(xPosition, yPosition);
			case 7:
				return new Flamepass(xPosition, yPosition);
			case 8:
				return new Mystery(xPosition, yPosition);
			default:
				return null;
		}
	}
}
