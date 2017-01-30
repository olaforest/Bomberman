package gameplayModel.gridObjects;

import lombok.Getter;
import utilities.Position;

import java.awt.image.BufferedImage;

import static gameplayView.ImageManager.importScaledImage;

public class Concrete extends FixedObject {
	@Getter
	private static final BufferedImage image = importScaledImage(2, 259);

	public Concrete(Position position) {
		super(position);
	}
}