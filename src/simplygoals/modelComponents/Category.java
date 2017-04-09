package simplygoals.modelComponents;
import java.io.Serializable;

import javafx.scene.paint.Color;

public class Category implements Comparable<Category>, Serializable{
	
	//***UID, NAME, FIELD COLOR***//
	private static final long serialVersionUID = 3812017177088226528L;
	private final String name;
	
	//***GETTERS AND SETTERS
	
	public String getName() {
		return name;
	}
	
	//***CONSTRUCTORS***//
	public Category(String name){this.name=name;}
	
	public Category(String name, Color fieldColor){
		this.name=name;
	}
	
	
	//***TO STRING, COMPARE, EQUAL AND HASHCODE FOR GOAL***//
	@Override
	public String toString() {
		String name =this.getName();
		name=name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
	@Override
	public int compareTo(Category category) {
		return name.compareTo(category.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}