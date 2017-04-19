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

import static gameplayView.AnimationType.Death;
import static java.util.stream.Collectors.toMap;

public abstract class AnimatedObject extends GridObject {
	private final Map<AnimationType, Animation> animations;
	@Getter private Animation currentAnimation;
	@Getter protected boolean isObsolete;
	protected int counter, animCycleParam;

	protected AnimatedObject(Position position, List<SimpleEntry<AnimationType, AnimParam>> animParams) {
		super(position);
		this.animations = generateAnimations(animParams);
		currentAnimation = animations.get(animParams.get(0).getKey());
		counter = 0;
		animCycleParam = 3;
		isObsolete = false;
	}

	public void cycleAnimation() {
		if (counter % animCycleParam == 0) {
			if (currentAnimation.getType() == Death)
				cycleDeathAnimation();
			else
				currentAnimation.cycleFrame();
		}
		counter++;
	}

	public void triggerDeath() {
		currentAnimation = animations.get(Death);
	}

	public boolean isDead() {
		return currentAnimation.getType() == Death;
	}

	public AnimationType getCurrentAnimationType() {
		return currentAnimation.getType();
	}

	public void setCurrentAnimation(AnimationType type) {
		currentAnimation = animations.get(type).reset();
	}

	private void cycleDeathAnimation() {
		if (currentAnimation.isAnimDone())
			isObsolete = true;
		else
			currentAnimation.cycleFrame();
	}

	private static Map<AnimationType, Animation> generateAnimations(List<SimpleEntry<AnimationType, AnimParam>> params) {
		return params.stream()
				.collect(toMap(SimpleEntry::getKey, entry -> new Animation(entry.getKey(), entry.getValue())));
	}
}
