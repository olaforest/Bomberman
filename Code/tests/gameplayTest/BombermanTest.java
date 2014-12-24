package gameplayTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gameplayController.GameplayController;
import gameplayModel.*;

public class BombermanTest {
	
	private Bomberman bomberman;
	
	@Before
	public void setUp() {
		bomberman = new Bomberman(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is out of bounds on the left.
		bomberman.setXPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomberman.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		bomberman.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomberman.getXPosition());
		
		//Tests if the inputed x position is valid.
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bomberman.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED - 1);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomberman.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED - 1, bomberman.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomberman.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED + 4, bomberman.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED + 1);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomberman.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED + 1, bomberman.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomberman.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED - 4, bomberman.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		bomberman.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomberman.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		bomberman.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomberman.getYPosition());
		
		//Tests if the inputed y position is valid.
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bomberman.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED - 1);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomberman.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED - 1, bomberman.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomberman.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - bomberman.MISALIGNMENT_ALLOWED + 4, bomberman.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED + 1);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomberman.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED + 1, bomberman.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomberman.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomberman.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + bomberman.MISALIGNMENT_ALLOWED - 4, bomberman.getXPosition());
	}
	
	@Test
	public void testAddPowerUp() {
		
		BombPU bombPU = new BombPU(0, 0);
		Flames flames = new Flames(0, 0);
		Speed speed = new Speed(0, 0);
		Wallpass wallpass = new Wallpass(0, 0);
		Detonator detonator = new Detonator(0, 0);
		Bombpass bombpass = new Bombpass(0, 0);
		Flamepass flamepass = new Flamepass(0, 0);
		Mystery mystery = new Mystery(0, 0);
		PowerUp powerUp = new PowerUp(0, 0);
		
		bomberman.getPowerUpsAcquired().clear();
		
		bomberman.addPowerUp(bombPU);
		assertTrue(bomberman.getPowerUpsAcquired().contains(bombPU));
		
		bomberman.addPowerUp(flames);
		assertTrue(bomberman.getPowerUpsAcquired().contains(flames));
		
		bomberman.addPowerUp(speed);
		assertTrue(bomberman.getPowerUpsAcquired().contains(speed));
		
		bomberman.addPowerUp(wallpass);
		assertTrue(bomberman.getPowerUpsAcquired().contains(wallpass));
		
		bomberman.addPowerUp(detonator);
		assertTrue(bomberman.getPowerUpsAcquired().contains(detonator));
		
		bomberman.addPowerUp(bombpass);
		assertTrue(bomberman.getPowerUpsAcquired().contains(bombpass));
		
		bomberman.addPowerUp(flamepass);
		assertTrue(bomberman.getPowerUpsAcquired().contains(flamepass));
		
		bomberman.addPowerUp(mystery);
		assertTrue(bomberman.getPowerUpsAcquired().contains(mystery));
		
		bomberman.addPowerUp(powerUp);
		assertTrue(bomberman.getPowerUpsAcquired().contains(powerUp));
		
		bomberman.getPowerUpsAcquired().clear();
	}

	
	@Test
	public void testRemovePowerUp() {
		
		BombPU bombPU = new BombPU(0, 0);
		Flames flames = new Flames(0, 0);
		Speed speed = new Speed(0, 0);
		Wallpass wallpass = new Wallpass(0, 0);
		Detonator detonator = new Detonator(0, 0);
		Bombpass bombpass = new Bombpass(0, 0);
		Flamepass flamepass = new Flamepass(0, 0);
		Mystery mystery = new Mystery(0, 0);
		PowerUp powerUp = new PowerUp(0, 0);
		
		bomberman.addPowerUp(bombPU);
		bomberman.addPowerUp(flames);
		bomberman.addPowerUp(speed);
		bomberman.addPowerUp(wallpass);
		bomberman.addPowerUp(detonator);
		bomberman.addPowerUp(bombpass);
		bomberman.addPowerUp(flamepass);
		bomberman.addPowerUp(mystery);
		bomberman.addPowerUp(powerUp);
		
		bomberman.removePowerUp(bombPU);
		assertFalse(bomberman.getPowerUpsAcquired().contains(bombPU));
		
		bomberman.removePowerUp(flames);
		assertFalse(bomberman.getPowerUpsAcquired().contains(flames));
		
		bomberman.removePowerUp(speed);
		assertFalse(bomberman.getPowerUpsAcquired().contains(speed));
		
		bomberman.removePowerUp(wallpass);
		assertFalse(bomberman.getPowerUpsAcquired().contains(wallpass));
		
		bomberman.removePowerUp(detonator);
		assertFalse(bomberman.getPowerUpsAcquired().contains(detonator));
		
		bomberman.removePowerUp(bombpass);
		assertFalse(bomberman.getPowerUpsAcquired().contains(bombpass));
		
		bomberman.removePowerUp(flamepass);
		assertFalse(bomberman.getPowerUpsAcquired().contains(flamepass));
		
		bomberman.removePowerUp(mystery);
		assertFalse(bomberman.getPowerUpsAcquired().contains(mystery));
		
		bomberman.removePowerUp(powerUp);
		assertFalse(bomberman.getPowerUpsAcquired().contains(powerUp));
		
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testGetPowerUpsAcquired() {
		assertTrue(bomberman.getPowerUpsAcquired().getClass().toString().equals("class java.util.ArrayList"));
	}
	
	@Test
	public void testDecreaseInvincibilityTimer() {
		
		assertFalse(bomberman.isInvincible());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Mystery(0,0));
		assertTrue(bomberman.isInvincible());
		
		for (int i = 0 ; i <= (bomberman.INVINCIBILITY_TIMEOUT / GameplayController.TIMEOUT - 1) ; i++)
			bomberman.decreaseInvincibilityTimer();
		
		// The invincibilityTimer should be one decrement away from the invincibilbity wearing off, therefore, bomberman should still be invincible.
		assertTrue(bomberman.isInvincible());
		
		bomberman.decreaseInvincibilityTimer();
		
		// The invincibilityTimer should be up, therefore, bomberman should not be invincible and should not have any power ups..
		assertFalse(bomberman.isInvincible());
		assertTrue(bomberman.getPowerUpsAcquired().size() == 0);
		
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testCanWallpass() {
		
		assertFalse(bomberman.canWallpass());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Wallpass(0,0));
		
		assertTrue(bomberman.canWallpass());
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testCanDetonateBombs() {
		
		assertFalse(bomberman.canDetonateBombs());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Detonator(0,0));
		
		assertTrue(bomberman.canDetonateBombs());
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testCanBombpass() {
		
		assertFalse(bomberman.canBombpass());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Bombpass(0,0));
		
		assertTrue(bomberman.canBombpass());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testCanFlamepass() {
		
		assertFalse(bomberman.canFlamepass());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Flamepass(0,0));
		
		assertTrue(bomberman.canFlamepass());
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testIsInvincible() {
		
		assertFalse(bomberman.isInvincible());
		
		bomberman.getPowerUpsAcquired().clear();
		bomberman.addPowerUp(new Mystery(0,0));
		
		assertTrue(bomberman.isInvincible());
		bomberman.getPowerUpsAcquired().clear();
	}
	
	@Test
	public void testGetSpeed() {
		
		bomberman.getPowerUpsAcquired().clear();
		assertEquals(4, bomberman.getSpeed());
		
		bomberman.addPowerUp(new Speed(0,0));
		assertEquals(6, bomberman.getSpeed());
		
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testIncreaseBombsAvailable() {
		
		int previousNumberOfBombsLeft = bomberman.getBombsAvailable();
		bomberman.increaseBombsAvailable();
		
		assertEquals(1, bomberman.getBombsAvailable() - previousNumberOfBombsLeft);
	}
	
	@Test
	public void testGetBombsAvailable() {
		assertTrue(bomberman.getBombsAvailable() > 0);
	}
	
	@Test
	public void testIncreaseBombsLeft() {
		
		bomberman.decreaseBombsLeft();
		int previousNumberOfBombsLeft = bomberman.getBombsLeft();
		bomberman.increaseBombsLeft();
		
		assertEquals(1, bomberman.getBombsLeft() - previousNumberOfBombsLeft);
		
		// Verifies that the count of bombs left is never greater than the number of bombs available
		for (int i = 0 ; i == (bomberman.getBombsAvailable() + 1) ; i++) {
			bomberman.increaseBombsLeft();
			assertTrue(bomberman.getBombsLeft() <= bomberman.getBombsAvailable());
		}
	}
	
	@Test
	public void testDecreaseBombsLeft() {
		
		int previousNumberOfBombsLeft = bomberman.getBombsLeft();
		bomberman.decreaseBombsLeft();
		
		assertEquals(1, previousNumberOfBombsLeft - bomberman.getBombsLeft());
		
		// Verifies that the count of bombs left is never negative
		for (int i = 0 ; i == (bomberman.getBombsAvailable() + 1) ; i++) {
			bomberman.decreaseBombsLeft();
			assertTrue(bomberman.getBombsLeft() >= 0);
		}
	}
	
	@Test
	public void testGetBombsLeft() {
		assertTrue(bomberman.getBombsLeft() <= bomberman.getBombsAvailable());
		assertTrue(bomberman.getBombsLeft() >= 0);
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(bomberman.toCSVEntry().size() == (5 + 3 * bomberman.getPowerUpsAcquired().size()));
		assertTrue(bomberman.toCSVEntry().get(0).equals(Integer.toString(bomberman.getXPosition())));
		assertTrue(bomberman.toCSVEntry().get(1).equals(Integer.toString(bomberman.getYPosition())));
		assertTrue(bomberman.toCSVEntry().get(3).equals(Integer.toString(bomberman.getBombsLeft())));
		assertTrue(bomberman.toCSVEntry().get(4).equals("PowerUpAcquired"));
	}
	
	@Test
	public void testIsDead() {
		assertFalse(bomberman.isDead());
	}
	
	@Test
	public void testIsObsolete() {
		assertFalse(bomberman.isObsolete());
	}
	
	@Test
	public void testTriggerDeath() {
		
		bomberman.triggerDeath();
		assertTrue(bomberman.isDead());
	}
	
	@Test
	public void testGetAndSetCurrentAnimation() {
		
		int animationNumber = (int) Math.random() * Enemy.AnimationType.values().length;
		
		bomberman.setCurrentAnimation(animationNumber);
		
		assertTrue(animationNumber == bomberman.getAnimationNumber());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(bomberman.isConcreteCollision());
	}
}
