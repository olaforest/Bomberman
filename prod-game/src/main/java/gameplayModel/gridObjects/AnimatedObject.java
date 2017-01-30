package gameplayModel.gridObjects;

import gameplayModel.GridObject;
import gameplayView.AnimParam;
import gameplayView.Animation;
import gameplayView.AnimationType;
import lombok.Getter;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public abstract class AnimatedObject extends GridObject {

	private final Map<AnimationType, Animation> animations;
	@Getter protected Animation currentAnimation;
	@Getter protected boolean isDead, isObsolete;
	protected int counter, animCycleParam;

	protected AnimatedObject(Position position, List<SimpleEntry<AnimationType, AnimParam>> animParams) {
		super(position);
		this.animations = generateAnimations(animParams);
		counter = 0;
		animCycleParam = 3;
		isDead = false;
		isObsolete = false;
	}

	public void cycleAnimation() {
		if (counter % animCycleParam == 0) {
//			if (!isDead)
//				currentAnimation.cycleFrame();
//			else
//				cycleDeathAnimation();
		}
		counter++;
	}

	public void setCurrentAnimation(int aT) {
//		currentAnimation = animations.get(aT);
//		currentAnimation.setToInitialFrame();
//		animationNumber = aT;
	}

	private void cycleDeathAnimation() {
//		if (currentAnimation.isAnimDone())
//			isObsolete = true;
//		else
//			currentAnimation.cycleFrame();
	}

	public void triggerDeath() {
		currentAnimation = animations.get(animations.size() - 1);
		isDead = true;
	}

	private Map<AnimationType, Animation> generateAnimations(List<SimpleEntry<AnimationType, AnimParam>> params) {
		return params.stream()
				.collect(toMap(SimpleEntry::getKey, entry -> new Animation(entry.getValue())));
	}
}
