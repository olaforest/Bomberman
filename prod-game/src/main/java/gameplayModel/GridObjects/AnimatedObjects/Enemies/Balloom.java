package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import static java.util.Arrays.asList;

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
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM);
	}
}
