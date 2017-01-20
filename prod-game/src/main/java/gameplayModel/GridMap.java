package gameplayModel;

import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.Exitway;
import gameplayModel.gridObjects.PowerUp;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import gameplayModel.gridObjects.animatedObjects.Brick;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static gameplayController.GameplayController.TIMEOUT;
import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static gameplayModel.gridObjects.HiddenObject.generateIndex;
import static gameplayModel.gridObjects.factories.EnemyFactory.createEnemy;
import static gameplayModel.gridObjects.factories.PowerUpFactory.createPowerUp;
import static utilities.Position.create;
import static utilities.Position.modulus;

public class GridMap {
	public static final int MAPWIDTH = 31;
	public static final int MAPHEIGHT = 13;
	static final int SPAWN_TIMEOUT = 10 * 1000;
	private static final double BRICK_FACTOR = 0.225;
	private static final int WIDTH = EFFECTIVE_PIXEL_DIMENSION;
	private static final int HEIGHT = EFFECTIVE_PIXEL_DIMENSION;

	private int spawnTimer;

	@Getter private Level level;
	@Getter private List<Concrete> concreteLayout;
	@Getter private List<Brick> bricks;
	@Getter private List<Bomb> bombs;
	@Getter private List<Enemy> enemies;
	@Getter private Exitway exitway;
	@Getter private PowerUp powerUp;
	@Getter private Bomberman bomberman;

	public GridMap(Level level) {
		this.level = level;
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
				.peek(i -> concreteLayout.add(new Concrete(modulus(i, 0))))
				.forEach(i -> concreteLayout.add(new Concrete(modulus(i, (MAPHEIGHT - 1)))));
	}

	private void addVerticalConcreteBoundary() {
		IntStream.range(1, MAPHEIGHT - 1)
				.peek(i -> concreteLayout.add(new Concrete(modulus(0, i))))
				.forEach(i -> concreteLayout.add(new Concrete(modulus((MAPWIDTH - 1), i))));
	}

	private void addInnerConcreteBlocks() {
		for (int i = 2; i < MAPWIDTH - 2; i += 2)
			addInnerConcreteBlockRow(i);
	}

	private void addInnerConcreteBlockRow(int i) {
		for (int j = 2; j < MAPHEIGHT - 2; j += 2)
			concreteLayout.add(new Concrete(modulus(i, j)));
	}

	private void populateMap() {
		this.bomberman = new Bomberman(modulus(1, 1));
		if (level.isBonusLevel())
			populateSpecialMap();
		else
			populateStandardMap();
	}

	private void populateStandardMap() {
		distributeBricks();
		addExitway();
		level.getPowerUpType().ifPresent(this::addPowerup);
		generateEnemies(level.getEnemiesCount());
	}

	private void distributeBricks() {
		addBricksToOddRows();
		addBricksToEvenRows();
	}

	private void addBricksToOddRows() {
		for (int i = 1; i < MAPHEIGHT; i += 2)
			for (int j = 1; j < MAPWIDTH - 1; j++)
				if (Math.random() < BRICK_FACTOR && !(i == 1 && j == 1) && !(i == 1 && j == 2))
					bricks.add(new Brick(modulus(j, i)));
	}

	private void addBricksToEvenRows() {
		for (int i = 2; i < MAPHEIGHT - 1; i += 2)
			for (int j = 1; j < MAPWIDTH - 1; j += 2)
				if (Math.random() < BRICK_FACTOR && !(i == 2 && j == 1))
					bricks.add(new Brick(modulus(j, i)));
	}

	private void addExitway() {
		int brickIndex = generateIndex(bricks.size());
		exitway = new Exitway(create(bricks.get(brickIndex).getPosition().getX(), bricks.get(brickIndex).getPosition().getY()), brickIndex);
	}

	private void addPowerup(int type) {
		int brickUpIndex;
		do {
			brickUpIndex = generateIndex(bricks.size());
		} while (brickUpIndex == exitway.getBrickIndex());
		powerUp = createPowerUp(type, bricks.get(brickUpIndex).getPosition().getX(), bricks.get(brickUpIndex).getPosition().getY());
	}

	public void generateEnemies(List<Integer> enemyCounts) {
		IntStream.range(0, enemyCounts.size() - 1)
				.forEach(i -> addEnemiesFromType(i, enemyCounts.get(i)));
	}

	private void addEnemiesFromType(int type, int quantity) {
		IntStream.range(0, quantity)
				.forEach(i -> addEnemy(type));
	}

	private void addEnemy(int type) {
		final int[] position = findNewEnemyLocation();
		enemies.add(createEnemy(type, position[0] * WIDTH, position[1] * HEIGHT));
	}

	private int[] findNewEnemyLocation() {
		int[] location;
		Optional<Brick> matchedBrick;
		do {
			location = generateRandomLocation();
			matchedBrick = findMatchingBrick(location);
		} while (matchedBrick.isPresent());
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

	private Optional<Brick> findMatchingBrick(int[] location) {
		return bricks.stream()
				.filter(brick -> brick.isSamePosition(location[0] * WIDTH, location[1] * HEIGHT))
				.findFirst();
	}

	private void populateSpecialMap() {
		spawnMoreEnemies();
	}

	private void spawnMoreEnemies() {
		int type = level.getHardestEnemyType();
		addEnemiesFromType(type, 8);
	}

	public void decreaseSpawnTimer() {
		spawnTimer -= TIMEOUT;
		if (spawnTimer <= 0) {
			spawnMoreEnemies();
			spawnTimer = SPAWN_TIMEOUT;
		}
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();

		entryList.add(Integer.toString(spawnTimer));
		entryList.add("Bricks");
		for (Brick brick : bricks)
			entryList.addAll(brick.toCSVEntry());

		entryList.add("Bombs");
		entryList.add(Integer.toString(Bomb.getRange()));
		bombs.stream()
				.filter(bomb -> !bomb.isDead())
				.forEach(bomb -> entryList.addAll(bomb.toCSVEntry()));

		entryList.add("Enemies");
		for (Enemy enemy : enemies)
			entryList.addAll(enemy.toCSVEntry());

		entryList.add("Exitway");
		entryList.addAll(exitway.toCSVEntry());

		entryList.add("PowerUp");
		entryList.addAll(powerUp.toCSVEntry());

		entryList.add("Bomberman");
		entryList.addAll(bomberman.toCSVEntry());

		return entryList;
	}
}
