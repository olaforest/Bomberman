
package menuModel;

import database.Database;

import java.util.ArrayList;

/**
 * This class handles all calculations and updates for the Leaderboard Panel 
 * 
 * @author Eric Liou
 *
 */
public class Leaderboard {

	
	public Database playerList;
	public ArrayList<Player> topPlayers = new ArrayList<Player>();
	public String[] names = new String[10];
	public int[] scores = new int[10];
	
	/**
	 * Constructs a new leaderboard and calculates and stores the top 10 scoring players
	 * @param database The current database which contains information of all players
	 */
	public Leaderboard(Database database){
		playerList = database;
		topPlayers = playerList.sortPlayers();
		for(int i = 0; i<topPlayers.size(); i++){
			scores[i] = topPlayers.get(i).getCumulativeScore();
		for(int j=0; j<topPlayers.size(); j++)
			names[j] = topPlayers.get(j).getUsername();	
		}
	}
	
	/**
	 * @return an array of the top 10 scores in descending order
	 */
	public int[] generateScores(){
		return scores;
	}
	
	/**
	 * @return an array of the top 10 players' usernames in descending order
	 */
	public String[] generateNames(){
		return names;
	}
	
	/**
	 * Checks the database for any updates or changes in the top 10 players and rearranges the order respectively
	 */
	public void updateLeaderboard(){
		topPlayers = playerList.sortPlayers();
		for(int i = 0; i<topPlayers.size(); i++){
			scores[i] = topPlayers.get(i).getCumulativeScore();
		for(int j=0; j<topPlayers.size(); j++)
			names[j] = topPlayers.get(j).getUsername();	
		}
	}
}

