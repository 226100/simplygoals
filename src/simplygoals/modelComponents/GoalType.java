package simplygoals.modelComponents;

/**Types of goals */
public enum GoalType {
	
	DAILY_GOAL(1, "Daily Goal"),WEEKLY_GOAL(2, "Weekly Goal"),MONTHLY_GOAL(3, "Monthly Goal"),YEARLY_GOAL(4, "Yearly Goal");

    private int id;
    private String desc;
    
    GoalType(int id, String desc) {
       this.id = id;
       this.desc=desc;
    }
     
    public int getId() {
        return id;
    }
    public String getDesc(){
    	return desc;
    }
   public static GoalType goalTypeFromId(int id) {
        for(GoalType g: GoalType.values()) {
            if(g.getId()==id)
                return g;
        }
        return GoalType.DAILY_GOAL;
    }
   @Override
   public String toString(){
	   return desc;
   }
}
