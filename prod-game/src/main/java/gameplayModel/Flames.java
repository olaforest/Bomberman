package gameplayModel;

public class Flames extends PowerUp {
	
	private int[] imageParam = {145, 259};
	
	public Flames(int x, int y) {
		super(x, y);
		isPermanent = true;
		image = generateImage(imageParam);
	}
}