package menuModel;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Player implements Comparable<Player> {

	private int levelUnlocked;
	private String realName;
	private final String username;
	private String password;
	private final CumulativeScore cumulativeScore;
	private final List<SavedGame> savedGameList;

	public Player(String name, String user, String pw) {
		realName = name;
		username = user;
		password = pw;
		levelUnlocked = 0;
		savedGameList = new ArrayList<>();
		cumulativeScore = new CumulativeScore();
	}

	public Player(String name, String user, String pw, int unlocked, int points, int gamePlayed) {
		realName = name;
		username = user;
		password = pw;
		levelUnlocked = unlocked;
		savedGameList = new ArrayList<>();
		cumulativeScore = new CumulativeScore(points, gamePlayed);
	}

	public String getRealName() {
		return realName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setRealName(String real) {
		realName = real;
	}

	public void setPassword(String pass) {
		password = pass;
	}

	public void updateScore(int score) {
		cumulativeScore.increaseCumulativeScore(score);
	}

	public int getCumulativeScore() {
		return cumulativeScore.getCumulativeScore();
	}

	public void resetScore() {
		cumulativeScore.clearScore();
	}

	public int getLevelUnlocked() {
		return levelUnlocked;
	}

	public void increaseLevelUnlocked() {
		levelUnlocked++;
	}

	public List<SavedGame> getSavedGameList() {
		return savedGameList;
	}

	public void addSavedGame(SavedGame game) {
		savedGameList.add(game);
	}

	public void withSavedGames(List<SavedGame> games) {
		savedGameList.addAll(games);
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();

		entryList.add(realName);
		entryList.add(username);
		entryList.add(password);
		entryList.add(Integer.toString(levelUnlocked));
		entryList.add(Integer.toString(cumulativeScore.getCumulativeScore()));
		entryList.add(Integer.toString(cumulativeScore.gamesPlayed));

		for (SavedGame game : savedGameList) {
			entryList.add("SavedGame");
			for (String token : game.toCSVEntry())
				entryList.add(token);
		}
		return entryList;
	}

	public int compareTo(Player plr) {
		return plr.getCumulativeScore() - this.getCumulativeScore();
	}
}
