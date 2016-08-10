package gameplayModel.GridObjects.AnimatedObjects;

import gameplayController.GameplayController;
import gameplayModel.Animation;
import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
public class Bomb extends AnimatedObject {
	public enum AnimationType {unexploded, expCenter, expRight, expLeft, expDown, expUp, expVertical, expHorizontal;}

	public static final int TIME_TO_EXPLOSION = 2500;
	public static final int[][] ANIM_PARAM = new int[][]{{113, 21, 4, 4, PIXEL_DIMENSION},
			{19, 223, 7, 4, 54},
			{37, 223, 7, 4, 54},
			{1, 223, 7, 4, 54},
			{19, 241, 7, 4, 54},
			{19, 205, 7, 4, 54},
			{37, 205, 7, 4, 54},
			{37, 241, 7, 4, 54}};

	private ArrayList<Animation> currentAnimations;
	private ArrayList<Integer> animXOffset, animYOffset;

	@Getter
	private static int range = 1;
	@Setter
	private int timer;
	private int rightRange, leftRange, downRange, upRange;
	@Getter(AccessLevel.NONE)
	private int counter, animCycleParam;

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
		counter = 0;
		animCycleParam = 3;

		rightRange = leftRange = downRange = upRange = range;
		setRanges();

		wasTrigByBomb = wasRightRangeChg = wasLeftRangeChg = wasDownRangeChg = wasUpRangeChg = false;

		addAnimation(Bomb.AnimationType.unexploded.ordinal(), 0, 0);
	}

	public Bomb(int range, int x, int y, int timer, int right, int left, int down, int up) {
		super(x, y);

		currentAnimations = new ArrayList<>();
		animXOffset = new ArrayList<>();
		animYOffset = new ArrayList<>();

		this.timer = timer;
		counter = 0;
		animCycleParam = 3;
		Bomb.range = range;
		rightRange = right;
		leftRange = left;
		downRange = down;
		upRange = up;

		addAnimation(Bomb.AnimationType.unexploded.ordinal(), 0, 0);
	}

	@Override
	public void generateAnimationList() {
		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()) {
			int i = type.ordinal();
			animationList[i] = new Animation(ANIM_PARAM[i][2]);

			for (int j = 0; j < ANIM_PARAM[i][3]; j++)
				animationList[i].setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * j, ANIM_PARAM[i][1]), j);

			for (int n = (ANIM_PARAM[i][2] - ANIM_PARAM[i][3]); n > 0; n--)
				animationList[i].setFrame(resizeImage(ANIM_PARAM[i][0] + ANIM_PARAM[i][4] * n, ANIM_PARAM[i][1]), ANIM_PARAM[i][3] - n);
		}
	}

	public void cycleAnimation() {

		if (counter % animCycleParam == 0) {
			if (!isDead) {
				for (Animation animation : currentAnimations)
					animation.cycleFrame();

			} else {
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
		}
		counter++;
	}

	public void decreaseTimer() {
		timer -= GameplayController.TIMEOUT;

		if (timer <= 0 && !isDead)
			triggerExplosion();
	}

	public void triggerExplosion() {

		isDead = true;
		timer = 0;
		animCycleParam = 2;
		clearAnimation();
		addAnimation(Bomb.AnimationType.expCenter.ordinal(), 0, 0);

		if (leftRange > 0) {
			if (wasLeftRangeChg) {
				for (int i = 1; i <= leftRange; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), -i, 0);
			} else {
				addAnimation(Bomb.AnimationType.expLeft.ordinal(), -leftRange, 0);

				for (int i = 1; i < leftRange; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), -i, 0);
			}
		}

		if (rightRange > 0) {
			if (wasRightRangeChg) {
				for (int i = 1; i <= rightRange; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), i, 0);
			} else {
				addAnimation(Bomb.AnimationType.expRight.ordinal(), rightRange, 0);

				for (int i = 1; i < rightRange; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), i, 0);
			}
		}

		if (upRange > 0) {
			if (wasUpRangeChg) {
				for (int i = 1; i <= upRange; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, -i);
			} else {
				addAnimation(Bomb.AnimationType.expUp.ordinal(), 0, -upRange);

				for (int i = 1; i < upRange; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, -i);
			}
		}

		if (downRange > 0) {
			if (wasDownRangeChg) {
				for (int i = 1; i <= downRange; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, i);
			} else {
				addAnimation(Bomb.AnimationType.expDown.ordinal(), 0, downRange);

				for (int i = 1; i < downRange; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, i);
			}
		}
	}

	private void setRanges() {

		boolean isNotAlignedWithRow = (yPosition % (EFFECTIVE_PIXEL_HEIGHT * 2)) == 0;

		if (isNotAlignedWithRow) {
			rightRange = 0;
			leftRange = 0;
		} else {
			if ((xPosition + rightRange * EFFECTIVE_PIXEL_WIDTH) >= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2))
				rightRange = (GridMap.MAPWIDTH - 2) - xPosition / EFFECTIVE_PIXEL_WIDTH;
			if ((xPosition - leftRange * EFFECTIVE_PIXEL_WIDTH) <= EFFECTIVE_PIXEL_WIDTH)
				leftRange = xPosition / EFFECTIVE_PIXEL_WIDTH - 1;
		}

		boolean isNotAlignedWithColumn = (xPosition % (EFFECTIVE_PIXEL_WIDTH * 2)) == 0;

		if (isNotAlignedWithColumn) {
			downRange = 0;
			upRange = 0;
		} else {
			if ((yPosition + downRange * EFFECTIVE_PIXEL_HEIGHT) >= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2))
				downRange = (GridMap.MAPHEIGHT - 2) - yPosition / EFFECTIVE_PIXEL_HEIGHT;
			if ((yPosition - upRange * EFFECTIVE_PIXEL_HEIGHT) <= EFFECTIVE_PIXEL_HEIGHT)
				upRange = yPosition / EFFECTIVE_PIXEL_HEIGHT - 1;
		}
	}

	private void addAnimation(int animType, int xOffset, int yOffset) {
		currentAnimations.add(new Animation(animationList[animType]));
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


