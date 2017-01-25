package database;

import gameplayModel.GameContext;
import gameplayModel.GridMap;
import gameplayModel.gridObjects.Exitway;
import gameplayModel.gridObjects.PowerUp;
import gameplayModel.gridObjects.animatedObjects.*;
import gameplayModel.gridObjects.powerUps.*;
import lombok.Getter;
import menuModel.Player;
import menuModel.SavedGame;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static gameplayModel.gridObjects.animatedObjects.Enemies.valueOf;
import static java.lang.Integer.parseInt;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;
import static utilities.CsvUtils.readCSV;
import static utilities.CsvUtils.writeCSV;
import static utilities.Position.create;

@Getter
public class Database {

	private static final String SAVED_GAME = "SavedGame";
	public static final UnaryOperator<List<String>> SAVED_GAME_CONTENT = content -> content.subList(6, content.size());

	private final List<Player> players;
	private Player currentLoggedPlayer;

	public Database() {
		players = importPlayers();
		currentLoggedPlayer = null;
	}

	public void generateCSV() {
		final List<List<String>> playersContent = players.stream()
				.map(Player::toCSVEntry)
				.collect(toList());
		writeCSV("Bomberman.csv", playersContent);
	}

	private List<Player> importPlayers() {
		return readCSV("Bomberman.csv").stream()
				.filter(entry -> entry.contains(SAVED_GAME) ? entry.indexOf(SAVED_GAME) == 6 : entry.size() == 6)
				.map(this::generatePlayer)
				.collect(toList());
	}

	private Player generatePlayer(List<String> playerContent) {
		final Player player = new Player(playerContent.get(0), playerContent.get(1), playerContent.get(2),
				parseInt(playerContent.get(3)), parseInt(playerContent.get(4)), parseInt(playerContent.get(5)));
		if (playerContent.contains(SAVED_GAME))
			player.withSavedGames(parseSavedGames(SAVED_GAME_CONTENT.apply(playerContent)));
		return player;
	}

	private List<SavedGame> parseSavedGames(List<String> gamesContent) {
		final List<List<String>> games = splitSavedGames(gamesContent);
		return games.stream()
				.map(this::generateSavedGame)
				.collect(toList());
	}

	private List<List<String>> splitSavedGames(List<String> gamesContent) {
		final List<List<String>> games = new ArrayList<>(new ArrayList<>());
		gamesContent = gamesContent.subList(1, gamesContent.size());
		while (gamesContent.contains(SAVED_GAME)) {
			final int indexOfNextGame = gamesContent.indexOf(SAVED_GAME);
			games.add(gamesContent.subList(0, indexOfNextGame));
			gamesContent = gamesContent.subList(indexOfNextGame + 2, gamesContent.size());
		}
		games.add(gamesContent);
		return games;
	}

	private SavedGame generateSavedGame(List<String> gameContent) {
		String gameName, gameDate;
		GameContext gameContext = generateGameContext(new ArrayList<>(gameContent.subList(gameContent.indexOf("GameContext") + 1, gameContent.size())));

		gameName = gameContent.get(0);
		gameDate = gameContent.get(1);

		return new SavedGame(gameName, gameDate, gameContext);
	}

	private GameContext generateGameContext(ArrayList<String> context) {
		int gameTime, livesLeft, score;

		GridMap gridMap = generateGridMap(new ArrayList<>(context.subList(context.indexOf("GridMap") + 1, context.size())));

		gameTime = parseInt(context.get(0));
		livesLeft = parseInt(context.get(1));
		score = parseInt(context.get(2));

		return new GameContext(gameTime, livesLeft, score, gridMap);
	}

	private GridMap generateGridMap(List<String> map) {
		int spawnTimer = parseInt(map.get(0));

		List<Brick> bricks = generateBricks(new ArrayList<>(map.subList(map.indexOf("Bricks") + 1, map.indexOf("Bombs"))));
		List<Bomb> bombs = generateBombs(new ArrayList<>(map.subList(map.indexOf("Bombs") + 1, map.indexOf("Enemies"))));
		List<Enemy> enemies = generateEnemies(new ArrayList<>(map.subList(map.indexOf("Enemies") + 1, map.indexOf("Exitway"))));
		Exitway exitway = generateExitway(new ArrayList<>(map.subList(map.indexOf("Exitway") + 1, map.indexOf("PowerUp"))));
		PowerUp powerup = generatePowerUp(new ArrayList<>(map.subList(map.indexOf("PowerUp") + 1, map.indexOf("Bomberman"))));
		Bomberman bomberman = generateBomberman(new ArrayList<>(map.subList(map.indexOf("Bomberman") + 1, map.size())));

		return new GridMap(spawnTimer, bricks, bombs, enemies, exitway, powerup, bomberman);
	}

	private List<Brick> generateBricks(List<String> data) {
		int xPos, yPos;
		List<Brick> bricks = new ArrayList<>();

		while (data.size() != 0) {
			xPos = parseInt(data.remove(0));
			yPos = parseInt(data.remove(0));
			bricks.add(new Brick(create(xPos, yPos)));
		}
		return bricks;
	}

	private List<Bomb> generateBombs(List<String> data) {
		int range, xPos, yPos, timer, rightRange, leftRange, downRange, upRange;
		List<Bomb> bombs = new ArrayList<>();
		range = parseInt(data.remove(0));

		while (data.size() != 0) {
			xPos = parseInt(data.remove(0));
			yPos = parseInt(data.remove(0));
			timer = parseInt(data.remove(0));
			rightRange = parseInt(data.remove(0));
			leftRange = parseInt(data.remove(0));
			downRange = parseInt(data.remove(0));
			upRange = parseInt(data.remove(0));
			bombs.add(new Bomb(range, create(xPos, yPos), timer, rightRange, leftRange, downRange, upRange));
		}
		return bombs;
	}

	private List<Enemy> generateEnemies(List<String> data) {
		final List<Enemy> enemies = new ArrayList<>();
		while (data.size() != 0) {
			final  Enemies type = valueOf(data.remove(0));
			final  int xPosition = parseInt(data.remove(0));
			final int yPosition = parseInt(data.remove(0));
			final Position position = create(xPosition, yPosition);
			final int direction = parseInt(data.remove(0));
			enemies.add(new Enemy(type, position, direction));
		}
		return enemies;
	}

	private Exitway generateExitway(List<String> data) {
		int xPos = parseInt(data.remove(0));
		int yPos = parseInt(data.remove(0));
		return new Exitway(create(xPos, yPos), -1);
	}

	private PowerUp generatePowerUp(List<String> data) {
		String type = data.remove(0);
		int xPosition = parseInt(data.remove(0));
		int yPosition = parseInt(data.remove(0));
		return determinePowerUp(type, xPosition, yPosition);
	}

	private Bomberman generateBomberman(List<String> data) {
		int xPos, yPos, invincibilityTimer, bombsLeft;
		xPos = parseInt(data.remove(0));
		yPos = parseInt(data.remove(0));
		invincibilityTimer = parseInt(data.remove(0));
		bombsLeft = parseInt(data.remove(0));

		List<PowerUp> powerUpsAcquired = generatePowerUpsAcquired(new ArrayList<>(data.subList(data.indexOf("PowerUpAcquired") + 1, data.size())));

		return new Bomberman(create(xPos, yPos), invincibilityTimer, bombsLeft, powerUpsAcquired);
	}

	private List<PowerUp> generatePowerUpsAcquired(List<String> data) {
		int xPos, yPos;
		String type;
		List<PowerUp> powerUps = new ArrayList<>();

		while (data.size() != 0) {
			type = data.remove(0);
			xPos = parseInt(data.remove(0));
			yPos = parseInt(data.remove(0));
			powerUps.add(determinePowerUp(type, xPos, yPos));
		}
		return powerUps;
	}

	private PowerUp determinePowerUp(String type, int xPosition, int yPosition) {
		final Position position = create(xPosition, yPosition);
		switch (type) {
			case "class gameplayModel.GridObjects.PowerUps.BombPU":
				return new BombPU(position);
			case "class gameplayModel.GridObjects.PowerUps.Flames":
				return new Flames(position);
			case "class gameplayModel.GridObjects.PowerUps.Speed":
				return new Speed(position);
			case "class gameplayModel.GridObjects.PowerUps.Wallpass":
				return new Wallpass(position);
			case "class gameplayModel.GridObjects.PowerUps.Detonator":
				return new Detonator(position);
			case "class gameplayModel.GridObjects.PowerUps.Bombpass":
				return new Bombpass(position);
			case "class gameplayModel.GridObjects.PowerUps.Flamepass":
				return new Flamepass(position);
			case "class gameplayModel.GridObjects.PowerUps.Mystery":
				return new Mystery(position);
			default:
				return null;
		}
	}

	public Player addPlayer(Player newPlayer) {
		if (findPlayer(newPlayer) == -1) {
			players.add(newPlayer);
			currentLoggedPlayer = newPlayer;
			return newPlayer;
		} else
			return null;
	}

	public void deletePlayer() {
		int index = findPlayer(currentLoggedPlayer);
		players.remove(index);
	}

	public void editRealName(String realName) {
		int index = findPlayer(currentLoggedPlayer);
		players.get(index).setRealName(realName);
	}

	public void editPassword(String password) {
		int index = findPlayer(currentLoggedPlayer);
		players.get(index).setPassword(password);
	}

	public Player getPlayer(String username, String password) {
		int index = findPlayer(new Player(null, username, password));

		if (index == -1) {
			return null;
		} else {
			if (players.get(index).getPassword().equals(password))
				return players.get(index);
			else
				return null;
		}
	}

	public List<Player> sortPlayers() {
		List<Player> topPlayers = players;
		sort(topPlayers);
		topPlayers = new ArrayList<>(topPlayers.subList(0, 10));
		return topPlayers;
	}

	private int findPlayer(Player pl) {
		int index = 0;

		for (Player player : players) {
			if (pl.getUsername().equals(player.getUsername())) return index;
			index++;
		}
		return -1;
	}
}
