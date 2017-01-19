package utilities;

import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static utilities.ListUtils.convertStringListToIntegerList;

public class ListUtilsTest {

	@Test
	public void emptyStringList_convertStringListToIntegerList_returnsEmptyList() {
		//given
		final List<String> emptyList = emptyList();
		//when
		final List<Integer> result = convertStringListToIntegerList(emptyList);
		//then
		assertThat(result).isEmpty();
	}

	@Test
	public void stringListWithNonIntegerValue_convertStringListToIntegerList_throwsException() {
		//given
		final List<String> list = asList("0", "1", "%", "3");
		//when
		assertThatThrownBy(() -> convertStringListToIntegerList(list))
				//then
				.isInstanceOf(NumberFormatException.class);
	}

	@Test
	public void validStringList_convertStringListToIntegerList_returnsValidIntegerList() {
		//given
		final List<String> emptyList = asList("0", "1", "9876");
		//when
		final List<Integer> result = convertStringListToIntegerList(emptyList);
		//then
		assertThat(result).containsExactly(0, 1, 9876);
	}
}