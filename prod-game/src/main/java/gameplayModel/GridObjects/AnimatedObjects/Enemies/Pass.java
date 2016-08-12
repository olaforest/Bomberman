package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import java.util.ArrayList;

public class Pass extends Enemy {
	public static final int POINTS = 4000;
	public static final int SPEED = 4;
	public static final int SMARTNESS = 3;
	public static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 182, 3}, {2, 182, 3}, {110, 182, 5}};

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
		animationList = new ArrayList<>(AnimationType.values().length);

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList.add(i, new Animation(ANIM_PARAM[i][2]));

			for (int j = 0; j < ANIM_PARAM[i][2]; j++) {
				animationList.get(i).setFrame(resizeImage(ANIM_PARAM[i][0] + (PIXEL_DIMENSION + 2) * j, ANIM_PARAM[i][1]), j);
			}
		}
	}
}