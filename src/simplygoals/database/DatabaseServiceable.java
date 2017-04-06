package simplygoals.database;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;

public interface DatabaseServiceable {
	   
	 public void createDatabase();
	 public boolean addTable(String tableName);
	 public boolean removeTable(String tableName);
	 public boolean addRecord(String tableName, String goalName, String plannedDateOfEnd, String realEndDate,
                             String goalCategory,int type, boolean executed, String goalNotes);
	 public boolean removeRecord(String tableName, String goalName);
	 public boolean updateRecord(String tableName, String goalName,  String realEndDate,boolean executed, String goalNotes);
	 public boolean hasNameRecord(String tableName, String recordName);
	 ObservableList<Goal> getAllRecords(String tableName);
	 ObservableList<Goal> getRecordsByType(String tableName, GoalType type);
	 ArrayList<String> getTableListfromDB();
}
	