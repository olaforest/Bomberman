package gameplayModel;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LevelManagerTest {

	@Test
	public void newLevelManager_returnsNewInstanceWithCurrentLevelBeingTheFirstLevelOfTheGame() {
		//when
		final LevelManager levelManager = new LevelManager();
		//then
		assertThat(levelManager.getCurrentLevel()).isEqualTo(0);
	}

	@Test
	public void currentLevelArg_newLevelManager_returnsNewInstanceWithCorrespondingCurrentLevel() {
		//given
		final int currentLevel = 10;
		//when
		final LevelManager levelManager = new LevelManager(currentLevel);
		//then
		assertThat(levelManager.getCurrentLevel()).isEqualTo(10);
	}
}
