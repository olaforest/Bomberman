package gameplayModel.GridObjects.Factories;

import gameplayModel.GridObjects.AnimatedObjects.Enemies.*;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class EnemyFactory {
	public static Enemy createEnemy(int type, int xPosition, int yPosition) {
		switch (type) {
			case 0:
				return new Balloom(xPosition, yPosition);
			case 1:
				return new Oneal(xPosition, yPosition);
			case 2:
				return new Doll(xPosition, yPosition);
			case 3:
				return new Minvo(xPosition, yPosition);
			case 4:
				return new Kondoria(xPosition, yPosition);
			case 5:
				return new Ovapi(xPosition, yPosition);
			case 6:
				return new Pass(xPosition, yPosition);
			case 7:
				return new Pontan(xPosition, yPosition);
			default:
				return null;
		}
	}
}
