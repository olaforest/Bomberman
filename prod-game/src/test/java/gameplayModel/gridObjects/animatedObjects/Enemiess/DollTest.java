package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class DollTest {

	private Doll doll;

	@Before
	public void setUp() {
		doll = new Doll(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(400, doll.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		doll.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(3, doll.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(1, doll.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(doll.toCSVEntry().size() == 4);
		assertTrue(doll.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll"));
		assertTrue(doll.toCSVEntry().get(1).equals(Integer.toString(doll.getPosition().getX())));
		assertTrue(doll.toCSVEntry().get(2).equals(Integer.toString(doll.getPosition().getY())));
		assertTrue(doll.toCSVEntry().get(3).equals(Integer.toString(doll.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(doll.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(doll.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		doll.triggerDeath();
		assertTrue(doll.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		doll.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == doll.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(doll.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		doll.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, doll.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		doll.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, doll.getPosition().getX());

		//Tests if the inputed x position is valid.
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, doll.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED - 1);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, doll.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED - 1, doll.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, doll.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED + 4, doll.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED + 1);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, doll.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED + 1, doll.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, doll.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED - 4, doll.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		doll.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, doll.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		doll.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, doll.getPosition().getY());

		//Tests if the inputed y position is valid.
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, doll.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED - 1);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, doll.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED - 1, doll.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, doll.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Doll.MISALIGNMENT_ALLOWED + 4, doll.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED + 1);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, doll.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED + 1, doll.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		doll.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED);
		doll.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, doll.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Doll.MISALIGNMENT_ALLOWED - 4, doll.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return doll.getDirection() >= 0 && doll.getDirection() <= 3;
	}
}
