package gameplayTest;

import gameplayModel.Balloom;
import gameplayModel.Enemy;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BalloomTest {
	
	private Balloom balloom;
	
	@Before
	public void setUp() {
		balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
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
		assertTrue(balloom.toCSVEntry().get(0).equals("class gameplayModel.Balloom"));
		assertTrue(balloom.toCSVEntry().get(1).equals(Integer.toString(balloom.getXPosition())));
		assertTrue(balloom.toCSVEntry().get(2).equals(Integer.toString(balloom.getYPosition())));
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
		
		int animationNumber = (int) Math.random() * Enemy.AnimationType.values().length;
		
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
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, balloom.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		balloom.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, balloom.getXPosition());
		
		//Tests if the inputed x position is valid.
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, balloom.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED - 1);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, balloom.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED - 1, balloom.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, balloom.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED + 4, balloom.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED + 1);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, balloom.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED + 1, balloom.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, balloom.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED - 4, balloom.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		balloom.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, balloom.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		balloom.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, balloom.getYPosition());
		
		//Tests if the inputed y position is valid.
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, balloom.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED - 1);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, balloom.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED - 1, balloom.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, balloom.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Balloom.MISALIGNMENT_ALLOWED + 4, balloom.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED + 1);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, balloom.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED + 1, balloom.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		balloom.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED);
		balloom.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, balloom.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Balloom.MISALIGNMENT_ALLOWED - 4, balloom.getXPosition());
	}
	
	private boolean isDirectionCorrect() {
		
		if (balloom.getDirection() >= 0 && balloom.getDirection() <= 3)
			return true;
		else
			return false;
	}
}
