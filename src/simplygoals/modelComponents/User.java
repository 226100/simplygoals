package simplygoals.modelComponents;
import java.util.Optional;

import javafx.collections.ObservableList;

public class User implements Storable<Goal>, Comparable<User> {
	
	//***NAME, GOALLIST***//
	private final String name;
	private ObservableList<Goal> goalList;
	

	
	//***GETTERS AND SETTERS//
	
	public String getName() {
		return this.name;
	}
	
	//*This getter for goalList is only for intern use in a User class*//
	private ObservableList<Goal> getGoalList() {	
		return goalList;
	}
	public void setGoalList(ObservableList<Goal> goalList) {
		this.goalList = goalList;
	}
	//***CONSTRUCTORS***//
	public User(String name){
		this.name=name;
		
	}
	
	//***HANDLE GOAL AND GOALLIST***
	
	//*This method return observablelist of goals created from arraylist of goals*//
	@Override
	public ObservableList<Goal> getComponentList() {
		return getGoalList();
	}
	
	//*This method add goal to goalList in object User*//
	@Override
	public void addComponent(Goal component) {	
		Optional<Goal> goalOp = Optional.of(component);
		goalOp.ifPresent(g->{
			getGoalList().add(g);
		});
	}
	
	//*This method remove goal from goalList in object User*//
	@Override
	public void removeComponent(Goal component) {
		Optional<Goal> goalOp = Optional.of(component);
		goalOp.ifPresent(g->{
			if(isComponentInList(component)){
				getGoalList().remove(g);
			}
		});
	}
	
	//*This method check if goal with exact name is in goalList*//
	@Override
	public boolean isComponentInList(String name) {
	    return	getGoalList().stream().anyMatch(g->g.getName().equals(name));
	}
	
	//*This method check if object goal is in goalList*//
	@Override
	public boolean isComponentInList(Goal component) {
		return	getGoalList().stream().anyMatch(g->g.equals(component));
	}
	
	//*This method return goal from goalList*//
	@Override
	public Goal getComponentFromList(String name) {
		Goal goal = getGoalList().stream().filter(g->g.getName().equals(name))
							  .collect(singletonCollector());
		return goal;
	}
	
	//*This method return goal from goalList*//
	@Override
	public Goal getComponentFromList(Goal component) {
		Goal goal = getGoalList().stream().filter(g->g.equals(component))
				  .collect(singletonCollector());
		return goal;
	}
	
	//***TO STRING, COMPARE, EQUAL AND HASHCODE FOR USER***//
	
	@Override
	public String toString(){
		String name =this.getName();
		name=name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
	@Override
	public int compareTo(User user) {
		return name.compareTo(user.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goalList == null) ? 0 : goalList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (goalList == null) {
			if (other.goalList != null)
				return false;
		} else if (!goalList.equals(other.goalList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void setComponentList(ObservableList<Goal> list) {
		this.goalList=list;
	}	
}