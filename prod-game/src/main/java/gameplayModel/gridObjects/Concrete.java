package gameplayModel.gridObjects;

import gameplayModel.GridObject;
import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;

public class Concrete extends GridObject {

	@Getter
	private BufferedImage image;
	private static final int[] imageParameter = {2, 259};

	public Concrete(Position position) {
		super(position);
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
}