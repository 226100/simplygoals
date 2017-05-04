package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Goal;

/** This controller handle removing goal */
public class RemoveGoalController implements Initializable {

	/**
	 * Variable of MainPanelController, used to get instance of object of type
	 * MainPanelController
	 */
	private MainPanelController mainControl = new MainPanelController();

	/**
	 * Variable of Stage, used to get access to current stage. Required to show
	 * alerts
	 */
	private Stage stage;

	@FXML
	private Label msg;

	@FXML
	private ListView<Goal> goalList;

	@FXML
	private Button remove;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl, next call other methods
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		setGoalListView();
		removeGoal();
	}

	/**
	 * Get instance of current stage(RemoveGoal) from MainPanelController and
	 * set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/** Set list of goals to list View */
	public void setGoalListView() {
		goalList.setItems(mainControl.getModelLogic().getAllGoalsList());
	}

	/**
	 * After click on button remove, if it's possible remove goal, otherwise
	 * show alert
	 */
	public void removeGoal() {
		remove.setOnAction(x -> {
			Goal goal = goalList.getSelectionModel().getSelectedItem();
			Optional<Goal> opGoal = Optional.ofNullable(goal);
			if (opGoal.isPresent()) {
				String message = mainControl.getModelLogic().removeGoalFromUser(goal);
				msg.setTextFill(Color.GREEN);
				msg.setText(message);
				setGoalListView();
				mainControl.refreshTableView();
			} else {
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