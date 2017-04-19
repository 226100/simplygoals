package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Goal;

public class RemoveGoalController implements Initializable {
	
	private MainPanelController mainControl = new MainPanelController();
	    
	@FXML
	private Label msg;

	@FXML
	private ListView<Goal> goalList;

	@FXML
	private Button remove;
	
	private Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		setGoalListView();
		removeGoal();
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	public void setGoalListView(){
		goalList.setItems(mainControl.getModelLogic().getAllGoalsList());	
	}
	
	public void removeGoal(){
		remove.setOnAction(x->{
			Goal goal = goalList.getSelectionModel().getSelectedItem();
			Optional<Goal> opGoal=Optional.ofNullable(goal);
			if(opGoal.isPresent()){
				String message=mainControl.getModelLogic().removeGoalFromUser(goal);
				msg.setTextFill(Color.GREEN);
				msg.setText(message);	
				setGoalListView();
				mainControl.refreshTableView();
			}else{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(stage);
		        alert.setTitle("No Selection");
		        alert.setHeaderText("No Goal Selected");
		        alert.setContentText("Please select a Goal in the list.");
		        alert.showAndWait();
			}
		});
	}	
}