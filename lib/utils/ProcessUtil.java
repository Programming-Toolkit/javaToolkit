package javaToolkit.lib.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class ProcessUtil {

	public static class ProcessReporter {
		public String cmd;
		public int exitCode;
		public String out;
		public String err;

		public ProcessReporter() {
			this.cmd = null;
			this.exitCode = -1;
			this.out = null;
			this.err = null;
		}

		@Override
		public String toString() {
			String res = "Process Report:\n";
			res += ("cmd:\n" + this.cmd + "\n");
			res += ("exit code:\n" + this.exitCode + "\n");
			res += ("out:\n" + this.out + "\n");
			res += ("err:\n" + this.err + "\n");
			return res;
		}
	}

	/**
	 * execute cmd
	 * 
	 * @param cmd
	 * @param envp
	 * @param workDir
	 * @return
	 */
	public static ProcessReporter executeCMD(String cmd, String[] envp, Path workDir) {
		ProcessReporter pr = new ProcessReporter();
		pr.cmd = cmd;
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd, envp, workDir.toFile());

			pr.exitCode = proc.waitFor();

			// Read the output from the command
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			// System.out.println("Here is the standard output of the
			// command:\n");
			String out = "";
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				// System.out.println(s);
				out += (s + "\n");
			}
			pr.out = out.trim();

			// Read any errors from the attempted command
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			// System.out.println("Here is the standard error of the command (if
			// any):\n");
			String err = "";
			while ((s = stdError.readLine()) != null) {
				// System.out.println(s);
				err += (s + "\n");
			}
			pr.err = err.trim();

			proc.destroy();

			if (pr.exitCode != 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			TimeUtil.printCurTimewithMsg(pr.toString());
			e.printStackTrace();
		}

		return pr;
	}

}
