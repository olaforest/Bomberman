package gameplayTest;

import gameplayModel.Enemy;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.Oneal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OnealTest {
	
	private Oneal oneal;
	
	@Before
	public void setUp() {
		oneal = new Oneal(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGetDirection() {
		assertTrue(isDirectionCorrect());
	}
	
	@Test
	public void testGetPoints() {
		assertEquals(200, oneal.getPoints());
	}
	
	@Test
	public void testSetDirection() {
		int direction = (int) (Math.random() * 3);
		
		oneal.setDirection(direction);
		
		assertTrue(isDirectionCorrect());
	}
	
	@Test
	public void testGetSpeed() {
		assertEquals(3, oneal.getSpeed());
	}
	
	@Test
	public void testGetSmartness() {
		assertEquals(2, oneal.getSmartness());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(oneal.toCSVEntry().size() == 4);
		assertTrue(oneal.toCSVEntry().get(0).equals("class gameplayModel.Oneal"));
		assertTrue(oneal.toCSVEntry().get(1).equals(Integer.toString(oneal.getXPosition())));
		assertTrue(oneal.toCSVEntry().get(2).equals(Integer.toString(oneal.getYPosition())));
		assertTrue(oneal.toCSVEntry().get(3).equals(Integer.toString(oneal.getDirection())));
	}
	
	@Test
	public void testIsDead() {
		assertFalse(oneal.isDead());
	}
	
	@Test
	public void testIsObsolete() {
		assertFalse(oneal.isObsolete());
	}
	
	@Test
	public void testTriggerDeath() {
		
		oneal.triggerDeath();
		assertTrue(oneal.isDead());
	}
	
	@Test
	public void testGetAndSetCurrentAnimation() {
		
		int animationNumber = (int) Math.random() * Enemy.AnimationType.values().length;
		
		oneal.setCurrentAnimation(animationNumber);
		
		assertTrue(animationNumber == oneal.getAnimationNumber());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(oneal.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is out of bounds on the left.
		oneal.setXPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, oneal.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		oneal.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, oneal.getXPosition());
		
		//Tests if the inputed x position is valid.
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, oneal.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED - 1);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, oneal.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED - 1, oneal.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, oneal.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED + 4, oneal.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED + 1);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, oneal.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED + 1, oneal.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, oneal.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED - 4, oneal.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		oneal.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, oneal.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		oneal.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, oneal.getYPosition());
		
		//Tests if the inputed y position is valid.
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, oneal.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED - 1);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, oneal.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED - 1, oneal.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, oneal.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Oneal.MISALIGNMENT_ALLOWED + 4, oneal.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED + 1);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, oneal.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED + 1, oneal.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		oneal.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED);
		oneal.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, oneal.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Oneal.MISALIGNMENT_ALLOWED - 4, oneal.getXPosition());
	}
	
	private boolean isDirectionCorrect() {
		
		if (oneal.getDirection() >= 0 && oneal.getDirection() <= 3)
			return true;
		else
			return false;
	}
}
