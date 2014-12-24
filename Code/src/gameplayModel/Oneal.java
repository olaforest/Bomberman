
package gameplayModel;

/**
 * This class defined the the enemy type "Doll". It defined all of the initializing parameter of this enemy type 
 * which includes the number of points players gain if killed,  the speed it moves at, the AI "smartnesss" of it, 
 * and the wall-passing capabilities.
 * 
 * @author Olivier Laforest
 */

public class Oneal extends Enemy {
	
	public final int POINTS = 200;
	public final int SPEED = 3;
	public final int SMARTNESS = 2;
	public final boolean WALLPASS = false;
	
	/**
	 * Initialize the enemy when each round of the game start
	 * @param  x x-coordinates of the spawning location of the enemy
	 * @param  y y-coordinates of the spawning location of the enemy
	 * @param dir the initial moving direction of enemy
	 *
	 */
	
	public Oneal(int x, int y) {
		super(x, y);
		
		initialize();
	}
	
	public Oneal(int x, int y, int dir) {
		super(x, y, dir);
		
		initialize();
		animCycleParam = 1;
	}
	
	public void initialize() {
		points = POINTS;
		speed = SPEED * SPEED_MULTIPLIER;
		smartness = SMARTNESS;
		wallpass = WALLPASS;
	}

	public void generateAnimationList() {
		
		int[][] animParam = {	{56, 57, 3},
								{2, 57, 3},
								{110, 57, 5}};

		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()){
			
			int i = type.ordinal();
			
			animationList[i] = new Animation(animParam[i][2]);
			
			for (int j = 0 ; j < animParam[i][2] ; j++){
			
			animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + (GridObject.PIXELWIDTH + 2) * j, 
					animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
		}
	}
}
