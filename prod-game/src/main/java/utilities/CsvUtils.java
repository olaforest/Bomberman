package utilities;

import au.com.bytecode.opencsv.CSVReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvUtils {

	public static List<List<String>> readCSV(String filePath) {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(filePath));
			return reader.readAll().stream()
					.map(Arrays::asList)
					.collect(toList());
		} catch (IOException e) {
			return emptyList();
		}
	}
}
