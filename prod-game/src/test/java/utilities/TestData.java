package utilities;

import java.util.List;

import static java.util.Arrays.asList;

public class TestData {

	private static final List<String> LONG_ENTRY = asList("0", "1", "2", "3", "4", "5", "6");
	private static final List<String> SHORT_ENTRY = asList("0", "1", "2", "3", "4");
	private static final List<String> VALID_ENTRY = asList("0", "1", "2", "3", "4", "5");
	private static final List<String> LONG_ENTRY_WITH_GAME = asList("0", "1", "2", "3", "4", "5", "6", "SavedGame", "...");
	private static final List<String> SHORT_ENTRY_WITH_GAME = asList("0", "1", "2", "3", "4", "SavedGame", "...");
	private static final List<String> VALID_ENTRY_WITH_GAME = asList("0", "1", "2", "3", "4", "5", "SavedGame", "save1",
			"Mon Dec 26 15:59:18 PST 2016", "GameContext", "198950", "2", "0", "0", "GridMap", "10000", "Bricks", "160",
			"32", "Bombs", "1", "Enemies", "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Balloom", "864",
			"86", "0", "Exitway", "832", "160", "PowerUp", "class gameplayModel.GridObjects.PowerUps.Flames",
			"800", "128", "Bomberman", "32", "32", "0", "1", "PowerUpAcquired");


	public static List<List<String>> getImportWithInvalidEntries() {
		return asList(LONG_ENTRY, SHORT_ENTRY, VALID_ENTRY, SHORT_ENTRY_WITH_GAME, VALID_ENTRY_WITH_GAME,
				LONG_ENTRY_WITH_GAME);
	}
}
