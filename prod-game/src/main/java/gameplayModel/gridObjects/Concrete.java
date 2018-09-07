package gameplayModel.gridObjects;

import static gameplayView.ImageManager.importScaledImage;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;

public class Concrete extends FixedObject {
	@Getter
	private static final BufferedImage image = importScaledImage(20, 346);

	public Concrete(Position position) {
		super(position);
	}
}