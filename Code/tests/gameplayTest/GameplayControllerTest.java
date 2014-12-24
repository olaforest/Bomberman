package gameplayTest;
import static org.junit.Assert.assertTrue;
import menuController.MenuController;

import org.junit.Before;
import org.junit.Test;

import gameplayController.GameplayController;


public class GameplayControllerTest {
	
	private GameplayController gameCtrl;
	private MenuController menuCtrl;
	
	@Before
	public void setUp() {
		menuCtrl = new MenuController();
		gameCtrl = new GameplayController(menuCtrl);
	}
	
	@Test
	public void testGetGameContext() {
		assertTrue(gameCtrl.getGameContext().getClass().toString().equals("class gameplayModel.GameContext"));
	}
}
