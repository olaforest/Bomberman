package gameplayModel.GridObjects;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class PowerUp extends HiddenObject {
	@Getter private BufferedImage image;
	@Getter protected boolean isPermanent;
	private final int[] imageParameter;

	protected PowerUp(int x, int y, int[] imageParameter) {
		super(x, y);
		this.imageParameter = imageParameter;
		image = generateImage();
	}

	public BufferedImage generateImage() {
		return resizeImage(imageParameter[0], imageParameter[1]);
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
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		return entryList;
	}
}
