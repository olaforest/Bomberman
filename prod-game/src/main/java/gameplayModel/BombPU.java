package gameplayModel;

public class BombPU extends PowerUp {
	
	private int[] imageParam = {163, 259};
	
	public BombPU(int x, int y) {
		super(x, y);
		isPermanent = true;
		image = generateImage(imageParam);
	}
}
