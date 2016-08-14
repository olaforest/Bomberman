package gameplayModel.GridObjects.AnimatedObjects;

import gameplayModel.GridObjects.AnimatedObject;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class Brick extends AnimatedObject {
	public enum AnimationType {destruction}

	public static final int[][] ANIM_PARAM = new int[][]{{19, 259, 7}};

	public Brick(int x, int y) {
		super(x, y);
		animCycleParam = 3;
	}

	public void generateAnimationList() {
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 2);
	}

	public ArrayList<String> toCSVEntry() {
		ArrayList<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		return entryList;
	}
}
