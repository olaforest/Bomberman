package gameplayModel;

import gameplayController.CollisionDetector;
import gameplayModel.gridObjects.*;
import gameplayModel.gridObjects.animatedObjects.*;
import gameplayView.AnimationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import utilities.Position;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static gameplayController.CollisionDetector.*;
import static gameplayController.GameplayController.TIMEOUT;
import static gameplayModel.gridObjects.HiddenObject.generateIndex;
import static gameplayModel.gridObjects.PowerUp.createPowerUp;
import static gameplayModel.gridObjects.animatedObjects.Enemy.createEnemy;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.Pontan;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.values;
import static gameplayView.AnimationType.*;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.awt.event.KeyEvent.*;
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
	private static final BiPredicate<Integer, Integer> BRICK_POS = CONCRETE_POS.or(START_POS).negate();

	private final Bomberman bomberman;
	private final List<Brick> bricks;
	private final List<Bomb> bombs;
	private final List<Enemy> enemies;
	private final Exitway exitway;
	private PowerUp powerUp;

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

	void updateBombermanStatus(ArrayDeque<Integer> activeDirectionKeys) {
		if (bomberman.isDead()) {
			bomberman.cycleAnimation();
		} else {
			if (activeDirectionKeys.size() != 0) {
				switch (activeDirectionKeys.getFirst()) {
					case VK_UP:
						updateBombermanStatus(Up, CollisionDetector::checkUpCollision, bomberman::moveUp);
						break;
					case VK_DOWN:
						updateBombermanStatus(Down, CollisionDetector::checkDownCollision, bomberman::moveDown);
						break;
					case VK_LEFT:
						updateBombermanStatus(Left, CollisionDetector::checkLeftCollision, bomberman::moveLeft);
						break;
					case VK_RIGHT:
						updateBombermanStatus(Right, CollisionDetector::checkRightCollision, bomberman::moveRight);
						break;
				}
			}
		}
	}

	private void updateBombermanStatus(AnimationType animationType, BiPredicate<GridObject, GridObject> collisionCheck, Runnable move) {
		if (bomberman.getCurrentAnimationType() != animationType)
			bomberman.setCurrentAnimation(animationType);
		else
			bomberman.cycleAnimation();
		if (canMove(collisionCheck)) move.run();
	}

	private Boolean canMove(BiPredicate<GridObject, GridObject> collisionCheck) {
		return bombs.stream()
				.filter(bomb -> collisionCheck.test(bomberman, bomb) && !bomberman.canBombpass())
				.findFirst()
				.map(bomb -> false)
				.orElse(true);
	}

	void removePowerUps() {
		if (powerUp == null) bomberman.removeCurrentLevelPowerUp();
		bomberman.removeNonPermanentPowerUps();
	}

	void updateEnemiesAnim() {
		for (int i = 0; i < enemies.size(); ) {
			if (!enemies.get(i).isDead()) {

				if (enemies.get(i).getDirection() == 1 || enemies.get(i).getDirection() == 2) {
					if (enemies.get(i).getCurrentAnimationType() != Left)
						enemies.get(i).setCurrentAnimation(Left);
					enemies.get(i).cycleAnimation();
				} else {
					if (enemies.get(i).getCurrentAnimationType() != Right)
						enemies.get(i).setCurrentAnimation(Right);
					enemies.get(i).cycleAnimation();
				}
				i++;
			} else if (enemies.get(i).isObsolete()) {
				enemies.remove(i);
			} else {
				enemies.get(i).cycleAnimation();
				i++;
			}
		}
	}

	void updateBombsStatus() {
		for (int i = 0; i < bombs.size(); ) {
			if (bombs.get(i).isObsolete()) {
				bombs.remove(i);
			} else {
				bombs.get(i).cycleAnimations();

				if (!bomberman.canDetonateBombs() || bombs.get(i).getTimer() <= TIMEOUT * 2)
					bombs.get(i).decreaseTimer();
				i++;
			}
		}

		final List<Bomb> unexplodedBombs = bombs.stream()
				.filter(bomb -> !bomb.isDead())
				.collect(toList());

		for (int i = 0; i < unexplodedBombs.size(); ) {
			if (unexplodedBombs.get(i).isDead()) {
				unexplodedBombs.remove(i);
				bomberman.increaseBombsLeft();
			} else i++;
		}
	}

	void updateBricksStatus() {
		for (int i = 0; i < bricks.size(); ) {
			if (bricks.get(i).isObsolete()) {
				bricks.remove(i);
			} else if (bricks.get(i).isDead()) {
				bricks.get(i).cycleAnimation();
				i++;
			} else {
				i++;
			}
		}
	}

	void destroyObjectInExplodedBombsRange(boolean isBonusLevel, EnemyType hardestEnemyType) {
		for (Bomb bomb : bombs) {
			if (bomb.getTimer() == TIMEOUT) {

				List<AnimatedObject> destBricks = checkExplBricks(bricks, bombs, bomb);

				destBricks.stream()
						.filter(Objects::nonNull)
						.forEach(destBrick -> {

							bricks.stream()
									.filter(brick -> destBrick.isSamePosition(brick) && !brick.isDead())
									.forEach(AnimatedObject::triggerDeath);

							bombs.stream()
									.filter(bomb1 -> destBrick.isSamePosition(bomb1) && !bomb1.isDead())
									.forEach(bomb1 -> bomb1.setTimer(TIMEOUT * 2));
						});

				ArrayList<Enemy> destEnemies = checkExplEnemies(enemies, bomb);

				int pointsMultiplier = destEnemies.size();

				for (Enemy enemy : destEnemies) {
					if (enemy != null) {
						for (Enemy enemy1 : enemies) {
							if ((enemy.getPosition().getX() == enemy1.getPosition().getX()) && (enemy.getPosition().getY() == enemy1.getPosition().getY()) && !enemy1.isDead()) {
								enemy1.triggerDeath();
//								gameContext.increaseScore(enemy1.getPoints() * ((int) Math.pow(2, pointsMultiplier)));
							}
						}
					}
					pointsMultiplier--;
				}

				if (checkExplGridObject(bomb, bomberman) && !bomberman.canFlamepass() && !bomberman.isInvincible() && !isBonusLevel && !bomberman.isDead())
					bomberman.triggerDeath();

				if (exitway != null && checkExplGridObject(bomb, exitway)) {
					spawnEightHarderEnemies(exitway, hardestEnemyType);
				}

				if (powerUp != null && checkExplGridObject(bomb, powerUp)) {
					spawnEightHarderEnemies(powerUp, hardestEnemyType);
				}
			}
		}
	}

	void checkCollisionBtwBombermanAndBricks() {
		if (!bomberman.canWallpass()) {
			for (Brick brick : bricks) {
				if (checkRightCollision(bomberman, brick))
					bomberman.setXPosition(brick.getPosition().getX() - EFFECTIVE_PIXEL_DIM);
				if (checkLeftCollision(bomberman, brick))
					bomberman.setXPosition(brick.getPosition().getX() + EFFECTIVE_PIXEL_DIM);
				if (checkDownCollision(bomberman, brick))
					bomberman.setYPosition(brick.getPosition().getY() - EFFECTIVE_PIXEL_DIM);
				if (CollisionDetector.checkUpCollision(bomberman, brick))
					bomberman.setYPosition(brick.getPosition().getY() + EFFECTIVE_PIXEL_DIM);
			}
		}
	}

	void checkCollisionBtwBombermanAndEnemies() {
		if (!bomberman.isInvincible()) {
			for (Enemy enemy : enemies) {
				boolean isHorzCollision = checkRightCollision(bomberman, enemy) || checkLeftCollision(bomberman, enemy);
				boolean isVertCollision = checkDownCollision(bomberman, enemy) || CollisionDetector.checkUpCollision(bomberman, enemy);

				if (isHorzCollision || isVertCollision)
					bomberman.triggerDeath();
			}
		}
	}

	boolean checkCollisionBtwBombermanAndExitway() {
		return checkExactCollision(bomberman, exitway) && enemies.size() == 0;
	}

	void checkCollisionBtwBombermanAndPowerUp() {
		if (powerUp != null && checkExactCollision(bomberman, powerUp)) {
			bomberman.addPowerUp(powerUp);
			powerUp = null;
		}
	}

	void addBomb() {
		int xPosition, yPosition;

		if ((bomberman.getPosition().getX() % EFFECTIVE_PIXEL_DIM) < (EFFECTIVE_PIXEL_DIM / 2))
			xPosition = EFFECTIVE_PIXEL_DIM * (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIM);
		else
			xPosition = EFFECTIVE_PIXEL_DIM * (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIM + 1);

		if ((bomberman.getPosition().getY() % EFFECTIVE_PIXEL_DIM) < (EFFECTIVE_PIXEL_DIM / 2))
			yPosition = EFFECTIVE_PIXEL_DIM * (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIM);
		else
			yPosition = EFFECTIVE_PIXEL_DIM * (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIM + 1);

		boolean canAddBomb = true;

		if (bombs.size() != 0) {
			int i = 0;

			while (canAddBomb && i < bombs.size()) {
				if (bombs.get(i).getPosition().getX() == xPosition && bombs.get(i).getPosition().getY() == yPosition)
					canAddBomb = false;
				i++;
			}
		}

		if (canAddBomb && bomberman.getBombsLeft() != 0) {
			Bomb tempBomb = new Bomb(create(xPosition, yPosition));
			bombs.add(tempBomb);
			bomberman.decreaseBombsLeft();
		}
	}

	Position getViewPortPosition() {
		return bomberman.getPosition();
	}

	void detonateBomb() {
		if (bomberman.canDetonateBombs() && bombs.size() != 0)
			bombs.get(0).setTimer(TIMEOUT * 2);
	}

	boolean isGameOver() {
		return bomberman.isObsolete();
	}

	private void spawnEightHarderEnemies(GridObject gridObj, EnemyType hardestEnemyType) {
		enemies.clear();
		final EnemyType type = hardestEnemyType;
		spawnEightEnemies(type == Pontan ? Pontan : values()[type.ordinal() + 1], gridObj.getX(), gridObj.getY());
	}

	private void spawnEightEnemies(EnemyType type, int xPosition, int yPosition) {
//		IntStream.range(0, 8)
//				.forEach(i -> enemies.add(createEnemy(type, xPosition, yPosition)));
	}

	private static List<Brick> generateBricks() {
		return IntStream.range(1, MAPHEIGHT - 1)
				.mapToObj(GridMap::generateBricksInRow)
				.flatMap(List::stream)
				.collect(toList());
	}

	private static List<Brick> generateBricksInRow(int rowNumber) {
		return IntStream.range(1, MAPWIDTH - 1)
				.filter(x -> BRICK_POS.test(x, rowNumber))
				.filter(x -> random() < BRICK_FACTOR)
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

	private List<Enemy> generateEnemiesOfType(Entry<EnemyType, Integer> type) {
		return IntStream.range(0, type.getValue())
				.mapToObj(i -> findNewEnemyLocation())
				.map(position -> createEnemy(type.getKey(), position))
				.collect(toList());
	}

	private Position findNewEnemyLocation() {
		return IntStream.iterate(0, i -> ++i)
				.limit(1000)
				.mapToObj(i -> generateRandomLocation())
				.filter(this::validPosition)
				.findFirst()
				.orElseThrow(RuntimeException::new);
	}

	private Boolean validPosition(Position position) {
		return bricks.stream()
				.filter(brick -> !BRICK_POS.test(position.getModX(), position.getModY()))
				.filter(brick -> brick.isSamePosition(position))
				.findFirst()
				.map(brick -> false)
				.orElse(true);
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
