package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Pontan extends Enemy {
	private static final int POINTS = 8000;
	private static final int SPEED = 4;
	private static final int SMARTNESS = 3;
	private static final boolean WALLPASS = true;
	public static final int[][] ANIM_PARAM = new int[][]{{73, 92, 4}, {1, 92, 4}, {146, 92, 5}};

	public Pontan(Position position) {
		super(, position);
		initialize();
	}

	public Pontan(Position position, int dir) {
		super(, position, dir);
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