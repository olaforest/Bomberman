package gameplayModel.GridObjects;

import gameplayModel.GridObject;
import lombok.Getter;

import java.awt.image.BufferedImage;

public class Concrete extends GridObject {

	@Getter
	private BufferedImage image;
	private static final int[] imageParameter = {2, 259};

	public Concrete(int x, int y) {
		super(x, y);
		image = resizeImage(imageParameter[0], imageParameter[1]);
	}

	@Override
	public void setXPosition(int xPosition) { this.xPosition = xPosition; }

	@Override
	public void setYPosition(int yPosition) { this.yPosition = yPosition; }
}