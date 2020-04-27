package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GitUtil {

	public static Boolean clone(String repoName, String usrName, Path targetDir) {

		if (targetDir.toFile().exists()) {
			FileUtil.deleteDirectory(targetDir.toFile());
		}
		targetDir.toFile().mkdirs();

		String cmd = "timeout 300 git clone https://github.com/" + repoName + "/" + usrName + " " + targetDir;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, targetDir, 0);
		if (pr.exitCode == 0) {
			return true;
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(targetDir.toString(), "clone_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(targetDir.toString(), "clone_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			FileUtil.deleteDirectory(targetDir.toFile());
			return false;
		}
	}

	public static List<String> getAllCommitsSha(Path repoDir) {

		String cmd = "timeout 300 git --no-pager log --all --pretty=format:\"%H\"";

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.replace("\"", "").split("\n"));
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getAllCommitsSha_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getAllCommitsSha_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static List<String> getComsWithSingleWordMatch(Path repoDir, String word) {

		String cmd = "timeout 300 git --no-pager log --all --pretty=format:\"%H\" --grep=" + word.trim();

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			if ("".equals(pr.out.trim())) {
				return null;
			}
			return Arrays.asList(pr.out.replace("\"", "").split("\n"));
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getAllCommitsSha_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getAllCommitsSha_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getDiff4SingleFileNCommit(Path repoDir, String parCom, String curCom, String oldRelFilePath,
			String newRelFilePath) {

		String cmd = "timeout 300 git --no-pager diff --unified=0 " + parCom + ":" + oldRelFilePath + " " + curCom + ":"
				+ newRelFilePath;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getDiff4SingleFileNCommit.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getDiff4SingleFileNCommit.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getCommitMsg(Path repoDir, String com) {

		String cmd = "timeout 300 git log --format=%B -n 1 " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getCommitMsg_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getCommitMsg_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getParentCommit(Path repoDir, String com) {

		String cmd = "timeout 300 git log --pretty=%P -n 1 " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.replace("\"", "").trim();
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getParentCommit_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getParentCommit_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	/**
	 * 
	 * @param repoDir
	 * @param oldCom
	 * @param newCom
	 * @param diffMode
	 * @return
	 */
	public static String getDiffBetween2Commits(Path repoDir, String oldCom, String newCom, String diffMode) {

		String cmd = null;
		if (diffMode != null) {
			cmd = "timeout 300 git --git-dir " + repoDir.toString() + "/.git --work-tree " + repoDir.toString()
					+ " diff " + diffMode + " --unified=0 " + oldCom + " " + newCom;
		} else {
			cmd = "timeout 300 git --git-dir " + repoDir.toString() + "/.git --work-tree " + repoDir.toString()
					+ " diff --unified=0 " + oldCom + " " + newCom;
		}

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getDiffBetween2Commits_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getDiffBetween2Commits_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static List<String> getChangedFileList(Path repoDir, String com) {

		String cmd = "timeout 300 git diff-tree --no-commit-id --name-only -r " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.split("\n"));
		} else {
			FileUtil.writeStr2File(pr.out, Paths.get(repoDir.toString(), "getChangedFileList_out.txt"));
			FileUtil.writeStr2File(pr.err, Paths.get(repoDir.toString(), "getChangedFileList_err.txt"));
			// System.out.println("cmd " + cmd + "\n");
			// System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static Boolean checkout(Path repoDir, String com, Boolean ifForce) {

		ProcessUtil.ProcessReporter pr = new ProcessUtil.ProcessReporter();

		if (ifForce) {
			String resetCMD = "timeout 300 git reset --hard";
			pr = ProcessUtil.executeCMD(resetCMD, null, repoDir, 0);
		}

		String checkoutCMD = null;
		if (com == null) { // checkout latest if null
			checkoutCMD = "timeout 300 git checkout master";
		} else {
			checkoutCMD = "timeout 300 git checkout " + com;
		}
		pr = ProcessUtil.executeCMD(checkoutCMD, null, repoDir, 0);

		if (pr.exitCode == 0) {
			return true;
		} else {
			String cleanCMD = "timeout 300 git --git-dir " + repoDir.toString() + "/.git --work-tree "
					+ repoDir.toString() + " clean -dfx .";
			pr = ProcessUtil.executeCMD(cleanCMD, null, repoDir, 0);
			String resetCMD = "timeout 300 git --git-dir " + repoDir.toString() + "/.git --work-tree "
					+ repoDir.toString() + " reset --hard";
			pr = ProcessUtil.executeCMD(resetCMD, null, repoDir, 0);
			pr = ProcessUtil.executeCMD(checkoutCMD, null, repoDir, 0);
			if (pr.exitCode == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

}
