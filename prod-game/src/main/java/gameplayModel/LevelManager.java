package gameplayModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

import static gameplayModel.Levels.getLevels;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LevelManager {
	private static final List<Level> LEVELS = getLevels();

	private int currentLevelIndex;

	public Level getCurrentLevel() {
		return LEVELS.get(currentLevelIndex);
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
}
