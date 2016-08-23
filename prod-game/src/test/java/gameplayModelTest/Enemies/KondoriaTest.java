package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Kondoria;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class KondoriaTest {

	private Kondoria kondoria;

	@Before
	public void setUp() {
		kondoria = new Kondoria(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetPoints() {
		assertEquals(1000, kondoria.getPoints());
	}

	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);

		kondoria.setDirection(direction);

		assertTrue(isDirectionCorrect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(1, kondoria.getSpeed());
	}

	@Test
	public void testGetSmartness() {
		assertEquals(3, kondoria.getSmartness());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(kondoria.toCSVEntry().size() == 4);
		assertTrue(kondoria.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Kondoria"));
		assertTrue(kondoria.toCSVEntry().get(1).equals(Integer.toString(kondoria.getXPosition())));
		assertTrue(kondoria.toCSVEntry().get(2).equals(Integer.toString(kondoria.getYPosition())));
		assertTrue(kondoria.toCSVEntry().get(3).equals(Integer.toString(kondoria.getDirection())));
	}

	@Test
	public void testIsDead() {
		assertFalse(kondoria.isDead());
	}

	@Test
	public void testIsObsolete() {
		assertFalse(kondoria.isObsolete());
	}

	@Test
	public void testTriggerDeath() {

		kondoria.triggerDeath();
		assertTrue(kondoria.isDead());
	}

	@Test
	public void testGetAndSetCurrentAnimation() {

		int animationNumber = (int) (Math.random() * Enemy.AnimationType.values().length);

		kondoria.setCurrentAnimation(animationNumber);

		assertTrue(animationNumber == kondoria.getAnimationNumber());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(kondoria.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is out of bounds on the left.
		kondoria.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, kondoria.getXPosition());

		//Tests if the inputed x position is out of bounds on the right.
		kondoria.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, kondoria.getXPosition());

		//Tests if the inputed x position is valid.
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, kondoria.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED - 1);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, kondoria.getXPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED - 1, kondoria.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, kondoria.getXPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED + 4, kondoria.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED + 1);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, kondoria.getXPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED + 1, kondoria.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, kondoria.getXPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED - 4, kondoria.getYPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		kondoria.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, kondoria.getYPosition());

		//Tests if the inputed y position is out of bounds on the bottom.
		kondoria.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, kondoria.getYPosition());

		//Tests if the inputed y position is valid.
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, kondoria.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED - 1);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, kondoria.getYPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED - 1, kondoria.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, kondoria.getYPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - Kondoria.MISALIGNMENT_ALLOWED + 4, kondoria.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED + 1);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, kondoria.getYPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED + 1, kondoria.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		kondoria.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED);
		kondoria.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, kondoria.getYPosition());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + Kondoria.MISALIGNMENT_ALLOWED - 4, kondoria.getXPosition());
	}

	private boolean isDirectionCorrect() {
		return kondoria.getDirection() >= 0 && kondoria.getDirection() <= 3;
	}
}
