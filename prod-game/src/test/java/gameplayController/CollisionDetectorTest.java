package gameplayController;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import org.junit.Test;

import static gameplayController.CollisionDetector.*;
import static org.junit.Assert.*;
import static utilities.Position.create;
import static utilities.Position.modulus;

public class CollisionDetectorTest {

	@Test
	public void testCheckUpCollision() {
		GridObject a;
		GridObject b;
		assertFalse(checkUpCollision(null, null));

		a = new GridObject(create(0, 100));
		b = new GridObject(create(0, 150));
		assertFalse(checkUpCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 80;
		assertTrue(checkUpCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 60;
		assertFalse(checkUpCollision(a, b));
	}

	@Test
	public void testCheckDownCollision() {
		GridObject a;
		GridObject b;
		assertFalse(checkDownCollision(null, null));

		a = new GridObject(create(0, 100));
		b = new GridObject(create(0, 150));
		assertFalse(checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 80;
		assertFalse(checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 120;
		assertTrue(checkDownCollision(a, b));

//		a.xPosition = 0;
//		a.yPosition = 100;
//		b.xPosition = 0;
//		b.yPosition = 150;
		assertFalse(checkDownCollision(a, b));
	}

	@Test
	public void testCheckLeftCollision() {
		GridObject a;
		GridObject b;
		assertFalse(checkLeftCollision(null, null));

		a = new GridObject(create(100, 0));
		b = new GridObject(create(150, 0));
		assertFalse(checkLeftCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 80;
//		b.yPosition = 0;
		assertTrue(checkLeftCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 60;
//		b.yPosition = 0;
		assertFalse(checkUpCollision(a, b));

//		a.xPosition = 100;
//		a.yPosition = 0;
//		b.xPosition = 120;
//		b.yPosition = 0;
		assertFalse(checkUpCollision(a, b));
	}

	@Test
	public void testCheckExplBricks() {
		Bomb bomb = new Bomb(modulus(7, 7));

//		assertEquals((checkExplBricks(bomb).indexOf(0)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(1)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(2)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(3)), -1);
//
//		assertEquals((checkExplBricks(bomb).indexOf(0)), -1);
		assertEquals(bomb.getRightRange(), 0);
	}

	@Test
	public void testCheckExactCollision() {
		Bomberman bomberman = new Bomberman(create(0, 0));
		GridObject a = new GridObject(create(0, 0));
		assertTrue(checkExactCollision(bomberman, a));

		GridObject b = new GridObject(create(2, 2));
		assertTrue(checkExactCollision(bomberman, b));

		Bomberman testNull = null;
		GridObject c = null;
		assertFalse(checkExactCollision(null, null));
	}
}
