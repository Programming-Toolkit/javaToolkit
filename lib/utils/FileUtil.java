package javaToolkit.lib.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static String[] getSubDirs(String rootDir) {

		File file = new File(rootDir);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
//		System.out.println(Arrays.toString(directories));
		return directories;
	}

	public static String readFile2Str(String fpath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(fpath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}

	public static boolean renameDir(String dirPath, String newDirName) {

		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			System.err.println("There is no directory @ given path");
			return false;
		} else {
			File newDir = new File(dir.getParent() + "/" + newDirName);
			dir.renameTo(newDir);
		}
		return true;
	}

	public static Boolean writeStr2File(String wStr, String fPath) {
		try {
			Files.write(Paths.get(fPath), wStr.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean deleteDirectory(String dirPath) {
		File directoryToBeDeleted = new File(dirPath);
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file.getAbsolutePath());
			}
		}
		return directoryToBeDeleted.delete();
	}

	public static void copyFolder(Path srcDirStr, Path destDirStr) {
		File source = new File(srcDirStr.toString());
		File dest = new File(destDirStr.toString());
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeStringToFile(String filePath, String dataStr) {
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			Writer writer = new FileWriter(file);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(dataStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception e) {
				System.out.println("Write file error!");
				e.printStackTrace();
			}
		}
	}

	public static String readStringFromFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				String ls = System.getProperty("line.separator");
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}

				// delete the last new line separator
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				bufferedReader.close();

				String content = stringBuilder.toString();
				return content;
			} else {
				System.out.printf("File not found! %s\n", filePath);
			}
		} catch (Exception e) {
			System.out.println("Read file error!");
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readFileToStrList(String filePath) {
		ArrayList<String> strList = new ArrayList<String>();
		try {
			if (!new File(filePath).exists()) {
				System.out.println("File not exists! " + filePath);
				return null;
			}
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				strList.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("filePath : " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("filePath : " + filePath);
			e.printStackTrace();
		}
		return strList;
	}

	public static void createFolder(String dirStr, Boolean deleteIfExist) {
		File dir = new File(dirStr);
		if (deleteIfExist && dir.exists()) {
			deleteDirectory(dir.getAbsolutePath());
			dir.mkdirs();
		} else if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}

	public static List<String> findFilePathofSpecifcTypeRecusive(String tarDir, String extension) {

		List<String> pathList = new ArrayList<String>();
		try {
			Files.walk(Paths.get(tarDir)).filter(Files::isRegularFile).forEach((f) -> {
				String filepath = f.toString();
				if (filepath.endsWith(extension))
//					System.out.println(file + " found!");
					pathList.add(filepath);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathList;
	}

	public static List<String> findRelativeFilePathofSpecifcTypeRecusive(String tarDir, String extension) {

		List<String> relPathList = new ArrayList<String>();
		try {
			Files.walk(Paths.get(tarDir)).filter(Files::isRegularFile).forEach((f) -> {
				String filepath = f.toString();
				if (filepath.endsWith(extension))
//					System.out.println(file + " found!");
					relPathList.add(filepath.replace(tarDir, ""));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relPathList;
	}

	public static void main(String[] args) {
		// test
		String dirPath = "/data/bowen/data/Transformation4J/FBMining/_4_fix_groups/RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE/correct the null check object/18601/buggy/";
		String extension = ".java";
		for (String filePath : findRelativeFilePathofSpecifcTypeRecusive(dirPath, extension)) {
			System.out.print(filePath);
		}
	}

}
