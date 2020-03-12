package javaToolkit.lib.utils;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVUtil {

	public static void writeLine2Csv(List<String> strList, Path filePath) {
		try {
			FileWriter writer = new FileWriter(filePath.toString());

			String collect = strList.stream().collect(Collectors.joining(","));
			System.out.println(collect);

			writer.write(collect);
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void appendLine2Csv(List<String> strList, Path filePath) {
		try {
			String collect = strList.stream().collect(Collectors.joining(","));
			// System.out.println(collect);

			// pass true for appending
			if (filePath.toFile().exists()) {
				FileWriter writer = new FileWriter(filePath.toString(), true);
				writer.write(collect);
				writer.close();
			} else {
				FileWriter writer = new FileWriter(filePath.toString());
				writer.write(collect);
				writer.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * if " (double quotation mark) exists in a cell, then the cell needs to be
	 * replace("\"","\"\"") and add " before and after the cell
	 * 
	 * the implementation is cell="\""+cellString.replace("\"","\"\"")+"\""
	 * 
	 * @param strList
	 * @param filePath
	 */
	public static void write2DArray2Csv(List<List<String>> strList, Path filePath) {
		try {
			if (filePath.toFile().exists()) {
				filePath.toFile().delete();
			}

			FileWriter writer = null;
			for (List<String> line : strList) {
				String collect = line.stream().collect(Collectors.joining(","));
				// System.out.println(collect);

				// pass true for appending
				if (filePath.toFile().exists()) {
					writer = new FileWriter(filePath.toString(), true);
					writer.write(collect.trim() + "\n");
				} else {
					writer = new FileWriter(filePath.toString());
					writer.write(collect.trim() + "\n");
				}
				writer.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * read csv line by line and parse single line to this function code example
	 * String csvFile = "/Users/mkyong/csv/country2.csv";
	 * 
	 * Scanner scanner = new Scanner(new File(csvFile)); while
	 * (scanner.hasNext()) { List<String> line = parseLine(scanner.nextLine());
	 * System.out.println("Country [id= " + line.get(0) + ", code= " +
	 * line.get(1) + " , name=" + line.get(2) + "]"); } scanner.close();
	 * 
	 * @param cvsLine
	 * @param separator
	 * @param quote
	 * @return
	 */
	public static List<String> parseLine(String cvsLine, char separator, char quote) {

		List<String> result = new ArrayList<>();

		// if empty, return!
		if (cvsLine == null || cvsLine.isEmpty()) {
			return result;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == quote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == quote) {

					inQuotes = true;

					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && quote == '\"') {
						curVal.append('"');
					}

					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separator) {

					result.add(curVal.toString());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}

}
