package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class OnealTest {

	private Oneal oneal;

	@Before
	public void setUp() {
		oneal = new Oneal(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(200, oneal.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		oneal.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(3, oneal.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(2, oneal.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(oneal.toCSVEntry().size() == 4);
		assertTrue(oneal.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Oneal"));
		assertTrue(oneal.toCSVEntry().get(1).equals(Integer.toString(oneal.getPosition().getX())));
		assertTrue(oneal.toCSVEntry().get(2).equals(Integer.toString(oneal.getPosition().getY())));
		assertTrue(oneal.toCSVEntry().get(3).equals(Integer.toString(oneal.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(oneal.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(oneal.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		oneal.triggerDeath();
		assertTrue(oneal.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		oneal.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == oneal.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(oneal.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		oneal.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, oneal.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		oneal.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, oneal.getPosition().getX());

		//Tests if the inputed x position is valid.
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, oneal.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED - 1);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, oneal.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED - 1, oneal.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, oneal.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED + 4, oneal.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED + 1);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, oneal.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED + 1, oneal.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, oneal.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED - 4, oneal.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		oneal.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, oneal.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		oneal.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, oneal.getPosition().getY());

		//Tests if the inputed y position is valid.
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, oneal.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED - 1);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, oneal.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED - 1, oneal.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, oneal.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Oneal.MISALIGNMENT_ALLOWED + 4, oneal.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED + 1);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, oneal.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED + 1, oneal.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		oneal.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED);
		oneal.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, oneal.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Oneal.MISALIGNMENT_ALLOWED - 4, oneal.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return oneal.getDirection() >= 0 && oneal.getDirection() <= 3;
	}
}
