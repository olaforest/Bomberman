package gameplayController;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import gameplayModel.gridObjects.animatedObjects.Brick;
import gameplayModel.gridObjects.animatedObjects.Enemy;
import utilities.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;

class ArtificialIntelligence {

	private final List<Enemy> enemies;
	private final List<Brick> bricks;
	private final List<Bomb> bombs;
	private final CollisionDetector detector;
	private final Bomberman bomberman;
	private boolean collision;
	private int collisionCount;

	public ArtificialIntelligence(Bomberman b, List<Enemy> e, List<Brick> br, List<Bomb> bo, CollisionDetector cD) {
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
				boolean isAlignedWithColumn = ((enemy.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);
				boolean isAlignedWithRow = ((enemy.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION) % (EFFECTIVE_PIXEL_DIMENSION * 2) == 0);

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

				collision = enemy.isConcreteCollision();

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
			enemy.setYPosition(enemy.getPosition().getY() + enemy.getSpeed());
			enemy.setDirection(1);
		} else if (enemy.getDirection() == 1) {
			enemy.setYPosition(enemy.getPosition().getY() - enemy.getSpeed());
			enemy.setDirection(0);
		} else if (enemy.getDirection() == 2) {
			enemy.setXPosition(enemy.getPosition().getX() + enemy.getSpeed());
			enemy.setDirection(3);
		} else if (enemy.getDirection() == 3) {
			enemy.setXPosition(enemy.getPosition().getX() - enemy.getSpeed());
			enemy.setDirection(2);
		}
	}

	private void moveEnemy(Enemy enemy) {
		if (enemy.getDirection() == 0) enemy.setYPosition(enemy.getPosition().getY() - enemy.getSpeed());

		else if (enemy.getDirection() == 1) enemy.setYPosition(enemy.getPosition().getY() + enemy.getSpeed());

		else if (enemy.getDirection() == 2) enemy.setXPosition(enemy.getPosition().getX() - enemy.getSpeed());

		else if (enemy.getDirection() == 3) enemy.setXPosition(enemy.getPosition().getX() + enemy.getSpeed());
	}

	private void randomChangeCheck(Enemy enemy, GridObject gridObject) {

		if (enemy.getDirection() < 2) {
			if ((gridObject.getPosition().getX() == enemy.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getPosition().getY() == enemy.getPosition().getY()))
				collisionCount++;

			if ((gridObject.getPosition().getX() == enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getPosition().getY() == enemy.getPosition().getY()))
				collisionCount++;
		} else if (enemy.getDirection() > 1) {
			if ((gridObject.getPosition().getY() == enemy.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getPosition().getX() == enemy.getPosition().getX()))
				collisionCount++;

			if ((gridObject.getPosition().getY() == enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION) && (gridObject.getPosition().getX() == enemy.getPosition().getX()))
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
			if ((Math.abs(bomberman.getPosition().getX() - enemy.getPosition().getX()) <= 2 * EFFECTIVE_PIXEL_DIMENSION)
					&& (Math.abs(bomberman.getPosition().getY() - enemy.getPosition().getY()) <= 2 * EFFECTIVE_PIXEL_DIMENSION)) {
				
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

							while (!((Direction.parent.xPosition == (((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION))
									&& (Direction.parent.yPosition == (((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION))))
								Direction = Direction.parent;

							if (Direction.xPosition == ((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION) {
								if (Direction.yPosition < enemy.getPosition().getY()) enemy.setDirection(0);

								if (Direction.yPosition > enemy.getPosition().getY()) enemy.setDirection(1);
							}

							if (Direction.yPosition == ((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * EFFECTIVE_PIXEL_DIMENSION) {
								if (Direction.xPosition < enemy.getPosition().getX()) enemy.setDirection(2);

								if (Direction.xPosition > enemy.getPosition().getX()) enemy.setDirection(3);
							}
						}
					}
				}
				
				/* Otherwise we just send the enemy towards bomberman
				 * first matching its x coordinate, and then moving to
				 * match it's y coordinate.
				 */
				else {
					if (bomberman.getPosition().getX() == enemy.getPosition().getX()) {
						if (bomberman.getPosition().getY() > enemy.getPosition().getY()) enemy.setDirection(1);

						if (bomberman.getPosition().getY() < enemy.getPosition().getY()) enemy.setDirection(0);
					} else if (bomberman.getPosition().getX() < enemy.getPosition().getX()) enemy.setDirection(2);

					else if (bomberman.getPosition().getX() > enemy.getPosition().getX()) enemy.setDirection(3);
				}
			}
		}
		
		/* If the enemy only has intelligence = 2, then we check if bomberman
		 * is within one range. If he is, we set the enemy's direction to face him
		 */
		else if (bomberman.getPosition().getX() == enemy.getPosition().getX()) {
			if ((bomberman.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION <= enemy.getPosition().getY())
					&& (enemy.getPosition().getY() < bomberman.getPosition().getY()))
				enemy.setDirection(1);

			if ((bomberman.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION >= enemy.getPosition().getY())
					&& (enemy.getPosition().getY() > bomberman.getPosition().getY()))
				enemy.setDirection(0);
		} else if (bomberman.getPosition().getY() == enemy.getPosition().getY()) {
			if ((bomberman.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION <= enemy.getPosition().getX()) && (enemy.getPosition().getX() < bomberman.getPosition().getX()))
				enemy.setDirection(3);

			if ((bomberman.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION >= enemy.getPosition().getX()) && (enemy.getPosition().getX() > bomberman.getPosition().getX()))
				enemy.setDirection(2);
		}
	}

	/* This is just AStar. The only thing of note is that we round up to the nearest cell width
	* so that we can use standard grid sizes for searching
	*/
	private Node AStar(Enemy enemy, Bomberman bomberman) {

		LinkedList<Node> openList = new LinkedList<>();
		LinkedList<Node> closedList = new LinkedList<>();

		int correctedX = ((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);
		int correctedY = ((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION / 2) / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);

		Node startNode = new Node(correctedX, correctedY, null, 0, euclidianDistance(enemy.getPosition().getX(), enemy.getPosition().getY(), bomberman.getPosition().getX(), bomberman.getPosition().getY()));
		openList.add(startNode);

		while (!openList.isEmpty()) {

			Node current = openList.removeFirst();

			closedList.add(current);

			int bCorrectedX = (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);
			int bCorrectedY = (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIMENSION) * (EFFECTIVE_PIXEL_DIMENSION);

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
							if ((brick.getPosition().getX() == current.xPosition) && (brick.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getPosition().getX() == current.xPosition) && (bomb.getPosition().getY() + EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIMENSION, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIMENSION, this.bomberman.getPosition().getX(), this.bomberman.getPosition().getY()));

							if (!(!notAlreadyIn(openList, next))) {
								if (!(!notAlreadyIn(closedList, next))) {
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
							if ((brick.getPosition().getX() == current.xPosition) && (brick.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getPosition().getX() == current.xPosition) && (bomb.getPosition().getY() - EFFECTIVE_PIXEL_DIMENSION == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIMENSION, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIMENSION, this.bomberman.getPosition().getX(), this.bomberman.getPosition().getY()));

							if (!(!notAlreadyIn(openList, next))) {
								if (!(!notAlreadyIn(closedList, next))) {
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
							if ((brick.getPosition().getY() == current.yPosition) && (brick.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getPosition().getY() == current.yPosition) && (bomb.getPosition().getX() + EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition - EFFECTIVE_PIXEL_DIMENSION, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition - EFFECTIVE_PIXEL_DIMENSION, current.yPosition, this.bomberman.getPosition().getX(), this.bomberman.getPosition().getY()));

							if (!(!notAlreadyIn(openList, next))) {
								if (!(!notAlreadyIn(closedList, next))) {
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
							if ((brick.getPosition().getY() == current.yPosition) && (brick.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : bombs) {
							if ((bomb.getPosition().getY() == current.yPosition) && (bomb.getPosition().getX() - EFFECTIVE_PIXEL_DIMENSION == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition + EFFECTIVE_PIXEL_DIMENSION, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition + EFFECTIVE_PIXEL_DIMENSION, current.yPosition, this.bomberman.getPosition().getX(), this.bomberman.getPosition().getY()));

							if (!(!notAlreadyIn(openList, next))) {
								if (!(!notAlreadyIn(closedList, next))) {
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
	private boolean notAlreadyIn(LinkedList<Node> l, Node n) {
		for (Node node : l) {
			if (n.xPosition == node.xPosition)
				if (n.yPosition == node.yPosition)
					return false;
		}
		return true;
	}
}
