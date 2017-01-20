package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Doll extends Enemy {
	private static final int POINTS = 400;
	private static final int SPEED = 3;
	private static final int SMARTNESS = 1;
	private static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{55, 110, 3}, {1, 110, 3}, {107, 110, 5}};

	public Doll(Position position) {
		super(position);
		initialize();
	}

	public Doll(Position position, int dir) {
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
