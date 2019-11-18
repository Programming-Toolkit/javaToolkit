package javaToolkit.lib.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javaToolkit.lib.utils.FileUtil;
import io.reflectoring.diffparser.api.DiffParser;
import io.reflectoring.diffparser.api.UnifiedDiffParser;
import io.reflectoring.diffparser.api.model.Diff;

public class DiffUtil {

	public static int getChangedLine(String diffFilePath) {
		try {
			List<String> diffFileStrList = FileUtil.readFileToStrList(diffFilePath);
			for (String line : diffFileStrList) {
				if (line.startsWith("@@")) {
					for (String tmp : line.split(" ")) {
						if (tmp.trim().startsWith("-")) {
							if (tmp.contains(",")) {
								return Integer.valueOf(tmp.trim().replace("-", "").split(",")[0]);
							} else {
								return Integer.valueOf(tmp.trim().replace("-", ""));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(diffFilePath);
			System.exit(0);
		}
		return -1;
	}

	public static List<String> getModifiedFileList(String diffFilePath) {
		/**
		 * can to be further improved in future
		 */
		List<String> modifiedFileRelPathList = new ArrayList<String>();
		for (String line : FileUtil.readFileToStrList(diffFilePath)) {
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
