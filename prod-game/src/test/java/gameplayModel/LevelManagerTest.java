package gameplayModel;

import gameplayModel.gridObjects.animatedObjects.EnemyType;
import org.testng.annotations.Test;

import java.util.List;

import static gameplayModel.LevelManager.importLevels;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.Doll;
import static gameplayModel.gridObjects.animatedObjects.EnemyType.Pass;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelManagerTest {
	private static final int TOTAL_NUMBER_OF_LEVELS = 60;
	private static final int NUMBER_OF_BONUS_LEVELS = 10;
	private static final List<Level> LEVELS = importLevels();

	@Test
	public void importLevels_returnsCompleteGameLevels() {
		//when
		final List<Level> levels = importLevels();
		//then
		assertThat(levels).hasSize(TOTAL_NUMBER_OF_LEVELS);
	}

	@Test
	public void importLevelsAndFilterByBonusLevel_returnsBonusLevels() {
		//given
		final List<Level> levels = importLevels();
		//when
		final List<Level> bonusLevels = levels.stream()
				.filter(Level::isBonusLevel)
				.collect(toList());
		//then
		assertThat(bonusLevels).hasSize(NUMBER_OF_BONUS_LEVELS);
	}

	@Test
	public void newLevelManager_returnsNewInstanceWithCurrentLevelBeingTheFirstLevelOfTheGame() {
		//when
		final LevelManager levelManager = new LevelManager();
		//then
		assertThat(levelManager.getLevel()).isEqualTo(LEVELS.get(0));
		assertThat(levelManager.getCurrentLevelIndex()).isEqualTo(0);
	}

	@Test
	public void currentLevelArg_newLevelManager_returnsNewInstanceWithCorrespondingCurrentLevel() {
		//given
		final int currentLevel = 10;
		//when
		final LevelManager levelManager = new LevelManager(currentLevel);
		//then
		assertThat(levelManager.getLevel()).isEqualTo(LEVELS.get(10));
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

	@Test
	public void bonusCurrentLevel_isBonusLevel_returnsTrue() {
		//given
		final LevelManager levelManager = new LevelManager(5);
		//when
		final boolean result = levelManager.isBonusLevel();
		//then
		assertThat(result).isTrue();
	}

	@Test
	public void nonBonusCurrentLevel_isBonusLevel_returnsFalse() {
		//given
		final LevelManager levelManager = new LevelManager(4);
		//when
		final boolean result = levelManager.isBonusLevel();
		//then
		assertThat(result).isFalse();
	}

	@Test
	public void currentLevelIndexIs4_getHardestEnemyType_returnsEnemyTypeOf2() {
		//given
		final LevelManager levelManager = new LevelManager(4);
		//when
		final EnemyType type = levelManager.getHardestEnemyType();
		//then
		assertThat(type).isEqualTo(Doll);
	}

	@Test
	public void currentLevelIndexIs21_getHardestEnemyType_returnsEnemyTypeOf6() {
		//given
		final LevelManager levelManager = new LevelManager(21);
		//when
		final EnemyType type = levelManager.getHardestEnemyType();
		//then
		assertThat(type).isEqualTo(Pass);
	}
}
