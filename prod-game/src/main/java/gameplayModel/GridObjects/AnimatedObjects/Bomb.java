package gameplayModel.GridObjects.AnimatedObjects;

import gameplayController.GameplayController;
import gameplayModel.Animation;
import gameplayModel.GridObjects.AnimatedObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;

import static gameplayModel.GridMap.MAPHEIGHT;
import static gameplayModel.GridMap.MAPWIDTH;
import static gameplayModel.GridObjects.AnimatedObjects.Bomb.AnimationType.*;
import static java.util.Arrays.asList;
import static java.util.function.IntUnaryOperator.identity;
import static java.util.stream.Collectors.toList;

@Getter
public class Bomb extends AnimatedObject {

	public enum AnimationType {unexploded, expCenter, expRight, expLeft, expDown, expUp, expVertical, expHorizontal}

	public static final int TIME_TO_EXPLOSION = 2500;
	public static final int[][] ANIM_PARAM = new int[][]{{113, 21, 4, 4, PIXEL_DIMENSION},
			{19, 223, 7, 4, 54},
			{37, 223, 7, 4, 54},
			{1, 223, 7, 4, 54},
			{19, 241, 7, 4, 54},
			{19, 205, 7, 4, 54},
			{37, 205, 7, 4, 54},
			{37, 241, 7, 4, 54}};

	@Getter
	private static int range = 1;

	private List<Animation> currentAnimations;
	private List<Integer> animXOffset, animYOffset;
	@Setter
	private int timer;
	private int rightRange, leftRange, downRange, upRange;

	@Accessors(fluent = true)
	private boolean wasTrigByBomb;

	@Getter(AccessLevel.NONE)
	private boolean wasRightRangeChg, wasLeftRangeChg, wasDownRangeChg, wasUpRangeChg;

	public Bomb(int x, int y) {
		super(x, y);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		timer = TIME_TO_EXPLOSION;
		rightRange = leftRange = downRange = upRange = range;
		setRanges();
		wasTrigByBomb = wasRightRangeChg = wasLeftRangeChg = wasDownRangeChg = wasUpRangeChg = false;

		addAnimation(unexploded.ordinal(), 0, 0);
	}

	public Bomb(int range, int x, int y, int timer, int right, int left, int down, int up) {
		super(x, y);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		this.timer = timer;
		Bomb.range = range;
		rightRange = right;
		leftRange = left;
		downRange = down;
		upRange = up;

		addAnimation(unexploded.ordinal(), 0, 0);
	}

	public void generateAnimationList() {
		animationList = generateAnimationList(asList(values()));
	}

	private List<Animation> generateAnimationList(List<?> animationType) {
		return IntStream.range(0, animationType.size())
				.mapToObj(this::generateAnimation)
				.collect(toList());
	}

	private Animation generateAnimation(int i) {
		final Animation animation = new Animation(ANIM_PARAM[i][2]);
		for (int j = 0; j < ANIM_PARAM[i][3]; j++)
			animation.setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * j, ANIM_PARAM[i][1]), j);

		for (int n = (ANIM_PARAM[i][2] - ANIM_PARAM[i][3]); n > 0; n--)
			animation.setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * n, ANIM_PARAM[i][1]), ANIM_PARAM[i][3] - n);
		return animation;
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
				if (currentAnimations.size() == 0) isObsolete = true;
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
		addAnimation(expCenter.ordinal(), 0, 0);
		processRanges();
	}

	private void processRanges() {
		processHorizontalRange(leftRange, wasLeftRangeChg, addLeftAnimation, i -> -i);
		processHorizontalRange(rightRange, wasRightRangeChg, addRightAnimation, identity());
		processVerticalRange(upRange, wasUpRangeChg, addUpAnimation, i -> -i);
		processVerticalRange(downRange, wasDownRangeChg, addDownAnimation, identity());
	}

	private void processHorizontalRange(int size, boolean wasRangeChg, IntConsumer animType, IntUnaryOperator direction) {
		processRange(size, wasRangeChg, animType, addHorizontalAnimation, direction);
	}

	private void processVerticalRange(int size, boolean wasRangeChg, IntConsumer animType, IntUnaryOperator direction) {
		processRange(size, wasRangeChg, animType, addVerticalAnimation, direction);
	}

	private void processRange(int size, boolean wasRangeChg, IntConsumer addEndAnim, IntConsumer addIntermediateAnim,
							  IntUnaryOperator direction) {
		if (size > 0) {
			if (wasRangeChg)
				addChangedRangeAnim(size, addIntermediateAnim, direction);
			else
				addRangeAnim(size, addEndAnim, addIntermediateAnim, direction);
		}
	}

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

	private IntConsumer addHorizontalAnimation = offset -> addAnimation(expHorizontal.ordinal(), offset, 0);
	private IntConsumer addLeftAnimation = offset -> addAnimation(expLeft.ordinal(), offset, 0);
	private IntConsumer addRightAnimation = offset -> addAnimation(expRight.ordinal(), offset, 0);

	private IntConsumer addVerticalAnimation = offset -> addAnimation(expVertical.ordinal(), 0, offset);
	private IntConsumer addUpAnimation = offset -> addAnimation(expUp.ordinal(), 0, offset);
	private IntConsumer addDownAnimation = offset -> addAnimation(expDown.ordinal(), 0, offset);

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

	private void resetHorizontalRanges() {
		rightRange = 0;
		leftRange = 0;
	}

	private void resetVerticalRanges() {
		downRange = 0;
		upRange = 0;
	}

	private void setHorizontalRanges() {
		if (maxRangePosition.applyAsInt(xPosition, rightRange) >= MAX_X_POSITION)
			rightRange = adjustedMaxRangePosition.applyAsInt(xPosition, MAPWIDTH);
		if (minRangePosition.applyAsInt(xPosition, leftRange) <= MIN_X_POSITION)
			leftRange = adjustedMinRangePosition.applyAsInt(xPosition);
	}

	private void setVerticalRanges() {
		if (maxRangePosition.applyAsInt(yPosition, downRange) >= MAX_Y_POSITION)
			downRange = adjustedMaxRangePosition.applyAsInt(yPosition, MAPHEIGHT);
		if (minRangePosition.applyAsInt(yPosition, upRange) <= MIN_Y_POSITION)
			upRange = adjustedMinRangePosition.applyAsInt(yPosition);
	}

	private IntBinaryOperator maxRangePosition = (position, maxRange) -> position + maxRange * EFFECTIVE_PIXEL_DIMENSION;
	private IntBinaryOperator minRangePosition = (position, minRange) -> position - minRange * EFFECTIVE_PIXEL_DIMENSION;

	private IntBinaryOperator adjustedMaxRangePosition = (position, maxDimension) -> (maxDimension - 2) - position / EFFECTIVE_PIXEL_DIMENSION;
	private IntUnaryOperator adjustedMinRangePosition = position -> position / EFFECTIVE_PIXEL_DIMENSION - 1;

	private IntPredicate isNotAlignedWithRowOrColumn = (position) -> position % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0;
	private BooleanSupplier isNotAlignedWithRow = () -> isNotAlignedWithRowOrColumn.test(yPosition);
	private BooleanSupplier isNotAlignedWithColumn = () -> isNotAlignedWithRowOrColumn.test(xPosition);

	private void addAnimation(int animType, int xOffset, int yOffset) {
		currentAnimations.add(new Animation(animationList.get(animType)));
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

	public void setWasTrigByBomb() {
		wasTrigByBomb = true;
	}

	public static void increaseRange() {
		range++;
	}

	public static void resetRange() {
		range = 1;
	}

	public void setRightRange(int rightRange) {
		wasRightRangeChg = true;
		this.rightRange = rightRange;
	}

	public void setLeftRange(int leftRange) {
		wasLeftRangeChg = true;
		this.leftRange = leftRange;
	}

	public void setDownRange(int downRange) {
		wasDownRangeChg = true;
		this.downRange = downRange;
	}

	public void setUpRange(int upRange) {
		wasUpRangeChg = true;
		this.upRange = upRange;
	}

	public ArrayList<String> toCSVEntry() {

		ArrayList<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(timer));
		entryList.add(Integer.toString(rightRange));
		entryList.add(Integer.toString(leftRange));
		entryList.add(Integer.toString(downRange));
		entryList.add(Integer.toString(upRange));

		return entryList;
	}
}


