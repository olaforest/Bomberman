package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
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

}