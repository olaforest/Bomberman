package menuModel;

import gameplayModel.GameContext;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@EqualsAndHashCode(exclude = {"gameContext", "levelIndex"})
public class SavedGame {
	private int levelIndex;

	private final String gameName;
	private final String gameDate;
	private final GameContext gameContext;
	public SavedGame(String name, String date, GameContext context) {
		gameName = name;
		gameDate = date;
		gameContext = context;
	}

	public int getLevelIndex() {
		return levelIndex;
	}

	public String getGameName() {
		return gameName;
	}

	public String getGameDate() {
		return gameDate;
	}

	public GameContext getGameContext() {
		return gameContext;
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add(valueOf(levelIndex));
		entryList.add(gameName);
		entryList.add(gameDate);
		entryList.add("GameContext");

		for (String token : gameContext.toCSVEntry())
			entryList.add(token);

		return entryList;
	}
}
