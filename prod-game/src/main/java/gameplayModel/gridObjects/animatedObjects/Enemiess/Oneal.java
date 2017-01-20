package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Oneal extends Enemy {
	private static final int POINTS = 200;
	private static final int SPEED = 3;
	private static final int SMARTNESS = 2;
	private static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 57, 3}, {2, 57, 3}, {110, 57, 5}};

	public Oneal(Position position) {
		super(position);
		initialize();
	}

	public Oneal(Position position, int dir) {
		super(position, dir);
		initialize();
		animCycleParam = 1;
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
