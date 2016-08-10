package menuModel;

/**
 * This Class calculates and stores the total cumulative points a player has achieved as well as
 * total number of games played
 *
 * @author Eric Liou
 */
public class CumulativeScore {


	public int gamesPlayed, cumulativePoints;

	/**
	 * Constructs a new cumulative score for new player accounts
	 */
	public CumulativeScore() {
		cumulativePoints = 0;
		gamesPlayed = 0;
	}

	/**
	 * Constructs a specified cumulative score for a player account
	 *
	 * @param points      The specified total cumulative points
	 * @param gamesPlayed The specified number of games played
	 */
	public CumulativeScore(int points, int gamesPlayed) {
		cumulativePoints = points;
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * @return the cumulative number of total games played
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * @return the cumulative score of a player
	 */
	public int getCumulativeScore() {
		return cumulativePoints;
	}

	/**
	 * @param additionalPoints number of points the cumulative total increases by
	 */
	public void increaseCumulativeScore(int additionalPoints) {
		cumulativePoints += additionalPoints;
	}

	/**
	 * Resets a player's cumulative score
	 */
	public void clearScore() {
		cumulativePoints = 0;
	}
}
