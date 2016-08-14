package gameplayModel.GridObjects.AnimatedObjects;

import gameplayController.GameplayController;
import gameplayModel.Animation;
import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObject;
import gameplayModel.GridObjects.PowerUps.PowerUp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

import static java.util.Arrays.asList;

@Getter
public class Bomberman extends AnimatedObject {
	public enum AnimationType {right, left, down, up, death}

	public static final int[][] ANIM_PARAM = new int[][]{{50, 3, 4}, {50, 21, 4}, {2, 3, 3}, {2, 21, 3}, {113, 3, 7}};
	public static final int INITIAL_SPEED = 4, SPEED_INCREMENT = 2, MISALIGNMENT_ALLOWED = 16, INVINCIBILITY_TIMEOUT = 10000;

	private ArrayList<PowerUp> powerUpsAcquired;
	private int speed, bombsAvailable, bombsLeft;
	@Getter(AccessLevel.NONE)
	private int invincibilityTimer;

	@Accessors(fluent = true)
	private boolean canWallpass, canDetonateBombs, canBombpass, canFlamepass, isInvincible;

	public Bomberman(int x, int y) {
		super(x, y);
		powerUpsAcquired = new ArrayList<>();
		setBombermanAbilities();
		bombsLeft = bombsAvailable;
	}

	public Bomberman(int x, int y, int invincibilityTimer, int bombsLeft, ArrayList<PowerUp> powerUpsAcquired) {
		super(x, y);
		this.invincibilityTimer = invincibilityTimer;
		this.bombsLeft = bombsLeft;
		this.powerUpsAcquired = powerUpsAcquired;
		setBombermanAbilities();
	}

	public void generateAnimationList() {
		animationList = generateAnimationList(asList(AnimationType.values()), ANIM_PARAM, 0);
	}

	public void setXPosition(int xPosition) {

		int yError = (this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2);

		boolean isInXRange = (xPosition >= EFFECTIVE_PIXEL_WIDTH) && (xPosition <= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			this.xPosition = xPosition;
		} else if (isAboveRow && isInXRange && yError <= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
			this.xPosition = xPosition;
			this.yPosition += speed;
		} else if (isAboveRow && isInXRange && yError > (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
			this.xPosition = xPosition;
			this.yPosition += 2;
		} else if (isBelowRow && isInXRange && yError >= speed) {
			this.xPosition = xPosition;
			this.yPosition -= speed;
		} else if (isBelowRow && isInXRange && yError < speed) {
			this.xPosition = xPosition;
			this.yPosition -= 2;
		}
	}

	public void setYPosition(int yPosition) {

		int xError = (this.xPosition - EFFECTIVE_PIXEL_WIDTH) % (EFFECTIVE_PIXEL_WIDTH * 2);

		boolean isInYRange = (yPosition >= EFFECTIVE_PIXEL_HEIGHT) && (yPosition <= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2));
		boolean isAlignedWithColumn = xError == 0;
		boolean isRightFromColumn = xError <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = xError >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			this.yPosition = yPosition;
		} else if (isRightFromColumn && isInYRange && xError >= speed) {
			this.yPosition = yPosition;
			this.xPosition -= speed;
		} else if (isRightFromColumn && isInYRange && xError < speed) {
			this.yPosition = yPosition;
			this.xPosition -= 2;
		} else if (isLeftFromColumn && isInYRange && xError <= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
			this.yPosition = yPosition;
			this.xPosition += speed;
		} else if (isLeftFromColumn && isInYRange && xError > (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
			this.yPosition = yPosition;
			this.xPosition += 2;
		}
	}

	public void addPowerUp(PowerUp powerUp) {
		powerUpsAcquired.add(powerUp);
		setBombermanAbilities();
	}

	public void removePowerUp(PowerUp powerUp) {
		powerUpsAcquired.remove(powerUp);
		setBombermanAbilities();
	}

	public void setPowerUpsAcquired(ArrayList<PowerUp> powerUpsAcquired) {
		this.powerUpsAcquired = powerUpsAcquired;
		setBombermanAbilities();
	}

	private void setBombermanAbilities() {

		bombsAvailable = 1;
		Bomb.resetRange();
		speed = INITIAL_SPEED;
		canWallpass = false;
		canDetonateBombs = false;
		canBombpass = false;
		canFlamepass = false;
		isInvincible = false;

		for (PowerUp powerup : powerUpsAcquired) {

			switch (powerup.getClass().toString()) {
				case "class gameplayModel.GridObjects.PowerUps.BombPU":
					bombsAvailable++;
					bombsLeft++;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Flames":
					Bomb.increaseRange();
					break;
				case "class gameplayModel.GridObjects.PowerUps.Speed":
					speed += SPEED_INCREMENT;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Wallpass":
					canWallpass = true;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Detonator":
					canDetonateBombs = true;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Bombpass":
					canBombpass = true;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Flamepass":
					canFlamepass = true;
					break;
				case "class gameplayModel.GridObjects.PowerUps.Mystery":
					isInvincible = true;
					invincibilityTimer = INVINCIBILITY_TIMEOUT;
					break;
			}
		}
	}

	public void decreaseInvincibilityTimer() {
		if (invincibilityTimer > 0)
			invincibilityTimer -= GameplayController.TIMEOUT;
		else {
			isInvincible = false;
			invincibilityTimer = 0;
			powerUpsAcquired.remove(powerUpsAcquired.size() - 1);
		}
	}

	public void increaseBombsAvailable() {
		bombsAvailable++;
	}

	public void increaseBombsLeft() {
		if (bombsLeft < bombsAvailable)
			bombsLeft++;
	}

	public void decreaseBombsLeft() {
		if (bombsLeft > 0)
			bombsLeft--;
	}

	public ArrayList<String> toCSVEntry() {

		ArrayList<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(invincibilityTimer));
		entryList.add(Integer.toString(bombsLeft));
		entryList.add("PowerUpAcquired");

		for (PowerUp powerup : powerUpsAcquired) {
			entryList.add(powerup.getClass().toString());
			entryList.add(Integer.toString(powerup.getXPosition()));
			entryList.add(Integer.toString(powerup.getYPosition()));
		}
		return entryList;
	}
}
