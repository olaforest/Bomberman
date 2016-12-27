package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;

import static gameplayModel.Levels.getLevels;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelsTest {

	@Test
	public void levelsLoaded_getLevels_returnsCompleteGameLevels() {
		//when
		final List<Level> levels = getLevels();
		//then
		assertThat(levels).hasSize(60);
	}
}
