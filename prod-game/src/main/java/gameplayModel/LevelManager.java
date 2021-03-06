package gameplayModel;

import static java.util.stream.Collectors.toList;
import static utilities.CsvUtils.readResourcesCSV;

import gameplayModel.gridObjects.animatedObjects.EnemyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utilities.ListUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LevelManager {
	private static final List<Level> levels = importLevels();

	private int currentLevelIndex;
	private final Supplier<Level> currentLevel = () -> levels.get(currentLevelIndex);

	public Level getLevel() {
		return currentLevel.get();
	}

	public void increaseLevel() {
		currentLevelIndex++;
	}

	int getActualLevelIndex() {
		return currentLevelIndex - (int) IntStream.range(0, currentLevelIndex)
				.mapToObj(levels::get)
				.filter(Level::isBonusLevel)
				.count();
	}

	public boolean isBonusLevel() {
		return currentLevel.get().isBonusLevel();
	}

	public EnemyType getHardestEnemyType() {
		return currentLevel.get().getHardestEnemyType();
	}

	public static List<Level> importLevels() {
		return readResourcesCSV("levelSpecifications").stream()
				.map(ListUtils::convertStringListToIntegerList)
				.map(Level::new)
				.collect(toList());
	}
}
