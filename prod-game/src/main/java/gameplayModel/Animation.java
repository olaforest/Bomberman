package gameplayModel;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	@Getter private final List<BufferedImage> frames;
	@Getter private boolean animDone;
	private int counter;

	public Animation(int frameNumber) {
		frames = new ArrayList<>(frameNumber);
		animDone = false;
		counter = 0;
	}

	public Animation(Animation anim) {
		frames = anim.getFrames();
		animDone = false;
		counter = 0;
	}

	public void setFrame(BufferedImage img, int index) {
		frames.add(index, img);
	}

	public BufferedImage getCurrentFrame() {
		return frames.get(counter);
	}

	public void cycleFrame() {
		counter = (counter + 1) % frames.size();

		if (counter == frames.size() - 1) animDone = true;
	}

	public void setToInitialFrame() {
		counter = 0;
	}
}
