package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimParam;
import gameplayView.AnimationType;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import static gameplayView.AnimationType.destruction;
import static java.util.Collections.singletonList;

public class Brick extends AnimatedObject {
	private static final List<SimpleEntry<AnimationType, AnimParam>> animParams =
			singletonList(new SimpleEntry<>(destruction, new AnimParam(19, 259, 7)));

	public Brick(Position position) {
		super(position);
		animations = generateAnimations(animParams);
		currentAnimation = animations.get(INITIAL_ANIMATION);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}
