package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimParam;
import gameplayView.AnimationType;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import static gameplayView.AnimationType.Death;
import static gameplayView.AnimationType.Fixed;
import static java.util.Arrays.asList;

public class Brick extends AnimatedObject {
	private static final List<SimpleEntry<AnimationType, AnimParam>> animParams = asList(
			new SimpleEntry<>(Fixed, new AnimParam(19, 259, 2)),
			new SimpleEntry<>(Death, new AnimParam(19, 259, 7)));

	public Brick(Position position) {
		super(position, animParams);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}
