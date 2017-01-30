package gameplayController;

import gameplayModel.GameContext;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.LevelManager;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.Exitway;
import gameplayModel.gridObjects.PowerUp;
import gameplayModel.gridObjects.animatedObjects.*;
import gameplayView.GameStatusPanel;
import gameplayView.GameplayPanel;
import lombok.Getter;
import menuController.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static gameplayModel.gridObjects.animatedObjects.Enemy.createEnemy;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.Pontan;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.values;
import static gameplayView.AnimationType.*;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIMENSION;
import static utilities.Position.create;

public class GameplayController implements ActionListener {

	public static final int TIMEOUT = 50;
	public static final int VIEW_PORT_WIDTH = 16 * EFFECTIVE_PIXEL_DIMENSION;

	private boolean placeBomb;

	private final MenuController menuCtrl;
	@Getter
	private final GameContext gameContext;
	private GridMap gridMap;
	private ArtificialIntelligence AI;
	private CollisionDetector colDetect;

	private final GameplayKeyListener keyListener = new GameplayKeyListener();
	private GameplayPanel gamePanel;
	private GameStatusPanel gameStatusPanel;

	private ArrayDeque<Integer> activeDirectionKeys;
	private List<Concrete> concreteLayout;
	private List<Brick> bricks;
	private List<Bomb> bombs;
	private List<Bomb> unexplodedBombs;
	private List<Enemy> enemies;
	private Bomberman bomberman;
	private Exitway exitway;
	private PowerUp powerup;

	private JFrame gameFrame;
	private final Timer timer;
	private LevelManager levelManager;

	public GameplayController(MenuController menuCtrl) {
		this.menuCtrl = menuCtrl;
		gameContext = new GameContext();
		initializeReferences();
		setupGameFrame(true);
		timer = new Timer(TIMEOUT, this);
		timer.start();
	}

	public GameplayController(MenuController menuCtrl, GameContext gameContext, LevelManager levelManager) {
		this.menuCtrl = menuCtrl;
		this.gameContext = gameContext;
		this.levelManager = levelManager;
		initializeReferences();
		setupGameFrame(false);
		timer = new Timer(TIMEOUT, this);
	}

	public void actionPerformed(ActionEvent event) {

		updateBombermanStatus();
		AI.updateEnemiesPosition();
		updateEnemiesAnim();
		updateBombsStatus();
		updateBricksStatus();

		destroyObjectInExplodedBombsRange();

		if (!bomberman.canWallpass()) checkCollisionBtwBombermanAndBricks();

		if (!bomberman.isInvincible() && !levelManager.isBonusLevel())
			checkCollisionBtwBombermanAndEnemies();

		checkCollisionBtwBombermanAndExitway();
		checkCollisionBtwBombermanAndPowerUp();

		if (placeBomb) addBomb();

		if (levelManager.isBonusLevel()) gridMap.decreaseSpawnTimer();

		if (gameContext.getGameTime() <= 0 && !gameContext.isEndGameEnemiesSpawned()) {
			gameContext.setEndGameEnemiesSpawned(true);
			gridMap.generateEnemiesOfType(Pontan);
		}

		updateViewport();
		gameStatusPanel.updateGameStatus();
		gameContext.decreaseGameTime();
	}

	public void resumeGame() {
		gameFrame.setVisible(true);
		timer.start();
	}

	private void initializeReferences() {

		gridMap = gameContext.getGridMap();

		activeDirectionKeys = new ArrayDeque<>();
		concreteLayout = gameContext.getGridMap().getConcreteLayout();
		bricks = gameContext.getGridMap().getBricks();
		bombs = gameContext.getGridMap().getBombs();
		unexplodedBombs = new ArrayList<>();
		enemies = gameContext.getGridMap().getEnemies();
		bomberman = gameContext.getGridMap().getBomberman();
		exitway = gameContext.getGridMap().getExitway();
		powerup = gameContext.getGridMap().getPowerUp();

		colDetect = new CollisionDetector(gameContext);
		AI = new ArtificialIntelligence(bomberman, enemies, bricks, bombs, colDetect);
	}

	private class GameplayKeyListener implements KeyListener {

		public void keyPressed(KeyEvent event) {

			if (!activeDirectionKeys.contains(event.getKeyCode())) {

				switch (event.getKeyCode()) {
					case KeyEvent.VK_UP:
						activeDirectionKeys.addFirst(KeyEvent.VK_UP);
						break;
					case KeyEvent.VK_DOWN:
						activeDirectionKeys.addFirst(KeyEvent.VK_DOWN);
						break;
					case KeyEvent.VK_LEFT:
						activeDirectionKeys.addFirst(KeyEvent.VK_LEFT);
						break;
					case KeyEvent.VK_RIGHT:
						activeDirectionKeys.addFirst(KeyEvent.VK_RIGHT);
						break;
					case KeyEvent.VK_ENTER:
						if (timer.isRunning()) {
							timer.stop();
							gameFrame.setVisible(false);
							menuCtrl.pause();
						}
						break;
					case KeyEvent.VK_X:
						placeBomb = true;
						break;
					case KeyEvent.VK_Z:
						if (bomberman.canDetonateBombs() && bombs.size() != 0)
							bombs.get(0).setTimer(TIMEOUT * 2);
						break;
				}
			}
		}

		public void keyReleased(KeyEvent event) {

			switch (event.getKeyCode()) {
				case KeyEvent.VK_UP:
					activeDirectionKeys.remove(KeyEvent.VK_UP);
					break;
				case KeyEvent.VK_DOWN:
					activeDirectionKeys.remove(KeyEvent.VK_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					activeDirectionKeys.remove(KeyEvent.VK_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					activeDirectionKeys.remove(KeyEvent.VK_RIGHT);
					break;
				case KeyEvent.VK_ENTER:
					break;
				case KeyEvent.VK_X:
					placeBomb = false;
					break;
				case KeyEvent.VK_Z:
					break;
			}
		}

		public void keyTyped(KeyEvent event) {
		}
	}

	private void updateBombermanStatus() {

		if (!bomberman.isDead()) {

			if (activeDirectionKeys.size() != 0) {

				switch (activeDirectionKeys.getFirst()) {
					case KeyEvent.VK_UP:
						if (bomberman.getCurrentAnimationType() != Up)
							bomberman.setCurrentAnimation(Up);
						else
							bomberman.cycleAnimation();

						boolean canMoveUp = true;

						for (Bomb bomb : bombs) {
							if (colDetect.checkUpCollision(bomberman, bomb) && !bomberman.canBombpass())
								canMoveUp = false;
						}

						if (canMoveUp) bomberman.setYPosition(bomberman.getPosition().getY() - bomberman.getSpeed());

						break;
					case KeyEvent.VK_DOWN:
						if (bomberman.getCurrentAnimationType() != Down)
							bomberman.setCurrentAnimation(Down);
						else
							bomberman.cycleAnimation();

						boolean canMoveDown = true;

						for (Bomb bomb : bombs) {
							if (colDetect.checkDownCollision(bomberman, bomb) && !bomberman.canBombpass())
								canMoveDown = false;
						}

						if (canMoveDown) bomberman.setYPosition(bomberman.getPosition().getY() + bomberman.getSpeed());

						break;
					case KeyEvent.VK_LEFT:
						if (bomberman.getCurrentAnimationType() != Left)
							bomberman.setCurrentAnimation(Left);
						else
							bomberman.cycleAnimation();

						boolean canMoveLeft = true;

						for (Bomb bomb : bombs) {
							if (colDetect.checkLeftCollision(bomberman, bomb) && !bomberman.canBombpass())
								canMoveLeft = false;
						}

						if (canMoveLeft) bomberman.setXPosition(bomberman.getPosition().getX() - bomberman.getSpeed());

						break;
					case KeyEvent.VK_RIGHT:
						if (bomberman.getCurrentAnimationType() != Right)
							bomberman.setCurrentAnimation(Right);
						else
							bomberman.cycleAnimation();

						boolean canMoveRight = true;

						for (Bomb bomb : bombs) {
							if (colDetect.checkRightCollision(bomberman, bomb) && !bomberman.canBombpass())
								canMoveRight = false;
						}

						if (canMoveRight) bomberman.setXPosition(bomberman.getPosition().getX() + bomberman.getSpeed());

						break;
				}
			}

		} else if (bomberman.isObsolete()) {

			timer.stop();

			if (gameContext.getLivesLeft() > 0) {

				List<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();

				// The power Up of the current map is removed from bomberman if he already picked it Up before dying.
				if (powerup == null)
					powerUpsAcquired.remove(powerUpsAcquired.size() - 1);

				// Removes the non permanent power ups acquired
				for (int i = 0; i < powerUpsAcquired.size(); ) {
					if (!powerUpsAcquired.get(i).isPermanent())
						powerUpsAcquired.remove(i);
					else
						i++;
				}

				gameContext.decreaseLivesLeft();
				gameContext.initializeGameTime();
				gameContext.restartMap();
				initializeReferences();

				bomberman.setPowerUpsAcquired(powerUpsAcquired);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				timer.start();
			} else {
				gameFrame.setVisible(false);
				menuCtrl.gameOver();
			}
		} else {
			bomberman.cycleAnimation();
		}
	}

	private void updateEnemiesAnim() {
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

	private void updateBombsStatus() {
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

		for (int i = 0; i < unexplodedBombs.size(); ) {
			if (unexplodedBombs.get(i).isDead()) {
				unexplodedBombs.remove(i);
				bomberman.increaseBombsLeft();
			} else i++;
		}
	}

	private void destroyObjectInExplodedBombsRange() {

		for (Bomb bomb : bombs) {

			if (bomb.getTimer() == TIMEOUT) {

				ArrayList<AnimatedObject> destBricks = colDetect.checkExplBricks(bomb);

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

				ArrayList<Enemy> destEnemies = colDetect.checkExplEnemies(bomb);

				int pointsMultiplier = destEnemies.size();

				for (Enemy enemy : destEnemies) {
					if (enemy != null) {
						for (Enemy enemy1 : enemies) {
							if ((enemy.getPosition().getX() == enemy1.getPosition().getX()) && (enemy.getPosition().getY() == enemy1.getPosition().getY()) && !enemy1.isDead()) {
								enemy1.triggerDeath();
								gameContext.increaseScore(enemy1.getPoints() * ((int) Math.pow(2, pointsMultiplier)));
							}
						}
					}
					pointsMultiplier--;
				}

				if (colDetect.checkExplGridObject(bomb, bomberman) && !bomberman.canFlamepass() && !bomberman.isInvincible() && !levelManager.isBonusLevel() && !bomberman.isDead())
					bomberman.triggerDeath();

				if (exitway != null && colDetect.checkExplGridObject(bomb, exitway)) {
					spawnEightHarderEnemies(exitway);
				}

				if (powerup != null && colDetect.checkExplGridObject(bomb, powerup)) {
					spawnEightHarderEnemies(powerup);
				}
			}
		}
	}

	private void checkCollisionBtwBombermanAndBricks() {
		for (Brick brick : bricks) {
			if (colDetect.checkRightCollision(bomberman, brick))
				bomberman.setXPosition(brick.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION);
			if (colDetect.checkLeftCollision(bomberman, brick))
				bomberman.setXPosition(brick.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION);
			if (colDetect.checkDownCollision(bomberman, brick))
				bomberman.setYPosition(brick.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION);
			if (colDetect.checkUpCollision(bomberman, brick))
				bomberman.setYPosition(brick.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION);
		}
	}

	private void checkCollisionBtwBombermanAndEnemies() {
		for (Enemy enemy : enemies) {
			boolean isHorzCollision = colDetect.checkRightCollision(bomberman, enemy) || colDetect.checkLeftCollision(bomberman, enemy);
			boolean isVertCollision = colDetect.checkDownCollision(bomberman, enemy) || colDetect.checkUpCollision(bomberman, enemy);

			if (isHorzCollision || isVertCollision)
				bomberman.triggerDeath();
		}
	}

	private void checkCollisionBtwBombermanAndExitway() {

		if (colDetect.checkExactCollision(bomberman, exitway) && enemies.size() == 0) {

			List<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();
			levelManager.increaseLevel();
			gameContext.initializeGameTime();
			gameContext.restartMap();
			initializeReferences();
			menuCtrl.getCurrentPlayer().increaseLevelUnlocked();
			bomberman.setPowerUpsAcquired(powerUpsAcquired);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			gameContext.increaseLivesLeft();
			timer.start();
		}
	}

	private void checkCollisionBtwBombermanAndPowerUp() {
		if (powerup != null && colDetect.checkExactCollision(bomberman, powerup)) {
			bomberman.addPowerUp(powerup);
			powerup = null;
		}
	}

	private void updateBricksStatus() {
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

	private void addBomb() {
		int xPosition, yPosition;

		if ((bomberman.getPosition().getX() % EFFECTIVE_PIXEL_DIMENSION) < (EFFECTIVE_PIXEL_DIMENSION / 2))
			xPosition = EFFECTIVE_PIXEL_DIMENSION * (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIMENSION);
		else
			xPosition = EFFECTIVE_PIXEL_DIMENSION * (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIMENSION + 1);

		if ((bomberman.getPosition().getY() % EFFECTIVE_PIXEL_DIMENSION) < (EFFECTIVE_PIXEL_DIMENSION / 2))
			yPosition = EFFECTIVE_PIXEL_DIMENSION * (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIMENSION);
		else
			yPosition = EFFECTIVE_PIXEL_DIMENSION * (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIMENSION + 1);

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
			unexplodedBombs.add(tempBomb);
			bomberman.decreaseBombsLeft();
		}
	}

	private void spawnEightHarderEnemies(GridObject gridObj) {
		enemies.clear();
		final EnemyType type = levelManager.getHardestEnemyType();
		spawnEightEnemies(type == Pontan ? Pontan : values()[type.ordinal() + 1], gridObj.getX(), gridObj.getY());
	}

	private void spawnEightEnemies(EnemyType type, int xPosition, int yPosition) {
		IntStream.range(0, 8)
				.forEach(i -> enemies.add(createEnemy(type, xPosition, yPosition)));
	}

	private void setupGameFrame(boolean isVisible) {

		gamePanel = new GameplayPanel(keyListener) {

			public void paintComponent(Graphics page) {

				super.paintComponent(page);
				Graphics2D g2d = (Graphics2D) page;

				if (exitway != null)
					g2d.drawImage(Exitway.getImage(), exitway.getPosition().getX(), exitway.getPosition().getY(), gamePanel);

				if (powerup != null)
					g2d.drawImage(powerup.getImage(), powerup.getPosition().getX(), powerup.getPosition().getY(), gamePanel);

				for (Bomb bomb : bombs) {
					for (int i = 0; i < bomb.getCurrentAnimations().size(); i++)
						g2d.drawImage(bomb.getCurrentAnimations().get(i).getCurrentFrame(),
								bomb.getPosition().getX() + bomb.getAnimXOffset().get(i) * EFFECTIVE_PIXEL_DIMENSION,
								bomb.getPosition().getY() + bomb.getAnimYOffset().get(i) * EFFECTIVE_PIXEL_DIMENSION, gamePanel);
				}

				for (Brick brick : bricks)
					g2d.drawImage(brick.getCurrentAnimation().getCurrentFrame(), brick.getPosition().getX(), brick.getPosition().getY(), gamePanel);

				for (Enemy enemy : enemies)
					g2d.drawImage(enemy.getCurrentAnimation().getCurrentFrame(), enemy.getPosition().getX(), enemy.getPosition().getY(), gamePanel);

				g2d.drawImage(bomberman.getCurrentAnimation().getCurrentFrame(), bomberman.getPosition().getX(), bomberman.getPosition().getY(), gamePanel);

				for (Concrete block : concreteLayout)
					g2d.drawImage(Concrete.getImage(), block.getPosition().getX(), block.getPosition().getY(), gamePanel);
			}
		};

		gameStatusPanel = new GameStatusPanel(gameContext);

		final JViewport gameView = new JViewport();
		gameView.setView(gamePanel);

		gameFrame = new JFrame("Bomberman");
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.getContentPane().setLayout(new BorderLayout());
		gameFrame.getContentPane().add(gameStatusPanel, BorderLayout.NORTH);
		gameFrame.getContentPane().add(gameView);
		gameFrame.getContentPane().setPreferredSize(new Dimension(VIEW_PORT_WIDTH, GameplayPanel.HEIGHT + GameStatusPanel.HEADERHEIGHT));
		gameFrame.pack();
		gameFrame.setLocation(400, 200);
		gameFrame.setVisible(isVisible);
	}

	private void updateViewport() {
		if (bomberman.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION / 2 <= VIEW_PORT_WIDTH / 2) {
			gamePanel.setLocation(0, 0);
			gamePanel.repaint();
		} else if (bomberman.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION / 2 >= GameplayPanel.WIDTH - VIEW_PORT_WIDTH / 2) {
			gamePanel.setLocation(VIEW_PORT_WIDTH - GameplayPanel.WIDTH, 0);
			gamePanel.repaint();
		} else {
			gamePanel.setLocation(VIEW_PORT_WIDTH / 2 - bomberman.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION / 2, 0);
			gamePanel.repaint();
		}
	}
}

