package gameplayController;

import gameplayModel.GameContext;
import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObject;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.*;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import gameplayModel.GridObjects.Concrete;
import gameplayModel.GridObjects.Exitway;
import gameplayModel.GridObjects.PowerUps.PowerUp;
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

public class GameplayController implements ActionListener {

	public static final int TIMEOUT = 50;
	public static final int VIEW_PORT_WIDTH = 16 * GridObject.EFFECTIVE_PIXEL_WIDTH;

	private boolean placeBomb;

	private MenuController menuCtrl;
	@Getter private GameContext gameContext;
	private GridMap gridMap;
	private ArtificialIntelligence AI;
	private CollisionDetector colDetect;

	private GameplayKeyListener keyListener = new GameplayKeyListener();
	private GameplayPanel gamePanel;
	private GameStatusPanel gameStatusPanel;

	private ArrayDeque<Integer> activeDirectionKeys;
	private ArrayList<Concrete> concreteLayout;
	private ArrayList<Brick> bricks;
	private ArrayList<Bomb> bombs;
	private ArrayList<Bomb> unexplodedBombs;
	private ArrayList<Enemy> enemies;
	private Bomberman bomberman;
	private Exitway exitway;
	private PowerUp powerup;

	private JFrame gameFrame;
	private JViewport gameView;
	private Timer timer;

	public GameplayController(MenuController menuCtrl) {
		this.menuCtrl = menuCtrl;
		gameContext = new GameContext(menuCtrl.getSelectedLevel());
		initializeReferences();
		setupGameFrame(true);
		timer = new Timer(TIMEOUT, this);
		timer.start();
	}

	public GameplayController(MenuController menuCtrl, GameContext gameContext) {
		this.menuCtrl = menuCtrl;
		this.gameContext = gameContext;
		initializeReferences();
		setupGameFrame(false);
		timer = new Timer(TIMEOUT, this);
	}

	public void actionPerformed(ActionEvent event){

		updateBombermanStatus();
		AI.updateEnemiesPosition();
		updateEnemiesAnim();
		updateBombsStatus();
		updateBricksStatus();

		destroyObjectInExplodedBombsRange();

		if (!bomberman.canWallpass()) checkCollisionBtwBombermanAndBricks();

		if (!bomberman.isInvincible() && gameContext.getLevelSpecification()[8] != 0)
			checkCollisionBtwBombermanAndEnemies();

		checkCollisionBtwBombermanAndExitway();
		checkCollisionBtwBombermanAndPowerUp();

		if (placeBomb) addBomb();

		if (gameContext.getLevelSpecification()[8] == 0) gridMap.decreaseSpawnTimer();

		if (gameContext.getGameTime() <= 0 && !gameContext.isEndGameEnemiesSpawned()) {
			gameContext.setEndGameEnemiesSpawned(true);
			int[] newEnemies = {0, 0, 0, 0, 0, 0, 0, 12};
			gridMap.generateEnemies(newEnemies);
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
		AI = new ArtificialIntelligence(bomberman,enemies, bricks, bombs,colDetect);
	}

	private class GameplayKeyListener implements KeyListener {

		public void keyPressed(KeyEvent event){

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
						bombs.get(0).setTimer(TIMEOUT*2);
					break;
				}
			}
		}

		public void keyReleased(KeyEvent event){

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

		public void keyTyped(KeyEvent event){}
	}

	private void updateBombermanStatus() {

		if (!bomberman.isDead()) {

			if (activeDirectionKeys.size() != 0) {

		    	switch (activeDirectionKeys.getFirst()) {
				case KeyEvent.VK_UP:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.up.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.up.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();

					boolean canMoveUp = true;

					for (Bomb bomb : bombs) {
						if (colDetect.checkUpCollision(bomberman, bomb) && !bomberman.canBombpass())
							canMoveUp = false;
					}

					if (canMoveUp) bomberman.setYPosition(bomberman.getYPosition() - bomberman.getSpeed());

					break;
				case KeyEvent.VK_DOWN:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.down.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.down.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();

					boolean canMoveDown = true;

					for (Bomb bomb : bombs) {
						if (colDetect.checkDownCollision(bomberman, bomb) && !bomberman.canBombpass())
							canMoveDown = false;
					}

					if (canMoveDown) bomberman.setYPosition(bomberman.getYPosition() + bomberman.getSpeed());

					break;
				case KeyEvent.VK_LEFT:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.left.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.left.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();

					boolean canMoveLeft = true;

					for (Bomb bomb : bombs) {
						if (colDetect.checkLeftCollision(bomberman, bomb) && !bomberman.canBombpass())
							canMoveLeft = false;
					}

					if (canMoveLeft) bomberman.setXPosition(bomberman.getXPosition() - bomberman.getSpeed());

					break;
				case KeyEvent.VK_RIGHT:
					if (bomberman.getAnimationNumber()!= Bomberman.AnimationType.right.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.right.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();

					boolean canMoveRight = true;

					for (Bomb bomb : bombs) {
						if (colDetect.checkRightCollision(bomberman, bomb) && !bomberman.canBombpass())
							canMoveRight = false;
					}

					if (canMoveRight) bomberman.setXPosition(bomberman.getXPosition() + bomberman.getSpeed());

					break;
		    	}
			}

		} else if (bomberman.isObsolete()) {

			timer.stop();

			if (gameContext.getLivesLeft() > 0) {

				ArrayList<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();

				// The power up of the current map is removed from bomberman if he already picked it up before dying.
				if (powerup == null)
					powerUpsAcquired.remove(powerUpsAcquired.size() - 1);

				// Removes the non permanent power ups acquired
				for (int i = 0 ; i < powerUpsAcquired.size() ;) {
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
		for (int i = 0 ; i < enemies.size() ;) {
			if (!enemies.get(i).isDead()) {

				if (enemies.get(i).getDirection() == 1 || enemies.get(i).getDirection() == 2) {
					if (enemies.get(i).getAnimationNumber() != Enemy.AnimationType.left.ordinal())
						enemies.get(i).setCurrentAnimation(Enemy.AnimationType.left.ordinal());
					enemies.get(i).cycleAnimation();
				} else {
					if (enemies.get(i).getAnimationNumber() != Enemy.AnimationType.right.ordinal())
						enemies.get(i).setCurrentAnimation(Enemy.AnimationType.right.ordinal());
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
		for (int i = 0 ; i < bombs.size() ;) {
			if (bombs.get(i).isObsolete()) {
				bombs.remove(i);
			} else {
				bombs.get(i).cycleAnimation();

				if (!bomberman.canDetonateBombs() || bombs.get(i).getTimer() <= TIMEOUT*2)
					bombs.get(i).decreaseTimer();
				i++;
			}
		}

		for (int i = 0 ; i < unexplodedBombs.size() ;) {
			if (unexplodedBombs.get(i).isDead()) {
				unexplodedBombs.remove(i);
				bomberman.increaseBombsLeft();
			}
			else i++;
		}
	}

	private void destroyObjectInExplodedBombsRange() {

		for (Bomb bomb : bombs) {

			if (bomb.getTimer() == TIMEOUT) {

				ArrayList<AnimatedObject> destBricks = colDetect.checkExplBricks(bomb);

				for (AnimatedObject destBrick : destBricks) {
					if (destBrick != null) {
						for (Brick brick : bricks) {
							if ((destBrick.getXPosition() == brick.getXPosition()) && (destBrick.getYPosition() == brick.getYPosition()) && !brick.isDead())
								brick.triggerDeath();
						}

						for (Bomb bomb1 : bombs) {
							if ((destBrick.getXPosition() == bomb1.getXPosition()) && (destBrick.getYPosition() == bomb1.getYPosition()) && !bomb1.isDead())
								bomb1.setTimer(TIMEOUT * 2);
						}
					}
				}

				ArrayList<Enemy> destEnemies = colDetect.checkExplEnemies(bomb);

				int pointsMultiplier = destEnemies.size();

				for (Enemy enemy : destEnemies) {
					if (enemy != null) {
						for (Enemy enemy1 : enemies) {
							if ((enemy.getXPosition() == enemy1.getXPosition()) && (enemy.getYPosition() == enemy1.getYPosition()) && !enemy1.isDead()) {
								enemy1.triggerDeath();
								gameContext.increaseScore(enemy1.getPoints() * ((int) Math.pow(2, pointsMultiplier)));
							}
						}
					}
					pointsMultiplier--;
				}

				if (colDetect.checkExplGridObject(bomb, bomberman) && !bomberman.canFlamepass() && !bomberman.isInvincible() && gameContext.getLevelSpecification()[8] != 0 && !bomberman.isDead())
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
				bomberman.setXPosition(brick.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH);
			if (colDetect.checkLeftCollision(bomberman, brick))
				bomberman.setXPosition(brick.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH);
			if (colDetect.checkDownCollision(bomberman, brick))
				bomberman.setYPosition(brick.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT);
			if (colDetect.checkUpCollision(bomberman, brick))
				bomberman.setYPosition(brick.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT);
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

			ArrayList<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();
			gameContext.increaseLevel();
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
		for (int i = 0 ; i < bricks.size() ;) {
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

		if ((bomberman.getXPosition() % GridObject.EFFECTIVE_PIXEL_WIDTH) < (GridObject.EFFECTIVE_PIXEL_WIDTH/2))
			xPosition = GridObject.EFFECTIVE_PIXEL_WIDTH * (bomberman.getXPosition()/GridObject.EFFECTIVE_PIXEL_WIDTH);
		else
			xPosition = GridObject.EFFECTIVE_PIXEL_WIDTH * (bomberman.getXPosition()/GridObject.EFFECTIVE_PIXEL_WIDTH + 1);

		if ((bomberman.getYPosition() % GridObject.EFFECTIVE_PIXEL_HEIGHT) < (GridObject.EFFECTIVE_PIXEL_HEIGHT/2))
			yPosition = GridObject.EFFECTIVE_PIXEL_HEIGHT * (bomberman.getYPosition()/GridObject.EFFECTIVE_PIXEL_HEIGHT);
		else
			yPosition = GridObject.EFFECTIVE_PIXEL_HEIGHT * (bomberman.getYPosition()/GridObject.EFFECTIVE_PIXEL_HEIGHT + 1);

		boolean canAddBomb = true;

		if (bombs.size() != 0) {

			int i = 0;

			while (canAddBomb && i < bombs.size()) {
				if (bombs.get(i).getXPosition() == xPosition && bombs.get(i).getYPosition() == yPosition)
					canAddBomb = false;

				i++;
			}
		}

		if (canAddBomb && bomberman.getBombsLeft() != 0) {
			Bomb tempBomb = new Bomb(xPosition,yPosition);

			bombs.add(tempBomb);
			unexplodedBombs.add(tempBomb);
			bomberman.decreaseBombsLeft();
		}
	}

	private void spawnEightHarderEnemies(GridObject gridObj) {

		int enemyType = 7;

		while (gameContext.getLevelSpecification()[enemyType] == 0) enemyType--;

		enemies.clear();

		for (int n = 0 ; n < 8 ; n++) {
			switch (enemyType) {
			case 0:
				enemies.add(new Oneal(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 1:
				enemies.add(new Doll(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 2:
				enemies.add(new Minvo(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 3:
				enemies.add(new Kondoria(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 4:
				enemies.add(new Ovapi(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 5:
				enemies.add(new Pass(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 6:
				enemies.add(new Pontan(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			case 7:
				enemies.add(new Pontan(gridObj.getXPosition(), gridObj.getYPosition()));
				break;
			}
		}
	}

	private void setupGameFrame(boolean isVisible) {

		gamePanel = new GameplayPanel(keyListener) {

			public void paintComponent(Graphics page){

				super.paintComponent(page);
				Graphics2D g2d=(Graphics2D)page;

				if (exitway != null)
					g2d.drawImage(exitway.getImage(), exitway.getXPosition(), exitway.getYPosition(),gamePanel);

				if (powerup != null)
					g2d.drawImage(powerup.getImage(), powerup.getXPosition(), powerup.getYPosition(),gamePanel);

				for (Bomb bomb : bombs) {
					for (int i = 0 ; i < bomb.getCurrentAnimations().size() ; i++)
						g2d.drawImage(bomb.getCurrentAnimations().get(i).getCurrentFrame(),
								bomb.getXPosition() + bomb.getAnimXOffset().get(i)*GridObject.EFFECTIVE_PIXEL_WIDTH,
								bomb.getYPosition() + bomb.getAnimYOffset().get(i)*GridObject.EFFECTIVE_PIXEL_HEIGHT, gamePanel);
				}

				for (Brick brick : bricks)
					g2d.drawImage(brick.getCurrentAnimation().getCurrentFrame(),brick.getXPosition(),brick.getYPosition(),gamePanel);

				for (Enemy enemy : enemies)
					g2d.drawImage(enemy.getCurrentAnimation().getCurrentFrame(),enemy.getXPosition(),enemy.getYPosition(),gamePanel);

				g2d.drawImage(bomberman.getCurrentAnimation().getCurrentFrame(),bomberman.getXPosition(),bomberman.getYPosition(),gamePanel);

				for (Concrete block : concreteLayout)
					g2d.drawImage(block.getImage(),block.getXPosition(),block.getYPosition(), gamePanel);
			}
		};

		gameStatusPanel = new GameStatusPanel(gameContext);

		gameView = new JViewport();
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
		if (bomberman.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH/2 <= VIEW_PORT_WIDTH/2) {
			gamePanel.setLocation(0, 0);
			gamePanel.repaint();
		} else if (bomberman.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH/2 >= GameplayPanel.WIDTH - VIEW_PORT_WIDTH/2) {
			gamePanel.setLocation(VIEW_PORT_WIDTH - GameplayPanel.WIDTH, 0);
			gamePanel.repaint();
		} else {
			gamePanel.setLocation(VIEW_PORT_WIDTH/2 - bomberman.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH/2, 0);
			gamePanel.repaint();
		}
	}
}

