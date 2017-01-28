package gameplayController;

import gameplayModel.GameContext;
import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static utilities.Position.create;
import static utilities.Position.modulus;

public class CollisionDetectorTest {

	private static CollisionDetector detector;

	@BeforeClass
	public static void setUpClass() {
		GameContext gC = new GameContext();
		detector = new CollisionDetector(new GameContext());
	}

	@Test
	public void testCheckUpCollision() {
		GridObject a;
		GridObject b;
		assertFalse(detector.checkUpCollision(null, null));

		a = new GridObject(create(0, 100));
		b = new GridObject(create(0, 150));
		assertFalse(detector.checkUpCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 80;
		assertTrue(detector.checkUpCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 60;
		assertFalse(detector.checkUpCollision(a, b));
	}

	@Test
	public void testCheckDownCollision() {
		GridObject a;
		GridObject b;
		assertFalse(detector.checkDownCollision(null, null));

		a = new GridObject(create(0, 100));
		b = new GridObject(create(0, 150));
		assertFalse(detector.checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 80;
		assertFalse(detector.checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 120;
		assertTrue(detector.checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 150;
		assertFalse(detector.checkDownCollision(a, b));
	}

	@Test
	public void testCheckLeftCollision() {
		GridObject a;
		GridObject b;
		assertFalse(detector.checkLeftCollision(null, null));

		a = new GridObject(create(100, 0));
		b = new GridObject(create(150, 0));
		assertFalse(detector.checkLeftCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 80;
//		b.yPosition = 0;
		assertTrue(detector.checkLeftCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 60;
//		b.yPosition = 0;
		assertFalse(detector.checkUpCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 120;
//		b.yPosition = 0;
		assertFalse(detector.checkUpCollision(a, b));
	}

	@Test
	public void testCheckExplBricks() {
		Bomb bomb = new Bomb(modulus(7, 7));

//		assertEquals((detector.checkExplBricks(bomb).indexOf(0)), -1);
//		assertEquals((detector.checkExplBricks(bomb).indexOf(1)), -1);
//		assertEquals((detector.checkExplBricks(bomb).indexOf(2)), -1);
//		assertEquals((detector.checkExplBricks(bomb).indexOf(3)), -1);
//
//		assertEquals((detector.checkExplBricks(bomb).indexOf(0)), -1);
		assertEquals(bomb.getRightRange(), 0);
	}

	@Test
	public void testCheckExactCollision() {
		Bomberman bomberman = new Bomberman(create(0, 0));
		GridObject a = new GridObject(create(0, 0));
		assertTrue(detector.checkExactCollision(bomberman, a));

		GridObject b = new GridObject(create(2, 2));
		assertTrue(detector.checkExactCollision(bomberman, b));

		Bomberman testNull = null;
		GridObject c = null;
		assertFalse(detector.checkExactCollision(null, null));
	}
}
