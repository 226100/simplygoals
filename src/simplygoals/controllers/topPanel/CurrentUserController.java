package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import simplygoals.modelComponents.User;

/** This controller is responsible for handle setting current user */
public class CurrentUserController implements Initializable {

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
	private Button setCurrentUser;

	@FXML
	private Label currentUser;

	@FXML
	private ListView<User> userList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser.setTextFill(Color.BLUE);
	}

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl and next call other methods
	 */
	public void setMainControl(MainPanelController mainPanel) {
		this.mainControl = mainPanel;
		setUserList();
		setUser();
	}

	/**
	 * Get instance of current stage(CurrentUser) from MainPanelController and
	 * set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Handling of label which show current user(if is there a user set visible
	 * to true, if there is no user choose set this label to invisible) and
	 * handling of button setCurrentUser
	 */
	public void setUser() {
		if (Optional.ofNullable(mainControl.getModelLogic().getCurrentUser()).isPresent()) {
			currentUser.setVisible(true);
		} else {
			currentUser.setVisible(false);
		}
		currentUser.textProperty().bind(mainControl.getModelLogic().getCurrentUserProperty().asString());
		currentUser.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String oldVal, String newVal) {
				if (newVal == "null") {
					currentUser.setVisible(false);
				} else {
					currentUser.setVisible(true);
				}
			}
		});
		setCurrentUser.setOnAction(x -> {
			User user = userList.getSelectionModel().getSelectedItem();
			Optional<User> opUser = Optional.ofNullable(user);
			if (opUser.isPresent()) {
				mainControl.getModelLogic().setCurrentUser(user);
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

	/** Set user list of all users to list View */
	public void setUserList() {
		userList.setItems(mainControl.getModelLogic().getUserList());
	}
}