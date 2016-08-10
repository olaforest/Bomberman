package gameplayModel.GridObjects.AnimatedObjects;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObject;

import java.util.ArrayList;

public class Brick extends AnimatedObject {

	public enum AnimationType {destruction}

	public static final int[][] ANIM_PARAM = new int[][]{{19, 259, 7}};

	public Brick(int x, int y) {
		super(x, y);
		animCycleParam = 3;
	}

	public void generateAnimationList() {
		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(ANIM_PARAM[i][2]);

			for (int j = 0; j < ANIM_PARAM[i][2]; j++) {
				animationList[i].setFrame(resizeImage(ANIM_PARAM[i][0] + (PIXEL_DIMENSION + 2) * j, ANIM_PARAM[i][1], ZOOM), j);
			}
		}
	}

	public ArrayList<String> toCSVEntry() {
		ArrayList<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		return entryList;
	}
}
