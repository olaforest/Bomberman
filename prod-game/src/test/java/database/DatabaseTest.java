package database;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {

	private Database database;

	@Test
	public void emptyCsvFile_instantiateDatabase_resultsInEmptyPlayersList() {
		//given
//		when(readCSV(isA(String.class))).thenReturn(emptyList());
		//when
		database = new Database();
		//then
		assertThat(database.getPlayers()).isEmpty();
	}

	@Test
	public void csvFileWithInvalidPlayerEntries_instantiateDatabase_resultsInPlayersListWithInvalidEntriesRemoved() {
		//given
//		final List<List<String>> importWithInvalidEntries = getImportWithInvalidEntries();
//		when(readCSV(isA(String.class))).thenReturn(importWithInvalidEntries);
		//when
		database = new Database();
		//then
//		assertThat(database.getPlayers()).hasSize(2)
//				.containsExactly(VALID_ENTRY_PLAYER, VALID_ENTRY_PLAYER_WITH_GAME);
	}
}