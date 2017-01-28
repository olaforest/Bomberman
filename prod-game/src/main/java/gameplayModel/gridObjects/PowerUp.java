package gameplayModel.gridObjects;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class PowerUp extends HiddenObject {
	private final BufferedImage image;
	private final boolean permanent;

	protected PowerUp(PowerUpType type, Position position) {
		super(position);
		permanent = type.isPermanent();
		image = generateImage(type.getAnimParam());
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
		return resizeImage(parameters.get(0), parameters.get(1));
	}
}
