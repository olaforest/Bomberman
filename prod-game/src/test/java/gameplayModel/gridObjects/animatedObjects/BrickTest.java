package gameplayModel.gridObjects.animatedObjects;

import gameplayModel.GridMap;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class BrickTest {

	private Brick brick;

	@Before
	public void setUp() {
		brick = new Brick(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(brick.toCSVEntry().size() == 2);
		assertTrue(brick.toCSVEntry().get(0).equals(Integer.toString(brick.getPosition().getX())));
		assertTrue(brick.toCSVEntry().get(1).equals(Integer.toString(brick.getPosition().getY())));
	}

	@Test
	public void testIsDead() {
		assertFalse(brick.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(brick.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		brick.triggerDeath();
		assertTrue(brick.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		brick.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == brick.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(brick.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		brick.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, brick.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		brick.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, brick.getPosition().getX());

		//Tests if the inputed x position is valid.
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, brick.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED - 1);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, brick.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED - 1, brick.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, brick.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED + 4, brick.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED + 1);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, brick.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED + 1, brick.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, brick.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED - 4, brick.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		brick.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, brick.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		brick.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, brick.getPosition().getY());

		//Tests if the inputed y position is valid.
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, brick.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED - 1);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, brick.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED - 1, brick.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, brick.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Brick.MISALIGNMENT_ALLOWED + 4, brick.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED + 1);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, brick.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED + 1, brick.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		brick.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED);
		brick.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, brick.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Brick.MISALIGNMENT_ALLOWED - 4, brick.getPosition().getX());
	}
}