package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Pontan;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PontanTest {

	private Pontan pontan;

	@Before
	public void setUp() {
		pontan = new Pontan(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
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
		assertTrue(pontan.toCSVEntry().get(1).equals(Integer.toString(pontan.getXPosition())));
		assertTrue(pontan.toCSVEntry().get(2).equals(Integer.toString(pontan.getYPosition())));
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
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pontan.getXPosition());

		//Tests if the inputed x position is out of bounds on the right.
		pontan.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pontan.getXPosition());

		//Tests if the inputed x position is valid.
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, pontan.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED - 1);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pontan.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED - 1, pontan.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pontan.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED + 4, pontan.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED + 1);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pontan.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED + 1, pontan.getYPosition());

		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pontan.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED - 4, pontan.getYPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is out of bounds on the top.
		pontan.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pontan.getYPosition());

		//Tests if the inputed y position is out of bounds on the bottom.
		pontan.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pontan.getYPosition());

		//Tests if the inputed y position is valid.
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, pontan.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED - 1);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pontan.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED - 1, pontan.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pontan.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pontan.MISALIGNMENT_ALLOWED + 4, pontan.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED + 1);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pontan.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED + 1, pontan.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pontan.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED);
		pontan.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pontan.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pontan.MISALIGNMENT_ALLOWED - 4, pontan.getXPosition());
	}

	private boolean isDirectionCorrect() {
		return pontan.getDirection() >= 0 && pontan.getDirection() <= 3;
	}
}
