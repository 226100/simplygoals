package simplygoals.modelComponents;

import java.io.Serializable;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simplygoals.serialization.ClassSerializable;
import simplygoals.util.FileCreator;
import simplygoals.util.FileTester;

public class AllCategories implements Storable<Category>, Serializable, ClassSerializable<Category> {
	
	//***UID, CATEGORY LIST***//
	private static final String FILE_NAME= "src/CategoryList.obj";
	private static final long serialVersionUID = 3812017177088226527L;
	private ObservableList<Category> categoryList=FXCollections.observableArrayList();;
	
	//***GETTERS AND SETTERS***//
	
	public void setCategoryList(ObservableList<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	//*This getter for categortyList is only for intern use in a categoryList class*//
	private ObservableList<Category> getCategoryList() {
		return categoryList;
	}
	
	//***CONSTRUCTORS***//
	public AllCategories(){
		
		if(FileTester.hasApplicationFile(FILE_NAME)){
			setCategoryList(serializeFromFile(FILE_NAME));
		}else{
			FileCreator.createFile(FILE_NAME);
		}
	
	}
	
	//***HANDLE CATEGORY LIST***//
	
	//*This method return observable list (outside a class) which cointain all categories*//
	@Override
	public ObservableList<Category> getComponentList() {
		return getCategoryList();
	}
	
	//*This method add category to categoryList*//
	@Override
	public void addComponent(Category component) {
		Optional<Category> categoryOp = Optional.of(component);
		categoryOp.ifPresent(c->{
			getCategoryList().add(c);
			if(FileTester.hasApplicationFile(FILE_NAME)){
				serializeToFile(getCategoryList(),FILE_NAME);
			}else{
				FileCreator.createFile(FILE_NAME);
				serializeToFile(getCategoryList(),FILE_NAME);
			}
		});
		
	}
	
	//*This method remove category from categoryList*//
	@Override
	public void removeComponent(Category component) {
		Optional<Category> categoryOp = Optional.of(component);
		categoryOp.ifPresent(c->{
			if(isComponentInList(component)){
				getCategoryList().remove(c);
				if(FileTester.hasApplicationFile(FILE_NAME)){
					serializeToFile(getCategoryList(),FILE_NAME);
				}else{
					FileCreator.createFile(FILE_NAME);
					serializeToFile(getCategoryList(),FILE_NAME);
				}
			}
		});
		
	}
	
	//*This method check if category with exact name is in categoryList*//
	@Override
	public boolean isComponentInList(String name) {
		return	getCategoryList().stream().anyMatch(c->c.getName().equals(name));
	}
	
	//*This method check if object category is in categoryList*//
	@Override
	public boolean isComponentInList(Category component) {
		return	getCategoryList().stream().anyMatch(c->c.equals(component));
	}
	
	//*This method return category from categoryList*//
	@Override
	public Category getComponentFromList(String name) {
		Category category = getCategoryList().stream().filter(c->c.getName().equals(name))
				  .collect(singletonCollector());
		return category;
	}
	
	//*This method return category from categoryList*//
	@Override
	public Category getComponentFromList(Category component) {
		Category category = getCategoryList().stream().filter(c->c.equals(component))
				  .collect(singletonCollector());
		return category;
	}

	@Override
	public void setComponentList(ObservableList<Category> list) {
		this.categoryList=list;
	}
}
