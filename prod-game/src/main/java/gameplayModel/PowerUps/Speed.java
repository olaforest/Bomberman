package gameplayModel.PowerUps;

import gameplayModel.PowerUp;

public class Speed extends PowerUp {
	
	private int[] imageParam = {180, 259};
	
	public Speed(int x, int y) {
		super(x, y);
		isPermanent = true;
		image = generateImage(imageParam);
	}
}