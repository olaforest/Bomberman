package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DollTest {

	private Doll doll;

	@Before
	public void setUp() {
		doll = new Doll(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
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
		assertTrue(doll.toCSVEntry().get(1).equals(Integer.toString(doll.getXPosition())));
		assertTrue(doll.toCSVEntry().get(2).equals(Integer.toString(doll.getYPosition())));
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
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, doll.getXPosition());

		//Tests if the inputed x position is out of bounds on the right.
		doll.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, doll.getXPosition());

		//Tests if the inputed x position is valid.
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, doll.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED - 1);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, doll.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED - 1, doll.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, doll.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED + 4, doll.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED + 1);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, doll.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED + 1, doll.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, doll.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED - 4, doll.getYPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		doll.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, doll.getYPosition());

		//Tests if the inputed y position is out of bounds on the bottom.
		doll.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, doll.getYPosition());

		//Tests if the inputed y position is valid.
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, doll.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED - 1);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, doll.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED - 1, doll.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, doll.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Doll.MISALIGNMENT_ALLOWED + 4, doll.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED + 1);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, doll.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED + 1, doll.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		doll.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED);
		doll.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, doll.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Doll.MISALIGNMENT_ALLOWED - 4, doll.getXPosition());
	}

	private boolean isDirectionCorrect() {
		return doll.getDirection() >= 0 && doll.getDirection() <= 3;
	}
}
