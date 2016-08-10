package gameplayModel.GridObjects.PowerUps;

public class Wallpass extends PowerUp {

	private int[] imageParam = {217, 241};

	public Wallpass(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}