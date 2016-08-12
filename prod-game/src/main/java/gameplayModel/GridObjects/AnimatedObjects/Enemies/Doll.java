package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import java.util.Arrays;

import static java.util.Arrays.asList;

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
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM);
	}
}
