//*****************************************************************************************************
//  Concrete.java
//
//*****************************************************************************************************

package gameplayModel;

import java.awt.image.BufferedImage;

public class Concrete extends GridObject {
	
	private BufferedImage img;
	private int[] imageParameter = {2, 259};

	public Concrete(int x, int y) {
		
		super(x, y);
		
		img = resizeImage(image.getSubimage(imageParameter[0], imageParameter[1], PIXELWIDTH, PIXELHEIGHT), ZOOM);
		
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}