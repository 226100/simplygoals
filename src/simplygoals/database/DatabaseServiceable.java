package simplygoals.database;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import simplygoals.modelComponents.Goal;

public interface DatabaseServiceable {
	   
	 public void createDatabase();
	 public boolean addTable(String tableName);
	 public boolean removeTable(String tableName);
	 public boolean addRecord(String tableName, String goalName, String plannedDateOfEnd, String realEndDate,
                             String goalCategory,int type, boolean executed, String goalNotes);
	 public boolean removeRecord(String tableName, String goalName);
	 public boolean updateRecord(String tableName, String goalName,  String realEndDate,boolean executed, String goalNotes);
	 public boolean hasNameRecord(String tableName, String recordName);
	 public ObservableList<Goal> getAllRecords(String tableName);
	 public ArrayList<String> getTableListfromDB();
}
	