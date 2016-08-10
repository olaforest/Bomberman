package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class Pass extends Enemy {

	public final int POINTS = 4000;
	public final int SPEED = 4;
	public final int SMARTNESS = 3;
	public final boolean WALLPASS = false;

	public Pass(int x, int y) {
		super(x, y);
		initialize();
	}

	public Pass(int x, int y, int dir) {
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

		int[][] animParam = {{56, 182, 3},
				{2, 182, 3},
				{110, 182, 5}};

		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(animParam[i][2]);

			for (int j = 0; j < animParam[i][2]; j++) {
				animationList[i].setFrame(resizeImage(animParam[i][0] + (PIXEL_DIMENSION + 2) * j, animParam[i][1], ZOOM), j);
			}
		}
	}
}