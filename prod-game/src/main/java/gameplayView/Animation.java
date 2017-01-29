package gameplayView;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import static gameplayView.ImageManager.getImages;

@Getter
public class Animation {
	private final Iterator<BufferedImage> frames;
	private BufferedImage currentFrame;

	Animation(AnimParam animParam) {
		frames = getImages(animParam.getNumOfFrames());
		cycleFrame();
	}

	boolean isAnimDone() {
		return !frames.hasNext();
	}

	void cycleFrame() {
		if (!isAnimDone())
			currentFrame = frames.next();
	}
}
