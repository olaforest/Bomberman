package gameplayModel.gridObjects.animatedObjects;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static org.junit.Assert.*;
import static utilities.Position.create;

import gameplayController.GameplayController;
import gameplayModel.GridMap;
import org.junit.Before;
import org.junit.Test;

public class BombermanTest {

	private Bomberman bomberman;

	@Before
	public void setUp() {
		bomberman = new Bomberman(create(EFFECTIVE_PIXEL_DIM, EFFECTIVE_PIXEL_DIM));
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputted x position is out of bounds on the Left.
		bomberman.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIM, bomberman.getPosition().getX());

		//Tests if the inputted x position is out of bounds on the Right.
		bomberman.setXPosition(GridMap.MAP_WIDTH * EFFECTIVE_PIXEL_DIM);
		assertEquals(EFFECTIVE_PIXEL_DIM, bomberman.getPosition().getX());

		//Tests if the inputted x position is valid.
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 2);
		assertEquals(EFFECTIVE_PIXEL_DIM * 2, bomberman.getPosition().getX());

		//Tests if the inputted x position is valid while the y position is above a row, outside of the misalignment tolerance.
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED - 1);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3, bomberman.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED - 1, bomberman.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is above a row, inside of the misalignment tolerance.
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + 4, bomberman.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED + 4, bomberman.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is below a row, outside of the misalignment tolerance.
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED + 1);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3, bomberman.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED + 1, bomberman.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is below a row, inside of the misalignment tolerance.
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + 4, bomberman.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED - 4, bomberman.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputted y position is out of bounds on the top.
		bomberman.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIM, bomberman.getPosition().getY());

		//Tests if the inputted y position is out of bounds on the bottom.
		bomberman.setYPosition(GridMap.MAP_WIDTH * EFFECTIVE_PIXEL_DIM);
		assertEquals(EFFECTIVE_PIXEL_DIM, bomberman.getPosition().getY());

		//Tests if the inputted y position is valid.
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 2);
		assertEquals(EFFECTIVE_PIXEL_DIM * 2, bomberman.getPosition().getY());

		//Tests if the inputted y position is valid while the x position is to the Left of a column, outside of the misalignment tolerance.
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED - 1);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3, bomberman.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED - 1, bomberman.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the Left of a column, inside of the misalignment tolerance.
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + 4, bomberman.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 - Bomberman.MISALIGNMENT_ALLOWED + 4, bomberman.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the Right of a column, outside of the misalignment tolerance.
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED + 1);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3, bomberman.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED + 1, bomberman.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the Right of a column, inside of the misalignment tolerance.
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3);
		bomberman.setXPosition(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED);
		bomberman.setYPosition(EFFECTIVE_PIXEL_DIM * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + 4, bomberman.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIM * 3 + Bomberman.MISALIGNMENT_ALLOWED - 4, bomberman.getPosition().getX());
	}

	@Test
	public void testGetPowerUpsAcquired() {
		assertEquals("class java.util.ArrayList", bomberman.getPowerUpsAcquired().getClass().toString());
	}

	@Test
	public void testDecreaseInvincibilityTimer() {

		assertFalse(bomberman.isInvincible());

		bomberman.getPowerUpsAcquired().clear();
		assertTrue(bomberman.isInvincible());

		for (int i = 0; i <= (Bomberman.INVINCIBILITY_TIMEOUT / GameplayController.TIMEOUT - 1); i++)
			bomberman.decreaseInvincibilityTimer();

		// The invincibilityTimer should be one decrement away from the invincibility wearing off, therefore, bomberman should still be invincible.
		assertTrue(bomberman.isInvincible());

		bomberman.decreaseInvincibilityTimer();

		// The invincibilityTimer should be Up, therefore, bomberman should not be invincible and should not have any power ups..
		assertFalse(bomberman.isInvincible());
		assertEquals(0, bomberman.getPowerUpsAcquired().size());

		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testCanWallpass() {

		assertFalse(bomberman.canWallPass());

		bomberman.getPowerUpsAcquired().clear();

		assertTrue(bomberman.canWallPass());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testCanDetonateBombs() {

		assertFalse(bomberman.canDetonateBombs());

		bomberman.getPowerUpsAcquired().clear();

		assertTrue(bomberman.canDetonateBombs());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testCanBombPass() {

		assertFalse(bomberman.canBombPass());

		bomberman.getPowerUpsAcquired().clear();

		assertTrue(bomberman.canBombPass());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testCanFlamePass() {

		assertFalse(bomberman.canFlamePass());

		bomberman.getPowerUpsAcquired().clear();

		assertTrue(bomberman.canFlamePass());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testIsInvincible() {

		assertFalse(bomberman.isInvincible());

		bomberman.getPowerUpsAcquired().clear();

		assertTrue(bomberman.isInvincible());
		bomberman.getPowerUpsAcquired().clear();
	}

	@Test
	public void testGetSpeed() {

		bomberman.getPowerUpsAcquired().clear();
		assertEquals(4, bomberman.getSpeed());

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

		// Verifies that the count of bombs Left is never greater than the number of bombs available
		for (int i = 0; i == (bomberman.getBombsAvailable() + 1); i++) {
			bomberman.increaseBombsLeft();
			assertTrue(bomberman.getBombsLeft() <= bomberman.getBombsAvailable());
		}
	}

	@Test
	public void testDecreaseBombsLeft() {

		int previousNumberOfBombsLeft = bomberman.getBombsLeft();
		bomberman.decreaseBombsLeft();

		assertEquals(1, previousNumberOfBombsLeft - bomberman.getBombsLeft());

		// Verifies that the count of bombs Left is never negative
		for (int i = 0; i == (bomberman.getBombsAvailable() + 1); i++) {
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

		assertEquals(bomberman.toCSVEntry().size(), (5 + 3 * bomberman.getPowerUpsAcquired().size()));
		assertEquals(bomberman.toCSVEntry().get(0), Integer.toString(bomberman.getPosition().getX()));
		assertEquals(bomberman.toCSVEntry().get(1), Integer.toString(bomberman.getPosition().getY()));
		assertEquals(bomberman.toCSVEntry().get(3), Integer.toString(bomberman.getBombsLeft()));
		assertEquals("PowerUpAcquired", bomberman.toCSVEntry().get(4));
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
	public void testIsConcreteCollision() {
		assertFalse(bomberman.isConcreteCollision());
	}
}
