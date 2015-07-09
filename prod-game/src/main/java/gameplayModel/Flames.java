
package gameplayModel;

/**
 * This class define the the "Flames" PowerUp. It also contains image parameter for animation generation 
 * from Sprite Sheets
 * 
 * @author Olivier Laforest
 */

public class Flames extends PowerUp {
	
	private int[] imageParam = {145, 259};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	
	public Flames(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		image = generateImage(imageParam);
	}
}