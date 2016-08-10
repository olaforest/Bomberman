package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Ovapi;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OvapiTest {

	private Ovapi ovapi;

	@Before
	public void setUp() {
		ovapi = new Ovapi(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
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
		assertTrue(ovapi.toCSVEntry().get(1).equals(Integer.toString(ovapi.getXPosition())));
		assertTrue(ovapi.toCSVEntry().get(2).equals(Integer.toString(ovapi.getYPosition())));
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
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, ovapi.getXPosition());

		//Tests if the inputed x position is out of bounds on the right.
		ovapi.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, ovapi.getXPosition());

		//Tests if the inputed x position is valid.
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, ovapi.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, ovapi.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1, ovapi.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, ovapi.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED + 4, ovapi.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, ovapi.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1, ovapi.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, ovapi.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED - 4, ovapi.getYPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		ovapi.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, ovapi.getYPosition());

		//Tests if the inputed y position is out of bounds on the bottom.
		ovapi.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, ovapi.getYPosition());

		//Tests if the inputed y position is valid.
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, ovapi.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, ovapi.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED - 1, ovapi.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, ovapi.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Ovapi.MISALIGNMENT_ALLOWED + 4, ovapi.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, ovapi.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED + 1, ovapi.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		ovapi.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED);
		ovapi.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, ovapi.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Ovapi.MISALIGNMENT_ALLOWED - 4, ovapi.getXPosition());
	}

	private boolean isDirectionCorrect() {
		return ovapi.getDirection() >= 0 && ovapi.getDirection() <= 3;
	}
}
