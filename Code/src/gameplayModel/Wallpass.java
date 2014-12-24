
package gameplayModel;

/**
 * This class define the the "WallPass" PowerUp. It also contains image parameter for animation generation 
 * from Sprite Sheets
 * 
 * @author Olivier Laforest
 */

public class Wallpass extends PowerUp {
	
	private int[] imageParam = {217, 241};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	
	public Wallpass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}