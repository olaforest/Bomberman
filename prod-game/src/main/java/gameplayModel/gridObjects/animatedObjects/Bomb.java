package gameplayModel.gridObjects.animatedObjects;

import gameplayController.GameplayController;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayView.Animation;
import gameplayView.ImageManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static gameplayModel.GridMap.MAPHEIGHT;
import static gameplayModel.GridMap.MAPWIDTH;
import static gameplayModel.gridObjects.animatedObjects.Bomb.AnimationType.*;
import static java.util.Arrays.asList;
import static java.util.function.IntUnaryOperator.identity;
import static java.util.stream.Collectors.toList;

@Getter
public class Bomb extends AnimatedObject {

	public enum AnimationType {unexploded, expCenter, expRight, expLeft, expDown, expUp, expVertical, expHorizontal}

	public static final int TIME_TO_EXPLOSION = 2500;
	public static final int[][] ANIM_PARAM = new int[][]{{113, 21, 4, 4, ImageManager.PIXEL_DIMENSION},
			{19, 223, 7, 4, 54},
			{37, 223, 7, 4, 54},
			{1, 223, 7, 4, 54},
			{19, 241, 7, 4, 54},
			{19, 205, 7, 4, 54},
			{37, 205, 7, 4, 54},
			{37, 241, 7, 4, 54}};

	@Getter
	private static int range = 1;

	private final List<Animation> currentAnimations;
	private final List<Integer> animXOffset;
	private final List<Integer> animYOffset;
	@Setter
	private int timer, rightRange, leftRange, downRange, upRange;

	@Accessors(fluent = true)
	private boolean wasTrigByBomb;

	public Bomb(Position position) {
		super(position);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		timer = TIME_TO_EXPLOSION;
		rightRange = leftRange = downRange = upRange = range;
		setRanges();
		wasTrigByBomb = false;
		animationList = generateAnimationList(asList(values()));
		addAnimation(unexploded.ordinal(), 0, 0);
	}

	public Bomb(int range, Position position, int timer, int right, int left, int down, int up) {
		super(position);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		this.timer = timer;
		Bomb.range = range;
		rightRange = right;
		leftRange = left;
		downRange = down;
		upRange = up;
		animationList = generateAnimationList(asList(values()));

		addAnimation(unexploded.ordinal(), 0, 0);
	}

	private List<Animation> generateAnimationList(List<AnimationType> animationType) {
		return IntStream.range(0, animationType.size())
				.mapToObj(this::generateAnimation)
				.collect(toList());
	}

	private Animation generateAnimation(int i) {
		final Animation animation = null;// = new Animation(ANIM_PARAM[i][2]);
//		for (int j = 0; j < ANIM_PARAM[i][3]; j++)
//			animation.setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * j, ANIM_PARAM[i][1]), j);

//		for (int n = (ANIM_PARAM[i][2] - ANIM_PARAM[i][3]); n > 0; n--)
//			animation.setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * n, ANIM_PARAM[i][1]), ANIM_PARAM[i][3] - n);
		return animation;
	}

	public void cycleAnimations() {
		if (counter % animCycleParam == 0) {
//			if (!isDead)
//				currentAnimations.forEach(Animation::cycleFrame);
//			else
//				cycleDeathAnimations();
		}
		counter++;
	}

	private void cycleDeathAnimations() {
		for (int i = 0; i < currentAnimations.size(); ) {
//			if (currentAnimations.get(i).isAnimDone()) {
//				removeAnimation(i);
//				if (currentAnimations.size() == 0) isObsolete = true;
//			} else {
//				currentAnimations.get(i).cycleFrame();
//				i++;
//			}
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
		addAnimation(expCenter.ordinal(), 0, 0);
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

	private final IntConsumer addHorizontalAnimation = offset -> addAnimation(expHorizontal.ordinal(), offset, 0);
	private final IntConsumer addLeftAnimation = offset -> addAnimation(expLeft.ordinal(), offset, 0);
	private final IntConsumer addRightAnimation = offset -> addAnimation(expRight.ordinal(), offset, 0);

	private final IntConsumer addVerticalAnimation = offset -> addAnimation(expVertical.ordinal(), 0, offset);
	private final IntConsumer addUpAnimation = offset -> addAnimation(expUp.ordinal(), 0, offset);
	private final IntConsumer addDownAnimation = offset -> addAnimation(expDown.ordinal(), 0, offset);

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

	private final IntPredicate isNotAlignedWithRowOrColumn = (position) -> position % (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2) == 0;
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
			rightRange = adjustedMaxRangePosition(position.getX(), MAPWIDTH);
		if (minRangePosition(position.getX(), leftRange) <= MIN_X_POSITION)
			leftRange = adjustedMinRangePosition(position.getX());
	}

	private void setVerticalRanges() {
		if (maxRangePosition(position.getY(), downRange) >= MAX_Y_POSITION)
			downRange = adjustedMaxRangePosition(position.getY(), MAPHEIGHT);
		if (minRangePosition(position.getY(), upRange) <= MIN_Y_POSITION)
			upRange = adjustedMinRangePosition(position.getY());
	}

	private int maxRangePosition(int position, int maxRange) {
		return position + maxRange * ImageManager.EFFECTIVE_PIXEL_DIMENSION;
	}

	private int minRangePosition(int position, int minRange) {
		return position - minRange * ImageManager.EFFECTIVE_PIXEL_DIMENSION;
	}

	private int adjustedMaxRangePosition(int position, int maxDimension) {
		return (maxDimension - 2) - position / ImageManager.EFFECTIVE_PIXEL_DIMENSION;
	}

	private int adjustedMinRangePosition(int position) {
		return position / ImageManager.EFFECTIVE_PIXEL_DIMENSION - 1;
	}

	private void addAnimation(int animType, int xOffset, int yOffset) {
//		currentAnimations.add(new Animation(animationList.get(animType)));
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
