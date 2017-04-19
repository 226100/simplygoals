package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
	    
	private Stage stage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}

	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		setDataInBoxes();
		addGoal();
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	public void addGoal(){
		addGoalButton.setOnAction(x->{
			if(goalName.getText().length()>0&&dateOfEnd.getValue()!=null&&typeOfGoal.getSelectionModel().isEmpty()==false&&category.getSelectionModel().isEmpty()==false){
				mainControl.getModelLogic().addGoalToUser(
					new Goal.Builder(goalName.getText(),dateOfEnd.getValue().toString(),typeOfGoal.getSelectionModel().getSelectedItem() ).category(category.getSelectionModel().getSelectedItem()).notes(notes.getText()).build());
					msg.setText("Goal added Successfully");
			}else{
				
				Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(stage);
		        alert.setTitle("No Input");
		        alert.setHeaderText("All fields are not filled");
		        alert.setContentText("Please insert data to all of fields");
		        alert.showAndWait();
		}
		});
	}
	
	public void setDataInBoxes(){
		category.setItems(mainControl.getModelLogic().getCategoryList());
		typeOfGoal.setItems(FXCollections.observableArrayList(GoalType.DAILY_GOAL,GoalType.MONTHLY_GOAL,GoalType.WEEKLY_GOAL,GoalType.YEARLY_GOAL));
	}
}