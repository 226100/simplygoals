package simplygoals.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
public class RemoveGoalController implements Initializable {

	    private MainPanelController mainControl = new MainPanelController();
	    @FXML
	    private Label msg;

	    @FXML
	    private ListView<Goal> goalList;

	    @FXML
	    private Button remove;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			
		}
		public void setGoalListView(){
				goalList.setItems(mainControl.getModelLogic().getAllGoalsList());	
		}
		public void removeGoal(){
			remove.setOnAction(x->{
				Goal goal = goalList.getSelectionModel().getSelectedItem();
				Optional<Goal> opGoal=Optional.of(goal);
				opGoal.ifPresent(t->{
					String message=mainControl.getModelLogic().removeGoalFromUser(goal);
					msg.setTextFill(Color.RED);
					msg.setText(message);	
					setGoalListView();
				});
			});
		}
		
		public void setMainControl(MainPanelController mainPanel){
			mainControl=mainPanel;
			setGoalListView();
			removeGoal();
		}

}
