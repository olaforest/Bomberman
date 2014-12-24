package gameplayModel;

/**
 * @author Olivier Laforest
 *
 */
public class BombPU extends PowerUp {
	
	private int[] imageParam = {163, 259};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	public BombPU(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		img = generateImage(imageParam);
	}
}
