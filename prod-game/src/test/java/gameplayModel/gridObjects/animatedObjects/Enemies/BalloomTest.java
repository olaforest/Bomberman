package gameplayModel.gridObjects.animatedObjects.Enemies;

import gameplayModel.GridMap;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;
import utilities.Position;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class BalloomTest {

	private Balloom balloom;

	@Before
	public void setUp() {
		balloom = new Balloom(Position.modulus(1, 1));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(100, balloom.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		balloom.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(2, balloom.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(1, balloom.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(balloom.toCSVEntry().size() == 4);
		assertTrue(balloom.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Balloom"));
		assertTrue(balloom.toCSVEntry().get(1).equals(Integer.toString(balloom.getPosition().getX())));
		assertTrue(balloom.toCSVEntry().get(2).equals(Integer.toString(balloom.getPosition().getY())));
		assertTrue(balloom.toCSVEntry().get(3).equals(Integer.toString(balloom.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(balloom.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(balloom.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		balloom.triggerDeath();
		assertTrue(balloom.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		balloom.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == balloom.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(balloom.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		balloom.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, balloom.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		balloom.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, balloom.getPosition().getX());

		//Tests if the inputed x position is valid.
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, balloom.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED - 1);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, balloom.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED - 1, balloom.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, balloom.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED + 4, balloom.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED + 1);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, balloom.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED + 1, balloom.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, balloom.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED - 4, balloom.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		balloom.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, balloom.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		balloom.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, balloom.getPosition().getY());

		//Tests if the inputed y position is valid.
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, balloom.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED - 1);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, balloom.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED - 1, balloom.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, balloom.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Balloom.MISALIGNMENT_ALLOWED + 4, balloom.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED + 1);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, balloom.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED + 1, balloom.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		balloom.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED);
		balloom.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, balloom.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Balloom.MISALIGNMENT_ALLOWED - 4, balloom.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return balloom.getDirection() >= 0 && balloom.getDirection() <= 3;
	}
}
