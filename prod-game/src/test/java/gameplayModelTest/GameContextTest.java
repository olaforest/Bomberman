package gameplayModelTest;

import gameplayController.GameplayController;
import gameplayModel.GameContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameContextTest {
	
	private GameContext gameContext;
	
	@Before
	public void setUp() {
		gameContext = new GameContext();
	}
	
	@Test
	public void testRestartMap() {
		assertTrue(gameContext.getGridMap().getClass().toString().equals("class gameplayModel.GridMap"));
	}
	
	@Test
	public void testGetAndDecreaseGameTime() {
		
		int test = ((int) (Math.random() * (gameContext.MAX_GAME_TIME / GameplayController.TIMEOUT))) + 1;
		
		for (int i = 0 ; i < test ; i++)
			gameContext.decreaseGameTime();
		
		assertEquals((gameContext.MAX_GAME_TIME / GameplayController.TIMEOUT - test) * GameplayController.TIMEOUT, gameContext.getGameTime());
	}
	
	@Test
	public void testInitializeGameTime() {
		
		int test = ((int) (Math.random() * (gameContext.MAX_GAME_TIME / GameplayController.TIMEOUT))) + 1;
		
		for (int i = 0 ; i < test ; i++)
			gameContext.decreaseGameTime();
		
		assertTrue(gameContext.getGameTime() < gameContext.MAX_GAME_TIME);
		
		gameContext.initializeGameTime();
		
		assertEquals(gameContext.MAX_GAME_TIME, gameContext.getGameTime());
	}
	
	@Test
	public void testDecreaseLivesLeft() {
		
		gameContext.decreaseLivesLeft();
		
		assertEquals(1, gameContext.getLivesLeft());
		
		gameContext.decreaseLivesLeft();
		
		assertEquals(0, gameContext.getLivesLeft());
		
		gameContext.decreaseLivesLeft();
		
		assertEquals(0, gameContext.getLivesLeft());
	}
	
	@Test
	public void testGetAndIncreaseLivesLeft() {
		
		assertEquals(2, gameContext.getLivesLeft());
		
		gameContext.increaseLivesLeft();
		
		assertEquals(3, gameContext.getLivesLeft());
	}
	
	@Test
	public void testGetAndIncreaseScore() {
		
		assertEquals(0, gameContext.getScore());
		
		gameContext.increaseScore(1500);
		
		assertEquals(1500, gameContext.getScore());
	}
	
	@Test
	public void testGetLevelSpecification() {
		
		int[] test = gameContext.getLevelSpecification();
		
		assertEquals(9, test.length);
		assertEquals(6, test[0]);
		assertEquals(0, test[1]);
		assertEquals(0, test[2]);
		assertEquals(0, test[3]);
		assertEquals(0, test[4]);
		assertEquals(0, test[5]);
		assertEquals(0, test[6]);
		assertEquals(0, test[7]);
		assertEquals(2, test[8]);
		
		gameContext.setLevel(59);
		
		test = gameContext.getLevelSpecification();
		
		assertEquals(9, test.length);
		assertEquals(0, test[0]);
		assertEquals(0, test[1]);
		assertEquals(0, test[2]);
		assertEquals(0, test[3]);
		assertEquals(1, test[4]);
		assertEquals(2, test[5]);
		assertEquals(5, test[6]);
		assertEquals(2, test[7]);
		assertEquals(8, test[8]);
	}
	
	@Test
	public void testGetActualLevel() {
		assertTrue(gameContext.getActualLevel() >= 0 && gameContext.getActualLevel() <= 49);
		
		gameContext.setLevel(29);
		
		assertEquals(24, gameContext.getActualLevel());
	}
	
	@Test
	public void testIncreaseLevel() {
		
		int test = (int) (Math.random() * 59);
		
		gameContext.setLevel(test);
		gameContext.increaseLevel();
		
		assertEquals(test + 1, gameContext.getLevel());
	}
	
	@Test
	public void testSetAndGetLevel() {
		assertTrue(gameContext.getLevel() >= 0 && gameContext.getLevel() <= 59);
		
		gameContext.setLevel(40);
		
		assertEquals(40, gameContext.getLevel());
		assertEquals(34, gameContext.getActualLevel());
	}
	
	@Test
	public void testGetGridMap() {
		assertTrue(gameContext.getGridMap().getClass().toString().equals("class gameplayModel.GridMap"));
	}
	
	@Test
	public void testSetAndGetEndGameEnemiesStatus() {
		
		assertFalse(gameContext.getEndGameEnemiesStatus());
		
		gameContext.setEndGameEnemiesStatus(true);
		
		assertTrue(gameContext.getEndGameEnemiesStatus());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(gameContext.toCSVEntry().getClass().toString().equals("class java.util.ArrayList"));
		
		assertTrue(gameContext.toCSVEntry().size() == (5 + gameContext.getGridMap().toCSVEntry().size()));
		assertTrue(gameContext.toCSVEntry().get(0).equals(Integer.toString(gameContext.getGameTime())));
		assertTrue(gameContext.toCSVEntry().get(1).equals(Integer.toString(gameContext.getLivesLeft())));
		assertTrue(gameContext.toCSVEntry().get(2).equals(Integer.toString(gameContext.getScore())));
		assertTrue(gameContext.toCSVEntry().get(3).equals(Integer.toString(gameContext.getLevel())));
		assertTrue(gameContext.toCSVEntry().get(4).equals("GridMap"));
	}
}
