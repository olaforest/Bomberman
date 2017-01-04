package gameplayModel.gridObjects.animatedObjects.Enemies;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class OvapiTest {

	private Ovapi ovapi;

	@Before
	public void setUp() {
		ovapi = new Ovapi(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(2000, ovapi.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		ovapi.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(2, ovapi.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(2, ovapi.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(ovapi.toCSVEntry().size() == 4);
		assertTrue(ovapi.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Ovapi"));
		assertTrue(ovapi.toCSVEntry().get(1).equals(Integer.toString(ovapi.getPosition().getX())));
		assertTrue(ovapi.toCSVEntry().get(2).equals(Integer.toString(ovapi.getPosition().getY())));
		assertTrue(ovapi.toCSVEntry().get(3).equals(Integer.toString(ovapi.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(ovapi.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(ovapi.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		ovapi.triggerDeath();
		assertTrue(ovapi.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		ovapi.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == ovapi.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(ovapi.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		ovapi.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, ovapi.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		ovapi.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, ovapi.getPosition().getX());

		//Tests if the inputed x position is valid.
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, ovapi.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, ovapi.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1, ovapi.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, ovapi.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED + 4, ovapi.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, ovapi.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1, ovapi.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, ovapi.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED - 4, ovapi.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		ovapi.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, ovapi.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		ovapi.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, ovapi.getPosition().getY());

		//Tests if the inputed y position is valid.
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, ovapi.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, ovapi.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1, ovapi.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, ovapi.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Ovapi.MISALIGNMENT_ALLOWED + 4, ovapi.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, ovapi.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1, ovapi.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		ovapi.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, ovapi.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Ovapi.MISALIGNMENT_ALLOWED - 4, ovapi.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return ovapi.getDirection() >= 0 && ovapi.getDirection() <= 3;
	}
}
