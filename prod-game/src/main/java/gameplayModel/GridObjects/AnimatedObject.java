package gameplayModel.GridObjects;

import gameplayModel.Animation;
import gameplayModel.GridObject;
import lombok.Getter;

import java.util.List;

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

	public void setCurrentAnimation(int aT) {
		currentAnimation = animationList.get(aT);
		currentAnimation.setToInitialFrame();
		animationNumber = aT;
	}
}
