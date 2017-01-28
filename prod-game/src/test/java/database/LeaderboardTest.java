package database;

import menuModel.Leaderboard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeaderboardTest {

	private Leaderboard leaderboard;
	private Database database;

	@Before
	public void setup() {
		database = new Database();
		leaderboard = new Leaderboard(database);
	}

	@Test
	public void testGetScores() {
		final int[] scores = leaderboard.generateScores();
		for (int i = 0; i < 10; i++)
			assertEquals(scores[i], database.sortPlayers().get(i).getCumulativeScore());
	}

	@Test
	public void testGetNames() {
		final String[] names = leaderboard.generateNames();
		for (int i = 0; i < 10; i++)
			assertEquals(names[i], database.sortPlayers().get(i).getUsername());
	}

}
