package javaToolkit.lib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static String getFileContent(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		br.close();
		return sb.toString();
	}

	/**
	 * use new File(PathString).listfiles(File::isDirectory) instead
	 */
	@Deprecated
	public static String[] getSubDirs(String rootDir) {

		File file = new File(rootDir);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		// System.out.println(Arrays.toString(directories));
		return directories;
	}

	public static String readFile2Str(Path fpath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(fpath));
		} catch (IOException e) {
			System.out.printf("%s not found!", fpath);
			e.printStackTrace();
			System.exit(0);
		}
		return content;
	}

	public static Boolean writeStr2File(String wStr, String fPath) {
		try {
			Path parentDir = Paths.get(fPath).getParent();
			if (!Files.exists(parentDir)) {
				Files.createDirectories(parentDir);
			}
			Files.write(Paths.get(fPath), wStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean writeStr2File(String wStr, Path fPath) {
		try {
			Path parentDir = fPath.getParent();
			if (!Files.exists(parentDir))
				Files.createDirectories(parentDir);
			Files.write(fPath, wStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean writeStrList2File(List<String> strList, Path fPath, Boolean ifTrim) {
		try {
			Path parentDir = fPath.getParent();
			if (!Files.exists(parentDir))
				Files.createDirectories(parentDir);
			String wStr = "";
			for (String line : strList) {
				if (ifTrim) {
					line = line.trim();
				}
				wStr += (line + "\n");
			}
			Files.write(fPath, wStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean copyFile2Dir(File f, String DestDir) {
		try {
			File DestDirFile = new File(DestDir);
			if (!DestDirFile.exists()) {
				DestDirFile.mkdirs();
			}
			String targetFilePath = DestDirFile.getAbsoluteFile() + "/" + f.getName();
			FileUtils.copyFile(f, new File(targetFilePath));
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean copyFile2Dir(Path fPath, Path DestDir) {
		try {
			File DestDirFile = DestDir.toFile();
			if (!DestDirFile.exists()) {
				DestDirFile.mkdirs();
			}
			Path targetFilePath = Paths.get(DestDirFile.getAbsolutePath(), fPath.toFile().getName());
			FileUtils.copyFile(fPath.toFile(), targetFilePath.toFile());
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean copyFile2File(File srcFile, File dstFile) {
		try {
			// dstfile will be overwrited if exist
			FileUtils.copyFile(srcFile, dstFile);
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean deleteDirectory(Path dirPath) {
		File directoryToBeDeleted = dirPath.toFile();
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file.toPath());
			}
		}
		return directoryToBeDeleted.delete();
	}

	public static void copyDirectory(Path srcDirPath, Path destDirPath) {
		File source = srcDirPath.toFile();
		File dest = destDirPath.toFile();
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
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

	/**
	 * there is no \newline at the end of each line
	 *
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileToLineList(Path filePath) {
		ArrayList<String> strList = new ArrayList<String>();
		try {
			if (!filePath.toFile().exists()) {
				System.out.println("File not exists! " + filePath);
				return null;
			}
			BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));
			String line = reader.readLine();
			while (line != null) {
				strList.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {

			System.out.println("filePath : " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("filePath : " + filePath);
			e.printStackTrace();
		}
		return strList;
	}

	public static void createFolder(String dirStr, Boolean deleteIfExist) {
		File dir = new File(dirStr);
		if (deleteIfExist && dir.exists()) {
			deleteDirectory(dir.toPath());
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

	public static List<File> findFilePathofSpecifcTypeRecusive(Path tarDir, String extension) {

		List<File> fileList = new ArrayList<File>();
		try {
			Files.walk(tarDir).filter(Files::isRegularFile).forEach((f) -> {
				String filepath = f.toString();
				if (filepath.endsWith(extension))
					// System.out.println(file + " found!");
					fileList.add(new File(filepath));
			});
		} catch (IOException e) {

			e.printStackTrace();
		}
		return fileList;
	}

	public static List<String> findRelativeFilePathofSpecifcTypeRecusive(String tarDir, String extension) {

		List<String> relPathList = new ArrayList<String>();
		try {
			Files.walk(Paths.get(tarDir)).filter(Files::isRegularFile).forEach((f) -> {
				String filepath = f.toString();
				if (filepath.endsWith(extension))
					// System.out.println(file + " found!");
					relPathList.add(filepath.replace(tarDir, ""));
			});
		} catch (IOException e) {

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
