package gameplayModel.gridObjects;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;

import static gameplayView.ImageManager.importScaledImage;

public class Concrete extends FixedObject {
	@Getter
	private static final BufferedImage image = importScaledImage(20, 346);

	public Concrete(Position position) {
		super(position);
	}
}