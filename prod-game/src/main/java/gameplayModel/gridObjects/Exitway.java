package gameplayModel.gridObjects;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static gameplayView.ImageManager.importScaledImage;

public class Exitway extends FixedObject implements HiddenObject {
	@Getter private static final BufferedImage image = importScaledImage(2, 346);
	@Getter private final int brickIndex;

	public Exitway(Position position, int index) {
		super(position);
		this.brickIndex = index;
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}