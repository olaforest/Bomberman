package gameplayModel.GridObjects.AnimatedObjects;

import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObject;

import java.util.ArrayList;

public class Brick extends AnimatedObject {

	public enum AnimationType {destruction}

	public Brick(int x, int y) {
		super(x, y);
		animCycleParam = 3;
	}

	public void generateAnimationList() {

		int[][] animParam = {{19, 259, 7}};

		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(animParam[i][2]);

			for (int j = 0; j < animParam[i][2]; j++) {
				animationList[i].setFrame(resizeImage(animParam[i][0] + (PIXEL_DIMENSION + 2) * j, animParam[i][1], ZOOM), j);
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
