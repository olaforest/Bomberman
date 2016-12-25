package utilities;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class CsvUtils {

	public static List<List<String>> readCSV(String filePath) {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(filePath));
			return readFile(reader);
		} catch (FileNotFoundException e) {
//			InputStream in = Bomberman.class.getResourceAsStream("/database.csv");
//			reader = new CSVReader(new InputStreamReader(in));
			return emptyList();
		}
	}

	private static List<List<String>> readFile(CSVReader reader) {
		try {
			return reader.readAll().stream()
					.map(Arrays::asList)
					.collect(Collectors.toList());
		} catch (IOException e) {
			return emptyList();
		}
	}
}
