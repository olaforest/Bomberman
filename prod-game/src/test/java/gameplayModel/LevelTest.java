package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;
import java.util.OptionalInt;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LevelTest {

	@Test
	public void missingElementsInLevelSpecList_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 2, 3, 4, 5, 6, 7, 8);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Trying to instantiate a level with invalid level specifications");
	}

	@Test
	public void superfluousElementsInLevelSpecList_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Trying to instantiate a level with invalid level specifications");
	}

	@Test
	public void nullLevelSpecList_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = null;
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(NullPointerException.class)
				.hasMessage("specification");
	}

	@Test
	public void validLevelWithPowerUpSpecList_newLevelInstance_resultsInValidNewLevelWithPowerUp() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, 1, 1, 1, 1);
		//when
		final Level level = new Level(levelSpec);
		//then
		assertThat(level.getEnemiesCount()).containsExactly(1, 1, 1, 1, 1, 1, 1, 1);
		assertThat(level.getPowerUpType()).contains(1);
	}

	@Test
	public void validLevelWithoutPowerUpSpecList_newLevelInstance_resultsInValidNewLevelWithEmptyPowerUp() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, 1, 1, 1, 0);
		//when
		final Level level = new Level(levelSpec);
		//then
		assertThat(level.getEnemiesCount()).containsExactly(1, 1, 1, 1, 1, 1, 1, 1);
		assertThat(level.getPowerUpType()).isEmpty();
	}

	@Test
	public void specListWithPowerUpTypeBelowAcceptedRange_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, 1, 1, 1, -1);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid power up type. Value is outside defined range.");
	}

	@Test
	public void specListWithPowerUpTypeAboveAcceptedRange_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, 1, 1, 1, 9);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid power up type. Value is outside defined range.");
	}

	@Test
	public void specListWithNegativeEnemyCount_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, -1, 1, 1, 1);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Enemy count cannot be a negative value");
	}

	@Test
	public void bonusLevelSpecListWithMoreThanOneNonZeroEnemyCount_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(0, 0, MAX_VALUE, 0, 1, 0, 0, 0, 8);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Bonus levels can only have 1 non-zero enemy count value.");
	}

	@Test
	public void validBonusLevelSpecList_newLevelInstance_resultsInValidNewLevel() {
		//given
		final List<Integer> levelSpec = asList(0, MAX_VALUE, 0, 0, 0, 0, 0, 0, 3);
		//when
		final Level level = new Level(levelSpec);
		//then
		assertThat(level.getEnemiesCount()).containsExactly(0, MAX_VALUE, 0, 0, 0, 0, 0, 0);
		assertThat(level.getPowerUpType()).contains(3);
	}

	@Test
	public void regularLevel_isBonusLevel_returnsFalse() {
		//given
		final Level level = new Level(asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
		//when
		final boolean isBonusLevel = level.isBonusLevel();
		//then
		assertThat(isBonusLevel).isFalse();
	}

	@Test
	public void bonusLevel_isBonusLevel_returnsTrue() {
		//given
		final Level level = new Level(asList(MAX_VALUE, 0, 0, 0, 0, 0, 0, 0, 1));
		//when
		final boolean isBonusLevel = level.isBonusLevel();
		//then
		assertThat(isBonusLevel).isTrue();
	}

	@Test
	public void levelWithEnemies_getHardestEnemyType_returnsTypesOfHardestEnemy() {
		//given
		final Level level = new Level(asList(0, 1, 0, 1, 1, 0, 1, 0, 3));
		//when
		final OptionalInt type = level.getHardestEnemyType();
		//then
		assertThat(type).hasValue(6);
	}

	@Test
	public void levelWithNoEnemies_getHardestEnemyType_returnsEmptyOptional() {
		//given
		final Level level = new Level(asList(0, 0, 0, 0, 0, 0, 0, 0, 3));
		//when
		final OptionalInt type = level.getHardestEnemyType();
		//then
		assertThat(type).isEmpty();
	}
}