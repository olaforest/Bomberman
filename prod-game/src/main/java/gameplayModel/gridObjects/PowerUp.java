package gameplayModel.gridObjects;

import gameplayVisual.ImageManager;
import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utilities.Position.create;

@Getter
public class PowerUp extends HiddenObject {
	private final BufferedImage image;
	private final boolean permanent;

	private PowerUp(PowerUpType type, Position position) {
		super(position);
		permanent = type.isPermanent();
		image = generateImage(type.getAnimParam());
	}

	public static PowerUp createPowerUp(PowerUpType type, int xPosition, int yPosition) {
		return new PowerUp(type, create(xPosition, yPosition));
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

	private BufferedImage generateImage(List<Integer> parameters) {
		return ImageManager.resizeImage(parameters.get(0), parameters.get(1));
	}
}
