package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;

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
				.hasMessage("Trying to instantiate a level with invalid level specifications")
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void superfluousElementsInLevelSpecList_newLevelInstance_throwsException() {
		//given
		final List<Integer> levelSpec = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		//when
		assertThatThrownBy(() -> new Level(levelSpec))
				//then
				.hasMessage("Trying to instantiate a level with invalid level specifications")
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void validLevelSpecList_newLevelInstance_resultsInValidNewLevel() {
		//given
		final List<Integer> levelSpec = asList(1, 1, 1, 1, 1, 1, 1, 1, 0);
		//when
		final Level level = new Level(levelSpec);
		//then
		assertThat(level.getEnemiesType()).containsExactly(1, 1, 1, 1, 1, 1, 1, 1);
		assertThat(level.getPowerUpType()).isEqualTo(0);
	}
}