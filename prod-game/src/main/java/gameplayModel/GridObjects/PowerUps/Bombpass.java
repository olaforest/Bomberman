package gameplayModel.GridObjects.PowerUps;

public class Bombpass extends PowerUp {
	
	private int[] imageParam = {216, 259};
	
	public Bombpass(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}