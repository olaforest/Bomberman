package gameplayControllerTest;

import gameplayController.GameplayController;
import menuController.MenuController;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


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
		assertThat(menuCtrl).as("Test").isNull();
	}
}
