
package gameplayModel;

/**
 * This class define the the "FlamePass" PowerUp. It also contains image parameter for animation generation 
 * from Sprite Sheets
 * 
 * @author Olivier Laforest
 */

public class Flamepass extends PowerUp {
	
	private int[] imageParam = {217, 204};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	
	public Flamepass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}