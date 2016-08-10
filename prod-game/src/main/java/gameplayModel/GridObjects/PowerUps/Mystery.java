package gameplayModel.GridObjects.PowerUps;

public class Mystery extends PowerUp {

	final private int[] imageParam = {217, 223};

	public Mystery(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}