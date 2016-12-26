package database;

import menuModel.Player;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.CsvUtils;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static utilities.CsvUtils.readCSV;

@PrepareForTest(CsvUtils.class)
public class DatabaseTest extends PowerMockTestCase {
	private static final List<String> TOO_LONG_ENTRY = asList("0", "1", "2", "3", "4", "5", "6");
	private static final List<String> TOO_SHORT_ENTRY = asList("0", "1", "2", "3", "4");
	private static final List<String> VALID_ENTRY = asList("0", "1", "2", "3", "4", "5");
	private static final Player VALID_ENTRY_PLAYER = new Player("0", "1", "2", 3, 4, 5);

	private Database database;

	@BeforeMethod
	public void setUp() {
		mockStatic(CsvUtils.class);
	}

	@Test
	public void emptyCsvFile_instantiateNewDatabase_resultsInEmptyPlayersList() {
		//given
		when(readCSV(isA(String.class))).thenReturn(emptyList());
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).isEmpty();
	}

	@Test
	public void csvFileWithInvalidEntries_instantiateNewDatabase_resultsInPlayersListWithInvalidEntriesRemoved() {
		//given
		final List<List<String>> importWithInvalidEntries = asList(TOO_LONG_ENTRY, TOO_SHORT_ENTRY, VALID_ENTRY);
		when(readCSV(isA(String.class))).thenReturn(importWithInvalidEntries);
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).hasSize(1)
				.containsExactly(VALID_ENTRY_PLAYER);
	}
}