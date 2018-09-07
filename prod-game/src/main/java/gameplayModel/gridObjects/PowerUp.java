package gameplayModel.gridObjects;

import gameplayModel.gridObjects.animatedObjects.Bomberman;
import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static gameplayView.ImageManager.importScaledImage;
import static utilities.Position.create;

@Getter
public class PowerUp extends FixedObject implements HiddenObject {
	private final BufferedImage image;
	private final boolean permanent;
	private final Consumer<Bomberman> action;

	private PowerUp(PowerUpType type, Position position) {
		super(position);
		permanent = type.isPermanent();
		image = generateImage(type.getAnimParam());
		action = type.getAction();
	}

	public void performAction(Bomberman bomberman) {
		action.accept(bomberman);
	}

	public static PowerUp createPowerUp(PowerUpType type, int xPosition, int yPosition) {
		return new PowerUp(type, create(xPosition, yPosition));
	}

	public static PowerUp createPowerUp(PowerUpType type, Position position) {
		return new PowerUp(type, position);
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
