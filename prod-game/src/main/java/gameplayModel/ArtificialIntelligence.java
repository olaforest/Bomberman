/**
 * 
 * This class defines the AI behaviours of enemies in the game.
 * Given a list of bombs, enemies, bricks, a CollisionDetector object 
 * and a Bomberman object, the ArtificialIntelligence class will move 
 * enemies according to the rules of the game.
 * 
 * 
 * CollisionCount, specifically is used to determine whether or not
 * there is an intersection of paths. 
 * 
 * Collision is a boolean which is used to determine if the enemies are 
 * in a collision with a bomb or a brick, if they are, they are turned around
 * and moved back to their original position
 * 
 * 
 *
 */
package gameplayModel;

import utility.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Olivier Laforest
 * @author Jonti
 */
public class ArtificialIntelligence {

	private ArrayList<Enemy> enemies;
	private ArrayList<Brick> bricks;
	private ArrayList<Bomb> bombs;
	private CollisionDetector detector;
	private Bomberman bomberman;
	boolean collision;
	int collisionCount;
 
        
	/**
	* Initialize the AI to start calculating movements
	* @param  b the Bomberman object currently in the level
	* @param  e the list of enemies in the level
	* @param  br the list of all bricks in the level
	* @param  bo the list of all bombs in the level
	* @param  cD a collisionDetector object, to use for detecting collisions
	*              between enemies and bricks or bombs
	*
	*/
	public ArtificialIntelligence(Bomberman b, ArrayList<Enemy> e, ArrayList<Brick> br, ArrayList<Bomb> bo, CollisionDetector cD) {
		
		enemies = e;
		bricks = br;
		bombs = bo;
		detector = cD;
		bomberman =b;
		collision = false;
		collisionCount = 0;
	}
	    
	public void updateEnemiesPosition() { //this is the method that is called to move everything
		
		for (Enemy temp : enemies){
			
			if (!temp.isDead()) {
				
				boolean isAlignedWithColumn = ((temp.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH) % (GridObject.EFFECTIVE_PIXEL_WIDTH * 2) == 0);
				
				boolean isAlignedWithRow = ((temp.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT) % (GridObject.EFFECTIVE_PIXEL_HEIGHT * 2) == 0);
                    
				/* because enemies can spawn in places where they can't move 
				 * for example, if an enemmy spawns with two concrete blocks to the left and right of it
				 * and its direction is left or right, it will not move.
				 * to fix this, we check if it is between two concrete blocks, either to the left and right,
				 * or up and down. Then we check if its direction causes it to be stuck, and then flip it
				 * if it is
				 */
				
				if (isAlignedWithColumn &&(!(isAlignedWithRow))){   
					
					if (temp.getDirection() > 1){ 
						
						temp.setDirection(0);
					}
				}
				
				if (isAlignedWithRow && (!(isAlignedWithColumn))){
					
					if (temp.getDirection() < 2){
						
						temp.setDirection(2);
					}
				}
				
				/* Now we check for random changes in path. If the enemy
				 * has an intelligence level of higher than 1, we check if
				 * it is at an intersection. This is done by a combination
				 * of seeing if it is in the right spots with regards to
				 * concrete blocks (if you have two on either side or up and down
				 * you are definitely not at an intersection), and then checking
				 * if the intersection is blocked by bombs or bricks using 
				 * randomChangeCheck.
				 */
				
				if (temp.getSmartness() > 1){
					
					if (isAlignedWithColumn && isAlignedWithRow){
						
						collisionCount = 0;
						
						if (!(temp.wallpass)){
							
							for (Brick brick:bricks)
								randomChangeCheck(temp,brick);
							for (Bomb bomb:bombs)
								randomChangeCheck(temp,bomb);  
						}
						
						// if there have been less than 2 closed spots around the enemy it means there is an intersection
						if (collisionCount < 2)
							randomChange(temp);
						
					}
				}
				
				// if the enemy's intelligence is > 2, it can search for bomberman
				
				if (temp.getSmartness() > 1) {
					
					checkForBomberman(temp);
				}
				
				//the enemy moves forward in the direction it is facing at its own speed
				moveEnemy(temp);
				
				collision = false;
				
				/* Due to how our setXPosition and setYPosition methods work,
				 * just moving the enemy and checking if it is colliding won't work
				 * with concrete, because we don't allow anything to move into concrete.
				 * 
				 * As such, if the enemy tried to move into concrete concreteCollision will
				 * be true, so we can just check that to tell if there was a collision
				 */
				if (temp.isConcreteCollision())
					collision = true;
				
				if (!(temp.wallpass)){
					/* Now we check if any bricks or bombs are colliding with the enemy due to 
					 * its initial movement
					 */        
					for (Brick brick: bricks)
						AICollisionCheck(temp,brick);
					
					for (Bomb bomb : bombs)
						AICollisionCheck(temp,bomb);
				}   
				
				/* If there was a collision, we move the enemy back to where it started and
				 * turn it around.
				 */
				if (collision)
					turnAround(temp);
			}
		}
	}
	
	/* This method just uses direction to feed the two objects, enemy and whatever else,
	* into CollisionDetector.
	*/
	private void AICollisionCheck(Enemy e, GridObject go ) {
		
		if (e.getDirection() == 0) {
			
			if (detector.checkUpCollision(e,go))
				collision = true;
		}
		
		else if (e.getDirection() == 1) {
			
			if (detector.checkDownCollision(e,go))
				collision = true;
		}
		
		else if (e.getDirection( )== 2){
			
			if (detector.checkLeftCollision(e,go))
				collision = true;
		}
		
		else if (e.getDirection() == 3){
			
			if (detector.checkRightCollision(e,go))
				collision = true;
		}
	}
	
	//moves an enemy backward, then reverses its direction   
	private void turnAround(Enemy e) {
		
		if (e.getDirection() == 0) {
			
			e.setYPosition(e.getYPosition() + e.getSpeed());
			
			e.setDirection(1);
		}
		
		else if (e.getDirection() == 1) {
			
			e.setYPosition(e.getYPosition() - e.getSpeed());
			
			e.setDirection(0);
		}
		
		else if (e.getDirection() == 2) {
			
			e.setXPosition(e.getXPosition() + e.getSpeed());
			
			e.setDirection(3);
		}
		
		else if (e.getDirection() == 3) {
			
			e.setXPosition(e.getXPosition() - e.getSpeed());
			
			e.setDirection(2);
		}
	}
	
	//moves an enemy in the direction it is facing
	private void moveEnemy(Enemy e) {
		
		if (e.getDirection()== 0)
			e.setYPosition(e.getYPosition()-e.getSpeed());
		
		else if (e.getDirection()== 1)
			e.setYPosition(e.getYPosition() + e.getSpeed());
		
		else if (e.getDirection()== 2)
			e.setXPosition(e.getXPosition() - e.getSpeed());
		
		else if (e.getDirection()== 3)
			e.setXPosition(e.getXPosition() + e.getSpeed());
		
	}
	
	/* This method checks if there are objects to the relative left and right of the enemy
	* This is used to determine whether there is an intersection or not
	*/
	private void randomChangeCheck(Enemy e,GridObject go) {
		
		if (e.getDirection()<2){
			
			if ((go.getXPosition() == e.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH) && (go.getYPosition() == e.getYPosition()))
				collisionCount++;
			
			if ((go.getXPosition() == e.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH) && (go.getYPosition() == e.getYPosition()))
				collisionCount++;
		}
		
		else if (e.getDirection()>1){ 
			
			if ((go.getYPosition() == e.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT) && (go.getXPosition() == e.getXPosition()))
				collisionCount++;
			
			if ((go.getYPosition() == e.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT) && (go.getXPosition() == e.getXPosition()))
				collisionCount++;
			
		}
	}
	
	/* This method has a certain chance, depending on the enemy's 
	* intelligence, to change the enemy's direction.
	*/
	private void randomChange(Enemy e){
		
		int chance = 0;
		
		if (e.getSmartness() == 2)
			chance = 8;
		
		if (e.getSmartness() == 3)
			chance = 4;
		
		Random rn = new Random();
		
		int random = rn.nextInt(10);
		
		if (random > chance){
			
			int newDirection = rn.nextInt(4);
			
			e.setDirection(newDirection);
		}
	}
	
	/* This method is used to check if bomberman is within range, and if
	* he is to take action based on intelligence level
	*/
	private void checkForBomberman(Enemy e){
		
		if (e.getSmartness() == 3) {
			
			//if bomberman is within 2 range
			if((Math.abs(bomberman.getXPosition()- e.getXPosition()) <= 2 * GridObject.EFFECTIVE_PIXEL_WIDTH)
					&&(Math.abs(bomberman.getYPosition() - e.getYPosition()) <= 2 * GridObject.EFFECTIVE_PIXEL_HEIGHT)){
				
				/* Wallpass makes it unnecessary to use AStar, so we only do it if
				 * the enemy doesn't have wallpass
				 */
				if(!e.wallpass){
					
					/* we use AStar to find a path. If the path exists, we trace it back 
					 * until we have a Node that is one move from our enemy's current position
					 * We figure out what kind of move will get us there, and set the enemy to 
					 * the corresponding direction
					 */
					
					Node Direction = AStar(e,bomberman);
					
					if(Direction != null){
						
						if(Direction.parent != null){
							
							while(!((Direction.parent.xPosition == (((e.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH / 2) / GridObject.EFFECTIVE_PIXEL_WIDTH) * GridObject.EFFECTIVE_PIXEL_WIDTH))
									&&(Direction.parent.yPosition == (((e.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT / 2) / GridObject.EFFECTIVE_PIXEL_HEIGHT) * GridObject.EFFECTIVE_PIXEL_HEIGHT))))
								Direction = Direction.parent;
							
							if(Direction.xPosition == ((e.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH / 2) / GridObject.EFFECTIVE_PIXEL_WIDTH)*GridObject.EFFECTIVE_PIXEL_WIDTH){
								
								if (Direction.yPosition < e.getYPosition())
									e.setDirection(0);
								
								if (Direction.yPosition > e.getYPosition())
									e.setDirection(1);
								
							}
							
							if(Direction.yPosition == ((e.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT / 2) / GridObject.EFFECTIVE_PIXEL_HEIGHT) * GridObject.EFFECTIVE_PIXEL_HEIGHT){
								
								if (Direction.xPosition < e.getXPosition())
									e.setDirection(2);
								
								if (Direction.xPosition > e.getXPosition())
									e.setDirection(3);
							}
						}
					}
				}
				
				/* Otherwise we just send the enemy towards bomberman
				 * first matching its x coordinate, and then moving to
				 * match it's y coordinate.
				 */
				else {
					if (bomberman.getXPosition() == e.getXPosition()) {
						
						if (bomberman.getYPosition() > e.getYPosition())
							e.setDirection(1);
						
						if (bomberman.getYPosition() < e.getYPosition())
							e.setDirection(0);
					}
					
					else if (bomberman.getXPosition() < e.getXPosition())
						e.setDirection(2);
					
					else if (bomberman.getXPosition() > e.getXPosition())
						e.setDirection(3);
				}
			}
		}
		
		/* If the enemy only has intelligence = 2, then we check if bomberman
		 * is within one range. If he is, we set the enemy's direction to face him
		 */
		else if (bomberman.getXPosition() == e.getXPosition()){
			
			if ((bomberman.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT <= e.getYPosition())
					&&(e.getYPosition() < bomberman.getYPosition()))
				e.setDirection(1);
			
			if ((bomberman.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT >= e.getYPosition())
					&&(e.getYPosition() > bomberman.getYPosition()))
				e.setDirection(0);
		}
		
		else if (bomberman.getYPosition() == e.getYPosition()){
			
			if ((bomberman.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH <= e.getXPosition())&&(e.getXPosition() < bomberman.getXPosition()))
				e.setDirection(3);
			
			if ((bomberman.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH >= e.getXPosition())&&(e.getXPosition() > bomberman.getXPosition()))
				e.setDirection(2);
		}
	}
	
	/* This is just AStar. The only thing of note is that we round up to the nearest cell width
	* so that we can use standard grid sizes for searching
	*/
	private Node AStar(Enemy e, Bomberman b){
		
		LinkedList<Node> openList = new LinkedList<Node>();
		LinkedList<Node> closedList = new LinkedList<Node>();
		
		int correctedX = ((e.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH / 2) / GridObject.EFFECTIVE_PIXEL_WIDTH) * (GridObject.EFFECTIVE_PIXEL_WIDTH);
		int correctedY = ((e.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT / 2) / GridObject.EFFECTIVE_PIXEL_HEIGHT) * (GridObject.EFFECTIVE_PIXEL_HEIGHT);
		
		Node startNode = new Node(correctedX, correctedY, null, 0, EuclidianDistance(e.getXPosition(),e.getYPosition(), b.getXPosition(),b.getYPosition()));
		
		openList.add(startNode);
		
		while (!openList.isEmpty()){
			
			Node current = openList.removeFirst();
			
			closedList.add(current);
			
			int bCorrectedX = (b.getXPosition()/GridObject.EFFECTIVE_PIXEL_WIDTH)*(GridObject.EFFECTIVE_PIXEL_WIDTH);
			int bCorrectedY = (b.getYPosition()/GridObject.EFFECTIVE_PIXEL_HEIGHT)*(GridObject.EFFECTIVE_PIXEL_HEIGHT);
			
			if ((current.xPosition == bCorrectedX) &&(current.yPosition == bCorrectedY))
				return current;
			
			boolean obstacle;    
			
			boolean isAlignedWithColumn = ((current.xPosition - GridObject.EFFECTIVE_PIXEL_WIDTH) % (GridObject.EFFECTIVE_PIXEL_WIDTH * 2)==0);
			boolean isAlignedWithRow = ((current.yPosition - GridObject.EFFECTIVE_PIXEL_HEIGHT) % (GridObject.EFFECTIVE_PIXEL_HEIGHT * 2) == 0);
			
			for (int i = 0; i < 4; i++){
				
				obstacle = false;
				if (i == 0) {
					
					if (current.yPosition <= GridObject.EFFECTIVE_PIXEL_HEIGHT)
						obstacle = true;
					
					if (isAlignedWithColumn) {
						
						for (Brick brick: bricks){
							
							if ((brick.getXPosition()== current.xPosition)&&(brick.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT == current.yPosition))
								obstacle = true;
						}
						
						for (Bomb bomb: bombs){
							
							if ((bomb.getXPosition()== current.xPosition)&&(bomb.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT == current.yPosition))
								obstacle = true;
						}
						
						if (!obstacle) {
							
							Node next = new Node(current.xPosition,current.yPosition - GridObject.EFFECTIVE_PIXEL_HEIGHT,current,current.gCost + 10,
									EuclidianDistance(current.xPosition,current.yPosition - GridObject.EFFECTIVE_PIXEL_HEIGHT,bomberman.getXPosition(),bomberman.getYPosition()));
							
							if (!(alreadyIn(openList,next))) {
								if (!(alreadyIn(closedList,next))){
									
									openList.add(next);
									
									sortList(openList);
								}
							}
						}
					}
				}
				
				else if (i == 1){
					
					if (current.yPosition >= GridObject.EFFECTIVE_PIXEL_HEIGHT * 13)
						obstacle = true;
					
					if (isAlignedWithColumn){
						
						for (Brick brick: bricks){
							
							if ((brick.getXPosition()== current.xPosition) && (brick.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT == current.yPosition))
								obstacle = true;
							
						}
						
						for (Bomb bomb: bombs){
							
							if ((bomb.getXPosition()== current.xPosition) && (bomb.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT == current.yPosition))
								obstacle = true;
							
						}
						
						if (!obstacle) {
							
							Node next = new Node(current.xPosition,current.yPosition + GridObject.EFFECTIVE_PIXEL_HEIGHT,current,current.gCost + 10,
									EuclidianDistance(current.xPosition,current.yPosition + GridObject.EFFECTIVE_PIXEL_HEIGHT,bomberman.getXPosition(),bomberman.getYPosition()));
							
							if (!(alreadyIn(openList,next))) {
								if (!(alreadyIn(closedList,next))) {
									
									openList.add(next);
									
									sortList(openList);
								}
							}
						}
					}
				}
				
				else  if (i == 2){
					
					if (current.xPosition<=GridObject.EFFECTIVE_PIXEL_WIDTH)
						obstacle = true;
					
					if (isAlignedWithRow){
						
						for (Brick brick: bricks){
							
							if ((brick.getYPosition()== current.yPosition) && (brick.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH == current.xPosition))
								obstacle = true;
						}
						
						for (Bomb bomb: bombs){
							
							if ((bomb.getYPosition()== current.yPosition) && (bomb.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH == current.xPosition))
								obstacle = true;
						}
						
						if (!obstacle) {
							
							Node next = new Node(current.xPosition - GridObject.EFFECTIVE_PIXEL_WIDTH,current.yPosition,current,current.gCost + 10,
									EuclidianDistance(current.xPosition - GridObject.EFFECTIVE_PIXEL_WIDTH,current.yPosition,bomberman.getXPosition(),bomberman.getYPosition()));
							
							if (!(alreadyIn(openList,next))) {
								if (!(alreadyIn(closedList,next))){
									
									openList.add(next);
									
									sortList(openList);
								}
							}
						}
					}
				}
				
				else  if (i == 3){
					
					if (current.xPosition >= GridObject.EFFECTIVE_PIXEL_WIDTH*13)
						obstacle = true;
					
					if (isAlignedWithRow){
						
						for (Brick brick: bricks) {
							
							if ((brick.getYPosition() == current.yPosition)&&(brick.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH == current.xPosition))
								obstacle = true;
						}
						
						for (Bomb bomb: bombs){
							
							if ((bomb.getYPosition() == current.yPosition) && (bomb.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH == current.xPosition))
								obstacle = true;
						}
						
						if (!obstacle){
							
							Node next = new Node(current.xPosition + GridObject.EFFECTIVE_PIXEL_WIDTH,current.yPosition,current,current.gCost + 10,
									EuclidianDistance(current.xPosition + GridObject.EFFECTIVE_PIXEL_WIDTH,current.yPosition,bomberman.getXPosition(),bomberman.getYPosition()));
							
							if (!(alreadyIn(openList,next))) {
								if (!(alreadyIn(closedList,next))){
									
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
	private double EuclidianDistance(int startX, int startY, int goalX, int goalY){
		
		return Math.sqrt(Math.pow(goalY - startY,2) + Math.pow(goalX - startX,2));
	}
	
	//sorts a list according to fCost
	private void sortList(LinkedList<Node> l){
		
		for (int k = 0;k < l.size() - 1;k++){
			
			if (l.get(k).fCost > l.get(k+1).fCost){
				
				Node temp = l.get(k);
				
				l.set(k, l.get(k + 1));
				
				l.set(k + 1, temp);
			}
		}
	}
	
	//returns true if the node already exists in the list
	private boolean alreadyIn(LinkedList<Node> l, Node n){
		
		for (Node node:l){
			if (n.xPosition == node.xPosition)
				if (n.yPosition == node.yPosition)
					return true;
		}
		return false;
	}
}
