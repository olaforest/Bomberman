
package menuModel;

import java.util.ArrayList;

/**
 * This Class generates and stores all data for a player account such as real name, username, password, 
 * total cumulative score, and all saved games
 * 
 * @author Eric Liou
 *
 */
public class Player implements Comparable<Player> {
	
	private int levelUnlocked;
	
	private String realName, username, password;
	private CumulativeScore cumulativeScore;
	private ArrayList<SavedGame> savedGameList;
	
	
	/**
	 * Constructs a new player account with no saved games and 0 cumulative score
	 * @param name The real name of the account
	 * @param user The username of the account
	 * @param pw The password of the account
	 */
	public Player(String name, String user, String pw){
		realName = name;
		username = user;
		password = pw;
		
		levelUnlocked = 0;
		
		savedGameList = new ArrayList<SavedGame>();
		cumulativeScore = new CumulativeScore();
	}
	
	/**
	 * Constructs a new player account with input parameters
	 * @param name The real name of the account
	 * @param user The username of the account
	 * @param pw The password of the account
	 * @param unlocked The farthest level reached
	 * @param points The cumulative score of the account
	 * @param gamePlayed The total number of games played 
	 */
	public Player(String name, String user, String pw, int unlocked, int points, int gamePlayed){
		realName = name;
		username = user;
		password = pw;
		levelUnlocked = unlocked;
		
		savedGameList = new ArrayList<SavedGame>();
		cumulativeScore = new CumulativeScore(points, gamePlayed);
	}
	
	/**
	 * @return The real name of the player
	 */
	public String getRealName(){
		return realName;
	}
	
	/**
	 * @return The username of the player
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * @return The password of the player
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * @param real New real name which current real name is updated to
	 */
	public void setRealName(String real){
		realName = real;
	}
	
	/**
	 * @param pass New password which current password is updated to
	 */
	public void setPassword(String pass){
		password = pass;
	}
	
	/**
	 * @param score Number of additional points which is added to the cumulative score
	 */
	public void updateScore(int score){
		cumulativeScore.increaseCumulativeScore(score);
	}
	
	/**
	 * @return Total Cumulative score of player
	 */
	public int getCumulativeScore(){
		return cumulativeScore.getCumulativeScore();
	}
	
	/**
	 * Resets the player's total score
	 */
	public void resetScore(){
		cumulativeScore.clearScore();
	}
	
	/**
	 * @return The farthest level that the player has reached
	 */
	public int getLevelUnlocked() {
		return levelUnlocked;
	}
	
	/**
	 * Increases the farthest level a player has reached by 1
	 */
	public void increaseLevelUnlocked() {
		levelUnlocked++;
	}

	/**
	 * @return an ArrayList of all saved games stored in the player account
	 */
	public ArrayList<SavedGame> getSavedGameList() {
		return savedGameList;
	}

	/**
	 * Adds a new saved game to a player account
	 * @param game new saved game which is stored into the player account
	 */
	public void addSavedGame(SavedGame game) {
		savedGameList.add(game);
	}
	
	/**
	 * Write and stores all data of player into the CSV
	 * @return an Arraylist of all stored data within player
	 */
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
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
	/**
	 * Compares the score of two players
	 * @param plr Other player which current player is compared to
	 * @return The difference in score between plr and current player
	 */
	public int compareTo(Player plr) {
		
		return plr.getCumulativeScore() - this.getCumulativeScore();
	}


}
