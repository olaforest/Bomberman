package gameplayModel.gridObjects.animatedObjects.Enemiess;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class PassTest {

	private Pass pass;

	@Before
	public void setUp() {
		pass = new Pass(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(4000, pass.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		pass.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(4, pass.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(3, pass.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(pass.toCSVEntry().size() == 4);
		assertTrue(pass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Pass"));
		assertTrue(pass.toCSVEntry().get(1).equals(Integer.toString(pass.getPosition().getX())));
		assertTrue(pass.toCSVEntry().get(2).equals(Integer.toString(pass.getPosition().getY())));
		assertTrue(pass.toCSVEntry().get(3).equals(Integer.toString(pass.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(pass.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(pass.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		pass.triggerDeath();
		assertTrue(pass.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		pass.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == pass.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(pass.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		pass.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pass.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		pass.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pass.getPosition().getX());

		//Tests if the inputed x position is valid.
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, pass.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED - 1);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pass.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED - 1, pass.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pass.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED + 4, pass.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED + 1);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pass.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED + 1, pass.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pass.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED - 4, pass.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		pass.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pass.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		pass.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pass.getPosition().getY());

		//Tests if the inputed y position is valid.
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, pass.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED - 1);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pass.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED - 1, pass.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pass.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pass.MISALIGNMENT_ALLOWED + 4, pass.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED + 1);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pass.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED + 1, pass.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED);
		pass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pass.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pass.MISALIGNMENT_ALLOWED - 4, pass.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return pass.getDirection() >= 0 && pass.getDirection() <= 3;
	}
}
