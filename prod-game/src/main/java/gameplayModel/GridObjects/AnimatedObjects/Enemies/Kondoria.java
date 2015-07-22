package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class Kondoria extends Enemy {
	
	public final int POINTS = 1000;
	public final int SPEED = 1;
	public final int SMARTNESS = 3;
	public final boolean WALLPASS = true;
	
	public Kondoria(int x, int y) {
		super(x, y);
		initialize();
	}
	
	public Kondoria(int x, int y, int dir) {
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
		
		int[][] animParam = {	{56, 146, 3},
								{2, 146, 3},
								{110, 146, 5}};

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
