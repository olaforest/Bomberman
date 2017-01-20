package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Ovapi extends Enemy {
	private static final int POINTS = 2000;
	private static final int SPEED = 2;
	private static final int SMARTNESS = 2;
	private static final boolean WALLPASS = true;
	public static final int[][] ANIM_PARAM = new int[][]{{55, 164, 3}, {1, 164, 3}, {109, 164, 5}};

	public Ovapi(Position position) {
		super(, position);
		initialize();
	}

	public Ovapi(Position position, int dir) {
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
