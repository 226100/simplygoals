package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import simplygoals.modelComponents.User;

public class CurrentUserController implements Initializable{
	
    private MainPanelController mainControl = new MainPanelController();

	@FXML
	private Button setCurrentUser;

	@FXML
	private Label currentUser;

	@FXML
	private ListView<User> userList;
	
	private Stage stage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser.setTextFill(Color.BLUE);
	}
	
	public void setMainControl(MainPanelController mainPanel){
		this.mainControl=mainPanel;
		setUserList();
		setUser();
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	//Handling of label which show current user and handling of button setCurrentUser
	public void setUser(){
		if(Optional.ofNullable(mainControl.getModelLogic().getCurrentUser()).isPresent()){
			currentUser.setVisible(true);
		}else{currentUser.setVisible(false);}
		currentUser.textProperty().bind(mainControl.getModelLogic().getCurrentUserProperty().asString());
		currentUser.textProperty().addListener(new ChangeListener<String>(){
	        @Override	
	         public void changed(ObservableValue<? extends String> o,String oldVal, String newVal){
	            if (newVal=="null"){
	                currentUser.setVisible(false);
	            }else{currentUser.setVisible(true);}
	         }
	    });
		setCurrentUser.setOnAction(x->{
			User user =userList.getSelectionModel().getSelectedItem();
			Optional<User> opUser=Optional.ofNullable(user);
			if(opUser.isPresent()){
				mainControl.getModelLogic().setCurrentUser(user);	
				mainControl.refreshTableView();	
			}else{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(stage);
		        alert.setTitle("No Selection");
		        alert.setHeaderText("No User Selected");
		        alert.setContentText("Please select a User in the list.");
		        alert.showAndWait();
			}
		});
	}
	
	public void setUserList(){
		userList.setItems(mainControl.getModelLogic().getUserList());
	}
}