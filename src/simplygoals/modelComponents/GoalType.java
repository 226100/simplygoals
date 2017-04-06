package simplygoals.modelComponents;

public enum GoalType {
	
	DAILY_GOAL(1),WEEKLY_GOAL(2),MONTHLY_GOAL(3),YEARLY_GOAL(4);

    private int id;
    GoalType(int id) {
       this.id = id;
    }
     
    public int getId() {
        return id;
    }
   public static GoalType goalTypeFromId(int id) {
        for(GoalType g: GoalType.values()) {
            if(g.getId()==id)
                return g;
        }
        return GoalType.DAILY_GOAL;
    }
}
