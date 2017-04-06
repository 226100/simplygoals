package simplygoals.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import simplygoals.modelComponents.Category;
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
		public void setUser(){

			currentUser.setText(mainControl.getModelLogic().getCurrentUser().toString());
			
			setCurrentUser.setOnAction(x->{
				User user =userList.getSelectionModel().getSelectedItem();
				Optional<User> opUser=Optional.of(user);
				opUser.ifPresent(t->{
					mainControl.getModelLogic().setCurrentUser(t);
					
					currentUser.setText(t.toString());	
					Button button = mainControl.getTopPanelController().getCurrentUserButton();
					button.setText(t.toString());
					setUserList();
					
				});
			});
		}
		public void setUserList(){
			userList.setItems(mainControl.getModelLogic().getUserList());
			userList.setCellFactory(lv -> {
		        TextFieldListCell<User> cell = new TextFieldListCell<User>();
		        cell.setConverter(new StringConverter<User>() {
		            @Override
		            public String toString(User user) {
		            	String currUser = user.getName();
		    			currUser=currUser.substring(0, 1).toUpperCase() + currUser.substring(1);
		            	return currUser;
		            }
		            @Override
		            public User fromString(String string) {
		            	User user = cell.getItem();
		                return user ;
		            }
		        });
		        return cell;
		    });
		}
		public void setMainControl(MainPanelController mainPanel){
			this.mainControl=mainPanel;
			setUserList();
			setUser();
		}
	}


