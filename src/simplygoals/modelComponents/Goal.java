package simplygoals.modelComponents;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import simplygoals.util.DateUtil;
public class Goal implements Comparable<Goal>{
	
	//***NAME, PLANNED DATE OF END, FIELDCOLOR, REALENDDATE, CATEGORY, EXECUTED, NOTES***//
	private final StringProperty name;
	private final ObjectProperty<LocalDate> plannedDateOfEnd;
	private ObjectProperty<LocalDate> realEndDate;
	private ObjectProperty<Category> category;
	private final ObjectProperty<GoalType> type;
	private BooleanProperty finished;
	private StringProperty notes;
	
	//***GETTERS AND SETTERS***//
	public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
    
	public LocalDate getPlannedDateOfEnd() {
        return plannedDateOfEnd.get();
    }

    public void setPlannedDateOfEnd(LocalDate date) {
        this.plannedDateOfEnd.set(date);
    }

    public ObjectProperty<LocalDate> plannedDateOfEndProperty() {
        return plannedDateOfEnd;
    }
      
	public LocalDate getRealEndDate() {
        return realEndDate.get();
    }

    public void setRealEndDate(LocalDate date) {
        this.realEndDate.set(date);
    }

    public ObjectProperty<LocalDate> realEndDateProperty() {
        return realEndDate;
    }

    
	public Category getCategory() {
        return category.get();
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }
    
	public GoalType getType() {
        return type.get();
    }

    public void setType(GoalType type) {
        this.type.set(type);
    }

    public ObjectProperty<GoalType> typeProperty() {
        return type;
    }
     
    public boolean getFinished() {
        return finished.get();
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public BooleanProperty finishedProperty() {
        return finished;
    }
    
    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty() {
        return notes;
    }
	//***BUILDER PATTERN***
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((finished == null) ? 0 : finished.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((plannedDateOfEnd == null) ? 0 : plannedDateOfEnd.hashCode());
		result = prime * result + ((realEndDate == null) ? 0 : realEndDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Goal other = (Goal) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (finished == null) {
			if (other.finished != null)
				return false;
		} else if (!finished.equals(other.finished))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (plannedDateOfEnd == null) {
			if (other.plannedDateOfEnd != null)
				return false;
		} else if (!plannedDateOfEnd.equals(other.plannedDateOfEnd))
			return false;
		if (realEndDate == null) {
			if (other.realEndDate != null)
				return false;
		} else if (!realEndDate.equals(other.realEndDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	//*Private constructor for class Goal*//
	private Goal(final Builder builder)
    {
        this.name = new SimpleStringProperty(builder.name);
        this.plannedDateOfEnd = new SimpleObjectProperty<LocalDate>(builder.plannedDateOfEnd);
        this.category = new SimpleObjectProperty<Category>(builder.category);
        this.type=new SimpleObjectProperty<GoalType>(builder.type);
        this.notes = new SimpleStringProperty(builder.notes);
        this.realEndDate=new SimpleObjectProperty<LocalDate>(builder.realEndDate);
        this.finished=new SimpleBooleanProperty(builder.finished);
    }
 
 
	//*Builder class*//
	public static class Builder
    {
    	private final String name;
    	private final LocalDate plannedDateOfEnd;
    	private final GoalType type;
    	private LocalDate realEndDate;
    	private Category category;
    	boolean finished;
    	private String notes;
    	
        public Builder(final String name, final String date, final GoalType type)
        {
            this.name = name;
            if(DateUtil.validDate(date)==false){
            	this.plannedDateOfEnd=null;
            }else{
            	this.plannedDateOfEnd=DateUtil.parse(date);
            }
            this.type = type;
        }
        public Builder category(final Category category)
        {
            this.category = category;
            return this;
        }
        public Builder finished(final boolean finished){
        	this.finished=finished;
        	return this;
        }
        public Builder realEndDate(final String date){
        	if(DateUtil.validDate(date)==false){
        		this.realEndDate=null;
        	}else{
        		this.realEndDate = DateUtil.parse(date);
        	}
        	return this;
        }
        public Builder notes(final String notes)
        {
            this.notes = notes;
            return this;
        }
 
        public Goal build()
        {
            return new Goal(this);
        }
 
    }
	//***TO STRING, COMPARE, EQUAL AND HASHCODE FOR GOAL***//
	@Override
	public int compareTo(Goal goal) {
		return getName().compareTo(goal.getName());
	}

	@Override
	public String toString() {
		String name =this.getName();
		name=name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
}