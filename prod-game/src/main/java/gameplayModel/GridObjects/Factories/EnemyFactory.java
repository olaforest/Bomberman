package gameplayModel.GridObjects.Factories;

import gameplayModel.GridObjects.AnimatedObjects.Enemies.*;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import utilities.Position;

import static utilities.Position.create;

public class EnemyFactory {
	public static Enemy createEnemy(int type, int xPosition, int yPosition) {
		final Position position = create(xPosition, yPosition);
		switch (type) {
			case 0:
				return new Balloom(position);
			case 1:
				return new Oneal(position);
			case 2:
				return new Doll(position);
			case 3:
				return new Minvo(position);
			case 4:
				return new Kondoria(position);
			case 5:
				return new Ovapi(position);
			case 6:
				return new Pass(position);
			case 7:
				return new Pontan(position);
			default:
				return null;
		}
	}
}
