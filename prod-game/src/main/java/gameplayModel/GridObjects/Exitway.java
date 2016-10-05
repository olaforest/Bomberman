package gameplayModel.GridObjects;

import lombok.AccessLevel;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Getter
public class Exitway extends HiddenObject {

	private BufferedImage image;
	private int brickIndex;

	@Getter(AccessLevel.NONE)
	private int[] imageParameter = {2, 241};

	public Exitway(int x, int y, int index) {
		super(x, y);
		this.brickIndex = index;
		image = resizeImage(imageParameter[0], imageParameter[1]);
	}

	@Override
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public ArrayList<String> toCSVEntry() {

		ArrayList<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));

		return entryList;
	}
}