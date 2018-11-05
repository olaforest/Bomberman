package gameplayView;

import static gameplayView.ImageManager.getImages;
import static java.util.Collections.emptyList;

import lombok.AccessLevel;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

@Getter
public class Animation {
	@Getter(AccessLevel.NONE)
	private final List<BufferedImage> frameList;
	private final AnimationType type;
	private Iterator<BufferedImage> frames;
	private BufferedImage currentFrame;

	public Animation(AnimationType type, AnimParam animParam) {
		this.type = type;
		frameList = getImages(animParam).orElse(emptyList());
		frames = frameList.iterator();
		cycleFrame();
	}

	public boolean isAnimDone() {
		return !frames.hasNext();
	}

	public void cycleFrame() {
		if (!isAnimDone())
			currentFrame = frames.next();
	}

	public Animation reset() {
		frames = frameList.iterator();
		cycleFrame();
		return this;
	}
}
