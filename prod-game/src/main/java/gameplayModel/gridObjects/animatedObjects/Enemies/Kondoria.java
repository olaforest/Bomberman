package gameplayModel.gridObjects.animatedObjects.Enemies;

import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Position;

import static java.util.Arrays.asList;

public class Kondoria extends Enemy {
	private static final int POINTS = 1000;
	private static final int SPEED = 1;
	private static final int SMARTNESS = 3;
	private static final boolean WALLPASS = true;
	public static final int[][] ANIM_PARAM = new int[][]{{56, 146, 3}, {2, 146, 3}, {110, 146, 5}};

	public Kondoria(Position position) {
		super(position);
		initialize();
	}

	public Kondoria(Position position, int dir) {
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
