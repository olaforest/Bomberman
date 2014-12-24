
package gameplayModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class define the Exitway of each level of game including its location and associated sprite images
 * 
 * @author Olivier Laforest
 */
public class Exitway extends GridObject {
	
	private BufferedImage img;
	private int[] imageParameter = {2, 241};

	/**
	 * @param x x-coordinates of the exitway
	 * @param y y-coordinates of the exitway
	 */
	public Exitway(int x, int y) {
		
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
    
    /**
	 * record the location of the exitway on the GridMap and prepare to be saved as CSV
	 * @return entryList the Array containing the location of the exitway
	 */
    public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));

		return entryList; 
	}
}