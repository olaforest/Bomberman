package gameplayModel.gridObjects;

import static gameplayView.AnimationType.Death;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.util.stream.Collectors.toMap;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayView.AnimParam;
import gameplayView.Animation;
import gameplayView.AnimationType;
import lombok.Getter;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

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

	public boolean isInRangeOf(Bomb bomb) {
		return isInHorizontalRangeOf(bomb) || isInVerticalRangeOf(bomb);
	}

	private boolean isInHorizontalRangeOf(Bomb bomb) {
		return isInSameRowAs(bomb) && (isInRightRangeOf(bomb) || isInLeftRangeOf(bomb));
	}

	private boolean isInVerticalRangeOf(Bomb bomb) {
		return isInSameColumnAs(bomb) && (isInDownRangeOf(bomb) || isInUpRangeOf(bomb));
	}

	private boolean isInRightRangeOf(Bomb bomb) {
		return bomb.getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM >= getX() + 1 && bomb.getX() < getX();
	}

	private boolean isInLeftRangeOf(Bomb bomb) {
		return bomb.getX() - (bomb.getLeftRange() + 1) * EFFECTIVE_PIXEL_DIM <= getX() - 1 && bomb.getX() > getX();
	}

	private boolean isInDownRangeOf(Bomb bomb) {
		return bomb.getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM >= getY() + 1 && bomb.getY() < getY();
	}

	private boolean isInUpRangeOf(Bomb bomb) {
		return bomb.getY() - (bomb.getUpRange() + 1) * EFFECTIVE_PIXEL_DIM <= getY() - 1 && bomb.getY() > getY();
	}
}
