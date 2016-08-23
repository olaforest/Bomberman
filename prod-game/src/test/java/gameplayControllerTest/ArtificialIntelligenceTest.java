package gameplayControllerTest;

import gameplayController.ArtificialIntelligence;
import gameplayController.CollisionDetector;
import gameplayModel.GameContext;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemies.Balloom;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.assertEquals;

public class ArtificialIntelligenceTest {

	private ArtificialIntelligence AI;
	private Balloom balloom;
	private ArrayList<Enemy> e;
	private ArrayList<Brick> br;
	private ArrayList<Bomb> bo;
	private CollisionDetector cD;
	private Bomberman b;

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Before
	public void setUp() {
		e = new ArrayList();
		br = new ArrayList();
		bo = new ArrayList();
		cD = new CollisionDetector(new GameContext());
		b = new Bomberman(31 * EFFECTIVE_PIXEL_DIMENSION, 13 * EFFECTIVE_PIXEL_DIMENSION);

		AI = new ArtificialIntelligence(b, e, br, bo, cD);
	}

	@Test
	public void testMoveUnobstructedUp() {
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 2, 0);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();
		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 2 - balloom.getSpeed());
	}

	@Test
	public void testMoveUnobstructedDown() {// tests whether, without an obstacle an enemy with direction down will move down
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 1);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();
		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
	}

	@Test
	public void testMoveUnobstructedLeft() { // tests whether, without an obstacle an enemy with direction left will move left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 2, EFFECTIVE_PIXEL_DIMENSION, 2);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();
		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 2 - balloom.getSpeed());

		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testMoveUnobstructedRight() { // tests whether, without an obstacle an enemy with direction right will move right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 3);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();
		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testMoveUpperBounds() {//tests whether an enemy going up into the boundary will turn around
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 0);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);

		//the extra + speed here has to do with the way GridObjects handle the setXPosition/setYPosition method.
		//because the true position of the enemy is in the top left corner, when told to move into concrete it will not.
		//combine that with the turn around method telling it to move back where it came, and you have an extra addition of +speed
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
	}

	@Test
	public void testMoveLowerBounds() {//tests whether an enemy going down into the boundary will turn around
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 13, 1);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 13);
	}

	@Test
	public void testMoveLeftBounds() { //tests whether an enemy going left into the boundary will turn around
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 2);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		//the extra + speed here has to do with the way GridObjects handle the setXPosition/setYPosition method.
		//because the true position of the enemy is in the top left corner, when told to move into concrete it will not.
		//combine that with the turn around method telling it to move back where it came, and you have an extra addition of +speed
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testMoveRightBounds() { //tests whether an enemy going right into the boundary will turn around
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 31, EFFECTIVE_PIXEL_DIMENSION, 3);

		e.clear();
		e.add(balloom);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 31);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testRightCollision() { //tests what happens when a brick or bomb is in the path of the enemy going right
		// when a brick is to the right of the enemy and it is going right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 3);

		br.clear();
		e.clear();

		e.add(balloom);
		br.add(new Brick(EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed(), EFFECTIVE_PIXEL_DIMENSION));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);

		//when a bomb is to the right of the enemy and it is going right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 3);

		e.clear();
		e.add(balloom);
		bo.clear();
		bo.add(new Bomb(EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed(), EFFECTIVE_PIXEL_DIMENSION));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testLeftCollision() { //tests what happens when a brick or bomb is in the path of the enemy going left
		// when a brick is to the left of the enemy and it is going left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 6, EFFECTIVE_PIXEL_DIMENSION, 2);

		br.clear();
		e.clear();

		e.add(balloom);
		br.add(new Brick(EFFECTIVE_PIXEL_DIMENSION * 6 - balloom.getSpeed(), EFFECTIVE_PIXEL_DIMENSION));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);

		//when a bomb is to the left of the enemy and it is going left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 6, EFFECTIVE_PIXEL_DIMENSION, 2);

		e.clear();
		e.add(balloom);
		bo.clear();
		bo.add(new Bomb(EFFECTIVE_PIXEL_DIMENSION * 6 - balloom.getSpeed(), EFFECTIVE_PIXEL_DIMENSION));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testUpCollision() { //tests what happens when a brick or bomb is in the path of the enemy going up
		// when a brick is above the enemy and it is going up
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6, 0);

		br.clear();
		e.clear();

		e.add(balloom);
		br.add(new Brick(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6 - balloom.getSpeed()));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);

		//when a bomb is above the enemy and it is going up
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6, 0);

		e.clear();
		e.add(balloom);
		bo.clear();
		bo.add(new Bomb(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6 - balloom.getSpeed()));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);
	}

	@Test
	public void testDownCollision() { //tests what happens when a brick or bomb is in the path of the enemy going down
		// when a brick is below the enemy and it is going down
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6, 1);

		br.clear();
		e.clear();

		e.add(balloom);
		br.add(new Brick(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6 + balloom.getSpeed()));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);

		//when a bomb is below the enemy and it is going down
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6, 1);

		e.clear();
		e.add(balloom);
		bo.clear();
		bo.add(new Bomb(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 6 + balloom.getSpeed()));

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 6);
	}

	@Test
	public void testFindBombermanDown() {
		//going right with bomberman 2 squares below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);

		//going right with bomberman 1 squares below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());

		//going left with bomberman 2 squares below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION);

		//going left with bomberman 1 square below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());

		//going up with bomberman 2 squares below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 5, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 7);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 - balloom.getSpeed());

		//going up with bomberman 1 square below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 5, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 6);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 + balloom.getSpeed());

		//going down with bomberman 2 squares below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 5, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 7);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 + balloom.getSpeed());

		//going down with bomberman 1 square below
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 5, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 6);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 + balloom.getSpeed());
	}

	@Test
	public void testFindBombermanUp() {
		//going right with bomberman 2 squares above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 7, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 5);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7);

		//going right with bomberman 1 square above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION * 7, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 6);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7 - balloom.getSpeed());

		//going left with bomberman 2 squares above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 3, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going left with bomberman 1 square above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 3, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3 - balloom.getSpeed());

		//going down with bomberman 2 squares above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 7, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 5);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7 + balloom.getSpeed());

		//going down with bomberman 1 squares above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 7, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 6);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7 - balloom.getSpeed());

		//going up with bomberman 2 squares above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 7, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 5);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7 - balloom.getSpeed());

		//going up with bomberman 1 square above
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 5, EFFECTIVE_PIXEL_DIMENSION * 7, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 5);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 6);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 5);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 7 - balloom.getSpeed());
	}

	@Test
	public void testFindBombermanLeft() { //tests whether an enemy with intelligence 2 will move towards bomberman if he is 1 square left of it.
		// going right with bomberman 2 squares to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 9);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);

		//going right with bomberman 1 square to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 10);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);

		//going left with bomberman 2 squares to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 9);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);

		//going left with bomberman 1 square to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 10);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);

		//going down with bomberman 2 squares to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 9);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9 + balloom.getSpeed());

		//going down with bomberman 1 square to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 10);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);

		//going up with bomberman 2 squares to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 9);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9 - balloom.getSpeed());

		//going up with bomberman 1 square to the left
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 11, EFFECTIVE_PIXEL_DIMENSION * 9, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 10);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 9);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 11 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 9);
	}

	@Test
	public void testFindBombermanRight() {
		//going right with bomberman 2 squares to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 23);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going right with bomberman 1 square to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 3);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 22);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going left with bomberman 2 squares to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 23);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);


		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 2);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 - balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going left with bomberman 1 square to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 2);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 22);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going down with bomberman 2 squares to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 23);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 1);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3 + balloom.getSpeed());

		//going down with bomberman 1 square to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 1);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 22);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);

		//going up with bomberman 2 squares to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 23);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 0);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21);
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3 - balloom.getSpeed());

		//going up with bomberman 1 square to the right
		balloom = new Balloom(EFFECTIVE_PIXEL_DIMENSION * 21, EFFECTIVE_PIXEL_DIMENSION * 3, 0);

		e.clear();
		bo.clear();
		br.clear();
		e.add(balloom);

		b.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 22);
		b.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);

		AI.updateEnemiesPosition();

		assertEquals(balloom.getDirection(), 3);
		assertEquals(balloom.getXPosition(), EFFECTIVE_PIXEL_DIMENSION * 21 + balloom.getSpeed());
		assertEquals(balloom.getYPosition(), EFFECTIVE_PIXEL_DIMENSION * 3);
	}
}
