package database;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.CsvUtils;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static utilities.CsvUtils.readCSV;
import static utilities.TestData.*;

@PrepareForTest(CsvUtils.class)
public class DatabaseTest extends PowerMockTestCase {

	private Database database;

	@BeforeMethod
	public void setUp() {
		mockStatic(CsvUtils.class);
	}

	@Test
	public void emptyCsvFile_instantiateDatabase_resultsInEmptyPlayersList() {
		//given
		when(readCSV(isA(String.class))).thenReturn(emptyList());
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).isEmpty();
	}

	@Test
	public void csvFileWithInvalidPlayerEntries_instantiateDatabase_resultsInPlayersListWithInvalidEntriesRemoved() {
		//given
		final List<List<String>> importWithInvalidEntries = getImportWithInvalidEntries();
		when(readCSV(isA(String.class))).thenReturn(importWithInvalidEntries);
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).hasSize(2)
				.containsExactly(VALID_ENTRY_PLAYER, VALID_ENTRY_PLAYER_WITH_GAME);
	}
}