package simplygoals.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;

public class MySQL implements DatabaseServiceable {
	
	//***ALL INFORMATION REQUIRED TO CONNECT WITH DATABASE***//
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_URL_MADE = "jdbc:mysql://localhost/SIMPLYGOALS";
	static final String USER = "root";
	static final String PASS = "";
	static final String DB_NAME = "SIMPLYGOALS";
	
	 static final String goal = "Name";
	 static final String plannedDate = "Planned_date_of_end";
	 static final String endDate ="Real_date_of_end";
	 static final String category="Category";
	 static final String type="Type";
	 static final String executed="Executed";
	 static final String notes="Notes";
	
	Connection conn = null;
	Statement stmt = null;
	
	//*This method create database if doesn't exist*//
	@Override
	public void createDatabase(){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS "+DB_NAME;
			stmt.executeUpdate(sql);
		}catch(SQLException se){
			se.printStackTrace();    
		}catch(Exception e){
			e.printStackTrace();   
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}	
	}
	//*This method add table to created Database, where table name is in app logic user name*//
	@Override
	public boolean addTable(String tableName){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			stmt = conn.createStatement();			   
			String sql = "CREATE TABLE "+tableName+" (ID INT NOT NULL AUTO_INCREMENT, "+goal+" VARCHAR(30), "
					+ plannedDate+" VARCHAR(30), "+endDate+" VARCHAR(30), "+category+" VARCHAR(30),"
					+ " "+executed+" BIT, "+type+" INT ,"+notes+" VARCHAR(200),"+ " PRIMARY KEY (ID));" ;
			stmt.executeUpdate(sql);
			return true;
		}catch(SQLException se){
			se.printStackTrace();
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	//*This method remove table from database, where table name is in app logic user name*//
	@Override
	public boolean removeTable(String tableName){
		
		try{
			      Class.forName("com.mysql.jdbc.Driver");
			      conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			      stmt = conn.createStatement();
			     
			      String sql = "DROP TABLE " +tableName;
			      stmt.executeUpdate(sql);
			      return true;
			   }catch(SQLException se){
			      se.printStackTrace();
			      return false;
			   }catch(Exception e){
			      e.printStackTrace();
			      return false;
			   }finally{
				   
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){}
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
	 }
	   //*This method add record to database, where record represent one goal(and all features) for one user in app logic*//
	   @Override
	   public boolean addRecord(String user, String goalName, String plannedDateOfEnd, String realEndDate,
			                  String goalCategory, int type, boolean executed, String goalNotes){
		   	try{
			      Class.forName("com.mysql.jdbc.Driver");
			      conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			      stmt = conn.createStatement();			   
			      String sql = "INSERT INTO "+user+" ("+goal+", "+plannedDate+", "
			                   +endDate+", "+category+", "+this.type+","+this.executed+", "+notes+")"+
			    		        "VALUES ('"+goalName+"', '"+plannedDateOfEnd+"', "+"'2000-01-01'"+", '"+
			                   goalCategory+"', "+type+" ,0, '"+goalNotes+"');";
			      stmt.executeUpdate(sql);
			      return true;
			   }catch(SQLException se){
			      se.printStackTrace();
			      return false;
			   }catch(Exception e){
			      e.printStackTrace();
			      return false;
			   }finally{
				  
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){}
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
	   }
	   
	 //*This method remove record from database, where record represent one goal(and all features) for one user in app logic*//
	   @Override
	   public boolean removeRecord(String user, String goalName){
		
		   try{
			      Class.forName("com.mysql.jdbc.Driver");
			      conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			      stmt = conn.createStatement();			   
			      String sql ="DELETE FROM "+user+" WHERE Name='"+goalName+"';";
			      stmt.executeUpdate(sql);
			      return true;
			   }catch(SQLException se){
			      se.printStackTrace();
			      return false;
			   }catch(Exception e){
			      e.printStackTrace();
			      return false;
			   }finally{
				  
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){}
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
	   }
	   
	   //*This method update data for one record in database, where one record is one goal*// 
	   @Override
	   public boolean updateRecord(String tableName, String goalName,String realEndDate,boolean executed, String goalNotes){
		  return false;
	   }
	   @Override
	   public ObservableList<Goal> getAllRecords(String tableName) {
		   ObservableList<Goal> goalList = FXCollections.observableArrayList();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
			   conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			   stmt = conn.createStatement();
			   String sql = "SELECT "+goal+", "+plannedDate+", "+endDate+", "+category+", "+type+", "+executed+", "+notes+" FROM "+tableName+";";
			  ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next()){
			   String nameT = rs.getString(goal);
			   String plannedDateT = rs.getString(plannedDate);
			   String endDateT = rs.getString(endDate);
			   String categoryT=rs.getString(category);
			   int typeT = rs.getInt(type);
			   boolean executedT = rs.getBoolean(executed);
			   String notesT=rs.getString(notes);
			   goalList.add(new Goal.Builder(nameT, plannedDateT, GoalType.goalTypeFromId(typeT))
					                .executed(executedT).notes(notesT).realEndDate(endDateT)
					   				.category(new Category(categoryT)).build());
		   }
		   return goalList;
		   }catch(SQLException se){
			   se.printStackTrace();
			   return goalList;
		   }catch(Exception e){
			   e.printStackTrace();
			   return goalList;
		   }finally{
			   try{
				   if(stmt!=null)
					  stmt.close();
			   }catch(SQLException se2){}
			   try{
				   if(conn!=null)
					   conn.close();
			   }catch(SQLException se){
				   se.printStackTrace();
			   }
		   }
	   }
	
	   @Override
	   public ArrayList<String> getTableListfromDB(){
		   ArrayList<String> tableList = new ArrayList<>();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
			   conn = DriverManager.getConnection(DB_URL, USER, PASS);
			   stmt = conn.createStatement();
			   String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = "
				   		+ "'BASE TABLE' AND TABLE_SCHEMA='simplygoals'";
			  ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next()){
			   tableList.add(rs.getString("TABLE_NAME"));
		   }
		   return tableList;
		   }catch(SQLException se){
			   se.printStackTrace();
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
			   return null;
		   }finally{
			   try{
				   if(stmt!=null)
					  stmt.close();
			   }catch(SQLException se2){}
			   try{
				   if(conn!=null)
					   conn.close();
			   }catch(SQLException se){
				   se.printStackTrace();
			   }
		   }
	   }
	   public boolean hasNameRecord(String tableName, String recordName){
		   
		   boolean temp;
		   ArrayList<String> goalList = new ArrayList<>();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
			   conn = DriverManager.getConnection(DB_URL_MADE, USER, PASS);
			   stmt = conn.createStatement();
			   String sql = "SELECT NAME FROM "+tableName+" WHERE NAME= '"+recordName+"';";
			  ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next()){
			   goalList.add(rs.getString("Name"));
		   }	   
		   return goalList.stream().anyMatch(x->x.equals(recordName));
		   }catch(SQLException se){
			   se.printStackTrace();
			   return false;
		   }catch(Exception e){
			   e.printStackTrace();
			   return false;
		   }finally{
			   try{
				   if(stmt!=null)
					  stmt.close();
			   }catch(SQLException se2){}
			   try{
				   if(conn!=null)
					   conn.close();
			   }catch(SQLException se){
				   se.printStackTrace();
			   }
		   }
	   }
	   
}
