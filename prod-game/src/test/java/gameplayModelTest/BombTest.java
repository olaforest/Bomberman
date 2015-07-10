package gameplayModelTest;

import gameplayController.GameplayController;
import gameplayModel.Bomb;
import gameplayModel.Enemy;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BombTest {
	
	private Bomb bomb;
	
	@Before
	public void setUp() {
		bomb = new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testBomb() {
		
		int testXPosition = (((int) (Math.random() * (GridMap.MAPWIDTH / 2))) * 2 + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH;
		int testYPosition = (((int) (Math.random() * (GridMap.MAPHEIGHT / 2))) * 2 + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT;
		int testTimer = (int) (Math.random() * (bomb.TIME_TO_EXPLOSION / GameplayController.TIMEOUT));
		int testRange = ((int) (Math.random() * 10)) + 1;
		int testRightRange = ((int) (Math.random() * testRange)) + 1;
		int testLeftRange = ((int) (Math.random() * testRange)) + 1;
		int testDownRange = ((int) (Math.random() * testRange)) + 1;
		int testUpRange = ((int) (Math.random() * testRange)) + 1;
		
		bomb = new Bomb(testRange, testXPosition, testYPosition, testTimer, testRightRange, testLeftRange, testDownRange, testUpRange);
		
		assertEquals(testXPosition, bomb.getXPosition());
		assertEquals(testYPosition, bomb.getYPosition());
		assertEquals(testTimer, bomb.getTimer());
		assertEquals(testRange, Bomb.getRange());
		assertEquals(testRightRange, bomb.getRightRange());
		assertEquals(testLeftRange, bomb.getLeftRange());
		assertEquals(testDownRange, bomb.getDownRange());
		assertEquals(testUpRange, bomb.getUpRange());
	}
	
	@Test
	public void testIsDead() {
		assertFalse(bomb.isDead());
	}
	
	@Test
	public void testIsObsolete() {
		assertFalse(bomb.isObsolete());
	}
	
	@Test
	public void testTriggerDeath() {
		
		bomb.triggerDeath();
		assertTrue(bomb.isDead());
	}
	
	@Test
	public void testDecreaseTimer() {
		
		int test = (int) (Math.random() * (bomb.TIME_TO_EXPLOSION / GameplayController.TIMEOUT));
		
		for (int i = 0 ; i < test ; i++)
			bomb.decreaseTimer();
		
		assertEquals(bomb.TIME_TO_EXPLOSION - test * GameplayController.TIMEOUT, bomb.getTimer());
		
		bomb.setTimer(GameplayController.TIMEOUT);
		bomb.decreaseTimer();
		
		assertTrue(bomb.isDead());
		assertEquals(0, bomb.getTimer());
	}
	
	@Test
	public void testSetAndGetTimer() {
		
		int test = (int) (Math.random() * bomb.TIME_TO_EXPLOSION);
		
		bomb.setTimer(test);
		
		assertEquals(test, bomb.getTimer());
	}
	
	@Test
	public void testGetAndSetCurrentAnimation() {
		
		int animationNumber = (int) Math.random() * Enemy.AnimationType.values().length;
		
		bomb.setCurrentAnimation(animationNumber);
		
		assertTrue(animationNumber == bomb.getAnimationNumber());
	}
	
	@Test
	public void testTriggerExplosion() {
		
		Bomb.resetRange();
		Bomb.increaseRange();
		Bomb.increaseRange();
		
		bomb = new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH * 5, GridObject.EFFECTIVE_PIXEL_HEIGHT * 5);
		
		bomb.setUpRange(1);
		bomb.setDownRange(0);
		bomb.setLeftRange(2);
		
		bomb.triggerExplosion();
		
		assertTrue(bomb.isDead());
		assertEquals(0, bomb.getTimer());
		
		assertEquals(7, bomb.getCurrentAnimations().size());
		
		Bomb.resetRange();
		
		bomb = new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
		
		bomb.triggerExplosion();
		
		assertTrue(bomb.isDead());
		assertEquals(0, bomb.getTimer());
		
		assertEquals(3, bomb.getCurrentAnimations().size());
	}
	
	@Test
	public void testWasTrigByBomb() {
		assertFalse(bomb.wasTrigByBomb());
	}
	
	@Test
	public void testSetWasTrigByBomb() {
		bomb.setWasTrigByBomb();
		
		assertTrue(bomb.wasTrigByBomb());
	}
	
	@Test
	public void testGetRange() {
		assertEquals(1, Bomb.getRange());
	}
	
	@Test 
	public void testIncreaseRange() {
		
		Bomb.resetRange();
		
		int test = (int) (Math.random()* 8) + 1;
		
		for (int i = 1 ; i < test ; i++)
			Bomb.increaseRange();
		
		assertEquals(test, Bomb.getRange());
	}
	
	@Test 
	public void testResetRange() {
		
		for (int i = 0 ; i < ((int) Math.random()* 8 + 1) ; i++)
			Bomb.increaseRange();
		
		Bomb.resetRange();
		
		assertEquals(1, Bomb.getRange());
	}
	
	@Test
	public void testSetAndGetRightRange() {
		
		assertTrue(bomb.getRightRange() >= 0);
		assertTrue(bomb.getRightRange() <= Bomb.getRange());
		
		bomb.setRightRange(4);
		assertEquals(4, bomb.getRightRange());
		
		bomb.setRightRange(0);
		assertEquals(0, bomb.getRightRange());
	}
	
	@Test
	public void testSetAndGetLeftRange() {
		
		assertTrue(bomb.getLeftRange() >= 0);
		assertTrue(bomb.getLeftRange() <= Bomb.getRange());
		
		bomb.setLeftRange(4);
		assertEquals(4, bomb.getLeftRange());
		
		bomb.setLeftRange(0);
		assertEquals(0, bomb.getLeftRange());
	}
	
	@Test
	public void testSetAndGetDownRange() {
		
		assertTrue(bomb.getDownRange() >= 0);
		assertTrue(bomb.getDownRange() <= Bomb.getRange());
		
		bomb.setDownRange(2);
		assertEquals(2, bomb.getDownRange());
		
		bomb.setDownRange(0);
		assertEquals(0, bomb.getDownRange());
	}
	
	@Test
	public void testSetAndGetUpRange() {
		
		assertTrue(bomb.getUpRange() >= 0);
		assertTrue(bomb.getUpRange() <= Bomb.getRange());
		
		bomb.setUpRange(3);
		assertEquals(3, bomb.getUpRange());
		
		bomb.setUpRange(0);
		assertEquals(0, bomb.getUpRange());
	}
	
	@Test
	public void testGetCurrentAnimations() {
		assertTrue(bomb.getCurrentAnimations().getClass().toString().equals("class java.util.ArrayList"));
	}
	
	@Test
	public void testGetAnimXOffset() {
		assertTrue(bomb.getAnimXOffset().getClass().toString().equals("class java.util.ArrayList"));
		assertTrue(bomb.getAnimXOffset().size() == bomb.getCurrentAnimations().size());
	}
	
	@Test
	public void testGetAnimYOffset() {
		assertTrue(bomb.getAnimYOffset().getClass().toString().equals("class java.util.ArrayList"));
		assertTrue(bomb.getAnimYOffset().size() == bomb.getCurrentAnimations().size());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(bomb.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is out of bounds on the left.
		bomb.setXPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomb.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		bomb.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomb.getXPosition());
		
		//Tests if the inputed x position is valid.
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bomb.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED - 1);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomb.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED - 1, bomb.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomb.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED + 4, bomb.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED + 1);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomb.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED + 1, bomb.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomb.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED - 4, bomb.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		bomb.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomb.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		bomb.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bomb.getYPosition());
		
		//Tests if the inputed y position is valid.
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bomb.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED - 1);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomb.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED - 1, bomb.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomb.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - Bomb.MISALIGNMENT_ALLOWED + 4, bomb.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED + 1);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, bomb.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED + 1, bomb.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		bomb.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED);
		bomb.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, bomb.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + Bomb.MISALIGNMENT_ALLOWED - 4, bomb.getXPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(bomb.toCSVEntry().size() == 7);
		assertTrue(bomb.toCSVEntry().get(0).equals(Integer.toString(bomb.getXPosition())));
		assertTrue(bomb.toCSVEntry().get(1).equals(Integer.toString(bomb.getYPosition())));
		assertTrue(bomb.toCSVEntry().get(2).equals(Integer.toString(bomb.getTimer())));
		assertTrue(bomb.toCSVEntry().get(3).equals(Integer.toString(bomb.getRightRange())));
		assertTrue(bomb.toCSVEntry().get(4).equals(Integer.toString(bomb.getLeftRange())));
		assertTrue(bomb.toCSVEntry().get(5).equals(Integer.toString(bomb.getDownRange())));
		assertTrue(bomb.toCSVEntry().get(6).equals(Integer.toString(bomb.getUpRange())));
	}
}
