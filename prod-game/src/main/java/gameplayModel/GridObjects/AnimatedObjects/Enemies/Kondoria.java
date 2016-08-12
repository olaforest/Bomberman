package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import static java.util.Arrays.asList;

public class Kondoria extends Enemy {
	public static final int POINTS = 1000;
	public static final int SPEED = 1;
	public static final int SMARTNESS = 3;
	public static final boolean WALLPASS = true;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 146, 3}, {2, 146, 3}, {110, 146, 5}};

	public Kondoria(int x, int y) {
		super(x, y);
		initialize();
	}

	public Kondoria(int x, int y, int dir) {
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
