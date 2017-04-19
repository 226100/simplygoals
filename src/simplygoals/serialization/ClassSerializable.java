package simplygoals.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ClassSerializable<T> {
	
	@SuppressWarnings("finally")
	default ObservableList<T> serializeFromFile(String fileName){
		
		ObservableList<T> obList=FXCollections.observableArrayList();
		try(	
				FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				) {
				
			    ArrayList<T> list = (ArrayList<T>) ois.readObject();
		     	obList = FXCollections.observableArrayList(list);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} finally{
			return obList;
		}
	}
	
	default void serializeToFile(ObservableList<T> obList, String fileName){
		
	    try(
	            FileOutputStream fs = new FileOutputStream(fileName);
	            ObjectOutputStream os = new ObjectOutputStream(fs);
	            ) {
	    	    ArrayList<T> arr = new ArrayList<T>(obList);
	            os.writeObject(arr);
	        
	         
	    } catch (FileNotFoundException e) {
	        
	        e.printStackTrace();
	    } catch (IOException e) {
	        
	        e.printStackTrace();
	    }
	     
	    System.out.println("Zapisano obiekt do pliku");
	}	
}
