package gameplayModel.GridObjects.factories;

import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.*;

public class PowerUpFactory {
	public static PowerUp createNewPowerUp(int type, int xPosition, int yPosition, int index) {
		switch (type) {
			case 1:
				return new BombPU(xPosition, yPosition, index);
			case 2:
				return new Flames(xPosition, yPosition, index);
			case 3:
				return new Speed(xPosition, yPosition, index);
			case 4:
				return new Wallpass(xPosition, yPosition, index);
			case 5:
				return new Detonator(xPosition, yPosition, index);
			case 6:
				return new Bombpass(xPosition, yPosition, index);
			case 7:
				return new Flamepass(xPosition, yPosition, index);
			case 8:
				return new Mystery(xPosition, yPosition, index);
			default:
				return null;
		}
	}
}
