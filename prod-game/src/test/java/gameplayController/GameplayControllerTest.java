package gameplayController;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class GameplayControllerTest {

	private GameplayController gameCtrl;

	@Before
	public void setUp() {
		gameCtrl = new GameplayController();
	}

	@Test
	public void testGetGameContext() {
		assertTrue(gameCtrl.getGameContext().getClass().toString().equals("class gameplayModel.GameContext"));
	}
}
