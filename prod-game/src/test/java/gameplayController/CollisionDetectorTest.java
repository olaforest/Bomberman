package gameplayController;

import static org.junit.Assert.assertEquals;
import static utilities.Position.modulus;

import gameplayModel.gridObjects.animatedObjects.Bomb;
import org.junit.Test;

public class CollisionDetectorTest {

	@Test
	public void testCheckExplBricks() {
		Bomb bomb = new Bomb(modulus(7, 7));

//		assertEquals((checkExplBricks(bomb).indexOf(0)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(1)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(2)), -1);
//		assertEquals((checkExplBricks(bomb).indexOf(3)), -1);

//		assertEquals((checkExplBricks(bomb).indexOf(0)), -1);
		assertEquals(bomb.getRightRange(), 0);
	}
}
