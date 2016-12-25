package gameplayModel.GridObjects.AnimatedObjects.Enemies;

import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Pass extends Enemy {
	private static final int POINTS = 4000;
	private static final int SPEED = 4;
	private static final int SMARTNESS = 3;
	private static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 182, 3}, {2, 182, 3}, {110, 182, 5}};

	public Pass(Position position) {
		super(position);
		initialize();
	}

	public Pass(Position position, int dir) {
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