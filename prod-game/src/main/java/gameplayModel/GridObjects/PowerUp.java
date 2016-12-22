package gameplayModel.GridObjects;

import lombok.Getter;
import utility.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class PowerUp extends HiddenObject {
	@Getter private BufferedImage image;
	@Getter protected boolean isPermanent;
	private final int[] imageParameter;

	protected PowerUp(Position position, int[] imageParameter) {
		super(position);
		this.imageParameter = imageParameter;
		image = generateImage();
	}

	public BufferedImage generateImage() {
		return resizeImage(imageParameter[0], imageParameter[1]);
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
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}
}
