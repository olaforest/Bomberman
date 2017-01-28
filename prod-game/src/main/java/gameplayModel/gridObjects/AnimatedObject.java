package gameplayModel.gridObjects;

import gameplayModel.Animation;
import gameplayModel.GridObject;
import lombok.Getter;
import utilities.Position;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class AnimatedObject extends GridObject {
	private static final int INITIAL_ANIMATION = 0;

	protected List<Animation> animationList;
	@Getter private Animation currentAnimation;
	@Getter protected boolean isDead, isObsolete;
	@Getter private int animationNumber;
	protected int counter, animCycleParam;

	protected AnimatedObject(Position position) {
		super(position);
		currentAnimation = animationList.get(INITIAL_ANIMATION);
		animationNumber = INITIAL_ANIMATION;
		counter = 0;
		animCycleParam = 3;
		isDead = false;
		isObsolete = false;
	}

	public void cycleAnimation() {
		if (counter % animCycleParam == 0) {
			if (!isDead)
				currentAnimation.cycleFrame();
			else
				cycleDeathAnimation();
		}
		counter++;
	}

	private void cycleDeathAnimation() {
		if (currentAnimation.isAnimDone())
			isObsolete = true;
		else
			currentAnimation.cycleFrame();
	}

	public void triggerDeath() {
		currentAnimation = animationList.get(animationList.size() - 1);
		isDead = true;
	}

	protected List<Animation> generateAnimationList(List<?> animationTypes, List<List<Integer>> animParam, int adjustment) {
		return IntStream.range(0, animationTypes.size())
				.mapToObj(i -> generateAnimation(i, animParam, adjustment))
				.collect(toList());
	}

	private Animation generateAnimation(int i, List<List<Integer>> animParam, int adjustment) {
		final Animation animation = new Animation(animParam.get(i).get(2));
		IntStream.range(0, animParam.get(i).get(2))
				.forEach(j -> animation.setFrame(resizeImage(animParam.get(i).get(0) + (PIXEL_DIMENSION + adjustment) * j, animParam.get(i).get(1)), j));
		return animation;
	}

	public void setCurrentAnimation(int aT) {
		currentAnimation = animationList.get(aT);
		currentAnimation.setToInitialFrame();
		animationNumber = aT;
	}
}
