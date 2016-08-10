package databaseTest;

import gameplayModel.GameContext;
import menuModel.Player;
import menuModel.SavedGame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDataTest {

	private Player player1, player2;
	private SavedGame game;

	@Before
	public void setup() {
		player1 = new Player("TestRealName1", "TestUsername1", "TestPassword1", 10, 20000, 10);
		player2 = new Player("TestRealName2", "TestUsername2", "TestPassword2", 10, 30000, 10);
		game = new SavedGame("TestSavedName", "TestSavedDate", new GameContext());
	}

	@Test
	public void testGetRealName() {
		assertEquals(player1.getRealName(), "TestRealName1");
	}

	@Test
	public void testGetUsername() {
		assertEquals(player1.getUsername(), "TestUsername1");
	}

	@Test
	public void testGetPassword() {
		assertEquals(player1.getPassword(), "TestPassword1");
	}

	@Test
	public void testSetRealName() {
		player1.setRealName("NewTestName");
		assertEquals(player1.getRealName(), "NewTestName");
	}

	@Test
	public void testSetPassword() {
		player1.setPassword("NewTestPW");
		assertEquals(player1.getPassword(), "NewTestPW");
	}

	@Test
	public void testUpdateScore() {
		player1.updateScore(100);
		assertEquals(player1.getCumulativeScore(), 20100);
	}

	@Test
	public void testResetScore() {
		player1.resetScore();
		assertEquals(player1.getCumulativeScore(), 0);
	}

	@Test
	public void testAddAndGetSavedGames() {
		player1.addSavedGame(game);
		assertEquals(player1.getSavedGameList().get(0).getGameName(), "TestSavedName");
		assertEquals(player1.getSavedGameList().get(0).getGameDate(), "TestSavedDate");
		assertNotNull(player1.getSavedGameList().get(0).getGameContext());
	}

	@Test
	public void testCompare() {
		assertEquals(player1.compareTo(player2), 10000);
	}

	@Test
	public void testToCSVEntry() {
		assertTrue(player1.toCSVEntry().size() == 6);
		assertTrue(player1.toCSVEntry().get(0).equals("TestRealName1"));
		assertTrue(player1.toCSVEntry().get(1).equals("TestUsername1"));
		assertTrue(player1.toCSVEntry().get(2).equals("TestPassword1"));
		assertTrue(player1.toCSVEntry().get(3).equals(Integer.toString(player1.getLevelUnlocked())));
		assertTrue(player1.toCSVEntry().get(4).equals(Integer.toString(player1.getCumulativeScore())));
	}
}
