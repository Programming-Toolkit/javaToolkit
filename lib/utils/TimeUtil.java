package javaToolkit.lib.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.jdt.core.dom.ThisExpression;

public class TimeUtil {

	public long startTime;
	public long endTime;

	public TimeUtil() {
		this.startTime = System.nanoTime();
	}

	public void setEndTime() {
		this.endTime = System.nanoTime();
	}

	public int computeTimeCostInMinuts() {
		return (int) ((this.endTime - this.startTime) / (1_000_000_000 * 60));
	}

	public int computeTimeCostInSecond() {
		return (int) ((this.endTime - this.startTime) / 1_000_000_000);
	}

	public static void printCurTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	public static void printCurTimewithMsg(String msg) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(msg + "\t" + dtf.format(now));
	}

	public static void main(String[] args) {
		printCurTimewithMsg("test");
	}

}
