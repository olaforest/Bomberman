package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

public class Doll extends Enemy {
	public static final int POINTS = 400;
	public static final int SPEED = 3;
	public static final int SMARTNESS = 1;
	public static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{55, 110, 3}, {1, 110, 3}, {107, 110, 5}};

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
		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(ANIM_PARAM[i][2]);

			for (int j = 0; j < ANIM_PARAM[i][2]; j++) {
				animationList[i].setFrame(resizeImage(ANIM_PARAM[i][0] + (PIXEL_DIMENSION + 2) * j, ANIM_PARAM[i][1]), j);
			}
		}
	}
}
