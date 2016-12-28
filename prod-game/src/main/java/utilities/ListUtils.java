package utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUtils {
	public static List<Integer> convertStringListToIntegerList(List<String> strings) {
		return strings.stream()
				.map(Integer::valueOf)
				.collect(toList());
	}
}
