package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Balloom extends Enemy {
	private static final int POINTS = 100;
	private static final int SPEED = 2;
	private static final int SMARTNESS = 1;
	private static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{72, 39, 4}, {1, 39, 4}, {144, 39, 5}};

	public Balloom(Position position) {
		super(position);
		initialize();
	}

	public Balloom(Position position, int dir) {
		super(position, dir);
		initialize();
	}

	private void initialize() {
		points = POINTS;
		speed = SPEED * SPEED_MULTIPLIER;
		smartness = SMARTNESS;
		isWallpass = WALLPASS;
	}

	public void generateAnimationList() {
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 2);
	}
}
