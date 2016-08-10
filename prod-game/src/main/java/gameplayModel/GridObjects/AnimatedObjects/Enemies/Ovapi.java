package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class Ovapi extends Enemy {
	public static final int POINTS = 2000;
	public static final int SPEED = 2;
	public static final int SMARTNESS = 2;
	public static final boolean WALLPASS = true;
	public static final int[][] ANIM_PARAM = new int[][]{{55, 164, 3}, {1, 164, 3}, {109, 164, 5}};

	public Ovapi(int x, int y) {
		super(x, y);
		initialize();
	}

	public Ovapi(int x, int y, int dir) {
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
		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(ANIM_PARAM[i][2]);

			for (int j = 0; j < ANIM_PARAM[i][2]; j++) {
				animationList[i].setFrame(resizeImage(ANIM_PARAM[i][0] + (PIXEL_DIMENSION + 2) * j, ANIM_PARAM[i][1], ZOOM), j);
			}
		}
	}
}
