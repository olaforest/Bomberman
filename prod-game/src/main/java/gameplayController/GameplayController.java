package gameplayController;

import gameplayModel.*;
import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.Exitway;
import gameplayModel.gridObjects.animatedObjects.*;
import gameplayView.GameStatusPanel;
import gameplayView.GameplayPanel;
import lombok.Getter;
import utilities.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;

import static gameplayView.GameStatusPanel.HEADERHEIGHT;
import static gameplayView.GameplayPanel.HEIGHT;
import static gameplayView.GameplayPanel.WIDTH;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;

public class GameplayController implements ActionListener {
	public static final int TIMEOUT = 50;
	public static final int VIEW_PORT_WIDTH = 16 * EFFECTIVE_PIXEL_DIM;

	@Getter
	private final GameContext gameContext;
	private final ArtificialIntelligence intelligence;
	private final CollisionDetector colDetect;
	private final GameplayKeyListener keyListener = new GameplayKeyListener();
	private final LevelManager levelManager;
	private final Timer timer;

	private GameplayPanel gamePanel;
	private GameStatusPanel gameStatusPanel;
	private ArrayDeque<Integer> activeDirectionKeys;
	private JFrame gameFrame;
	private boolean placeBomb;

	public GameplayController() {
		gameContext = new GameContext();
		colDetect = new CollisionDetector();
		intelligence = new ArtificialIntelligence(gameContext.getGridMap());
		levelManager = new LevelManager();
		activeDirectionKeys = new ArrayDeque<>();
		setupGameFrame(true, gameContext.getGridMap());
		timer = new Timer(TIMEOUT, this);
		timer.start();
	}

	public GameplayController(GameContext gameContext, LevelManager levelManager) {
		this.gameContext = gameContext;
		colDetect = new CollisionDetector();
		intelligence = new ArtificialIntelligence(gameContext.getGridMap());
		this.levelManager = levelManager;
		activeDirectionKeys = new ArrayDeque<>();
		setupGameFrame(false, gameContext.getGridMap());
		timer = new Timer(TIMEOUT, this);
	}

	public void actionPerformed(ActionEvent event) {
		if (gameContext.isGameOver()) {
			timer.stop();

			if (gameContext.getLivesLeft() > 0) {
				gameContext.removePowerUps();
//				List<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();
				gameContext.decreaseLivesLeft();
				gameContext.initializeGameTime();
				gameContext.restartMap();
//				bomberman.setPowerUpsAcquired(powerUpsAcquired);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.start();
			} else {
				gameFrame.setVisible(false);
			}
		}

		gameContext.updateBombermanStatus(activeDirectionKeys);
		intelligence.updateEnemiesPosition();
		gameContext.updateEnemiesAnim();
		gameContext.updateBombsStatus();
		gameContext.updateBricksStatus();
		gameContext.destroyObjectInExplodedBombsRange(levelManager.isBonusLevel(), levelManager.getHardestEnemyType());
		gameContext.checkCollisionBtwBombermanAndBricks();

		if (!levelManager.isBonusLevel())
			gameContext.checkCollisionBtwBombermanAndEnemies();

		if (gameContext.checkCollisionBtwBombermanAndExitway()) {
//			List<PowerUp> powerUpsAcquired = bomberman.getPowerUpsAcquired();
			levelManager.increaseLevel();
			gameContext.initializeGameTime();
			gameContext.restartMap();
//			bomberman.setPowerUpsAcquired(powerUpsAcquired);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameContext.increaseLivesLeft();
			timer.start();
		}
		gameContext.checkCollisionBtwBombermanAndPowerUp();

		if (placeBomb) gameContext.addBomb();

//		if (levelManager.isBonusLevel()) gridMap.decreaseSpawnTimer();

		if (gameContext.getGameTime() <= 0 && !gameContext.isEndGameEnemiesSpawned()) {
			gameContext.setEndGameEnemiesSpawned(true);
//			gridMap.generateEnemiesOfType(Pontan);
		}

		updateViewport(gameContext.getViewPortPosition());
		gameStatusPanel.updateGameStatus();
		gameContext.decreaseGameTime();
	}

	public void resumeGame() {
		gameFrame.setVisible(true);
		timer.start();
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
						}
						break;
					case KeyEvent.VK_X:
						placeBomb = true;
						break;
					case KeyEvent.VK_Z:
						gameContext.detonateBomb();
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

	private void setupGameFrame(boolean isVisible, GridMap gridMap) {

		gamePanel = new GameplayPanel(keyListener) {

			public void paintComponent(Graphics page) {

				super.paintComponent(page);
				Graphics2D g2d = (Graphics2D) page;

				if (gridMap.getExitway() != null)
					g2d.drawImage(Exitway.getImage(), gridMap.getExitway().getX(), gridMap.getExitway().getY(), gamePanel);

				if (gridMap.getPowerUp() != null)
					g2d.drawImage(gridMap.getPowerUp().getImage(), gridMap.getPowerUp().getX(), gridMap.getPowerUp().getY(), gamePanel);

				for (Bomb bomb : gridMap.getBombs()) {
					for (int i = 0; i < bomb.getCurrentAnimations().size(); i++)
						g2d.drawImage(bomb.getCurrentAnimations().get(i).getCurrentFrame(),
								bomb.getPosition().getX() + bomb.getAnimXOffset().get(i) * EFFECTIVE_PIXEL_DIM,
								bomb.getPosition().getY() + bomb.getAnimYOffset().get(i) * EFFECTIVE_PIXEL_DIM, gamePanel);
				}

				for (Brick brick : gridMap.getBricks())
					g2d.drawImage(brick.getCurrentAnimation().getCurrentFrame(), brick.getPosition().getX(), brick.getPosition().getY(), gamePanel);

				for (Enemy enemy : gridMap.getEnemies())
					g2d.drawImage(enemy.getCurrentAnimation().getCurrentFrame(), enemy.getPosition().getX(), enemy.getPosition().getY(), gamePanel);

				g2d.drawImage(gridMap.getBomberman().getCurrentAnimation().getCurrentFrame(), gridMap.getBomberman().getPosition().getX(), gridMap.getBomberman().getPosition().getY(), gamePanel);

				for (Concrete block : GridMap.CONCRETE_LAYOUT)
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
//		gameFrame.getContentPane().setPreferredSize(new Dimension(VIEW_PORT_WIDTH, GameplayPanel.HEIGHT + GameStatusPanel.HEADERHEIGHT));
		gameFrame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT + HEADERHEIGHT));
		gameFrame.pack();
		gameFrame.setLocation(400, 200);
		gameFrame.setVisible(isVisible);
	}

	private void updateViewport(Position position) {
		if (position.getX() + EFFECTIVE_PIXEL_DIM / 2 <= VIEW_PORT_WIDTH / 2) {
			gamePanel.setLocation(0, 0);
			gamePanel.repaint();
		} else if (position.getX() + EFFECTIVE_PIXEL_DIM / 2 >= WIDTH - VIEW_PORT_WIDTH / 2) {
			gamePanel.setLocation(VIEW_PORT_WIDTH - WIDTH, 0);
			gamePanel.repaint();
		} else {
			gamePanel.setLocation(VIEW_PORT_WIDTH / 2 - position.getX() - EFFECTIVE_PIXEL_DIM / 2, 0);
			gamePanel.repaint();
		}
	}
}

