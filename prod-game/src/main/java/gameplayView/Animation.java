package gameplayView;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import static gameplayView.ImageManager.getImages;
import static java.util.Collections.emptyList;

@Getter
public class Animation {
	private final Iterator<BufferedImage> frames;
	private BufferedImage currentFrame;

	public Animation(AnimParam animParam) {
		frames = getImages(animParam)
				.orElse(emptyList())
				.iterator();
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
