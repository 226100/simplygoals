package simplygoals.util;

import java.io.File;

/** This class has a method which check if file with given name exist */
public class FileTester {
	public static boolean hasApplicationFile(String fileName) {
		File file = new File(fileName);
		boolean fileExists = file.exists();
		if (fileExists) {
			return true;
		} else {
			return false;
		}
	}
}