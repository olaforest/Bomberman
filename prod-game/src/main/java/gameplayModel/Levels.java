package gameplayModel;

import lombok.Getter;
import utilities.ListUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static utilities.CsvUtils.readResourcesCSV;

public class Levels {

	@Getter
	private static List<Level> levels = importLevels();

	private static List<Level> importLevels() {
		return readResourcesCSV("levelSpecifications").stream()
				.map(ListUtils::convertStringListToIntegerList)
				.map(Level::new)
				.collect(toList());
	}
}
