package utilities;

import gameplayModel.GameContext;
import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Balloom;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import gameplayModel.GridObjects.Exitway;
import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.Flames;
import menuModel.Player;
import menuModel.SavedGame;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static utilities.Position.create;

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

	public static final List<Brick> BRICKS = singletonList(new Brick(create(160, 32)));
	public static final List<Enemy> ENEMIES = singletonList(new Balloom(create(864, 86), 0));
	public static final Exitway EXITWAY = new Exitway(create(832, 160), 0);
	public static final PowerUp POWER_UP = new Flames(create(800, 128));
	public static final Bomberman BOMBERMAN = new Bomberman(create(32, 32), 0, 1, emptyList());

	public static final GridMap GRID_MAP = new GridMap(10000, BRICKS, emptyList(), ENEMIES, EXITWAY, POWER_UP, BOMBERMAN);
	public static final GameContext GAME_CONTEXT = new GameContext(198950, 2, 0, 0, GRID_MAP);
	public static final SavedGame SAVED_GAME = new SavedGame("save1", "Mon Dec 26 15:59:18 PST 2016", GAME_CONTEXT);

	public static final Player VALID_ENTRY_PLAYER = new Player("0", "1", "2", 3, 4, 5);
	public static final Player VALID_ENTRY_PLAYER_WITH_GAME = new Player("0", "1", "2", 3, 4, 5)
			.withSavedGames(singletonList(SAVED_GAME));


	public static List<List<String>> getImportWithInvalidEntries() {
		return asList(LONG_ENTRY, SHORT_ENTRY, VALID_ENTRY, SHORT_ENTRY_WITH_GAME, VALID_ENTRY_WITH_GAME,
				LONG_ENTRY_WITH_GAME);
	}
}
