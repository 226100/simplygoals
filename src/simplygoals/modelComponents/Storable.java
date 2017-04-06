package simplygoals.modelComponents;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

public interface Storable<T> {
	public ObservableList<T> getComponentList();
	public void setComponentList(ObservableList<T> list);
	public void addComponent(T component);
	public void removeComponent(T component);
	public boolean isComponentInList(String name);
	public boolean isComponentInList(T component);
	public T getComponentFromList(String name);
	public T getComponentFromList(T component);
	
	default public <T> Collector<T, ?, T> singletonCollector() {
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
