package gameplayModelTest;

import gameplayController.GameplayController;
import gameplayModel.GridMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridMapTest {
	
	private GridMap gridMap;
	
	@Before
	public void setUp() {
		
		int[] testLevel = {2, 2, 2, 2, 2, 2, 2, 2, 2};
		
		gridMap = new GridMap(testLevel);
	}
	
	@Test
	public void testGenerateEnemies() {
		
		assertEquals(16, gridMap.getEnemies().size());
		
		// Tests for invalid input, can't generate negative enemies.
		int[] testLevelSpec = {-1, 0, 0, 0, 0, 0, 0, 0, 0};
		gridMap.generateEnemies(testLevelSpec);
		
		assertEquals(16, gridMap.getEnemies().size());
		
		// Test for valid input, add three enemies of type Doll.
		int[] testLevelSpec2 = {0, 0, 3, 0, 0, 0, 0, 0, 0};
		gridMap.generateEnemies(testLevelSpec2);
		
		assertEquals(19, gridMap.getEnemies().size());
		assertTrue(gridMap.getEnemies().get(16).getClass().toString().equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll"));
		assertTrue(gridMap.getEnemies().get(17).getClass().toString().equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll"));
		assertTrue(gridMap.getEnemies().get(18).getClass().toString().equals("class gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll"));
		
		/* Test irrelevant input, last number in the array corresponds to the power up type in the level, therefore 
		should not be taken into account for generating new enemies.*/
		int[] testLevelSpec3 = {0, 0, 0, 0, 0, 0, 0, 0, -2};
		gridMap.generateEnemies(testLevelSpec3);
		
		assertEquals(19, gridMap.getEnemies().size());
	}
	
	@Test
	public void testDecreaseSpawnTimer() {
		
		int[] testLevel = {-1, 0, 0, 0, 0, 0, 0, 0, 0};
		
		gridMap = new GridMap(testLevel);
		
		assertEquals(8, gridMap.getEnemies().size());
		
		for (int i = 0 ; i < (gridMap.SPAWN_TIMEOUT / GameplayController.TIMEOUT - 1) ; i++)
			gridMap.decreaseSpawnTimer();
		
		assertEquals(8, gridMap.getEnemies().size());
		
		gridMap.decreaseSpawnTimer();
		
		assertEquals(16, gridMap.getEnemies().size());
	}
	
	@Test
	public void testGetBomberman() {
		assertTrue(gridMap.getBomberman().getClass().toString().equals("class gameplayModel.GridObjects.AnimatedObjects.Bomberman"));
	}
	
	@Test
	public void testGetConcreteLayout() {
		assertTrue(gridMap.getConcreteLayout().getClass().toString().equals("class java.util.ArrayList"));
		assertEquals(154, gridMap.getConcreteLayout().size());
	}
	
	@Test
	public void testGetBricks() {
		assertTrue(gridMap.getBricks().getClass().toString().equals("class java.util.ArrayList"));
	}
	
	@Test
	public void testGetPowerUp() {
		assertTrue(gridMap.getPowerUp().getClass().toString().equals("class gameplayModel.GridObjects.PowerUps.Flames"));
	}
	
	@Test
	public void testGetBombs() {
		assertTrue(gridMap.getBombs().getClass().toString().equals("class java.util.ArrayList"));
		assertEquals(0, gridMap.getBombs().size());
	}
	
	@Test
	public void testGetEnemies() {
		assertTrue(gridMap.getEnemies().getClass().toString().equals("class java.util.ArrayList"));
		assertEquals(16, gridMap.getEnemies().size());
	}
	
	@Test
	public void testGetExitway() {
		assertTrue(gridMap.getExitway().getClass().toString().equals("class gameplayModel.GridObjects.Exitway"));
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(gridMap.toCSVEntry().getClass().toString().equals("class java.util.ArrayList"));
		
		assertTrue(gridMap.toCSVEntry().size() == (8 + gridMap.getBricks().size()*2 + gridMap.getBombs().size()*7 + gridMap.getEnemies().size()*4 + gridMap.getExitway().toCSVEntry().size() + 
				gridMap.getPowerUp().toCSVEntry().size() + gridMap.getBomberman().toCSVEntry().size()));
		assertTrue(gridMap.toCSVEntry().get(1).equals("Bricks"));
	}
}
