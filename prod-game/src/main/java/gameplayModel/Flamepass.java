package gameplayModel;

public class Flamepass extends PowerUp {
	
	private int[] imageParam = {217, 204};
	
	public Flamepass(int x, int y) {
		super(x, y);
		isPermanent = false;
		image = generateImage(imageParam);
	}
}