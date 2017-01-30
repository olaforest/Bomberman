package gameplayModel.gridObjects;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static gameplayView.ImageManager.importScaledImage;
import static utilities.Position.create;

@Getter
public class PowerUp extends FixedObject implements HiddenObject {
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

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		return entryList;
	}

	private BufferedImage generateImage(List<Integer> parameters) {
		return importScaledImage(parameters.get(0), parameters.get(1));
	}
}
