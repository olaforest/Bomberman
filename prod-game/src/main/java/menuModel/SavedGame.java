package menuModel;

import gameplayModel.GameContext;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = "gameContext")
public class SavedGame {

	private String gameName, gameDate;
	private GameContext gameContext;

	public SavedGame(String name, String date, GameContext context) {
		gameName = name;
		gameDate = date;
		gameContext = context;
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
		entryList.add(gameName);
		entryList.add(gameDate);
		entryList.add("GameContext");

		for (String token : gameContext.toCSVEntry())
			entryList.add(token);

		return entryList;
	}
}
