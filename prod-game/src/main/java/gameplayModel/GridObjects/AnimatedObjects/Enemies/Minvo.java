package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import static java.util.Arrays.asList;

public class Minvo extends Enemy {
	public static final int POINTS = 800;
	public static final int SPEED = 4;
	public static final int SMARTNESS = 2;
	public static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 128, 3}, {2, 128, 3}, {110, 128, 5}};

	public Minvo(int x, int y) {
		super(x, y);
		initialize();
	}

	public Minvo(int x, int y, int dir) {
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
