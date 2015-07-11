package gameplayModel.GridObjects.PowerUps;

import gameplayModel.GridObject;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Getter
public class PowerUp extends GridObject {
	
	protected BufferedImage image;
	protected boolean isPermanent;
	
	public PowerUp(int x, int y) {
		super(x, y);
	}
	
	public BufferedImage generateImage(int[] imageParameter) {
		return resizeImage(image.getSubimage(imageParameter[0], imageParameter[1], PIXELWIDTH, PIXELHEIGHT), ZOOM);
	}
	
	@Override
	public void setXPosition(int xPosition) { this.xPosition = xPosition; }

    @Override
	public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
	public ArrayList<String> toCSVEntry() {
		ArrayList<String> entryList = new ArrayList<>();
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		return entryList;
	}
}
