package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

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
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			FileUtil.deleteDirectory(targetDir.toFile());
			return false;
		}
	}

	public static List<String> getAllCommitsSha(Path repoDir) {

		String cmd = "timeout 60 git log --pretty=format:\"%H\"";

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.replace("\"", "").split("\n"));
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getCommitMsg(Path repoDir, String com) {

		String cmd = "timeout 60 git log --format=%B -n 1 " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getParentCommit(Path repoDir, String com) {

		String cmd = "timeout 60 git log --pretty=%P -n 1 " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.replace("\"", "").trim();
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
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
			cmd = "timeout 60 git --git-dir " + repoDir.toString() + " /.git --work-tree " + repoDir.toString()
					+ " diff " + diffMode + " --unified=0 " + oldCom + " " + newCom;
		} else {
			cmd = "timeout 60 git --git-dir " + repoDir.toString() + " /.git --work-tree " + repoDir.toString()
					+ " diff --unified=0 " + oldCom + " " + newCom;
		}

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static List<String> getChangedFileList(Path repoDir, String com) {

		String cmd = "timeout 30 git diff-tree --no-commit-id --name-only -r " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir, 0);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.split("\n"));
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static Boolean checkout(Path repoDir, String com, Boolean ifForce) {

		ProcessUtil.ProcessReporter pr = new ProcessUtil.ProcessReporter();

		if (ifForce) {
			String resetCMD = "timeout 60 git reset --hard";
			pr = ProcessUtil.executeCMD(resetCMD, null, repoDir, 0);
		}

		String checkoutCMD = null;
		if (com == null) { // checkout latest if null
			checkoutCMD = "timeout 60 git checkout master";
		} else {
			checkoutCMD = "timeout 60 git checkout " + com;
		}
		pr = ProcessUtil.executeCMD(checkoutCMD, null, repoDir, 0);

		if (pr.exitCode == 0) {
			return true;
		} else {
			String cleanCMD = "timeout 60 git --git-dir " + repoDir.toString() + " /.git --work-tree "
					+ repoDir.toString() + " clean -dfx .";
			pr = ProcessUtil.executeCMD(cleanCMD, null, repoDir, 0);
			String resetCMD = "timeout 60 git --git-dir " + repoDir.toString() + " /.git --work-tree "
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
