package utilities;

import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static utilities.CsvUtils.readCSV;

public class CsvUtilsTest {

	@Test
	public void invalidFilePathOrName_readCSV_returnsEmptyList() {
		//when
		final List<List<String>> result = readCSV("invalid file path or name");
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void validFilePathOrName_readCSV_returnsValidList() {
		//when
		final List<List<String>> result = readCSV("prod-game/src/test/resources/testReadCSV");
		//then
		assertThat(result).isNotNull()
				.containsExactly(asList("1", "2", "3"), asList("4", "5", "6"));
	}
}
