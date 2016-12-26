package database;

import menuModel.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {
	private Database database;
	private Player player;
	private Player arrayPlayer1, arrayPlayer2, arrayPlayer3, arrayPlayer4, arrayPlayer5, arrayPlayer6, arrayPlayer7, arrayPlayer8, arrayPlayer9, arrayPlayer10;
	private ArrayList<Player> sorted;

	@Before
	public void setup() {
		database = new Database();
		player = new Player("testName", "testUsername", "testPassword");
		arrayPlayer1 = new Player("name1", "user1", "pw1", 1, 100000, 1);
		arrayPlayer2 = new Player("name2", "user2", "pw2", 2, 200000, 2);
		arrayPlayer3 = new Player("name3", "user3", "pw3", 3, 300000, 3);
		arrayPlayer4 = new Player("name4", "user4", "pw4", 4, 400000, 4);
		arrayPlayer5 = new Player("name5", "user5", "pw5", 5, 500000, 5);
		arrayPlayer6 = new Player("name6", "user6", "pw6", 6, 600000, 6);
		arrayPlayer7 = new Player("name7", "user7", "pw7", 7, 700000, 7);
		arrayPlayer8 = new Player("name8", "user8", "pw8", 8, 800000, 8);
		arrayPlayer9 = new Player("name9", "user9", "pw9", 9, 900000, 9);
		arrayPlayer10 = new Player("name10", "user10", "pw10", 10, 1000000, 10);

		sorted = new ArrayList<Player>();
		sorted.add(arrayPlayer10);
		sorted.add(arrayPlayer9);
		sorted.add(arrayPlayer8);
		sorted.add(arrayPlayer7);
		sorted.add(arrayPlayer6);
		sorted.add(arrayPlayer5);
		sorted.add(arrayPlayer4);
		sorted.add(arrayPlayer3);
		sorted.add(arrayPlayer2);
		sorted.add(arrayPlayer1);
	}

	@Test
	public void testPlayerAdd() {
		assertNotNull(database.addPlayer(player));
	}

	@Test
	public void testPlayerDelete() {
		database.addPlayer(player);
		database.deletePlayer();
		assertNull(database.getPlayer("testUsername", "testPassword"));
	}

	@Test
	public void testEditPlayerName() {
		database.addPlayer(player);
		database.editRealName("newTestName");
		assertSame(player.getRealName(), "newTestName");
	}

	@Test
	public void testEditPassword() {
		database.addPlayer(player);
		database.editPassword("newTestPassword");
		assertNotNull(player.getPassword(), "newTestPassword");
	}

	@Test
	public void testfindPlayer() {
		database.addPlayer(player);
		assertNotNull(database.getPlayer("testUsername", "testPassword"));
	}

	@Test
	public void testPlayerSort() {
		database.addPlayer(arrayPlayer1);
		database.addPlayer(arrayPlayer2);
		database.addPlayer(arrayPlayer3);
		database.addPlayer(arrayPlayer4);
		database.addPlayer(arrayPlayer5);
		database.addPlayer(arrayPlayer6);
		database.addPlayer(arrayPlayer7);
		database.addPlayer(arrayPlayer8);
		database.addPlayer(arrayPlayer9);
		database.addPlayer(arrayPlayer10);

		assertEquals(database.sortPlayers(), sorted);
	}


}
