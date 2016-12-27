package utilities;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;
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
			final List<String[]> fileContent = reader.readAll();
			reader.close();
			return fileContent.stream()
					.map(Arrays::asList)
					.collect(toList());
		} catch (IOException e) {
			return emptyList();
		}
	}

	public static boolean writeCSV(String filePath, List<List<String>> fileContent) {
		try {
			final CSVWriter writer = new CSVWriter(new FileWriter(filePath, false));
			final List<String[]> convertedFileContent = fileContent.stream()
					.map(line -> line.toArray(new String[line.size()]))
					.collect(toList());
			writer.writeAll(convertedFileContent);
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
