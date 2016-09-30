package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObjects.PowerUp;

public class Wallpass extends PowerUp {

	private int[] imageParam = {217, 241};

	public Wallpass(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}