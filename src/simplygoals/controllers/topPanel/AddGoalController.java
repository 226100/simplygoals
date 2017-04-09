package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;

public class AddGoalController implements Initializable {

	private MainPanelController mainControl = new MainPanelController();
	  
    @FXML
    private Label msg;
    
    @FXML
    private Button addGoalButton;
    
	@FXML
	private ComboBox<GoalType> typeOfGoal;

	@FXML
	private TextArea notes;
	    
	@FXML
	private DatePicker dateOfEnd;
	    

	@FXML
	private TextField goalName;
	    
	@FXML
	private ImageView goalAdded;
	   
	@FXML
	private ComboBox<Category> category;
	    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}

	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		setDataInBoxes();
		addGoal();
	}
	
	public void addGoal(){
		addGoalButton.setOnAction(x->{
			if(goalName.getText().length()>0&&dateOfEnd.getValue()!=null&&typeOfGoal.getSelectionModel().isEmpty()==false&&category.getSelectionModel().isEmpty()==false){
			mainControl.getModelLogic().addGoalToUser(
					new Goal.Builder(goalName.getText(),dateOfEnd.getValue().toString(),typeOfGoal.getSelectionModel().getSelectedItem() ).category(category.getSelectionModel().getSelectedItem()).notes(notes.getText()).build());
					msg.setText("Goal added Successfully");
			}else{msg.setText("Data in all fields required");}
		});
	}
	
	public void setDataInBoxes(){
		category.setItems(mainControl.getModelLogic().getCategoryList());
		typeOfGoal.setItems(FXCollections.observableArrayList(GoalType.DAILY_GOAL,GoalType.MONTHLY_GOAL,GoalType.WEEKLY_GOAL,GoalType.YEARLY_GOAL));
	}
}