package gameplayModel;

import org.testng.annotations.Test;

import java.util.List;

import static gameplayModel.Levels.getLevels;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelsTest {

	private static final int TOTAL_NUMBER_OF_LEVELS = 60;
	private static final int NUMBER_OF_BONUS_LEVELS = 10;

	@Test
	public void levelsLoaded_getLevels_returnsCompleteGameLevels() {
		//when
		final List<Level> levels = getLevels();
		//then
		assertThat(levels).hasSize(TOTAL_NUMBER_OF_LEVELS);
	}

	@Test
	public void levelsLoaded_getLevelsAndFilterByBonusLevel_returnsBonusLevels() {
		//given
		final List<Level> levels = getLevels();
		//when
		final List<Level> bonusLevels = levels.stream()
				.filter(Level::isBonusLevel)
				.collect(toList());
		//then
		assertThat(bonusLevels).hasSize(NUMBER_OF_BONUS_LEVELS);
	}
}
