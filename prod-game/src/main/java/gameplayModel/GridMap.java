package gameplayModel;

import gameplayController.GameplayController;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.*;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import gameplayModel.GridObjects.Concrete;
import gameplayModel.GridObjects.Exitway;
import gameplayModel.GridObjects.PowerUp;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static gameplayModel.GridObjects.factories.PowerUpFactory.createNewPowerUp;

public class GridMap {
	public static final int MAPWIDTH = 31;
	public static final int MAPHEIGHT = 13;
	public static final int SPAWN_TIMEOUT = 10 * 1000;
	private static final double BRICK_FACTOR = 0.225;

	private int width = EFFECTIVE_PIXEL_DIMENSION;
	private int height = EFFECTIVE_PIXEL_DIMENSION;
	private int brickExitIndex, spawnTimer;
	private int[] levelSpec;

	@Getter private List<Concrete> concreteLayout;
	@Getter private List<Brick> bricks;
	@Getter private List<Bomb> bombs;
	@Getter private List<Enemy> enemies;
	@Getter private Exitway exitway;
	@Getter private PowerUp powerUp;
	@Getter private Bomberman bomberman;

	public GridMap(int[] levelSpecification) {
		levelSpec = levelSpecification;
		spawnTimer = SPAWN_TIMEOUT;
		concreteLayout = new ArrayList<>();
		bricks = new ArrayList<>();
		bombs = new ArrayList<>();
		enemies = new ArrayList<>();

		generateMap();
		populateMap();
	}

	public GridMap(int spawnTimer, List<Brick> bricks, List<Bomb> bombs, List<Enemy> enemies, Exitway exitway,
				   PowerUp powerup, Bomberman bomberman) {
		this.spawnTimer = spawnTimer;
		concreteLayout = new ArrayList<>();
		this.bricks = bricks;
		this.bombs = bombs;
		this.enemies = enemies;
		this.bomberman = bomberman;
		this.exitway = exitway;
		this.powerUp = powerup;

		generateMap();
	}

	private void generateMap() {
		addHorizontalConcreteBoundary();
		addVerticalConcreteBoundary();
		addInnerConcreteBlocks();
	}

	private void addHorizontalConcreteBoundary() {
		IntStream.range(0, MAPWIDTH)
				.peek(i -> concreteLayout.add(new Concrete(i * width, 0)))
				.forEach(i -> concreteLayout.add(new Concrete(i * width, (MAPHEIGHT - 1) * height)));
	}

	private void addVerticalConcreteBoundary() {
		IntStream.range(1, MAPHEIGHT - 1)
				.peek(i -> concreteLayout.add(new Concrete(0, i * height)))
				.forEach(i -> concreteLayout.add(new Concrete((MAPWIDTH - 1) * width, i * height)));
	}

	private void addInnerConcreteBlocks() {
		for (int i = 2; i < MAPWIDTH - 2; i += 2) addInnerConcreteBlockRow(i);
	}

	private void addInnerConcreteBlockRow(int i) {
		for (int j = 2; j < MAPHEIGHT - 2; j += 2)
			concreteLayout.add(new Concrete(i * width, j * height));
	}

	private void populateMap() {
		this.bomberman = new Bomberman(width, height);
		if (levelSpec[8] != 0)
			populateStandardMap();
		else
			populateSpecialMap();
	}

	private void populateStandardMap() {
		distributeBricks();
		addExitway();
		addPowerup(levelSpec[8]);
		generateEnemies(levelSpec);
	}

	private void distributeBricks() {
		addBricksToOddRows();
		addBricksToEvenRows();
	}

	private void addBricksToOddRows() {
		for (int i = 1; i < MAPHEIGHT; i += 2) {
			for (int j = 1; j < MAPWIDTH - 1; j++) {
				if (Math.random() < BRICK_FACTOR && !(i == 1 && j == 1) && !(i == 1 && j == 2))
					bricks.add(new Brick(width * j, height * i));
			}
		}
	}

	private void addBricksToEvenRows() {
		for (int i = 2; i < MAPHEIGHT - 1; i += 2) {
			for (int j = 1; j < MAPWIDTH - 1; j += 2) {
				if (Math.random() < BRICK_FACTOR && !(i == 2 && j == 1))
					bricks.add(new Brick(width * j, height * i));
			}
		}
	}

	private void addExitway() {
		brickExitIndex = (int) (Math.random() * bricks.size());
		exitway = new Exitway(bricks.get(brickExitIndex).getXPosition(), bricks.get(brickExitIndex).getYPosition());
	}

	private void addPowerup(int type) {
		int brickPowerUpIndex = (int) (Math.random() * bricks.size());

		while (brickPowerUpIndex == brickExitIndex)
			brickPowerUpIndex = (int) (Math.random() * bricks.size());

		powerUp = createNewPowerUp(type, bricks.get(brickPowerUpIndex).getXPosition(), bricks.get(brickPowerUpIndex).getYPosition());
	}

	public void generateEnemies(int[] levelSpec) {
		int[] position;

		for (int i = 0; i < levelSpec[0]; i++) {
			position = findEnemyLocation();
			enemies.add(new Balloom(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[1]; i++) {
			position = findEnemyLocation();
			enemies.add(new Oneal(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[2]; i++) {
			position = findEnemyLocation();
			enemies.add(new Doll(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[3]; i++) {
			position = findEnemyLocation();
			enemies.add(new Minvo(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[4]; i++) {
			position = findEnemyLocation();
			enemies.add(new Kondoria(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[5]; i++) {
			position = findEnemyLocation();
			enemies.add(new Ovapi(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[6]; i++) {
			position = findEnemyLocation();
			enemies.add(new Pass(position[0] * width, position[1] * height));
		}

		for (int i = 0; i < levelSpec[7]; i++) {
			position = findEnemyLocation();
			enemies.add(new Pontan(position[0] * width, position[1] * height));
		}
	}

	private void populateSpecialMap() {
		spawnMoreEnemies(levelSpec);
	}

	private void spawnMoreEnemies(int[] levelSpec) {
		int i = 0;

		while (levelSpec[i] >= 0) {
			i++;
		}

		int[] spec = new int[8];
		spec[i] = 8;
		generateEnemies(spec);
	}

	private int[] findEnemyLocation() {
		int[] location = generateRandomLocation();
		int i = 0;

		while (i < bricks.size()) {
			if (location[0] * width == bricks.get(i).getXPosition() && location[1] * height == bricks.get(i).getYPosition()) {
				location = generateRandomLocation();
				i = 0;
			} else
				i++;
		}
		return location;
	}

	private int[] generateRandomLocation() {
		int[] location = new int[2];
		double row = Math.random();

		if (row >= 0.5) {
			location[0] = ((int) (Math.random() * 29)) + 1;
			location[1] = ((int) (Math.random() * 6)) * 2 + 1;
		} else {
			location[0] = ((int) (Math.random() * 15)) * 2 + 1;
			location[1] = ((int) (Math.random() * 5)) * 2 + 2;
		}
		return location;
	}

	public void decreaseSpawnTimer() {
		spawnTimer -= GameplayController.TIMEOUT;

		if (spawnTimer <= 0) {
			spawnMoreEnemies(levelSpec);
			spawnTimer = SPAWN_TIMEOUT;
		}
	}

	public ArrayList<String> toCSVEntry() {

		ArrayList<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(spawnTimer));
		entryList.add("Bricks");

		for (Brick brick : bricks) {
			for (String token : brick.toCSVEntry())
				entryList.add(token);
		}

		entryList.add("Bombs");
		entryList.add(Integer.toString(Bomb.getRange()));

		for (Bomb bomb : bombs) {
			if (!bomb.isDead()) {
				for (String token : bomb.toCSVEntry())
					entryList.add(token);
			}
		}

		entryList.add("Enemies");

		for (Enemy enemy : enemies) {

			for (String token : enemy.toCSVEntry())
				entryList.add(token);
		}

		entryList.add("Exitway");

		for (String token : exitway.toCSVEntry())
			entryList.add(token);

		entryList.add("PowerUp");

		for (String token : powerUp.toCSVEntry())
			entryList.add(token);

		entryList.add("Bomberman");

		for (String token : bomberman.toCSVEntry())
			entryList.add(token);

		return entryList;
	}
}
