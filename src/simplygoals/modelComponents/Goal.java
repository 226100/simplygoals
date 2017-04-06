package simplygoals.modelComponents;
import java.time.LocalDate;
import javafx.scene.paint.Color;
public class Goal implements Comparable<Goal>{
	
	//***NAME, PLANNED DATE OF END, FIELDCOLOR, REALENDDATE, CATEGORY, EXECUTED, NOTES***//
	private final String name;
	private final LocalDate plannedDateOfEnd;
	private LocalDate realEndDate;
	private Category category;
	private final GoalType type;
	private boolean executed;
	private String notes;
	
	//***GETTERS AND SETTERS***//
	public String getName() {
			return name;
		}

	public LocalDate getPlannedDateOfEnd() {
		return plannedDateOfEnd;
	}
	public LocalDate getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(LocalDate realEndDate) {
		this.realEndDate=realEndDate;
	}

	public Category getCategory() {
		return category;
	}

	public GoalType getType() {
		return type;
	}


	public boolean isExecuted() {
		return executed;
	}
	
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
	public String getNotes() {
		return notes;
	}

	//***BUILDER PATTERN***
	
	//*Private constructor for class Goal*//
	private Goal(final Builder builder)
    {
        this.name = builder.name;
        this.plannedDateOfEnd = builder.plannedDateOfEnd;
        this.category = builder.category;
        this.type=builder.type;
        this.notes = builder.notes;
        
    }
 
 
	//*Builder class*//
	public static class Builder
    {
    	private final String name;
    	private final LocalDate plannedDateOfEnd;
    	private final GoalType type;
    	private LocalDate realEndDate;
    	private Category category;
    	boolean executed;
    	private String notes;
    	
        public Builder(final String name, final String date, final GoalType type)
        {
            this.name = name;
            this.plannedDateOfEnd=LocalDate.parse(date);
            this.type = type;
        }
        public Builder category(final Category category)
        {
            this.category = category;
            return this;
        }
        public Builder executed(final boolean executed){
        	this.executed=executed;
        	return this;
        }
        public Builder realEndDate(final String date){
        	this.realEndDate = LocalDate.parse(date);
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
		return name.compareTo(goal.getName());
	}

	@Override
	public String toString() {
		String name =this.getName();
		name=name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + (executed ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((plannedDateOfEnd == null) ? 0 : plannedDateOfEnd.hashCode());
		result = prime * result + ((realEndDate == null) ? 0 : realEndDate.hashCode());
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
		if (executed != other.executed)
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
		return true;
	}



}
