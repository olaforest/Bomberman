package gameplayModel.gridObjects.animatedObjects;

import static gameplayController.GameplayController.TIMEOUT;
import static gameplayView.AnimationType.*;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.PowerUp;
import gameplayView.AnimParam;
import gameplayView.AnimationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import utilities.Position;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@Getter
public class Bomberman extends AnimatedObject {
	private static final List<Entry<AnimationType, AnimParam>> animParams = asList(
			new SimpleEntry<>(Right, new AnimParam(56, 3, 4)),
			new SimpleEntry<>(Left, new AnimParam(56, 21, 4)),
			new SimpleEntry<>(Down, new AnimParam(2, 3, 3)),
			new SimpleEntry<>(Up, new AnimParam(2, 21, 3)),
			new SimpleEntry<>(Death, new AnimParam(128, 3, 6)));

	public static final int INITIAL_SPEED = 4;
	public static final int SPEED_INCREMENT = 2;
	public static final int MISALIGNMENT_ALLOWED = 16;
	public static final int INVINCIBILITY_TIMEOUT = 10000;

	private List<PowerUp> powerUpsAcquired;
	private int speed, bombsAvailable, bombsLeft;
	@Getter(AccessLevel.NONE)
	private int invincibilityTimer;

	@Accessors(fluent = true)
	private boolean canWallPass, canDetonateBombs, canBombPass, canFlamePass, isInvincible;

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

	public void moveRight() {
		setXPosition(position.getX() + speed);
	}

	public void moveLeft() {
		setXPosition(position.getX() - speed);
	}

	public void moveUp() {
		setYPosition(position.getY() - speed);
	}

	public void moveDown() {
		setYPosition(position.getY() + speed);
	}

	public void setXPosition(int xPos) {
		int yError = (position.getY() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2);

		boolean isInXRange = xPos >= EFFECTIVE_PIXEL_DIM && xPos <= EFFECTIVE_PIXEL_DIM * (GridMap.MAP_WIDTH - 2);
		boolean isAlignedWithRow = yError == 0;
		boolean isBelowRow = yError <= MISALIGNMENT_ALLOWED;
		boolean isAboveRow = yError >= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithRow && isInXRange) {
			position.setX(xPos);
		} else if (isAboveRow && isInXRange && yError <= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setX(xPos);
			position.incrementY(speed);
		} else if (isAboveRow && isInXRange && yError > (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED + speed)) {
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
		int xError = (position.getX() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2);

		boolean isInYRange = (yPos >= EFFECTIVE_PIXEL_DIM) && (yPos <= EFFECTIVE_PIXEL_DIM * (GridMap.MAP_HEIGHT - 2));
		boolean isAlignedWithColumn = xError == 0;
		boolean isRightFromColumn = xError <= MISALIGNMENT_ALLOWED;
		boolean isLeftFromColumn = xError >= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED);

		if (isAlignedWithColumn && isInYRange) {
			position.setY(yPos);
		} else if (isRightFromColumn && isInYRange && xError >= speed) {
			position.setY(yPos);
			position.decrementX(speed);
		} else if (isRightFromColumn && isInYRange && xError < speed) {
			position.setY(yPos);
			position.decrementX(2);
		} else if (isLeftFromColumn && isInYRange && xError <= (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setY(yPos);
			position.incrementX(speed);
		} else if (isLeftFromColumn && isInYRange && xError > (EFFECTIVE_PIXEL_DIM * 2 - MISALIGNMENT_ALLOWED + speed)) {
			position.setY(yPos);
			position.incrementX(2);
		}
	}

	public void addPowerUp(PowerUp powerUp) {
		powerUpsAcquired.add(powerUp);
		setBombermanAbilities();
	}

	private void setBombermanAbilities() {
		resetAbilities();
		powerUpsAcquired.forEach(powerUp -> powerUp.performAction(this));
	}

	private void resetAbilities() {
		bombsAvailable = 1;
		Bomb.resetRange();
		speed = INITIAL_SPEED;
		canWallPass = false;
		canDetonateBombs = false;
		canBombPass = false;
		canFlamePass = false;
		isInvincible = false;
	}

	void decreaseInvincibilityTimer() {
		if (invincibilityTimer > 0)
			invincibilityTimer -= TIMEOUT;
		else {
			isInvincible = false;
			invincibilityTimer = 0;
			removeCurrentLevelPowerUp();
		}
	}

	void increaseBombsAvailable() {
		bombsAvailable++;
	}

	public void increaseBombsLeft() {
		if (bombsLeft < bombsAvailable) bombsLeft++;
	}

	public void decreaseBombsLeft() {
		if (bombsLeft > 0) bombsLeft--;
	}

	public void increaseBombAvailable() {
		bombsAvailable++;
		bombsLeft++;
	}

	public void increaseBombRange() {
		Bomb.increaseRange();
	}

	public void increaseSpeed() {
		speed += SPEED_INCREMENT;
	}

	public void activateCanWallPass() {
		canWallPass = true;
	}

	public void activateCanDetonateBomb() {
		canDetonateBombs = true;
	}

	public void activateCanBombPass() {
		canBombPass = true;
	}

	public void activateCanFlamePass() {
		canFlamePass = true;
	}

	public void activateInvincibility() {
		isInvincible = true;
		invincibilityTimer = INVINCIBILITY_TIMEOUT;
	}

	public void removeCurrentLevelPowerUp() {
		powerUpsAcquired.remove(powerUpsAcquired.size() - 1);
	}

	public void removeNonPermanentPowerUps() {
		powerUpsAcquired = powerUpsAcquired.stream()
				.filter(PowerUp::isPermanent)
				.collect(toList());
	}

	public List<String> toCSVEntry() {
		final List<String> entryList = new ArrayList<>();
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
