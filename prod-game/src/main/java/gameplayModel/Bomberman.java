package gameplayModel;

import gameplayController.GameplayController;

import java.util.ArrayList;

/**
 * This class containts the various methods and attribute which allow the game controller to display, manipulate,
 *  animate and keep track of all the aspect related to bomberman.
 * 
 * @author Olivier Laforest
 *
 */
public class Bomberman extends AnimatedObject {

	public static enum AnimationType {right, left, down, up, death};
	
	public final int INITIAL_SPEED = 4, SPEED_INCREMENT = 2, MISALIGNMENT_ALLOWED = 16, INVINCIBILITY_TIMEOUT = 10000;
	
	private int speed, bombsAvailable, bombsLeft, invincibilityTimer;
	
	private boolean canWallpass, canDetonateBombs, canBombpass, canFlamepass, isInvincible;

	private ArrayList<PowerUp> powerUpsAcquired;
	
	/**
	 * Construtor used to generate bomberman for new games.
	 * 
	 * @param x position of bomberman related to the top left corner of the game panel
	 * @param y position of bomberman related to the top left corner of the game panel
	 */
	public Bomberman(int x, int y) {
		
		super(x, y);
		
		powerUpsAcquired = new ArrayList<PowerUp>();
		
		setBombermanAbilities();
		bombsLeft = bombsAvailable;
	}
	
	/**
	 * Construtor used to generate bomberman when a saved game is resumed.
	 * 
	 * @param x position of bomberman related to the top left corner of the game panel
	 * @param y position of bomberman related to the top left corner of the game panel
	 * @param invincibilityTimer time left to bomberman invincibility caused by the Mystery power up
	 * @param bombsLeft number of bomb available left that bomberman can drop on the map
	 * @param powerUpsAcquired list of power ups already acquired by bomberman
	 */
	public Bomberman(int x, int y, int invincibilityTimer, int bombsLeft, ArrayList<PowerUp> powerUpsAcquired) {
		
		super(x, y);
		
		this.invincibilityTimer = invincibilityTimer;
		this.bombsLeft = bombsLeft;
		this.powerUpsAcquired = powerUpsAcquired;
		setBombermanAbilities();
	}
	
	/* (non-Javadoc)
	 * @see gameplayModel.AnimatedObject#generateAnimationList()
	 */
	public void generateAnimationList() {
		
		int[][] animParam = {	{50, 3, 4},
								{50, 21, 4},
								{2, 3, 3},
								{2, 21, 3},
								{113, 3, 7}};
		
		animationList = new Animation[AnimationType.values().length];
		
		for (AnimationType type : AnimationType.values()){
			
			int i = type.ordinal();
			
			animationList[i] = new Animation(animParam[i][2]);
			
			for (int j = 0 ; j < animParam[i][2] ; j++){
				
				animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + GridObject.PIXELWIDTH * j, animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
		}
	}
	
	/** 
	 * This method set the new x position of bomberban while allowing for the misalignment of bomberman's y position with a row.
	 * It also ensures that bomberman's horizontal position never gets out of bounds.
	 * 
	 * @param xPosition new desired x position of bomberman
	 */
	public void setXPosition(int xPosition) {
		
		int yError = (this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2);
		
    	boolean isInXRange = (xPosition >= EFFECTIVE_PIXEL_WIDTH) && (xPosition <= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2));
    	boolean isAlignedWithRow = (yError) == 0;
    	boolean isBelowRow = (yError) <= MISALIGNMENT_ALLOWED;
    	boolean isAboveRow = (yError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
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

    public int getYPosition() {
        return yPosition;
    }

	/** 
	 * This method set the new y position of bomberban while allowing for the misalignment of bomberman's x position with a column.
	 * It also ensures that bomberman's vertical position never gets out of bounds.
	 * 
	 * @param yPosition new desired y position of bomberman
	 */
    public void setYPosition(int yPosition) {
    	
    	int xError = (this.xPosition - EFFECTIVE_PIXEL_WIDTH) % (EFFECTIVE_PIXEL_WIDTH * 2);
    	
    	boolean isInYRange = (yPosition >= EFFECTIVE_PIXEL_HEIGHT) && (yPosition <= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2));
    	boolean isAlignedWithColumn = ((xError) == 0);
    	boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
    	boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
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

	/**
	 * @param powerUp to be added to the list of acquired power ups.
	 */
	public void addPowerUp(PowerUp powerUp) {
		powerUpsAcquired.add(powerUp);
		setBombermanAbilities();
	}

	/**
	 * @param powerUp to be removed from the list of acquired power ups
	 */
	public void removePowerUp(PowerUp powerUp) {
		powerUpsAcquired.remove(powerUp);
		setBombermanAbilities();
	}
	
	/**
	 * @param powerUpsAcquired the list of power ups acquired that bomberman's list of power ups acquired should be set to.
	 */
	public void setPowerUpsAcquired(ArrayList<PowerUp> powerUpsAcquired) {
		this.powerUpsAcquired = powerUpsAcquired;
		setBombermanAbilities();
	}

	/**
	 * @return bomberman's list of acquired power ups. 
	 */
	public ArrayList<PowerUp> getPowerUpsAcquired() {
		return powerUpsAcquired;
	}

	// Sets bomberman's attribute according to the list of power ups that he has acquired.
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
			case "class gameplayModel.BombPU":
				bombsAvailable++;
				bombsLeft++;
				break;
			case "class gameplayModel.Flames":
				Bomb.increaseRange();
				break;
			case "class gameplayModel.Speed":
				speed += SPEED_INCREMENT;
				break;
			case "class gameplayModel.Wallpass":
				canWallpass = true;
				break;
			case "class gameplayModel.Detonator":
				canDetonateBombs = true;
				break;
			case "class gameplayModel.Bombpass":
				canBombpass = true;
				break;
			case "class gameplayModel.Flamepass":
				canFlamepass = true;
				break;
			case "class gameplayModel.Mystery":
				isInvincible = true;
				invincibilityTimer = INVINCIBILITY_TIMEOUT;
				break;
			}
		}
	}
	
	
	/**
	 * If bomberman has picked up a power up of type mystery, than this method decreases the time left for bomberman's invincibility.
	 */
	public void decreaseInvincibilityTimer() {
		
		if (invincibilityTimer > 0)
			invincibilityTimer -= GameplayController.TIMEOUT;
		else {
			isInvincible = false;
			invincibilityTimer = 0;
			powerUpsAcquired.remove(powerUpsAcquired.size() - 1);
		}
	}

	/**
	 * @return true if bomberman has a power up of type Wall pass, false otherwise.
	 */
	public boolean canWallpass() {
		return canWallpass;
	}

	/**
	 * @return true if bomberman has a power up of type Detonator, false otherwise.
	 */
	public boolean canDetonateBombs() {
		return canDetonateBombs;
	}

	/**
	 * @return true if bomberman has a power up of type Bombpass, false otherwise.
	 */
	public boolean canBombpass() {
		return canBombpass;
	}

	/**
	 * @return true if bomberman has a power up of type Flamepass, false otherwise.
	 */
	public boolean canFlamepass() {
		return canFlamepass;
	}

	/**
	 * @return true if bomberman has a power up of type Mystery, false otherwise.
	 */
	public boolean isInvincible() {
		return isInvincible;
	}
	
	/**
	 * @return bomberman's speed.
	 */
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * Increases the number of bomb available to bomberman at any given time.
	 */
	public void increaseBombsAvailable() {
		bombsAvailable++;
	}
	
	/**
	 * @return the number of bomb available to bomberman at any given time.
	 */
	public int getBombsAvailable() {
		return bombsAvailable;
	}
	
	/**
	 * Increases the number of bomb available left that bomberman has not already dropped. This happens when a bomb has 
	 * exploded and become obsolete and therefore is returned to bomberman to be dropped again.
	 */
	public void increaseBombsLeft() {
		if (bombsLeft < bombsAvailable)
			bombsLeft++;
	}
	
	/**
	 * Decreases the number of bomb available left that bomberman has not already dropped. This happens when a bomb is dropped by bomberman.
	 */
	public void decreaseBombsLeft() {
		if (bombsLeft > 0)
			bombsLeft--;
	}
	
	/**
	 * @return number of bomb available left that bomberman can drop
	 */
	public int getBombsLeft() {
		return bombsLeft;
	}
	
	/**
	 * Convert the relevant attribute of bomberman into an arraylist of string in order to be stored in the database.
	 * 
	 * @return list of relevant attribute converted to string 
	 */
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
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


