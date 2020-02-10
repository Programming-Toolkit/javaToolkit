package javaToolkit.lib.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

	public static void printCurTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

	public static void printCurTimewithMsg(String msg) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Processing " + msg + " " + dtf.format(now));
	}
}
