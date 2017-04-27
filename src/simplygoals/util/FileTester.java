package simplygoals.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class FileTester {
	public static boolean hasApplicationFile(String fileName){
		File file = new File(fileName); 
        boolean fileExists = file.exists();
        if(fileExists){
        	return true;
        }else{
        	return false;
        }
	}
}
