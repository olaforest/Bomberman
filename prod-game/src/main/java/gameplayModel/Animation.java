package gameplayModel;

import lombok.Getter;

import java.awt.image.BufferedImage;

public class Animation {

	@Getter
	private BufferedImage[] frames;
	@Getter
	private boolean isAnimDone;
	private int counter;

	public Animation(int frameNumber) {
		frames = new BufferedImage[frameNumber];
		isAnimDone = false;
		counter = 0;
	}

	public Animation(Animation anim) {
		frames = anim.getFrames();
		isAnimDone = false;
		counter = 0;
	}

	public void setFrame(BufferedImage img, int index) {
		frames[index] = img;
	}

	public BufferedImage getCurrentFrame() {
		return frames[counter];
	}

	public void cycleFrame() {
		counter = ((counter + 1) % frames.length);

		if (counter == frames.length - 1)
			isAnimDone = true;
	}

	public void setToInitialFrame() {
		counter = 0;
	}
}
