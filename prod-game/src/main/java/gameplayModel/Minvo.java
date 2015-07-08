package gameplayModel;

public class Minvo extends Enemy {
	
	public final int POINTS = 800;
	public final int SPEED = 4;
	public final int SMARTNESS = 2;
	public final boolean WALLPASS = false;
	
	/**
	 * Initialize the enemy when each round of the game start
	 * @param  x x-coordinates of the spawning location of the enemy
	 * @param  y y-coordinates of the spawning location of the enemy
	 *
	 */
	
	public Minvo (int x, int y) {
		super(x, y);
		
		initialize();
	}
	
	public Minvo (int x, int y, int dir) {
		super(x, y, dir);
		
		initialize();
	}
	
	public void initialize() {
		points = POINTS;
		speed = SPEED * SPEED_MULTIPLIER;
		smartness = SMARTNESS;
		isWallpass = WALLPASS;
	}

	public void generateAnimationList() {
		
		int[][] animParam = {	{56, 128, 3},
								{2, 128, 3},
								{110, 128, 5}};

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
