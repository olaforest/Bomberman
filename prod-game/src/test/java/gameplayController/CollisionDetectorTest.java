package gameplayController;

import static gameplayController.CollisionDetector.checkExactCollision;
import static org.junit.Assert.*;
import static utilities.Position.create;
import static utilities.Position.modulus;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import org.junit.Test;

public class CollisionDetectorTest {

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
