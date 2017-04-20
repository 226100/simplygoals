package simplygoals.controllers.mainPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
public class GoalDetailsController implements Initializable {
	private MainPanelController mainControl = new MainPanelController();
	private Goal goal=null;
	@FXML
	private ChoiceBox<GoalType> typeList;

	@FXML
	private ChoiceBox<Category> categoryList;

	@FXML
	private CheckBox isFinished;

	@FXML
	private DatePicker realDateOfEnd;
	
    @FXML
    private TextArea notes;
    
    @FXML
    private Button save;

    @FXML
    private TextField name;

    @FXML
    private TextField plannedDateOfEnd;
    
    private Stage stage;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		showGoalDetails();
		saveGoal();
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	public void setGoal(Goal goal){
		this.goal=goal;
	}
	
	public void showGoalDetails(){
		
		if (Optional.ofNullable(goal).isPresent()) {
	        
			name.setText(this.goal.toString());
			plannedDateOfEnd.setText(this.goal.getPlannedDateOfEnd().toString());
			realDateOfEnd.setValue(this.goal.getRealEndDate());
			categoryList.setItems(mainControl.getModelLogic().getCategoryList());
			categoryList.setValue(this.goal.getCategory());;
			isFinished.setSelected(this.goal.getExecuted());
			typeList.setItems(FXCollections.observableArrayList(GoalType.DAILY_GOAL,GoalType.MONTHLY_GOAL,GoalType.WEEKLY_GOAL,GoalType.YEARLY_GOAL));
			typeList.setValue(this.goal.getType());
			notes.setText(this.goal.getNotes());
	    } else {

	    	name.setText("");
	    	plannedDateOfEnd.setText("");
			notes.setText("");
	    }
	}
	public void saveGoal(){
		save.setOnAction(x->{

			if(name.getText().length()>0&plannedDateOfEnd.getText().length()>0&&realDateOfEnd.getValue()!=null&&categoryList.getSelectionModel().isEmpty()==false&&typeList.getSelectionModel().isEmpty()==false){
						this.goal.setRealEndDate(realDateOfEnd.getValue());
						this.goal.setCategory(categoryList.getValue());
						this.goal.setExecuted(isFinished.isSelected());
						this.goal.setType(typeList.getValue());
						this.goal.setNotes(notes.getText());				
						mainControl.getModelLogic().updateGoal(this.goal);
						Alert alert = new Alert(AlertType.WARNING);
				        alert.initOwner(stage);
				        alert.setTitle("Goal saved");
				        alert.setContentText("Goal was saved");
				        alert.showAndWait();
				        mainControl.refreshTableView();
						
			}else{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(stage);
		        alert.setTitle("No inputs");
		        alert.setHeaderText("No all fields filled");
		        alert.setContentText("Please fill all fields with data");
		        alert.showAndWait();
			}
		});
	}
}


