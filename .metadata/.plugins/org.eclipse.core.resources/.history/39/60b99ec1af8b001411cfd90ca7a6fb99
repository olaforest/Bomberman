//*****************************************************************************************************
//  Animation.java
//
//*****************************************************************************************************

package gameplayModel;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jonti
 */
public class Animation {
	
    private BufferedImage[] frames;
    private int counter;
    private boolean isAnimDone;
    
    public Animation(int frameNumber) {
    	
        frames = new BufferedImage[frameNumber];
        counter = 0;
        isAnimDone = false;
    }
    
    public Animation(Animation anim) {
    	
    	frames = anim.getFrames();
    	counter = 0;
    	isAnimDone = false;
    }
    
    public void setFrame(BufferedImage img, int index) {
    	frames[index] = img;
	}
    
	public BufferedImage getCurrentFrame() {
		return frames[counter];
    }
	
	private BufferedImage[] getFrames() {
		return frames;
	}
	
	/*public int getCurrentFrameNumber() {
		return counter;
	}*/
	
	/*public int getNumberOfFrame() {
		return frames.length;
	}*/
	
	public void cycleFrame() {
		counter = ((counter + 1) % frames.length);
		
		if (counter == frames.length - 1)
			isAnimDone = true;
	}
	
	public void setToInitialFrame(){
		counter = 0;
	}
	
	public boolean isAnimDone() {
		return isAnimDone;
	}
}