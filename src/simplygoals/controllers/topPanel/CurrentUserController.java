package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser.setTextFill(Color.BLUE);
	}
	
	public void setMainControl(MainPanelController mainPanel){
		this.mainControl=mainPanel;
		setUserList();
		setUser();
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
			Optional<User> opUser=Optional.of(user);
			opUser.ifPresent(t->{
				mainControl.getModelLogic().setCurrentUser(t);	
				mainControl.refreshTableView();	
			});
		});
	}
	
	public void setUserList(){
		userList.setItems(mainControl.getModelLogic().getUserList());
	}
}