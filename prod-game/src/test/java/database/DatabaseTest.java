package database;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.CsvUtils;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(CsvUtils.class)
public class DatabaseTest extends PowerMockTestCase {

	private Database database;

	@BeforeMethod
	public void setUp() {
		mockStatic(CsvUtils.class);
	}

	@Test
	public void emptyCsvFile_instantiateNewDatabase_resultsInEmptyPlayersList() {
		//given
		when(CsvUtils.readCSV("test")).thenReturn(emptyList());
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).isEmpty();
	}
}