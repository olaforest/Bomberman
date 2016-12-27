package gameplayModel;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static utilities.CsvUtils.readResourcesCSV;

public class Levels {
	private static List<Level> levels = importLevels();

	private static List<Level> importLevels() {
		return readResourcesCSV("levelSpecifications").stream()
				.map(Level::new)
				.collect(toList());
	}

	public static List<Level> getLevels() {
		return levels;
	}
}
