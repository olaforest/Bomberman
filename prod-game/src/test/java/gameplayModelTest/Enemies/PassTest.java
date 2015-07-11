package gameplayModelTest.Enemies;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Pass;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassTest {
	
	private Pass pass;
	
	@Before
	public void setUp() {
		pass = new Pass(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
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
		assertTrue(pass.toCSVEntry().get(1).equals(Integer.toString(pass.getXPosition())));
		assertTrue(pass.toCSVEntry().get(2).equals(Integer.toString(pass.getYPosition())));
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
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pass.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		pass.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pass.getXPosition());
		
		//Tests if the inputed x position is valid.
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, pass.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED - 1);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pass.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED - 1, pass.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pass.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED + 4, pass.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED + 1);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pass.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED + 1, pass.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pass.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED - 4, pass.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		pass.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pass.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		pass.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, pass.getYPosition());
		
		//Tests if the inputed y position is valid.
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, pass.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED - 1);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pass.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED - 1, pass.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pass.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Pass.MISALIGNMENT_ALLOWED + 4, pass.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED + 1);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, pass.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED + 1, pass.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		pass.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED);
		pass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, pass.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Pass.MISALIGNMENT_ALLOWED - 4, pass.getXPosition());
	}
	
	private boolean isDirectionCorrect() { return pass.getDirection() >= 0 && pass.getDirection() <= 3; }
}
