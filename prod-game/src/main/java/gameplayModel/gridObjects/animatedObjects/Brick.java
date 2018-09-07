package gameplayModel.gridObjects.animatedObjects;

import static gameplayView.AnimationType.Death;
import static gameplayView.AnimationType.Fixed;
import static java.util.Arrays.asList;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimParam;
import gameplayView.AnimationType;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class Brick extends AnimatedObject {
	private static final List<SimpleEntry<AnimationType, AnimParam>> animParams = asList(
			new SimpleEntry<>(Fixed, new AnimParam(38, 346, 2)),
			new SimpleEntry<>(Death, new AnimParam(56, 346, 6)));

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
