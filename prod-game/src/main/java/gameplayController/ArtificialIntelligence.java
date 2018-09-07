package gameplayController;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.*;
import utilities.Node;

import java.util.LinkedList;
import java.util.Random;

import static gameplayController.CollisionDetector.*;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.lang.Math.abs;

class ArtificialIntelligence {
	private final GridMap gridMap;
	private boolean collision;
	private int collisionCount;

	ArtificialIntelligence(GridMap gridMap) {
		this.gridMap = gridMap;
		collision = false;
		collisionCount = 0;
	}

	void updateEnemiesPosition() {
		for (Enemy enemy : gridMap.getEnemies()) {
			if (!enemy.isDead()) {
				boolean isAlignedWithColumn = ((enemy.getPosition().getX() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2) == 0);
				boolean isAlignedWithRow = ((enemy.getPosition().getY() - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2) == 0);

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
							for (Brick brick : gridMap.getBricks())
								randomChangeCheck(enemy, brick);
							for (Bomb bomb : gridMap.getBombs())
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
					for (Brick brick : gridMap.getBricks())
						AICollisionCheck(enemy, brick);

					for (Bomb bomb : gridMap.getBombs())
						AICollisionCheck(enemy, bomb);
				}

				// If there was a collision, we move the enemy back to where it started and turn it around.
				if (collision) turnAround(enemy);
			}
		}
	}

	private void AICollisionCheck(Enemy enemy, GridObject gridObject) {

		if (enemy.getDirection() == 0) {
			if (checkUpCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 1) {
			if (checkDownCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 2) {
			if (checkLeftCollision(enemy, gridObject))
				collision = true;
		} else if (enemy.getDirection() == 3) {
			if (checkRightCollision(enemy, gridObject))
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
			if ((gridObject.getPosition().getX() == enemy.getPosition().getX() - EFFECTIVE_PIXEL_DIM) && (gridObject.getPosition().getY() == enemy.getPosition().getY()))
				collisionCount++;

			if ((gridObject.getPosition().getX() == enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIM) && (gridObject.getPosition().getY() == enemy.getPosition().getY()))
				collisionCount++;
		} else if (enemy.getDirection() > 1) {
			if ((gridObject.getPosition().getY() == enemy.getPosition().getY() - EFFECTIVE_PIXEL_DIM) && (gridObject.getPosition().getX() == enemy.getPosition().getX()))
				collisionCount++;

			if ((gridObject.getPosition().getY() == enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIM) && (gridObject.getPosition().getX() == enemy.getPosition().getX()))
				collisionCount++;
		}
	}

	private void randomChange(Enemy enemy) {
		int chance = 0;

		if (enemy.getSmartness() == 2) chance = 8;

		if (enemy.getSmartness() == 3) chance = 4;

		final var rn = new Random();
		int random = rn.nextInt(10);

		if (random > chance) {
			int newDirection = rn.nextInt(4);
			enemy.setDirection(newDirection);
		}
	}

	private void checkForBomberman(Enemy enemy) {
		if (enemy.getSmartness() == 3) {

			//if bomberman is within 2 range
			if ((abs(gridMap.getBomberman().getPosition().getX() - enemy.getPosition().getX()) <= 2 * EFFECTIVE_PIXEL_DIM)
					&& (abs(gridMap.getBomberman().getPosition().getY() - enemy.getPosition().getY()) <= 2 * EFFECTIVE_PIXEL_DIM)) {
				
				/* getWallpass makes it unnecessary to use AStar, so we only do it if
				 * the enemy doesn't have isWallpass
				 */
				if (!enemy.isWallpass()) {
					
					/* we use AStar to find a path. If the path exists, we trace it back 
					 * until we have a Node that is one move from our enemy's current position
					 * We figure out what kind of move will get us there, and set the enemy to 
					 * the corresponding direction
					 */
					Node Direction = AStar(enemy, gridMap.getBomberman());

					if (Direction != null) {
						if (Direction.parent != null) {

							while (!((Direction.parent.xPosition == (((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * EFFECTIVE_PIXEL_DIM))
									&& (Direction.parent.yPosition == (((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * EFFECTIVE_PIXEL_DIM))))
								Direction = Direction.parent;

							if (Direction.xPosition == ((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * EFFECTIVE_PIXEL_DIM) {
								if (Direction.yPosition < enemy.getPosition().getY()) enemy.setDirection(0);

								if (Direction.yPosition > enemy.getPosition().getY()) enemy.setDirection(1);
							}

							if (Direction.yPosition == ((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * EFFECTIVE_PIXEL_DIM) {
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
					if (gridMap.getBomberman().getPosition().getX() == enemy.getPosition().getX()) {
						if (gridMap.getBomberman().getPosition().getY() > enemy.getPosition().getY()) enemy.setDirection(1);

						if (gridMap.getBomberman().getPosition().getY() < enemy.getPosition().getY()) enemy.setDirection(0);
					} else if (gridMap.getBomberman().getPosition().getX() < enemy.getPosition().getX()) enemy.setDirection(2);

					else if (gridMap.getBomberman().getPosition().getX() > enemy.getPosition().getX()) enemy.setDirection(3);
				}
			}
		}
		
		/* If the enemy only has intelligence = 2, then we check if bomberman
		 * is within one range. If he is, we set the enemy's direction to face him
		 */
		else if (gridMap.getBomberman().getPosition().getX() == enemy.getPosition().getX()) {
			if ((gridMap.getBomberman().getPosition().getY() - EFFECTIVE_PIXEL_DIM <= enemy.getPosition().getY())
					&& (enemy.getPosition().getY() < gridMap.getBomberman().getPosition().getY()))
				enemy.setDirection(1);

			if ((gridMap.getBomberman().getPosition().getY() + EFFECTIVE_PIXEL_DIM >= enemy.getPosition().getY())
					&& (enemy.getPosition().getY() > gridMap.getBomberman().getPosition().getY()))
				enemy.setDirection(0);
		} else if (gridMap.getBomberman().getPosition().getY() == enemy.getPosition().getY()) {
			if ((gridMap.getBomberman().getPosition().getX() - EFFECTIVE_PIXEL_DIM <= enemy.getPosition().getX()) && (enemy.getPosition().getX() < gridMap.getBomberman().getPosition().getX()))
				enemy.setDirection(3);

			if ((gridMap.getBomberman().getPosition().getX() + EFFECTIVE_PIXEL_DIM >= enemy.getPosition().getX()) && (enemy.getPosition().getX() > gridMap.getBomberman().getPosition().getX()))
				enemy.setDirection(2);
		}
	}

	/* This is just AStar. The only thing of note is that we round Up to the nearest cell width
	* so that we can use standard grid sizes for searching
	*/
	private Node AStar(Enemy enemy, Bomberman bomberman) {

		LinkedList<Node> openList = new LinkedList<>();
		LinkedList<Node> closedList = new LinkedList<>();

		int correctedX = ((enemy.getPosition().getX() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * (EFFECTIVE_PIXEL_DIM);
		int correctedY = ((enemy.getPosition().getY() + EFFECTIVE_PIXEL_DIM / 2) / EFFECTIVE_PIXEL_DIM) * (EFFECTIVE_PIXEL_DIM);

		Node startNode = new Node(correctedX, correctedY, null, 0, euclidianDistance(enemy.getPosition().getX(), enemy.getPosition().getY(), bomberman.getPosition().getX(), bomberman.getPosition().getY()));
		openList.add(startNode);

		while (!openList.isEmpty()) {

			Node current = openList.removeFirst();

			closedList.add(current);

			int bCorrectedX = (bomberman.getPosition().getX() / EFFECTIVE_PIXEL_DIM) * (EFFECTIVE_PIXEL_DIM);
			int bCorrectedY = (bomberman.getPosition().getY() / EFFECTIVE_PIXEL_DIM) * (EFFECTIVE_PIXEL_DIM);

			if ((current.xPosition == bCorrectedX) && (current.yPosition == bCorrectedY))
				return current;

			boolean obstacle;

			boolean isAlignedWithColumn = ((current.xPosition - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2) == 0);
			boolean isAlignedWithRow = ((current.yPosition - EFFECTIVE_PIXEL_DIM) % (EFFECTIVE_PIXEL_DIM * 2) == 0);

			for (int i = 0; i < 4; i++) {

				obstacle = false;
				if (i == 0) {

					if (current.yPosition <= EFFECTIVE_PIXEL_DIM) obstacle = true;

					if (isAlignedWithColumn) {
						for (Brick brick : gridMap.getBricks()) {
							if ((brick.getPosition().getX() == current.xPosition) && (brick.getPosition().getY() + EFFECTIVE_PIXEL_DIM == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : gridMap.getBombs()) {
							if ((bomb.getPosition().getX() == current.xPosition) && (bomb.getPosition().getY() + EFFECTIVE_PIXEL_DIM == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIM, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition - EFFECTIVE_PIXEL_DIM, this.gridMap.getBomberman().getPosition().getX(), this.gridMap.getBomberman().getPosition().getY()));

							if (notAlreadyIn(openList, next)) {
								if (notAlreadyIn(closedList, next)) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 1) {

					if (current.yPosition >= EFFECTIVE_PIXEL_DIM * 13) obstacle = true;

					if (isAlignedWithColumn) {

						for (Brick brick : gridMap.getBricks()) {
							if ((brick.getPosition().getX() == current.xPosition) && (brick.getPosition().getY() - EFFECTIVE_PIXEL_DIM == current.yPosition))
								obstacle = true;
						}

						for (Bomb bomb : gridMap.getBombs()) {
							if ((bomb.getPosition().getX() == current.xPosition) && (bomb.getPosition().getY() - EFFECTIVE_PIXEL_DIM == current.yPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIM, current, current.gCost + 10,
									euclidianDistance(current.xPosition, current.yPosition + EFFECTIVE_PIXEL_DIM, this.gridMap.getBomberman().getPosition().getX(), this.gridMap.getBomberman().getPosition().getY()));

							if (notAlreadyIn(openList, next)) {
								if (notAlreadyIn(closedList, next)) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 2) {

					if (current.xPosition <= EFFECTIVE_PIXEL_DIM)
						obstacle = true;

					if (isAlignedWithRow) {
						for (Brick brick : gridMap.getBricks()) {
							if ((brick.getPosition().getY() == current.yPosition) && (brick.getPosition().getX() + EFFECTIVE_PIXEL_DIM == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : gridMap.getBombs()) {
							if ((bomb.getPosition().getY() == current.yPosition) && (bomb.getPosition().getX() + EFFECTIVE_PIXEL_DIM == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition - EFFECTIVE_PIXEL_DIM, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition - EFFECTIVE_PIXEL_DIM, current.yPosition, this.gridMap.getBomberman().getPosition().getX(), this.gridMap.getBomberman().getPosition().getY()));

							if (notAlreadyIn(openList, next)) {
								if (notAlreadyIn(closedList, next)) {
									openList.add(next);
									sortList(openList);
								}
							}
						}
					}
				} else if (i == 3) {
					if (current.xPosition >= EFFECTIVE_PIXEL_DIM * 13)
						obstacle = true;

					if (isAlignedWithRow) {
						for (Brick brick : gridMap.getBricks()) {
							if ((brick.getPosition().getY() == current.yPosition) && (brick.getPosition().getX() - EFFECTIVE_PIXEL_DIM == current.xPosition))
								obstacle = true;
						}

						for (Bomb bomb : gridMap.getBombs()) {
							if ((bomb.getPosition().getY() == current.yPosition) && (bomb.getPosition().getX() - EFFECTIVE_PIXEL_DIM == current.xPosition))
								obstacle = true;
						}

						if (!obstacle) {
							Node next = new Node(current.xPosition + EFFECTIVE_PIXEL_DIM, current.yPosition, current, current.gCost + 10,
									euclidianDistance(current.xPosition + EFFECTIVE_PIXEL_DIM, current.yPosition, this.gridMap.getBomberman().getPosition().getX(), this.gridMap.getBomberman().getPosition().getY()));

							if (notAlreadyIn(openList, next)) {
								if (notAlreadyIn(closedList, next)) {
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
