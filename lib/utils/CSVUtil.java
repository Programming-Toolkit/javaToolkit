package javaToolkit.lib.utils;

import java.io.FileWriter;
import java.nio.file.Path;
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

	public static void write2DArray2Csv(List<List<String>> strList, Path filePath) {
		try {
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

}
