package gameplayModel.GridObjects;

import lombok.AccessLevel;
import lombok.Getter;
import utility.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Exitway extends HiddenObject {

	private BufferedImage image;
	private int brickIndex;

	@Getter(AccessLevel.NONE)
	private int[] imageParameter = {2, 241};

	public Exitway(Position position, int index) {
		super(position);
		this.brickIndex = index;
		image = resizeImage(imageParameter[0], imageParameter[1]);
	}

	@Override
	public void setXPosition(int xPos) {
		position.setX(xPos);
	}

	@Override
	public void setYPosition(int yPos) {
		position.setY(yPos);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}