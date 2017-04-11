package simplygoals.model;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simplygoals.database.MySQL;
import simplygoals.modelComponents.AllCategories;
import simplygoals.modelComponents.AllUsers;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
import simplygoals.modelComponents.User;
public class ModelLogic implements LogicHandling{
	
	//***USER LIST, MYSQL, CATEGORYLIST***//
	public  AllUsers userList = new AllUsers();// object of class UserList which handle list of all users
	private MySQL mySQL = new MySQL();//mySQL object to handle Database
	private AllCategories categoryList = new AllCategories();// object of class CategoryList which handle list of all categories
	private String msg;//message to controller
	
	//***CONSTRUCTOR**//
	public ModelLogic(){	
	}
	
	//***GETTERS AND SETTERS***//
	private AllUsers getAllUsers() {
		return userList;
	}
	
	public MySQL getMySQL() {
		return mySQL;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	//***PROGRAM INITIALIZATION***//
	@Override
	public void initDB(){
		mySQL.createDatabase();
	}
	
	@Override
	public void initUserList() {
		ObservableList<User> uList = FXCollections.observableArrayList();
		ArrayList<String> tempList = mySQL.getTableListfromDB();
		Optional<ArrayList<String>> listOp = Optional.ofNullable(tempList);
		if(listOp.isPresent()){
			tempList.stream().forEach(l->{addUserToModel(new User(l));});
		}
	}
	
	//***HANDLE USERS***//
	
	//This method is default method to get userList and return to Controller class*//
	@Override
	public ObservableList<User> getUserList() {
		return userList.getComponentList();
	}
	
	@Override
	public void setUserList(ObservableList<User> userList) {
		this.userList.setComponentList(userList);
	}
	
	//*This method use where application user want to add new user in application. This method add user to whole logic and send this to database*//
	@Override
	public String addUserToLogic(User user){
		setMsg("User exist");
		Optional<User> userOp = Optional.of(user);
		userOp.ifPresent(u->{
			boolean isUserAdded=mySQL.addTable(user.getName().toLowerCase());
			boolean	isUserInDb=mySQL.getTableListfromDB().stream()
									                 .anyMatch(l->l.equals(user.getName().toLowerCase()));
			if(isUserInDb&&isUserAdded){
				userList.addComponent(user);
				setMsg("User added successfully");
			}else{
				setMsg("User not added to database");
			}
		});
		return msg;
	}
	
	//*This method use where application user want to remove user from application. This method remove user from whole logic and remove this user from database*//
	@Override
	public String removeUserFromLogic(User user) {
		setMsg("User not removed");
		Optional<User> userOp = Optional.of(user);
		userOp.ifPresent(u->{
			boolean isUserInList=userList.isComponentInList(user);
			boolean	isUserInDb=mySQL.getTableListfromDB().stream()
	                                .anyMatch(l->l.equals(user.getName().toLowerCase()));
			if(isUserInList&&isUserInDb){
				userList.removeComponent(user);
				if(!userList.isComponentInList(user)){
					mySQL.removeTable(user.getName());
					setMsg("User removed successfully");
				}
			}
		});
		return msg;
	}
	
	//*This method use where you start the programm, and you want to get users from database, add only to model in application*//
	@Override
	public void addUserToModel(User user) {
		
		Optional<User> userOp = Optional.of(user);
		userOp.ifPresent(u->{
			boolean isUserInModel = userList.isComponentInList(user);
			if(!isUserInModel){
				userList.addComponent(user);	
			}
		});
	}
	
	@Override
	public void removeUserFromModel(User user) {	
		Optional<User> userOp = Optional.of(user);
		userOp.ifPresent(u->{
			boolean isUserInModel = userList.isComponentInList(user);
			if(isUserInModel){
				userList.removeComponent(user);	
			}
		});
	}
	
	@Override
	public void setCurrentUser(User user){
		userList.setCurrentUser(user);
	}
	
	@Override
	public User getCurrentUser(){	
		return userList.getCurrentUser();
	}
	
	public ObjectProperty<User> getCurrentUserProperty(){	
		return userList.getCurrentUserProperty();
	}


	//***HANDLE GOALS***//
	@Override
	public String addGoalToUser(Goal goal) {
		setMsg("Goal value null");
		
		Optional.of(goal).ifPresent(u->{
			if(goal.getCategory()!=null&&goal.getType()!=null&&goal.getNotes()!=null){
				mySQL.addRecord(getCurrentUser().getName(), goal.getName(), goal.getPlannedDateOfEnd().toString(),"0001-01-01",
					            goal.getCategory().getName(),goal.getType().getId(),false, goal.getNotes());
			}
		});
		return msg;
	}
	
	@Override
	public String removeGoalFromUser(Goal goal) {
		Optional.of(goal).ifPresent(g->{
			setMsg("Goal doesn't exist");
			if(mySQL.hasNameRecord(getCurrentUser().getName(),goal.getName())){
				setMsg("Goal not removed");
				mySQL.removeRecord(getCurrentUser().getName(), goal.getName());
					if(!mySQL.hasNameRecord(getCurrentUser().getName(), goal.getName())){
						setMsg("Goal removed successfully");
					}
			}
		});
		return msg;
	}

	@Override
	public ObservableList<Goal> getCurrentGoalList() {
		
		return getCurrentUser().getComponentList();
	}
	@Override
	public ObservableList<Goal> getAllGoalsList(){
		if(Optional.ofNullable(userList.getCurrentUser()).isPresent()){
			userList.getCurrentUser().setGoalList(mySQL.getAllRecords(userList.getCurrentUser().getName()));
			return userList.getCurrentUser().getComponentList();
		}else{return FXCollections.observableArrayList();}
	}
	
	@Override
	public ObservableList<Goal> getGoalListByType(GoalType type){
	
		if(Optional.ofNullable(getCurrentUser()).isPresent()){
			return mySQL.getRecordsByType(getCurrentUser().getName(), type);
		}else{return FXCollections.observableArrayList();}
	}
	
	@Override
	public void setGoalList(ObservableList<Goal> goalList) {
		getCurrentUser().setComponentList(goalList);
	}

	@Override
	public void updateGoal(Goal goal) {
			
	}
	
	//***HANDLE CATEGORIES***//
	@Override
	public String addCategoryToLogic(Category category) {
		
		setMsg("Category exist");
		Optional<Category> categoryOp = Optional.of(category);
		categoryOp.ifPresent(c->{
			if(!categoryList.isComponentInList(category)){
				categoryList.addComponent(category);
				setMsg("Category added successfully");
			}else{
				setMsg("Category not added");
			}
		});
		return msg;
	}

	@Override
	public String removeCategoryFromLogic(Category category) {
		setMsg("Category not removed");
		Optional<Category> categoryOp = Optional.of(category);
		categoryOp.ifPresent(c->{
			if(categoryList.isComponentInList(category)){
				categoryList.removeComponent(category);
				setMsg("Category removed successfully");
				}
		});
		return msg;
	}

	@Override
	public ObservableList<Category> getCategoryList() {
		return categoryList.getComponentList();
	}

	@Override
	public void setCategoryList(ObservableList<Category> categoryList) {	
		this.categoryList.setCategoryList(categoryList);
	}
	
	@Override
	public boolean isCategoryInLogic(Category category) {
		Optional<Category> categoryOp = Optional.of(category);
		if(categoryOp.isPresent()&&categoryList.isComponentInList(category)){return true;}
		else {return false;}
	}
}