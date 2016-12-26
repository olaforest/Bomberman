package database;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import gameplayModel.GameContext;
import gameplayModel.GridMap;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.*;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import gameplayModel.GridObjects.Exitway;
import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.*;
import lombok.Getter;
import menuModel.Player;
import menuModel.SavedGame;
import utilities.Position;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;
import static utilities.Position.create;

@Getter
public class Database {

	private List<Player> players;
	private Player currentLoggedPlayer;

	public Database() {
		players = importPlayers();
//		players = new ArrayList<>();
//		currentLoggedPlayer = null;
//
//		try {
//			readCSV();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		sortPlayers();
	}

	private List<Player> importPlayers() {
		return emptyList();
	}

	public void generateCSV() throws IOException, URISyntaxException {

		File file = new File("Bomberman.csv");
		FileWriter fileWriter = new FileWriter(file, false);
		CSVWriter writer = new CSVWriter(fileWriter);

		for (Player player : players) {
			ArrayList<String> temp = player.toCSVEntry();

			String[] csvEntries = new String[temp.size()];

			for (int i = 0; i < temp.size(); i++) {
				csvEntries[i] = temp.get(i);
			}
			writer.writeNext(csvEntries);
		}
		writer.close();
	}

	private void readCSV() throws IOException {
		CSVReader reader;

		try {
			reader = new CSVReader(new FileReader(new File("Bomberman.CSV")));
		} catch (FileNotFoundException e) {
			InputStream in = Bomberman.class.getResourceAsStream("/database.csv");
			reader = new CSVReader(new InputStreamReader(in));
		}

		String[] nextLine;
		int index = 0;
		nextLine = reader.readNext();

		while (nextLine != null) {
			ArrayList<String> data = new ArrayList<>();
			Collections.addAll(data, nextLine);
			players.add(new Player(data.get(0), data.get(1), data.get(2), parseInt(data.get(3)), parseInt(data.get(4)), parseInt(data.get(5))));

			while (data.contains("SavedGame")) {
				data = new ArrayList<>(data.subList(data.indexOf("SavedGame") + 1, data.size()));
				SavedGame savedGame;

				if (data.contains("SavedGame"))
					savedGame = generateSavedGame(new ArrayList<>(data.subList(0, data.indexOf("SavedGame"))));
				else
					savedGame = generateSavedGame(data);

				players.get(index).addSavedGame(savedGame);
			}
			index++;
			nextLine = reader.readNext();
		}
		reader.close();
	}

	private SavedGame generateSavedGame(ArrayList<String> data) {
		String gameName, gameDate;
		GameContext gameContext = generateGameContext(new ArrayList<>(data.subList(data.indexOf("GameContext") + 1, data.size())));

		gameName = data.get(0);
		gameDate = data.get(1);

		return new SavedGame(gameName, gameDate, gameContext);
	}

	private GameContext generateGameContext(ArrayList<String> context) {
		int gameTime, livesLeft, score, level;

		GridMap gridMap = generateGridMap(new ArrayList<>(context.subList(context.indexOf("GridMap") + 1, context.size())));

		gameTime = parseInt(context.get(0));
		livesLeft = parseInt(context.get(1));
		score = parseInt(context.get(2));
		level = parseInt(context.get(3));

		return new GameContext(gameTime, livesLeft, score, level, gridMap);
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
		int xPosition, yPosition, direction;
		String type;
		List<Enemy> enemies = new ArrayList<>();

		while (data.size() != 0) {
			type = data.remove(0);
			xPosition = parseInt(data.remove(0));
			yPosition = parseInt(data.remove(0));
			final Position position = create(xPosition, yPosition);
			direction = parseInt(data.remove(0));

			switch (type) {
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Balloom":
					enemies.add(new Balloom(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Oneal":
					enemies.add(new Oneal(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Doll":
					enemies.add(new Doll(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Minvo":
					enemies.add(new Minvo(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Kondoria":
					enemies.add(new Kondoria(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Ovapi":
					enemies.add(new Ovapi(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Pass":
					enemies.add(new Pass(position, direction));
					break;
				case "class gameplayModel.GridObjects.AnimatedObjects.Enemies.Pontan":
					enemies.add(new Pontan(position, direction));
					break;
			}
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
		Collections.sort(topPlayers);
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
