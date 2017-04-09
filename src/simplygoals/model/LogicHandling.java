package simplygoals.model;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
import simplygoals.modelComponents.User;
public interface LogicHandling {
	
	
//***HANDLE USERS***//
	
	
	//*This method add user to logic whole application, so user is added to the model and database
	String addUserToLogic(User user);
	
	//*This method remove user from logic of whole application, so user is removed from the model and database
	String removeUserFromLogic(User user);
	
	/*This method add user only to the model, without database. 
	Use this method only when you want to get users from database*/
	void addUserToModel(User user);
	
	/*This method remove user only from the model, without database. This can be helpful with synchronize db with logic */
	void removeUserFromModel(User user);
	
	/* */
	void setCurrentUser(User user);
	/* */
	User getCurrentUser();
	/* */
	ObservableList<User> getUserList();
	/* */
	void setUserList(ObservableList<User> userList);
	/* */
	
//***HANDLE GOALS***//
	/* */
	String addGoalToUser(Goal goal);
	/* */
	String removeGoalFromUser(Goal goal);
	/* */
	ObservableList<Goal> getCurrentGoalList();
	ObservableList<Goal> getAllGoalsList();
	ObservableList<Goal> getGoalListByType(GoalType type);
	/* */
	void setGoalList(ObservableList<Goal> goalList);
	/* */
	void updateGoal(Goal goal);
	
	
//***HANDLE CATEGORIES***//
	//CATEGORY IS AUTOSERIALIZED AFTER ADDING CATEGORY OR REMOVING CATEGORY.//
   

   /* */
	String addCategoryToLogic(Category category);
	/* */
	String removeCategoryFromLogic(Category category);
	/* */
	boolean isCategoryInLogic(Category category);
    /* */
	ObservableList<Category> getCategoryList();
	/* */
	void setCategoryList(ObservableList<Category> categoryList);
	
	

//***PROGRAM INITIALIZATION FROM DATABASE***//
	void initDB();
	void initUserList();
	
	
//SINGLETON COLLECTOR
	default <T> Collector<T, ?, T> singletonCollector() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() != 1) {
	                    throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}
	
}