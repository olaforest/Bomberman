package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Brick extends AnimatedObject {
	public enum AnimationType {destruction}
	private static final List<List<Integer>> ANIM_PARAM = singletonList(asList(19, 259, 7));

	public Brick(Position position) {
		super(position);
	}

	public void generateAnimationList() {
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 2);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}
