package gameplayModel.gridObjects.animatedObjects;

import static gameplayModel.GridMap.MAP_HEIGHT;
import static gameplayModel.GridMap.MAP_WIDTH;
import static gameplayView.AnimationType.*;
import static java.util.Arrays.asList;
import static java.util.function.IntUnaryOperator.identity;

import gameplayController.GameplayController;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.AnimParam;
import gameplayView.Animation;
import gameplayView.AnimationType;
import gameplayView.ImageManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

@Getter
public class Bomb extends AnimatedObject {
	private static final int TIME_TO_EXPLOSION = 2500;
	private static final List<SimpleEntry<AnimationType, AnimParam>> animParams = asList(
			new SimpleEntry<>(Unexploded, new AnimParam(128, 21, 4)),
			new SimpleEntry<>(ExpCenter, new AnimParam(2, 273, 7)),
			new SimpleEntry<>(ExpRight, new AnimParam(2, 255, 7)),
			new SimpleEntry<>(ExpLeft, new AnimParam(2, 219, 7)),
			new SimpleEntry<>(ExpDown, new AnimParam(2, 237, 7)),
			new SimpleEntry<>(ExpUp, new AnimParam(2, 201, 7)),
			new SimpleEntry<>(ExpVertical, new AnimParam(2, 309, 7)),
			new SimpleEntry<>(ExpHorizontal, new AnimParam(2, 291, 7)));

	@Getter
	private static int range = 1;

	private final List<Animation> currentAnimations;
	private final List<Integer> animXOffset;
	private final List<Integer> animYOffset;
	@Setter
	private int timer, rightRange, leftRange, downRange, upRange;

	@Accessors(fluent = true)
	private boolean wasTrigByBomb, isDead;

	public Bomb(Position position) {
		super(position, animParams);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		timer = TIME_TO_EXPLOSION;
		rightRange = leftRange = downRange = upRange = range;
		setRanges();
		wasTrigByBomb = false;
		isDead = false;
		addAnimation(Unexploded.ordinal(), 0, 0);
	}

	public Bomb(int range, Position position, int timer, int right, int left, int down, int up) {
		super(position, animParams);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();
		isDead = false;

		this.timer = timer;
		Bomb.range = range;
		rightRange = right;
		leftRange = left;
		downRange = down;
		upRange = up;
		addAnimation(Unexploded.ordinal(), 0, 0);
	}

	public void cycleAnimations() {
		if (counter % animCycleParam == 0) {
			if (!isDead)
				currentAnimations.forEach(Animation::cycleFrame);
			else
				cycleDeathAnimations();
		}
		counter++;
	}

	private void cycleDeathAnimations() {
		for (int i = 0; i < currentAnimations.size(); ) {
			if (currentAnimations.get(i).isAnimDone()) {
				removeAnimation(i);
				if (currentAnimations.size() == 0)
					isObsolete = true;
			} else {
				currentAnimations.get(i).cycleFrame();
				i++;
			}
		}
	}

	public void decreaseTimer() {
		timer -= GameplayController.TIMEOUT;

		if (timer <= 0 && !isDead) triggerExplosion();
	}

	public void triggerExplosion() {
		isDead = true;
		timer = 0;
		animCycleParam = 2;
		clearAnimation();
		addAnimation(ExpCenter.ordinal(), 0, 0);
		processRanges();
	}

	private void processRanges() {
		processHorizontalRange(leftRange, addLeftAnimation, i -> -i);
		processHorizontalRange(rightRange, addRightAnimation, identity());
		processVerticalRange(upRange, addUpAnimation, i -> -i);
		processVerticalRange(downRange, addDownAnimation, identity());
	}

	private void processHorizontalRange(int size, IntConsumer animType, IntUnaryOperator direction) {
		processRange(size, animType, addHorizontalAnimation, direction);
	}

	private void processVerticalRange(int size, IntConsumer animType, IntUnaryOperator direction) {
		processRange(size, animType, addVerticalAnimation, direction);
	}

	private void processRange(int size, IntConsumer addEndAnim, IntConsumer addIntermediateAnim,
														IntUnaryOperator direction) {
		if (size > 0) {
			if (isRangeChanged.test(size))
				addChangedRangeAnim(size, addIntermediateAnim, direction);
			else
				addRangeAnim(size, addEndAnim, addIntermediateAnim, direction);
		}
	}

	private final IntPredicate isRangeChanged = (size) -> range != size;

	private void addChangedRangeAnim(int rangeSize, IntConsumer addIntermediateAnim, IntUnaryOperator direction) {
		IntStream.rangeClosed(1, rangeSize)
				.map(direction)
				.forEach(addIntermediateAnim);
	}

	private void addRangeAnim(int rangeSize, IntConsumer addEndAnim, IntConsumer addIntermediateAnim, IntUnaryOperator direction) {
		addEndAnim.accept(direction.applyAsInt(rangeSize));
		IntStream.range(1, rangeSize)
				.map(direction)
				.forEach(addIntermediateAnim);
	}

	private final IntConsumer addHorizontalAnimation = offset -> addAnimation(ExpHorizontal.ordinal(), offset, 0);
	private final IntConsumer addLeftAnimation = offset -> addAnimation(ExpLeft.ordinal(), offset, 0);
	private final IntConsumer addRightAnimation = offset -> addAnimation(ExpRight.ordinal(), offset, 0);

	private final IntConsumer addVerticalAnimation = offset -> addAnimation(ExpVertical.ordinal(), 0, offset);
	private final IntConsumer addUpAnimation = offset -> addAnimation(ExpUp.ordinal(), 0, offset);
	private final IntConsumer addDownAnimation = offset -> addAnimation(ExpDown.ordinal(), 0, offset);

	private void setRanges() {
		setRanges(isNotAlignedWithRow, this::resetHorizontalRanges, this::setHorizontalRanges);
		setRanges(isNotAlignedWithColumn, this::resetVerticalRanges, this::setVerticalRanges);
	}

	private void setRanges(BooleanSupplier isNotAligned, Runnable resetRanges, Runnable setAdjustedRanges) {
		if (isNotAligned.getAsBoolean())
			resetRanges.run();
		else
			setAdjustedRanges.run();
	}

	private final IntPredicate isNotAlignedWithRowOrColumn = (position) -> position % (ImageManager.EFFECTIVE_PIXEL_DIM * 2) == 0;
	private final BooleanSupplier isNotAlignedWithRow = () -> isNotAlignedWithRowOrColumn.test(position.getY());
	private final BooleanSupplier isNotAlignedWithColumn = () -> isNotAlignedWithRowOrColumn.test(position.getX());

	private void resetHorizontalRanges() {
		rightRange = 0;
		leftRange = 0;
	}

	private void resetVerticalRanges() {
		downRange = 0;
		upRange = 0;
	}

	private void setHorizontalRanges() {
		if (maxRangePosition(position.getX(), rightRange) >= MAX_X_POSITION)
			rightRange = adjustedMaxRangePosition(position.getX(), MAP_WIDTH);
		if (minRangePosition(position.getX(), leftRange) <= MIN_X_POSITION)
			leftRange = adjustedMinRangePosition(position.getX());
	}

	private void setVerticalRanges() {
		if (maxRangePosition(position.getY(), downRange) >= MAX_Y_POSITION)
			downRange = adjustedMaxRangePosition(position.getY(), MAP_HEIGHT);
		if (minRangePosition(position.getY(), upRange) <= MIN_Y_POSITION)
			upRange = adjustedMinRangePosition(position.getY());
	}

	private int maxRangePosition(int position, int maxRange) {
		return position + maxRange * ImageManager.EFFECTIVE_PIXEL_DIM;
	}

	private int minRangePosition(int position, int minRange) {
		return position - minRange * ImageManager.EFFECTIVE_PIXEL_DIM;
	}

	private int adjustedMaxRangePosition(int position, int maxDimension) {
		return (maxDimension - 2) - position / ImageManager.EFFECTIVE_PIXEL_DIM;
	}

	private int adjustedMinRangePosition(int position) {
		return position / ImageManager.EFFECTIVE_PIXEL_DIM - 1;
	}

	private void addAnimation(int animType, int xOffset, int yOffset) {
//		currentAnimations.add(new Animation(animations.get(animType)));
		animXOffset.add(xOffset);
		animYOffset.add(yOffset);
	}

	private void removeAnimation(int index) {
		currentAnimations.remove(index);
		animXOffset.remove(index);
		animYOffset.remove(index);
	}

	private void clearAnimation() {
		currentAnimations.clear();
		animXOffset.clear();
		animYOffset.clear();
	}

	void setWasTrigByBomb() {
		wasTrigByBomb = true;
	}

	static void increaseRange() {
		range++;
	}

	public static void resetRange() {
		range = 1;
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		entryList.add(Integer.toString(timer));
		entryList.add(Integer.toString(rightRange));
		entryList.add(Integer.toString(leftRange));
		entryList.add(Integer.toString(downRange));
		entryList.add(Integer.toString(upRange));

		return entryList;
	}
}
