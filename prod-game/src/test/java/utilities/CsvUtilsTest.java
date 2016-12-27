package utilities;

import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static utilities.CsvUtils.*;

public class CsvUtilsTest {

	private static final String INVALID_FILE_PATH = "/invalid file path";
	private static final List<List<String>> FILE_CONTENT = asList(asList("6", "5", "4"), asList("3", "2", "1"));

	@Test
	public void invalidFilePath_readCSV_returnsEmptyList() {
		//given
		final String filePath = INVALID_FILE_PATH;
		//when
		final List<List<String>> result = readCSV(filePath);
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void validFilePath_readCSV_returnsValidList() {
		//given
		final String validTestFilePath = "prod-game/src/test/resources/testReadCSV";
		//when
		final List<List<String>> result = readCSV(validTestFilePath);
		//then
		assertThat(result).isNotNull()
				.containsExactly(asList("1", "2", "3"), asList("4", "5", "6"));
	}

	@Test
	public void emptyFile_readCSV_returnsEmptyList() {
		//given
		final String emptyTestFilePath = "prod-game/src/test/resources/testEmptyReadCSV";
		//when
		final List<List<String>> result = readCSV(emptyTestFilePath);
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void invalidFileName_readResourcesCSV_returnsEmptyList() {
		//given
		final String filePath = INVALID_FILE_PATH;
		//when
		final List<List<String>> result = readResourcesCSV(filePath);
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void validFileName_readResourcesCSV_returnsValidList() {
		//given
		final String validTestFilePath = "testReadCSV";
		//when
		final List<List<String>> result = readResourcesCSV(validTestFilePath);
		//then
		assertThat(result).isNotNull()
				.containsExactly(asList("1", "2", "3"), asList("4", "5", "6"));
	}

	@Test
	public void emptyFile_readResourcesCSV_returnsEmptyList() {
		//given
		final String emptyTestFilePath = "prod-game/src/test/resources/testEmptyReadCSV";
		//when
		final List<List<String>> result = readResourcesCSV(emptyTestFilePath);
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void invalidFilePath_writeCSV_returnsFalse() {
		//given
		final String filePath = INVALID_FILE_PATH;
		final  List<List<String>> fileContent = FILE_CONTENT;
		//when
		final boolean result = writeCSV(filePath, fileContent);
		//then
		assertThat(result).isFalse();
	}

	@Test
	public void validFilePath_writeCSV_returnsTrue() {
		//given
		final String validTestFilePath = "prod-game/src/test/resources/testWriteCSV";
		final  List<List<String>> fileContent = FILE_CONTENT;
		//when
		final boolean result = writeCSV(validTestFilePath, fileContent);
		//then
		assertThat(result).isTrue();
	}

	@Test
	public void emptyFileContent_writeCSV_returnsTrue() {
		//given
		final String validTestFilePath = "prod-game/src/test/resources/testEmptyWriteCSV";
		final  List<List<String>> fileContent = emptyList();
		//when
		final boolean result = writeCSV(validTestFilePath, fileContent);
		//then
		assertThat(result).isTrue();
	}
}
