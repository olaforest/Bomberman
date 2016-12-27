
package menuModel;

import database.Database;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

	private Database playerList;
	private List<Player> topPlayers = new ArrayList<>();
	public String[] names = new String[10];
	public int[] scores = new int[10];

	public Leaderboard(Database database) {
		playerList = database;
		topPlayers = playerList.sortPlayers();
		for (int i = 0; i < topPlayers.size(); i++) {
			scores[i] = topPlayers.get(i).getCumulativeScore();
			for (int j = 0; j < topPlayers.size(); j++)
				names[j] = topPlayers.get(j).getUsername();
		}
	}

	public int[] generateScores() {
		return scores;
	}

	public String[] generateNames() {
		return names;
	}

	public void updateLeaderboard() {
		topPlayers = playerList.sortPlayers();
		for (int i = 0; i < topPlayers.size(); i++) {
			scores[i] = topPlayers.get(i).getCumulativeScore();
			for (int j = 0; j < topPlayers.size(); j++)
				names[j] = topPlayers.get(j).getUsername();
		}
	}
}

