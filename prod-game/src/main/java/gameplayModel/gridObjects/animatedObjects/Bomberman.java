package gameplayModel.gridObjects.animatedObjects;

import gameplayController.GameplayController;
import gameplayModel.GridMap;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.PowerUp;
import gameplayView.AnimParam;
import gameplayView.AnimationType;
import gameplayView.ImageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import static gameplayView.AnimationType.*;
import static java.util.Arrays.asList;

@Getter
public class Bomberman extends AnimatedObject {
	public static final List<SimpleEntry<AnimationType, AnimParam>> animParams = asList(
			new SimpleEntry<>(right, new AnimParam(50, 3, 4)),
			new SimpleEntry<>(left, new AnimParam(50, 21, 4)),
			new SimpleEntry<>(down, new AnimParam(2, 3, 3)),
			new SimpleEntry<>(up, new AnimParam(2, 21, 3)),
			new SimpleEntry<>(death, new AnimParam(113, 3, 7)));

	public static final int INITIAL_SPEED = 4;
	public static final int SPEED_INCREMENT = 2;
	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int INVINCIBILITY_TIMEOUT = 10000;

	private List<PowerUp> powerUpsAcquired;
	private int speed, bombsAvailable, bombsLeft;
	@Getter(AccessLevel.NONE)
	private int invincibilityTimer;

	@Accessors(fluent = true)
	private boolean canWallpass, canDetonateBombs, canBombpass, canFlamepass, isInvincible;

	public Bomberman(Position position) {
		super(position, animParams);
		powerUpsAcquired = new ArrayList<>();
		setBombermanAbilities();
		bombsLeft = bombsAvailable;
	}

	public Bomberman(Position position, int invincibilityTimer, int bombsLeft, List<PowerUp> powerUpsAcquired) {
		super(position, animParams);
		this.invincibilityTimer = invincibilityTimer;
		this.bombsLeft = bombsLeft;
		this.powerUpsAcquired = powerUpsAcquired;
		setBombermanAbilities();
	}

	public void setXPosition(int xPos) {
		int yError = (position.getY() - ImageManager.EFFECTIVE_PIXEL_DIMENSION) % (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInXRange = (xPos >= ImageManager.EFFECTIVE_PIXEL_DIMENSION) && (xPos <= ImageManager.EFFECTIVE_PIXEL_DIMENSION * (GridMap.MAPWIDTH - 2));
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			position.setX(xPos);
		} else if (isAboveRow && isInXRange && yError <= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setX(xPos);
			position.incrementY(speed);
		} else if (isAboveRow && isInXRange && yError > (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setX(xPos);
			position.incrementY(2);
		} else if (isBelowRow && isInXRange && yError >= speed) {
			position.setX(xPos);
			position.decrementY(speed);
		} else if (isBelowRow && isInXRange && yError < speed) {
			position.setX(xPos);
			position.decrementY(2);
		}
	}

	public void setYPosition(int yPos) {
		int xError = (position.getX() - ImageManager.EFFECTIVE_PIXEL_DIMENSION) % (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2);

		boolean isInYRange = (yPos >= ImageManager.EFFECTIVE_PIXEL_DIMENSION) && (yPos <= ImageManager.EFFECTIVE_PIXEL_DIMENSION * (GridMap.MAPHEIGHT - 2));
		boolean isAlignedWithColumn = xError == 0;
		boolean isRightFromColumn = xError <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = xError >= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			position.setY(yPos);
		} else if (isRightFromColumn && isInYRange && xError >= speed) {
			position.setY(yPos);
			position.decrementX(speed);
		} else if (isRightFromColumn && isInYRange && xError < speed) {
			position.setY(yPos);
			position.decrementX(2);
		} else if (isLeftFromColumn && isInYRange && xError <= (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setY(yPos);
			position.incrementX(speed);
		} else if (isLeftFromColumn && isInYRange && xError > (ImageManager.EFFECTIVE_PIXEL_DIMENSION * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setY(yPos);
			position.incrementX(2);
		}
	}

	public void addPowerUp(PowerUp powerUp) {
		powerUpsAcquired.add(powerUp);
		setBombermanAbilities();
	}

	void removePowerUp(PowerUp powerUp) {
		powerUpsAcquired.remove(powerUp);
		setBombermanAbilities();
	}

	public void setPowerUpsAcquired(List<PowerUp> powerUpsAcquired) {
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

	void decreaseInvincibilityTimer() {
		if (invincibilityTimer > 0)
			invincibilityTimer -= GameplayController.TIMEOUT;
		else {
			isInvincible = false;
			invincibilityTimer = 0;
			powerUpsAcquired.remove(powerUpsAcquired.size() - 1);
		}
	}

	void increaseBombsAvailable() {
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

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(position.getX()));
		entryList.add(Integer.toString(position.getY()));
		entryList.add(Integer.toString(invincibilityTimer));
		entryList.add(Integer.toString(bombsLeft));
		entryList.add("PowerUpAcquired");

		for (PowerUp powerup : powerUpsAcquired) {
			entryList.add(powerup.getClass().toString());
			entryList.add(Integer.toString(powerup.getPosition().getX()));
			entryList.add(Integer.toString(powerup.getPosition().getY()));
		}
		return entryList;
	}
}
