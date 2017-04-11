package simplygoals.controllers.mainPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
public class GoalDetailsController implements Initializable {
	private MainPanelController mainControl = new MainPanelController();
	private Goal goal=null;
	 @FXML
	private ChoiceBox<GoalType> typeList;

	@FXML
	private Label name;

	@FXML
	private ChoiceBox<Category> categoryList;

	@FXML
	private Label finished;

	@FXML
	private Label category;

	@FXML
	private Label type;

	@FXML
	private CheckBox isFinished;

	@FXML
	private Label pDateEnd;

	@FXML
	private Label rDateEnd;

	@FXML
	private DatePicker realDateOfEnd;
	
    @FXML
    private TextArea notes;
    
    @FXML
    private Button save;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		showGoalDetails();
	}
	public void setGoal(Goal goal){
		this.goal=goal;
	}
	public void showGoalDetails(){
		System.out.println(this.goal);
		if (this.goal != null) {
	        
			name.setText(this.goal.getName());
			pDateEnd.setText(this.goal.getPlannedDateOfEnd().toString());
			rDateEnd.setText(this.goal.getRealEndDate().toString());
			category.setText(this.goal.getCategory().toString());
			finished.setText(Boolean.toString(this.goal.getExecuted()));
			type.setText(this.goal.getType().getDesc());
			notes.setText(this.goal.getNotes());
	    } else {

	    	name.setText("");
			pDateEnd.setText("");
			rDateEnd.setText("");
			category.setText("");
			finished.setText("");
			type.setText("");
			notes.setText("");
	    }
	}
}


