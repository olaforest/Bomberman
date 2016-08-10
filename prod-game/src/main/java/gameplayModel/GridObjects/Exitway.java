package gameplayModel.GridObjects;

import gameplayModel.GridObject;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Exitway extends GridObject {

	@Getter
	private BufferedImage image;
	private int[] imageParameter = {2, 241};

	public Exitway(int x, int y) {
		super(x, y);
		image = resizeImage(sprite.getSubimage(imageParameter[0], imageParameter[1], PIXELWIDTH, PIXELHEIGHT), ZOOM);
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