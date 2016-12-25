package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Minvo;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class MinvoTest {

	private Minvo minvo;

	@Before
	public void setUp() {
		minvo = new Minvo(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(800, minvo.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		minvo.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(4, minvo.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(2, minvo.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(minvo.toCSVEntry().size() == 4);
		assertTrue(minvo.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Minvo"));
		assertTrue(minvo.toCSVEntry().get(1).equals(Integer.toString(minvo.getPosition().getX())));
		assertTrue(minvo.toCSVEntry().get(2).equals(Integer.toString(minvo.getPosition().getY())));
		assertTrue(minvo.toCSVEntry().get(3).equals(Integer.toString(minvo.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(minvo.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(minvo.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		minvo.triggerDeath();
		assertTrue(minvo.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		minvo.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == minvo.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(minvo.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		minvo.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, minvo.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		minvo.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, minvo.getPosition().getX());

		//Tests if the inputed x position is valid.
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, minvo.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED - 1);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, minvo.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED - 1, minvo.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, minvo.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED + 4, minvo.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED + 1);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, minvo.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED + 1, minvo.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, minvo.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED - 4, minvo.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		minvo.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, minvo.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		minvo.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, minvo.getPosition().getY());

		//Tests if the inputed y position is valid.
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, minvo.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED - 1);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, minvo.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED - 1, minvo.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, minvo.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Minvo.MISALIGNMENT_ALLOWED + 4, minvo.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED + 1);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, minvo.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED + 1, minvo.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		minvo.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED);
		minvo.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, minvo.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Minvo.MISALIGNMENT_ALLOWED - 4, minvo.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return minvo.getDirection() >= 0 && minvo.getDirection() <= 3;
	}
}
