package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Minvo extends Enemy {
	private static final int POINTS = 800;
	private static final int SPEED = 4;
	private static final int SMARTNESS = 2;
	private static final boolean WALLPASS = false;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 128, 3}, {2, 128, 3}, {110, 128, 5}};

	public Minvo(Position position) {
		super(, position);
		initialize();
	}

	public Minvo(Position position, int dir) {
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
