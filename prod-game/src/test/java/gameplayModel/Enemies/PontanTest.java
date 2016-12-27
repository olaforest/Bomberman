package gameplayModel.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Pontan;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class PontanTest {

	private Pontan pontan;

	@Before
	public void setUp() {
		pontan = new Pontan(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(8000, pontan.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		pontan.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(4, pontan.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(3, pontan.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(pontan.toCSVEntry().size() == 4);
		assertTrue(pontan.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Pontan"));
		assertTrue(pontan.toCSVEntry().get(1).equals(Integer.toString(pontan.getPosition().getX())));
		assertTrue(pontan.toCSVEntry().get(2).equals(Integer.toString(pontan.getPosition().getY())));
		assertTrue(pontan.toCSVEntry().get(3).equals(Integer.toString(pontan.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(pontan.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(pontan.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		pontan.triggerDeath();
		assertTrue(pontan.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		pontan.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == pontan.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(pontan.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		pontan.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pontan.getPosition().getX());

		//Tests if the inputed x position is out of bounds on the right.
		pontan.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pontan.getPosition().getX());

		//Tests if the inputed x position is valid.
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, pontan.getPosition().getX());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED - 1);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pontan.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED - 1, pontan.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pontan.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED + 4, pontan.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED + 1);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pontan.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED + 1, pontan.getPosition().getY());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pontan.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED - 4, pontan.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		pontan.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pontan.getPosition().getY());

		//Tests if the inputed y position is out of bounds on the bottom.
		pontan.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, pontan.getPosition().getY());

		//Tests if the inputed y position is valid.
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, pontan.getPosition().getY());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED - 1);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pontan.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED - 1, pontan.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pontan.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Pontan.MISALIGNMENT_ALLOWED + 4, pontan.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED + 1);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, pontan.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED + 1, pontan.getPosition().getX());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		pontan.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED);
		pontan.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, pontan.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Pontan.MISALIGNMENT_ALLOWED - 4, pontan.getPosition().getX());
	}

	private boolean isDirectionCorrect() {
		return pontan.getDirection() >= 0 && pontan.getDirection() <= 3;
	}
}
