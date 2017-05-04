package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.User;

/**This controller is responsible for removing users */
public class RemoveUserController implements Initializable {

	/**
	 * Variable of MainPanelController, used to get instance of object of type
	 * MainPanelController
	 */
	private MainPanelController mainControl;

	/**
	 * Variable of Stage, used to get access to current stage. Required to show
	 * alerts
	 */
	private Stage stage;

	/** This variable store object of user, which is set in choice box */
	User removedUser = null;

	@FXML
	private ChoiceBox<User> UserChoiceBox;

	@FXML
	private Label MessageLabel;

	@FXML
	private Button RemoveUserButton;

	@FXML
	void f50303(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl and next call other methods
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		choiceBoxInit();
		observeChoiceBox();
		removeUser();
	}

	/**
	 * Get instance of current stage(RemoveUser) from MainPanelController and
	 * set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/** Initialize choice Box with list of users */
	public void choiceBoxInit() {
		UserChoiceBox.setItems(mainControl.getModelLogic().getUserList());
	}

	/**
	 * Observer for choiceBox, if value in choice box is changing set this value
	 * to variable removedUser
	 */
	public void observeChoiceBox() {
		UserChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				if ((new_value.intValue() >= 0)
						&& (new_value.intValue() <= mainControl.getModelLogic().getUserList().size())) {
					removedUser = mainControl.getModelLogic().getUserList().get(new_value.intValue());
				}
			}
		});
	}

	/**
	 * After click on RemoveUserButton, if it is possible remove User from
	 * application, otherwise show alert
	 */
	public void removeUser() {
		RemoveUserButton.setTooltip(new Tooltip("Select user"));
		RemoveUserButton.setOnAction(x -> {
			User user = UserChoiceBox.getSelectionModel().getSelectedItem();
			if (Optional.ofNullable(user).isPresent()) {
				String message = mainControl.getModelLogic().removeUserFromLogic(removedUser);
				MessageLabel.setTextFill(Color.RED);
				MessageLabel.setText(message);
				PauseTransition delay = new PauseTransition(Duration.seconds(1));
				delay.setOnFinished(event -> MessageLabel.setText(""));
				delay.play();
				UserChoiceBox.setItems(mainControl.getModelLogic().getUserList());
				if (mainControl.getModelLogic().getUserList().size() > 0) {
					mainControl.getModelLogic().setCurrentUser(mainControl.getModelLogic().getUserList().get(0));
				} else {
					mainControl.getModelLogic().setCurrentUser(null);
				}
				mainControl.refreshTableView();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("No Selection");
				alert.setHeaderText("No User Selected");
				alert.setContentText("Please select a User in the list.");
				alert.showAndWait();
			}
		});
	}

}