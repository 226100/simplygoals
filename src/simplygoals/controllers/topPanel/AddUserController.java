package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		addUser();
	}
	
	public void addUser(){
		UserNameOkButton.setOnAction(x->{
			if (!UserNameTextField.textProperty().getValue().isEmpty()){
				String message=mainControl.getModelLogic().addUserToLogic(new User(UserNameTextField.getText()));
				UserErrorLabel.setTextFill(Color.GREEN);
				UserErrorLabel.setText(message);
				if(mainControl.getModelLogic().getUserList().size()==1){
					mainControl.getModelLogic().setCurrentUser(mainControl.getModelLogic().getUserList().get(0));	
				}
				PauseTransition delay = new PauseTransition(Duration.seconds(1));
				delay.setOnFinished( event -> UserErrorLabel.setText("") );
				delay.play();
			}else{
				UserErrorLabel.setTextFill(Color.RED);
				UserErrorLabel.setText("Please fill text field with your username");
				PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
				delay.setOnFinished( event -> UserErrorLabel.setText("") );
				delay.play();
				}
			//If there was no user and first User was added automatically set this user as current User

		});
	}
}
