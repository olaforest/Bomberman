package gameplayTest;

import gameplayModel.Enemy;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.Minvo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinvoTest {
	
	private Minvo minvo;
	
	@Before
	public void setUp() {
		minvo = new Minvo(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}
	
	@Test
	public void testGetPoints() {
		assertEquals(800, minvo.getPoints());
	}
	
	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);
		
		minvo.setDirection(direction);
		
		assertTrue(isDirectionCorrect());
	}
	
	@Test
	public void testGetSpeed() {
		assertEquals(4, minvo.getSpeed());
	}
	
	@Test
	public void testGetSmartness() {
		assertEquals(2, minvo.getSmartness());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(minvo.toCSVEntry().size() == 4);
		assertTrue(minvo.toCSVEntry().get(0).equals("class gameplayModel.Minvo"));
		assertTrue(minvo.toCSVEntry().get(1).equals(Integer.toString(minvo.getXPosition())));
		assertTrue(minvo.toCSVEntry().get(2).equals(Integer.toString(minvo.getYPosition())));
		assertTrue(minvo.toCSVEntry().get(3).equals(Integer.toString(minvo.getDirection())));
	}
	
	@Test
	public void testIsDead() {
		assertFalse(minvo.isDead());
	}
	
	@Test
	public void testIsObsolete() {
		assertFalse(minvo.isObsolete());
	}
	
	@Test
	public void testTriggerDeath() {
		
		minvo.triggerDeath();
		assertTrue(minvo.isDead());
	}
	
	@Test
	public void testGetAndSetCurrentAnimation() {
		
		int animationNumber = (int) Math.random() * Enemy.AnimationType.values().length;
		
		minvo.setCurrentAnimation(animationNumber);
		
		assertTrue(animationNumber == minvo.getAnimationNumber());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(minvo.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is out of bounds on the left.
		minvo.setXPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, minvo.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		minvo.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, minvo.getXPosition());
		
		//Tests if the inputed x position is valid.
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, minvo.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED - 1);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, minvo.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED - 1, minvo.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, minvo.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED + 4, minvo.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED + 1);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, minvo.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED + 1, minvo.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, minvo.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED - 4, minvo.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		minvo.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, minvo.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		minvo.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, minvo.getYPosition());
		
		//Tests if the inputed y position is valid.
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, minvo.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED - 1);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, minvo.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED - 1, minvo.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, minvo.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Minvo.MISALIGNMENT_ALLOWED + 4, minvo.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED + 1);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, minvo.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED + 1, minvo.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		minvo.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED);
		minvo.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, minvo.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Minvo.MISALIGNMENT_ALLOWED - 4, minvo.getXPosition());
	}
	
	private boolean isDirectionCorrect() {
		
		if (minvo.getDirection() >= 0 && minvo.getDirection() <= 3)
			return true;
		else
			return false;
	}
}
