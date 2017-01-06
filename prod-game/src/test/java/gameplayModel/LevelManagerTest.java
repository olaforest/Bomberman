package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;

import static gameplayModel.Levels.getLevels;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelManagerTest {

	private static final List<Level> LEVELS = getLevels();

	@Test
	public void newLevelManager_returnsNewInstanceWithCurrentLevelBeingTheFirstLevelOfTheGame() {
		//when
		final LevelManager levelManager = new LevelManager();
		//then
		assertThat(levelManager.getCurrentLevel()).isEqualTo(LEVELS.get(0));
		assertThat(levelManager.getCurrentLevelIndex()).isEqualTo(0);
	}

	@Test
	public void currentLevelArg_newLevelManager_returnsNewInstanceWithCorrespondingCurrentLevel() {
		//given
		final int currentLevel = 10;
		//when
		final LevelManager levelManager = new LevelManager(currentLevel);
		//then
		assertThat(levelManager.getCurrentLevel()).isEqualTo(LEVELS.get(10));
		assertThat(levelManager.getCurrentLevelIndex()).isEqualTo(10);
	}

	@Test
	public void givenCurrentLevel_increaseLevel_currentLevelIsNextLevel() {
		//given
		final LevelManager levelManager = new LevelManager(2);
		//when
		levelManager.increaseLevel();
		//then
		assertThat(levelManager.getCurrentLevelIndex()).isEqualTo(3);
	}

	@Test
	public void givenCurrentLevelJustBeforeBonusLevel_getActualLevel_returnsCurrentLevelIndexMinusBonusLevels() {
		//given
		final LevelManager levelManager = new LevelManager(45);
		//when
		final int actualLevelIndex = levelManager.getActualLevelIndex();
		//then
		assertThat(actualLevelIndex).isEqualTo(38);
	}

	@Test
	public void givenCurrentLevelJustAfterBonusLevel_getActualLevel_returnsCurrentLevelIndexMinusBonusLevels() {
		//given
		final LevelManager levelManager = new LevelManager(47);
		//when
		final int actualLevelIndex = levelManager.getActualLevelIndex();
		//then
		assertThat(actualLevelIndex).isEqualTo(39);
	}

	@Test
	public void bonusCurrentLevel_getActualLevel_returnsLevelIndexOfNextNonBonusLevelMinusBonusLevels() {
		//given
		final LevelManager levelManager = new LevelManager(46);
		//when
		final int actualLevelIndex = levelManager.getActualLevelIndex();
		//then
		assertThat(actualLevelIndex).isEqualTo(39);
	}
}
