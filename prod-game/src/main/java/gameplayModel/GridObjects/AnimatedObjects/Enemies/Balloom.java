package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Balloom extends Enemy {
	public static final int POINTS = 100;
	public static final int SPEED = 2;
	public static final int SMARTNESS = 1;
	public static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{72, 39, 4}, {1, 39, 4}, {144, 39, 5}};

	public Balloom(int x, int y) {
		super(x, y);
		initialize();
	}

	public Balloom(int x, int y, int dir) {
		super(x, y, dir);
		initialize();
	}

	private void initialize() {
		points = POINTS;
		speed = SPEED * SPEED_MULTIPLIER;
		smartness = SMARTNESS;
		isWallpass = WALLPASS;
	}

	public void generateAnimationList() {
//		animationList = new Animation[AnimationType.values().length];

		animationList = generateAnimationList(AnimationType.values(), ANIM_PARAM);
	}

	private List<Animation> generateAnimationList(AnimationType[] animationTypes, int[][] animParam) {
		List<Animation> animations = new ArrayList<>(animationTypes.length);
		for (AnimationType type : animationTypes) {
			int i = type.ordinal();
//			animations[i] = new Animation(animParam[i][2]);
			animations.add(i, new Animation(animParam[i][2]));

			for (int j = 0; j < animParam[i][2]; j++)
				animations.get(i).setFrame(resizeImage(animParam[i][0] + (PIXEL_DIMENSION + 2) * j, animParam[i][1]), j);
		}
		return animations;
	}
}
