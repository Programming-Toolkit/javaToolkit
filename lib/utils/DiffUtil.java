package javaToolkit.lib.utils;

import java.util.ArrayList;
import java.util.List;

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

	public static Integer getChangedLineCount(String diffStr, String mode) {

		int count = 0;
		String symbol = null;
		try {
			if ("delete".equals(mode)) {
				symbol = "-";
			} else if ("add".equals(mode)) {
				symbol = "+";
			} else {
				throw new Exception("Only have delete or add mode!");
			}
			for (String line : diffStr.split("\n")) {
				if (line.startsWith(symbol) && !line.startsWith("---") && !line.startsWith("+++")) {
					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return count;
	}

	public static List<Integer> getChangedLineNumListInNewVersion(String diffFilePath) {

		List<Integer> changedLineNumList = new ArrayList<>();
		try {
			List<String> diffFileStrList = FileUtil.readFileToLineList(diffFilePath);
			for (String line : diffFileStrList) {
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
			System.out.println(diffFilePath);
			e.printStackTrace();
		}
		return changedLineNumList;
	}

	public static List<String> getModifiedFileList(String diffFilePath) {
		/**
		 * can to be further improved in future
		 */
		List<String> modifiedFileRelPathList = new ArrayList<String>();
		for (String line : FileUtil.readFileToLineList(diffFilePath)) {
			if (line.startsWith("diff --git ")) {
				// Heuristic
				String fileRelPath = line.replace("diff --git ", "").split(" ")[0].replaceFirst("a/", "").trim();
				modifiedFileRelPathList.add(fileRelPath);
			}
		}
		return modifiedFileRelPathList;
	}

	public static void main(String[] args) {
		String diffFilePath = "/media/sf__3_fix_groups/RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE/delete redundent null check/27769/diff.txt";
		getModifiedFileList(diffFilePath);
	}

}
