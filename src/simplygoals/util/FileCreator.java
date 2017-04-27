package simplygoals.util;
import java.io.File;
import java.io.IOException;
public class FileCreator {
	       
	public static boolean createFile(String fileName){
	        
	        File file = new File(fileName); 
	        boolean fileExists = file.exists();
	        if(!fileExists) {
	            try {
	                fileExists = file.createNewFile();
	                return true;
	            } catch (IOException e) {
	                System.out.println("Nie uda³o siê utworzyæ pliku");
	                return false;
	            }
	        }
	         
	        if(fileExists)
	            System.out.println("Plik " + fileName + " istnieje lub zosta³ utworzony");
	        return false;
	    }
	}

