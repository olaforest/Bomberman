package gameplayModel;

import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.Exitway;
import gameplayModel.gridObjects.PowerUp;
import gameplayModel.gridObjects.PowerUpType;
import gameplayModel.gridObjects.animatedObjects.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import utilities.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static gameplayModel.gridObjects.HiddenObject.generateIndex;
import static gameplayModel.gridObjects.PowerUp.createPowerUp;
import static gameplayModel.gridObjects.animatedObjects.Enemy.createEnemy;
import static java.lang.Math.random;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static utilities.Position.create;
import static utilities.Position.modulus;

@Getter
@RequiredArgsConstructor
public class GridMap {
	public static final int MAPWIDTH = 31;
	public static final int MAPHEIGHT = 13;
	public static final List<Concrete> CONCRETE_LAYOUT = generateConcreteBlocks();
	private static final double BRICK_FACTOR = 0.225;
	private static final BiPredicate<Integer, Integer> CONCRETE_POS = (x, y) -> x % 2 == 0 && y % 2 == 0;
	private static final BiPredicate<Integer, Integer> START_POS = (x, y) -> (x == 1 && y == 1) || (x == 1 && y == 2) || (x == 2 && y == 1);
	private static final BiPredicate<Integer, Integer> BRICK_POS = CONCRETE_POS.and(START_POS).negate();

	private final Bomberman bomberman;
	private final List<Brick> bricks;
	private final List<Bomb> bombs;
	private final List<Enemy> enemies;
	private final Exitway exitway;
	private final PowerUp powerUp;

	public GridMap(Level level) {
		this.bomberman = new Bomberman(modulus(1, 1));
		this.bricks = generateBricks();
		this.bombs = new ArrayList<>();
		this.enemies = generateEnemies(level.getEnemiesCount());
		this.exitway = addExitway();
		this.powerUp = level.getPowerUpType()
				.map(type -> addPowerup(type, exitway))
				.orElse(null);
	}

	private static List<Brick> generateBricks() {
		return IntStream.range(1, MAPHEIGHT)
				.peek(System.out::println)
				.mapToObj(GridMap::generateBricksInRow)
				.flatMap(List::stream)
				.collect(toList());
	}

	private static List<Brick> generateBricksInRow(int rowNumber) {
		return IntStream.range(0, MAPWIDTH - 1)
				.filter(x -> BRICK_POS.test(x, rowNumber))
				.mapToObj(x -> new Brick(modulus(x, rowNumber)))
				.collect(toList());
	}

	private Exitway addExitway() {
		int brickIndex = generateIndex(bricks.size());
		return new Exitway(create(bricks.get(brickIndex).getPosition()));
	}

	private PowerUp addPowerup(PowerUpType type, Exitway exitway) {
		return IntStream.iterate(0, i -> i++)
				.limit(1000)
				.mapToObj(i -> generateIndex(bricks.size()))
				.filter(index -> !bricks.get(index).isSamePosition(exitway))
				.findFirst()
				.map(index -> createPowerUp(type, bricks.get(index).getPosition()))
				.orElseThrow(RuntimeException::new);
	}

	private List<Enemy> generateEnemies(Map<EnemyType, Integer> enemyCounts) {
		return enemyCounts.entrySet().stream()
				.map(this::generateEnemiesOfType)
				.flatMap(List::stream)
				.collect(toList());
	}

	public List<Enemy> generateEnemiesOfType(Entry<EnemyType, Integer> type) {
		return IntStream.range(0, type.getValue())
				.mapToObj(i -> findNewEnemyLocation())
				.map(position -> createEnemy(type.getKey(), position))
				.collect(toList());
	}

	private Position findNewEnemyLocation() {
		return IntStream.iterate(0, i -> i++)
				.limit(1000)
				.mapToObj(i -> generateRandomLocation())
				.filter(this::validPosition)
				.findFirst()
				.orElseThrow(RuntimeException::new);
	}

	private Boolean validPosition(Position position) {
		return bricks.stream()
				.filter(brick -> BRICK_POS.test(brick.getX(), brick.getY()))
				.filter(brick -> !brick.isSamePosition(position))
				.findFirst()
				.map(brick -> true)
				.orElse(false);
	}

	private static Position generateRandomLocation() {
		return modulus((int) (random() * 29) + 1, (int) (random() * 11) + 1);
	}

	private static List<Concrete> generateConcreteBlocks() {
		return Stream.of(generateHoriConcreteBoundary(), generateVertConcreteBoundary(), generateInnerConcreteBlocks())
				.flatMap(Collection::stream)
				.collect(toList());
	}

	private static List<Concrete> generateHoriConcreteBoundary() {
		return IntStream.range(0, MAPWIDTH)
				.mapToObj(i -> asList(new Concrete(modulus(i, 0)), new Concrete(modulus(i, (MAPHEIGHT - 1)))))
				.flatMap(Collection::stream)
				.collect(toList());
	}

	private static List<Concrete> generateVertConcreteBoundary() {
		return IntStream.range(1, MAPHEIGHT - 1)
				.mapToObj(i -> asList(new Concrete(modulus(0, i)), new Concrete(modulus((MAPWIDTH - 1), i))))
				.flatMap(Collection::stream)
				.collect(toList());
	}

	private static List<Concrete> generateInnerConcreteBlocks() {
		return IntStream.iterate(2, i -> i + 2)
				.limit((MAPWIDTH - 2) / 2)
				.mapToObj(GridMap::addInnerConcreteBlockRow)
				.flatMap(Collection::stream)
				.collect(toList());
	}

	private static List<Concrete> addInnerConcreteBlockRow(int xModulusPosition) {
		return IntStream.iterate(2, i -> i + 2)
				.limit((MAPHEIGHT - 2) / 2)
				.mapToObj(i -> new Concrete(modulus(xModulusPosition, i)))
				.collect(toList());
	}

	public List<String> toCSVEntry() {
		List<String> entryList = new ArrayList<>();
		entryList.add("Bricks");
		for (Brick brick : bricks)
			entryList.addAll(brick.toCSVEntry());

		entryList.add("Bombs");
		entryList.add(Integer.toString(Bomb.getRange()));
		bombs.stream()
				.filter(bomb -> !bomb.isDead())
				.forEach(bomb -> entryList.addAll(bomb.toCSVEntry()));

		entryList.add("EnemyType");
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
