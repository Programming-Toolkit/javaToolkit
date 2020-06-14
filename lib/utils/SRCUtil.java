package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.util.List;

public class SRCUtil {

	public static String changePackage(Path SRCPath, String newPackName) {
		List<String> srcStrList = FileUtil.readFileToLineList(SRCPath);

		Boolean ifchanged = false;
		String wrtStr = "";
		for (int i = 0; i < srcStrList.size(); i++) {
			String line = srcStrList.get(i);
			if (line.trim().startsWith("package ") && !ifchanged) {
				wrtStr += ("package " + newPackName + ";\n");
				ifchanged = true;
			} else {
				wrtStr += (line + "\n");
			}
		}

		if (!ifchanged) {
			wrtStr = "package " + newPackName + ";\n" + wrtStr;
		}
		
		return wrtStr.trim();

	}

}
