package gameplayController;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import utility.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;

public class ArtificialIntelligence {

	private ArrayList<Enemy> enemies;
	private ArrayList<Brick> bricks;
	private ArrayList<Bomb> bombs;
	private CollisionDetector detector;
	private Bomberman bomberman;
	boolean collision;
	int collisionCount;

	public ArtificialIntelligence(Bomberman b, ArrayList<Enemy> e, ArrayList<Brick> br, ArrayList<Bomb> bo, CollisionDetector cD) {
		enemies = e;
		bricks = br;
		bombs = bo;
		detector = cD;
		bomberman = b;
		collision = false;
		collisionCount = 0;
	}

	public void updateEnemiesPosition() {
		for (Enemy enemy : enemies) {
			if (!enemy.isDead()) {
				boolean isAlignedWithColumn = ((enemy.getXPosition() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);
				boolean isAlignedWithRow = ((enemy.getYPosition() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);

				if (isAlignedWithColumn && (!(isAlignedWithRow))) {
					if (enemy.getDirection() > 1)
						enemy.setDirection(0);
				}

				if (isAlignedWithRow && (!(isAlignedWithColumn))) {
					if (enemy.getDirection() < 2)
						enemy.setDirection(2);
				}

				if (enemy.getSmartness() > 1) {
					if (isAlignedWithColumn && isAlignedWithRow) {

						collisionCount = 0;

						if (!(enemy.isWallpass())) {
							for (Brick brick : bricks)
								randomChangeCheck(enemy, brick);
							for (Bomb bomb : bombs)
								randomChangeCheck(enemy, bomb);
						}

						// if there have been less than 2 closed spots around the enemy it means there is an intersection
						if (collisionCount < 2)
							randomChange(enemy);
					}
				}

				// if the enemy's intelligence is > 2, it can search for bomberman
				if (enemy.getSmartness() > 1)
					checkForBomberman(enemy);

				moveEnemy(enemy);

				collision = false;

				if (enemy.isConcreteCollision()) collision = true;

				if (!(enemy.isWallpass())) {
					// Now we check if any bricks or bombs are colliding with the enemy due to its initial movement
					for (Brick brick : bricks)
						AICollisionCheck(enemy, brick);

					for (Bomb bomb : bombs)
						AICollisionCheck(enemy, bomb);
				}

				// If there was a collision, we move the enemy back to where it started and turn it around.
				if (collision) turnAround(enemy);
			}
		}
	}

	private void AICollisionCheck(Enemy enemy, GridObject gridObject) {

		if (enemy.getDirection() == 0) {
			if (detector.checkUpCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 1) {
			if (detector.checkDownCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 2) {
			if (detector.checkLeftCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 3) {
			if (detector.checkRightCollision(enemy, gridObject))
				collision = true;
		}
	}

	private void turnAround(Enemy enemy) {
		if (enemy.getDirection() == 0) {
			enemy.setYPosition(enemy.getYPosition() + enemy.getSpeed());
			enemy.setDirection(1);
		} else if (enemy.getDirection() == 1) {
			enemy.setYPosition(enemy.getYPosition() - enemy.getSpeed());
			enemy.setDirection(0);
		} else if (enemy.getDirection() == 2) {
			enemy.setXPosition(enemy.getXPosition() + enemy.getSpeed());
			enemy.setDirection(3);
		} else if (enemy.getDirection() == 3) {
			enemy.setXPosition(enemy.getXPosition() - enemy.getSpeed());
			enemy.setDirection(2);
		}
	}

	private void moveEnemy(Enemy enemy) {
		if (enemy.getDirection() == 0) enemy.setYPosition(enemy.getYPosition() - enemy.getSpeed());

		else if (enemy.getDirection() == 1) enemy.setYPosition(enemy.getYPosition() + enemy.getSpeed());

		else if (enemy.getDirection() == 2) enemy.setXPosition(enemy.getXPosition() - enemy.getSpeed());

		else if (enemy.getDirection() == 3) enemy.setXPosition(enemy.getXPosition() + enemy.getSpeed());
	}

	private void randomChangeCheck(Enemy enemy, GridObject gridObject) {

		if (enemy.getDirection() < 2) {
			if ((gridObject.getXPosition() == enemy.getXPosition() - EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getYPosition() == enemy.getYPosition()))
				collisionCount++;

			if ((gridObject.getXPosition() == enemy.getXPosition() + EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getYPosition() == enemy.getYPosition()))
				collisionCount++;
		} else if (enemy.getDirection() > 1) {
			if ((gridObject.getYPosition() == enemy.getYPosition() - EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getXPosition() == enemy.getXPosition()))
				collisionCount++;

			if ((gridObject.getYPosition() == enemy.getYPosition() + EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getXPosition() == enemy.getXPosition()))
				collisionCount++;
		}
	}

	private void randomChange(Enemy enemy) {
		int chance = 0;

		if (enemy.getSmartness() == 2) chance = 8;

		if (enemy.getSmartness() == 3) chance = 4;

		Random rn = new Random();
		int random = rn.nextInt(10);

		if (random > chance) {
			int newDirection = rn.nextInt(4);
			enemy.setDirection(newDirection);
		}
	}

	private void checkForBomberman(Enemy enemy) {
		if (enemy.getSmartness() == 3) {

			//if bomberman is within 2 range
			if ((Math.abs(bomberman.getXPosition() - enemy.getXPosition()) <= 2 * EFFECTIVE_PIXEL_DIMENSION)
					&& (Math.abs(bomberman.getYPosition() - enemy.getYPosition()) <= 2 * EFFECTIVE_PIXEL_DIMENSION)) {
				
				/* getWallpass makes it unnecessary to use AStar, so we only do it if
				 * the enemy doesn't have isWallpass
				 */
				if (!enemy.isWallpass()) {
					
					/* we use AStar to find a path. If the path exists, we trace it back 
					 * until we have a Node that is one move from our enemy's current position
					 * We figure out what kind of move will get us there, and set the enemy to 
					 * the corresponding direction
					 */
					Node Direction = AStar(enemy, bomberman);

					if (Direction != null) {
						if (Direction.parent != null) {

							while (!((Direction.parent.xPosition == (((enemy.getXPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION))
									&& (Direction.parent.yPosition == (((enemy.getYPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION))))
								Direction = Direction.parent;

							if (Direction.xPosition == ((enemy.getXPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION) {
								if (Direction.yPosition < enemy.getYPosition()) enemy.setDirection(0);

								if (Direction.yPosition > enemy.getYPosition()) enemy.setDirection(1);
							}

							if (Direction.yPosition == ((enemy.getYPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION) {
								if (Direction.xPosition < enemy.getXPosition()) enemy.setDirection(2);

								if (Direction.xPosition > enemy.getXPosition()) enemy.setDirection(3);
							}
						}
					}
				}
				
				/* Otherwise we just send the enemy towards bomberman
				 * first matching its x coordinate, and then moving to
				 * match it's y coordinate.
				 */
				else {
					if (bomberman.getXPosition() == enemy.getXPosition()) {
						if (bomberman.getYPosition() > enemy.getYPosition()) enemy.setDirection(1);

						if (bomberman.getYPosition() < enemy.getYPosition()) enemy.setDirection(0);
					} else if (bomberman.getXPosition() < enemy.getXPosition()) enemy.setDirection(2);

					else if (bomberman.getXPosition() > enemy.getXPosition()) enemy.setDirection(3);
				}
			}
		}
		
		/* If the enemy only has intelligence = 2, then we check if bomberman
		 * is within one range. If he is, we set the enemy's direction to face him
		 */
		else if (bomberman.getXPosition() == enemy.getXPosition()) {
			if ((bomberman.getYPosition() - EFFECTIVE_PIXEL_DIMENSION <= enemy.getYPosition())
					&& (enemy.getYPosition() < bomberman.getYPosition()))
				enemy.setDirection(1);

			if ((bomberman.getYPosition() + EFFECTIVE_PIXEL_DIMENSION >= enemy.getYPosition())
					&& (enemy.getYPosition() > bomberman.getYPosition()))
				enemy.setDirection(0);
		} else if (bomberman.getYPosition() == enemy.getYPosition()) {
			if ((bomberman.getXPosition() - EFFECTIVE_PIXEL_DIMENSION <= enemy.getXPosition()) && (enemy.getXPosition() < bomberman.getXPosition()))
				enemy.setDirection(3);

			if ((bomberman.getXPosition() + EFFECTIVE_PIXEL_DIMENSION >= enemy.getXPosition()) && (enemy.getXPosition() > bomberman.getXPosition()))
				enemy.setDirection(2);
		}
	}

	/* This is just AStar. The only thing of note is that we round up to the nearest cell width
	* so that we can use standard grid sizes for searching
	*/
	private Node AStar(Enemy enemy, Bomberman bomberman) {

		LinkedList<Node> openList = new LinkedList<>();
		LinkedList<Node> closedList = new LinkedList<>();

		int correctedX = ((enemy.getXPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);
		int correctedY = ((enemy.getYPosition() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);

		Node startNode = new Node(correctedX, correctedY, null, 0, euclidianDistance(enemy.getXPosition(), enemy.getYPosition(), bomberman.getXPosition(), bomberman.getYPosition()));
		openList.add(startNode);

		while (!openList.isEmpty()) {

			Node current = openList.removeFirst();

			closedList.add(current);

			int bCorrectedX = (bomberman.getXPosition() / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);
			int bCorrectedY = (bomberman.getYPosition() / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);

			if ((current.xPosition == bCorrectedX) && (current.yPosition == bCorrectedY))
				return current;

			boolean obstacle;

			boolean isAlignedWithColumn = ((current.xPosition - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);
			boolean isAlignedWithRow = ((current.yPosition - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);

			for (int i = 0; i < 4; i++) {

				obstacle = false;
				if (i == 0) {

					if (current.yPosition <= EFFECTIVE_PIXEL_DIMENSION) obstacle = true;

					if (isAlignedWithColumn) {
						for (Brick brick : bricks) {
							if ((brick.getXPosition() == current.xPosition) && (brick.getYPosition() + EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getXPosition() == current.xPosition) && (bomb.getYPosition() + EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIMENSION, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIMENSION, this.bomberman.getXPosition(), this.bomberman.getYPosition()));

							if (!(alreadyIn(openList, next))) {
								if (!(alreadyIn(closedList, next))) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 1) {

					if (current.yPosition >= EFFECTIVE_PIXEL_DIMENSION * 13) obstacle = true;

					if (isAlignedWithColumn) {

						for (Brick brick : bricks) {
							if ((brick.getXPosition() == current.xPosition) && (brick.getYPosition() - EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getXPosition() == current.xPosition) && (bomb.getYPosition() - EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIMENSION, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIMENSION, this.bomberman.getXPosition(), this.bomberman.getYPosition()));

							if (!(alreadyIn(openList, next))) {
								if (!(alreadyIn(closedList, next))) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 2) {

					if (current.xPosition <= EFFECTIVE_PIXEL_DIMENSION)
						obstacle = true;

					if (isAlignedWithRow) {
						for (Brick brick : bricks) {
							if ((brick.getYPosition() == current.yPosition) && (brick.getXPosition() + EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getYPosition() == current.yPosition) && (bomb.getXPosition() + EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition - EFFECTIVE_PIXEL_DIMENSION, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition - EFFECTIVE_PIXEL_DIMENSION, current.yPosition, this.bomberman.getXPosition(), this.bomberman.getYPosition()));

							if (!(alreadyIn(openList, next))) {
								if (!(alreadyIn(closedList, next))) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 3) {
					if (current.xPosition >= EFFECTIVE_PIXEL_DIMENSION * 13)
						obstacle = true;

					if (isAlignedWithRow) {
						for (Brick brick : bricks) {
							if ((brick.getYPosition() == current.yPosition) && (brick.getXPosition() - EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getYPosition() == current.yPosition) && (bomb.getXPosition() - EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition + EFFECTIVE_PIXEL_DIMENSION, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition + EFFECTIVE_PIXEL_DIMENSION, current.yPosition, this.bomberman.getXPosition(), this.bomberman.getYPosition()));

							if (!(alreadyIn(openList, next))) {
								if (!(alreadyIn(closedList, next))) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	//returns the euclidian distance from one point to another
	private double euclidianDistance(int startX, int startY, int goalX, int goalY) {
		return Math.sqrt(Math.pow(goalY - startY, 2) + Math.pow(goalX - startX, 2));
	}

	//sorts a list according to fCost
	private void sortList(LinkedList<Node> l) {
		for (int k = 0; k < l.size() - 1; k++) {
			if (l.get(k).fCost > l.get(k + 1).fCost) {
				Node temp = l.get(k);
				l.set(k, l.get(k + 1));
				l.set(k + 1, temp);
			}
		}
	}

	//returns true if the node already exists in the list
	private boolean alreadyIn(LinkedList<Node> l, Node n) {
		for (Node node : l) {
			if (n.xPosition == node.xPosition)
				if (n.yPosition == node.yPosition)
					return true;
		}
		return false;
	}
}
