package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimationType;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

import static gameplayView.AnimationType.destruction;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Brick extends AnimatedObject {
	public static final List<AnimationType> animationType = singletonList(destruction);
	private static final List<List<Integer>> ANIM_PARAM = singletonList(asList(19, 259, 7));

	public Brick(Position position) {
		super(position);
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 2);
		currentAnimation = animationList.get(INITIAL_ANIMATION);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}
