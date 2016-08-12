package gameplayModel.GridObjects;

import gameplayModel.Animation;
import gameplayModel.GridObject;
import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class AnimatedObject extends GridObject {

	public final int INITIAL_ANIMATION = 0;

	protected List<Animation> animationList;
	@Getter
	private Animation currentAnimation;
	@Getter
	protected boolean isDead, isObsolete;
	@Getter
	private int animationNumber;
	protected int animCycleParam;
	protected int counter;

	public AnimatedObject(int x, int y) {

		super(x, y);

		generateAnimationList();

		currentAnimation = animationList.get(INITIAL_ANIMATION);
		animationNumber = INITIAL_ANIMATION;

		counter = 0;
		animCycleParam = 3;

		isDead = false;
		isObsolete = false;
	}

	public void cycleAnimation() {

		if (counter % animCycleParam == 0) {
			if (!isDead) {
				currentAnimation.cycleFrame();

			} else {
				if (currentAnimation.isAnimDone())
					isObsolete = true;
				else
					currentAnimation.cycleFrame();
			}
		}
		counter++;
	}

	public void triggerDeath() {
		currentAnimation = animationList.get(animationList.size() - 1);
		isDead = true;
	}

	public abstract void generateAnimationList();

	protected List<Animation> generateAnimationList(List<?> animationTypes, int[][] animParam) {
		return IntStream.range(0, animationTypes.size())
				.mapToObj(i -> generateAnimation(i, animParam))
				.collect(toList());
	}

	private Animation generateAnimation(int i, int[][] animParam) {
		final Animation animation = new Animation(animParam[i][2]);
		IntStream.range(0, animParam[i][2])
				.forEach(j -> animation.setFrame(resizeImage(animParam[i][0] + (PIXEL_DIMENSION + 2) * j, animParam[i][1]), j));
		return animation;
	}

	public void setCurrentAnimation(int aT) {
		currentAnimation = animationList.get(aT);
		currentAnimation.setToInitialFrame();
		animationNumber = aT;
	}
}
