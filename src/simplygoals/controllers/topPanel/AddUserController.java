package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.User;

public class AddUserController implements Initializable {

	private MainPanelController mainControl = new MainPanelController();

	@FXML
	private Label UserErrorLabel;

	@FXML
	private Button UserNameOkButton;

	@FXML
	private AnchorPane AddUserAnchor;

	@FXML
	private TextField UserNameTextField;

	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		addUser();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void addUser() {
		UserNameOkButton.setOnAction(x -> {
			if (mainControl.getModelLogic().getMySQL().isConnEstablished()) {
				if (!UserNameTextField.textProperty().getValue().isEmpty()) {
					String message = mainControl.getModelLogic().addUserToLogic(new User(UserNameTextField.getText()));
					UserErrorLabel.setTextFill(Color.GREEN);
					UserErrorLabel.setText(message);
					if (mainControl.getModelLogic().getUserList().size() == 1) {
						mainControl.getModelLogic().setCurrentUser(mainControl.getModelLogic().getUserList().get(0));
					}
					PauseTransition delay = new PauseTransition(Duration.seconds(1));
					delay.setOnFinished(event -> UserErrorLabel.setText(""));
					delay.play();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(stage);
					alert.setTitle("No Input");
					alert.setHeaderText("Lack of username");
					alert.setContentText("Please insert username to field");
					alert.showAndWait();
					// If there was no user and first User was added
					// automatically set this user as current User
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("No connection with database");
				alert.setHeaderText("No connection with database");
				alert.setContentText("Please check your connection with database");
				alert.showAndWait();
			}
		});
	}
}

