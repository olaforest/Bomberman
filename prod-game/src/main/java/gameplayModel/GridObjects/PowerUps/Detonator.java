package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Detonator extends PowerUp {

	private int[] imageParam = {198, 259};

	public Detonator(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}