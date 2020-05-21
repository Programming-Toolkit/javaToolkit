package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffUtil {

	public static List<Integer> getChangedLineNumListInOldVersion(String diffStr) {

		List<Integer> changedLineNumList = new ArrayList<>();
		try {
			for (String line : diffStr.split("\n")) {
				if (line.startsWith("@@")) {
					for (String tmp : line.split(" ")) {
						if (tmp.trim().startsWith("-")) {
							if (tmp.contains(",")) {
								changedLineNumList.add(Integer.valueOf(tmp.trim().replace("-", "").split(",")[0]));
							} else {
								changedLineNumList.add(Integer.valueOf(tmp.trim().replace("-", "")));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return changedLineNumList;
	}

	/**
	 * the mode can only be "add" or "delete"
	 * 
	 * @param diffStr
	 * @param mode
	 * @return
	 */
	public static int getChangedLineCount(String diffStr, String mode) {

		int count = 0;
		String symbol = null;
		if ("delete".equals(mode)) {
			symbol = "-";
		} else if ("add".equals(mode)) {
			symbol = "+";
		}
		for (String line : diffStr.split("\n")) {
			if (line.startsWith(symbol) && !line.startsWith("---") && !line.startsWith("+++")) {
				// if the line is comment
				if (line.substring(1).trim().startsWith("*") || line.substring(1).trim().startsWith("/*")
						|| line.substring(1).trim().startsWith("//")) {
					continue;
				} else {
					count++;
				}
			}
		}
		return count;
	}

	public static List<Integer> getChangedLineNumListInNewVersion(String diffStr) {
		List<Integer> changedLineNumList = new ArrayList<>();
		try {
			for (String line : diffStr.split("\n")) {
				if (line.startsWith("@@")) {
					for (String tmp : line.split(" ")) {
						if (tmp.trim().startsWith("+")) {
							if (tmp.contains(",")) {
								changedLineNumList.add(Integer.valueOf(tmp.trim().replace("-", "").split(",")[0]));
							} else {
								changedLineNumList.add(Integer.valueOf(tmp.trim().replace("-", "")));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return changedLineNumList;
	}

	public static List<Map<String, String>> getModifiedFileList(Path diffFilePath) {
		List<Map<String, String>> modifiedFileRelPathList = new ArrayList<Map<String, String>>();
		for (String line : FileUtil.readFileToLineList(diffFilePath)) {
			if (line.startsWith("diff --git ")) {
				// Heuristic
				String oldFileRelPath = line.replace("diff --git ", "").split(" ")[0].replaceFirst("a/", "").trim();
				String newFileRelPath = line.replace("diff --git ", "").split(" ")[1].replaceFirst("b/", "").trim();
				Map<String, String> oldNnewMap = new HashMap<String, String>();
				oldNnewMap.put("old", oldFileRelPath);
				oldNnewMap.put("new", newFileRelPath);
				modifiedFileRelPathList.add(oldNnewMap);
			}
		}
		return modifiedFileRelPathList;
	}

}
