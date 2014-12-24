
package gameplayModel;

/**
 * This class define the the "Bombpass" PowerUp. It also contains image parameter for animation generation 
 * from Sprite Sheets
 * 
 * @author Olivier Laforest
 */

public class Bombpass extends PowerUp {
	
	private int[] imageParam = {216, 259};
	
	/**
	 * @param x the x-coordinates of location of this specific PowerUp
	 * @param y the y-coordinates of location of this specific PowerUp
	 */
	public Bombpass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}