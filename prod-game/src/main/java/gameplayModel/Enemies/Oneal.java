package gameplayModel.Enemies;

import gameplayModel.Animation;
import gameplayModel.Enemy;
import gameplayModel.GridObject;

public class Oneal extends Enemy {
	
	public final int POINTS = 200;
	public final int SPEED = 3;
	public final int SMARTNESS = 2;
	public final boolean WALLPASS = false;
	
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
		isWallpass = WALLPASS;
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
