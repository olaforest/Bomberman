package gameplayModel;

import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static gameplayModel.GridMap.MAPWIDTH;

@Getter
public class GridObject {
	public static final BufferedImage sprite = loadSpriteSheet();

	public static final int ZOOM = 2;
	public static final int PIXEL_DIMENSION = 16;
	public static final int EFFECTIVE_PIXEL_WIDTH = PIXEL_DIMENSION * ZOOM;
	public static final int EFFECTIVE_PIXEL_HEIGHT = PIXEL_DIMENSION * ZOOM;
	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int ADJUSTMENT = 4;

	protected int xPosition;
	protected int yPosition;
	protected boolean isConcreteCollision;

	public GridObject(int x, int y) {
		xPosition = x;
		yPosition = y;
		isConcreteCollision = false;
	}

	public void setXPosition(int xPosition) {
		isConcreteCollision = false;
		final int yError = (this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2);

		boolean isInXRange = (xPosition >= EFFECTIVE_PIXEL_WIDTH) && (xPosition <= EFFECTIVE_PIXEL_WIDTH * (MAPWIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			this.xPosition = xPosition;
		} else if (isAboveRow && isInXRange) {
			this.xPosition = xPosition;
			this.yPosition += ADJUSTMENT;
		} else if (isBelowRow && isInXRange) {
			this.xPosition = xPosition;
			this.yPosition -= ADJUSTMENT;
		} else
			isConcreteCollision = true;
	}

	public void setYPosition(int yPosition) {
		isConcreteCollision = false;
		final int xError = (this.xPosition - EFFECTIVE_PIXEL_WIDTH) % (EFFECTIVE_PIXEL_WIDTH * 2);

		boolean isInYRange = (yPosition >= EFFECTIVE_PIXEL_HEIGHT) && (yPosition <= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2));
		boolean isAlignedWithColumn = ((xError) == 0);
		boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			this.yPosition = yPosition;
		} else if (isRightFromColumn && isInYRange) {
			this.yPosition = yPosition;
			this.xPosition -= ADJUSTMENT;
		} else if (isLeftFromColumn && isInYRange) {
			this.yPosition = yPosition;
			this.xPosition += ADJUSTMENT;
		} else
			isConcreteCollision = true;
	}

	public boolean isSamePosition(GridObject object) {
		return xPosition == object.getXPosition() && yPosition == object.getYPosition();
	}

	protected static BufferedImage resizeImage(int xCoordinate, int yCoordinate) {
		return resizeImage(sprite.getSubimage(xCoordinate, yCoordinate, PIXEL_DIMENSION, PIXEL_DIMENSION), ZOOM);
	}

	private static BufferedImage resizeImage(BufferedImage imageIn, int factor) {
		final BufferedImage imageOut = new BufferedImage(imageIn.getWidth() * factor, imageIn.getHeight() * factor, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = imageOut.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);

		//The three lines below are for RenderingHints for better sprite quality at cost of higher processing time.
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics2D.drawImage(imageIn, 0, 0, imageIn.getWidth() * factor, imageIn.getHeight() * factor, null);
		graphics2D.dispose();
		return imageOut;
	}

	private static BufferedImage loadSpriteSheet() {
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/spritesheet.png");
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
