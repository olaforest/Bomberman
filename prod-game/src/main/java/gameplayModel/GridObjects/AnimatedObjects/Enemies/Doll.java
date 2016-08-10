package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class Doll extends Enemy {

	public final int POINTS = 400;
	public final int SPEED = 3;
	public final int SMARTNESS = 1;
	public final boolean WALLPASS = false;

	public Doll(int x, int y) {
		super(x, y);
		initialize();
	}

	public Doll(int x, int y, int dir) {
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

		int[][] animParam = {{55, 110, 3},
				{1, 110, 3},
				{107, 110, 5}};

		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(animParam[i][2]);

			for (int j = 0; j < animParam[i][2]; j++) {
				animationList[i].setFrame(resizeImage(sprite.getSubimage(animParam[i][0] + (GridObject.PIXELWIDTH + 2) * j,
						animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
		}
	}
}
