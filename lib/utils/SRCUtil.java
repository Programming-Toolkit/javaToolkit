package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SRCUtil {

	public static void changePackage(String oriSRCPath, String dstSRCPath, String newPackName) {
		List<String> srcStrList = FileUtil.readFileToLineList(Paths.get(oriSRCPath));

		Boolean ifchanged = false;
		String wrtStr = "";
		for (int i = 0; i < srcStrList.size(); i++) {
			String line = srcStrList.get(i);
			if (line.trim().startsWith("package ") && !ifchanged) {
				line = "package " + newPackName + ";";
				ifchanged = true;
			}
			wrtStr += (line + "\n");
		}

		FileUtil.writeStr2File(wrtStr, dstSRCPath);

	}

	public static void changePackage(Path srcPath, String newPackName) {
		List<String> srcStrList = FileUtil.readFileToLineList(srcPath);

		Boolean ifchanged = false;
		String wrtStr = "";
		for (int i = 0; i < srcStrList.size(); i++) {
			String line = srcStrList.get(i);
			if (line.trim().startsWith("package ") && !ifchanged) {
				line = "package " + newPackName + ";";
				ifchanged = true;
			}
			wrtStr += (line + "\n");
		}

		FileUtil.writeStr2File(wrtStr, srcPath);

	}

	public static void main(String[] args) {
		/*
		 * test purpose
		 */
		String oriPathString = "/home/appevolve/Desktop/2019_ISSTA_AE_225/run/Update_Example_Analysis+API-Usage_Update/EPR-Data/RepositoryWriter.java";
		String dstPathString = "/home/appevolve/Desktop/2019_ISSTA_AE_225/run/Update_Example_Analysis+API-Usage_Update/EPR-Data/RepositoryWriter_tmp.java";
		String newPackString = "main";
		changePackage(oriPathString, dstPathString, newPackString);
		System.out.println("Finished!");

	}

}
