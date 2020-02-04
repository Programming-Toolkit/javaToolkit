package javaToolkit.lib.utils;

import java.nio.file.Path;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class GitUtil {

	public static Boolean clone(String repoName, String usrName, Path targetDir) {

		String cmd = "timeout 600 git clone https://github.com/" + repoName + "/" + usrName + " " + targetDir;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, targetDir);
		if (pr.exitCode == 0) {
			return true;
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return false;
		}
	}

	public static List<String> getAllCommits(Path repoDir) {

		String cmd = "timeout 600 git log --pretty=format:\"%H\"";

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.split("\n"));
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}

	public static String getCommitMsg(Path repoDir, String com) {

		String cmd = "git log --format=%%B -n 1 " + com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir);
		if (pr.exitCode == 0) {
			return pr.out.trim();
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}
	
	public static List<String> getChangedFileList(Path repoDir, String com) {

		String  cmd = "git diff-tree --no-commit-id --name-only -r "+ com;

		ProcessUtil.ProcessReporter pr = ProcessUtil.executeCMD(cmd, null, repoDir);
		if (pr.exitCode == 0) {
			return Arrays.asList(pr.out.split("\n"));
		} else {
			System.out.println("cmd " + cmd + "\n");
			System.out.println("report \n" + pr.toString());
			return null;
		}
	}
	
	public static Boolean checkout(Path repoDir, String com,Boolean ifForce) {
		
		
		ProcessUtil.ProcessReporter pr = new ProcessUtil.ProcessReporter();
		
		if(ifForce){
			String resetCMD="git reset --hard";
			pr=ProcessUtil.executeCMD(resetCMD, null, repoDir);
		}
		
		String checkoutCMD=null;
		if(com==null){ // checkout latest if null
			checkoutCMD="git --git-dir "+repoDir.toString()+" /.git --work-tree "+repoDir.toString()+" checkout master";
		}else{
			checkoutCMD="git --git-dir "+repoDir.toString()+" /.git --work-tree "+repoDir.toString()+" checkout "+com;
		}
		pr=ProcessUtil.executeCMD(checkoutCMD, null, repoDir);
		
		if (pr.exitCode == 0) {
			return true;
		} else {
			String cleanCMD="git --git-dir "+repoDir.toString()+" /.git --work-tree "+repoDir.toString()+" clean -dfx .";
			pr=ProcessUtil.executeCMD(cleanCMD, null, repoDir);
			String resetCMD = "git --git-dir "+repoDir.toString()+ " /.git --work-tree "+repoDir.toString()+" reset --hard";
			pr=ProcessUtil.executeCMD(resetCMD, null, repoDir);
			pr=ProcessUtil.executeCMD(checkoutCMD, null, repoDir);
			if(pr.exitCode==0){
				return true;
			}else{
				return false;
			}
		}
	}

}
