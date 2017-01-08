package gameplayModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LevelManager {
	private static final List<Level> LEVELS = Levels.getLevels();

	private int currentLevelIndex;
	private final Supplier<Level> currentLevel = () -> LEVELS.get(currentLevelIndex);

	public Level getLevel() {
		return currentLevel.get();
	}

	public void increaseLevel() {
		currentLevelIndex++;
	}

	public int getActualLevelIndex() {
		return currentLevelIndex - (int) IntStream.range(0, currentLevelIndex)
				.mapToObj(LEVELS::get)
				.filter(Level::isBonusLevel)
				.count();
	}

	public boolean isBonusLevel() {
		return currentLevel.get().isBonusLevel();
	}

	public OptionalInt getHardestEnemyType() {
		return currentLevel.get().getHardestEnemyType();
	}
}
