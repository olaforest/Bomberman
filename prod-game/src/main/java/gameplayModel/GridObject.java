package gameplayModel;

import gameplayModel.gridObjects.animatedObjects.Bomberman;
import lombok.Getter;
import utilities.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static gameplayModel.GridMap.MAPHEIGHT;
import static gameplayModel.GridMap.MAPWIDTH;

@Getter
public class GridObject {
	public static final BufferedImage sprite = loadSpriteSheet();

	public static final int ZOOM = 2;
	public static final int PIXEL_DIMENSION = 16;
	public static final int EFFECTIVE_PIXEL_DIMENSION = PIXEL_DIMENSION * ZOOM;
	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int ADJUSTMENT = 4;
	protected static final int MIN_Y_POSITION = EFFECTIVE_PIXEL_DIMENSION;
	protected static final int MIN_X_POSITION = EFFECTIVE_PIXEL_DIMENSION;
	protected static final int MAX_X_POSITION = EFFECTIVE_PIXEL_DIMENSION * (MAPWIDTH - 2);
	protected static final int MAX_Y_POSITION = EFFECTIVE_PIXEL_DIMENSION * (MAPHEIGHT - 2);

	protected Position position;
	protected boolean isConcreteCollision;

	public GridObject(Position position) {
		this.position = position;
		isConcreteCollision = false;
	}

	public void setXPosition(int xPos) {
		isConcreteCollision = false;
		final int yError = (position.getY() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInXRange = (xPos >= EFFECTIVE_PIXEL_DIMENSION) && (xPos <= EFFECTIVE_PIXEL_DIMENSION * (MAPWIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			position.setX(xPos);
		} else if (isAboveRow && isInXRange) {
			position.setX(xPos);
			position.incrementY(ADJUSTMENT);
		} else if (isBelowRow && isInXRange) {
			position.setX(xPos);
			position.decrementY(ADJUSTMENT);
		} else
			isConcreteCollision = true;
	}

	public void setYPosition(int yPos) {
		isConcreteCollision = false;
		final int xError = (position.getX() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInYRange = (yPos >= EFFECTIVE_PIXEL_DIMENSION) && (yPos <= EFFECTIVE_PIXEL_DIMENSION * (GridMap.MAPHEIGHT - 2));
		boolean isAlignedWithColumn = ((xError) == 0);
		boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			position.setY(yPos);
		} else if (isRightFromColumn && isInYRange) {
			position.setY(yPos);
			position.decrementX(ADJUSTMENT);
		} else if (isLeftFromColumn && isInYRange) {
			position.setY(yPos);
			position.incrementX(ADJUSTMENT);
		} else
			isConcreteCollision = true;
	}

	public boolean isSamePosition(GridObject object) {
		return position.isSame(object.getPosition());
	}

	public boolean isSamePosition(int xPosition, int yPosition) {
		return position.getX() == xPosition && position.getY() == yPosition;
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
