package gameplayModel;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

	@Getter
	private List<BufferedImage> frames;
	@Getter
	private boolean isAnimDone;
	private int counter;

	public Animation(int frameNumber) {
		frames = new ArrayList<>(frameNumber);
		isAnimDone = false;
		counter = 0;
	}

	public Animation(Animation anim) {
		frames = anim.getFrames();
		isAnimDone = false;
		counter = 0;
	}

	public void setFrame(BufferedImage img, int index) { frames.add(index, img); }

	public BufferedImage getCurrentFrame() { return frames.get(counter); }

	public void cycleFrame() {
		counter = (counter + 1) % frames.size();

		if (counter == frames.size() - 1) isAnimDone = true;
	}

	public void setToInitialFrame() { counter = 0; }
}
