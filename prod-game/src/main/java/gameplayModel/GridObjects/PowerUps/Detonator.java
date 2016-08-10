package gameplayModel.GridObjects.PowerUps;

public class Detonator extends PowerUp {

	private int[] imageParam = {198, 259};

	public Detonator(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}