package gameplayView;

import gameplayModel.gridObjects.animatedObjects.Bomberman;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

import static java.awt.RenderingHints.*;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static javax.imageio.ImageIO.read;

@UtilityClass
public class ImageManager {
	private static final int ZOOM = 2;
	public static final int PIXEL_DIMENSION = 16;
	public static final int EFFECTIVE_PIXEL_DIMENSION = PIXEL_DIMENSION * ZOOM;
	private static final BufferedImage sprite = loadSpriteSheet()
			.orElseThrow(() -> new RuntimeException("Could not load sprite sheet"));

	public static Iterator<BufferedImage> getImages(int numOfImages) {
		return null;
	}

	public static BufferedImage resizeImage(int xCoordinate, int yCoordinate) {
		return resizeImage(sprite.getSubimage(xCoordinate, yCoordinate, PIXEL_DIMENSION, PIXEL_DIMENSION));
	}

	private static BufferedImage resizeImage(BufferedImage imageIn) {
		final BufferedImage imageOut = new BufferedImage(imageIn.getWidth() * ZOOM, imageIn.getHeight() * ZOOM, TYPE_INT_RGB);
		final Graphics2D graphics2D = imageOut.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);

		//The three lines below are for RenderingHints for better sprite quality at cost of higher processing time.
		graphics2D.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		graphics2D.drawImage(imageIn, 0, 0, imageIn.getWidth() * ZOOM, imageIn.getHeight() * ZOOM, null);
		graphics2D.dispose();
		return imageOut;
	}

	private static Optional<BufferedImage> loadSpriteSheet() {
		try {
			final InputStream in = Bomberman.class.getResourceAsStream("/spritesheet.png");
			return of(read(in));
		} catch (IOException e) {
			e.printStackTrace();
			return empty();
		}
	}
}
