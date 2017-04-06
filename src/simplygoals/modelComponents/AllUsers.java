package simplygoals.modelComponents;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AllUsers implements Storable<User> {
	
	//***CURRENT USER, LIST OF ALL USERS
	private User currentUser; // this user is set in program as a current user
	private ObservableList<User> userList;
	
	//GETTERS AND SETTERS
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User presentUser) {
		this.currentUser = presentUser;
	}
	
	//*This getter for userList is only for intern use in a UserList class*//
	private ObservableList<User> getUserList() {
		return userList;
	}
	private void setUserList(ObservableList<User> userList) {
		this.userList = userList;
	}
	//***CONSTRUCTORS***//
	
	public AllUsers(){
		userList=FXCollections.observableArrayList();
	};
	
	//***HANDLE USER LIST***//
	
	//*This method return observable list (outside a class) which cointain all users *//
	@Override
	public ObservableList<User> getComponentList() {
		return getUserList();
	}
	@Override
	public void setComponentList(ObservableList<User> list) {
		setUserList(list);
	}
	//*This method add user to userList*//
	@Override
	public void addComponent(User component) {
		
		Optional<User> userOp = Optional.of(component);
		userOp.ifPresent(u->{
			getUserList().add(component);
			
		});
	}
	
	//*This method remove user from userList*//
	@Override
	public void removeComponent(User component) {

		Optional<User> userOp = Optional.of(component);
		userOp.ifPresent(u->{
			if(isComponentInList(component)){
				getUserList().remove(u);
			}
		});
	}
	
	//*This method check if user with exact name is in userList*//
	@Override
	public boolean isComponentInList(String name) {
		return	getUserList().stream().anyMatch(u->u.getName().equals(name));
	}
	
	//*This method check if object user is in userList*//
	@Override
	public boolean isComponentInList(User component) {
		return	getUserList().stream().anyMatch(u->u.equals(component));
	}
	
	//*This method return user from userList*//
	@Override
	public User getComponentFromList(String name) {
		
		User user = getUserList().stream().filter(u->u.getName().equals(name))
				  .collect(singletonCollector());
		return user;
	}
	
	//*This method return goal from userList*//
	@Override
	public User getComponentFromList(User component) {
		
		User user = getUserList().stream().filter(u->u.equals(component))
				  .collect(singletonCollector());
		return user;
	}





	
}
