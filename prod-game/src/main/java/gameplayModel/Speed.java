
package gameplayModel;

/**
 * This class define the the "Speed" PowerUp. It also contains image parameter for animation generation 
 * from Sprite Sheets
 * 
 * @author Olivier Laforest
 */

public class Speed extends PowerUp {
	
	private int[] imageParam = {180, 259};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	
	public Speed(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		image = generateImage(imageParam);
	}
}