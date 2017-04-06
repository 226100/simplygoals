package simplygoals.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import simplygoals.controllers.MainPanelController;
import simplygoals.modelComponents.User;
import javafx.scene.control.ChoiceBox;
public class RemoveUserController extends MainController implements Initializable {
	private MainPanelController mainControl;
    @FXML
    private ChoiceBox<User> UserChoiceBox;

    @FXML
    private Label MessageLabel;

    @FXML
    private Button RemoveUserButton;

    @FXML
    void f50303(ActionEvent event) {

    }

    User removedUser=null;
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			

		}
		
		public void choiceBoxInit(){
			System.out.println(mainControl.getModelLogic());
			UserChoiceBox.setItems(mainControl.getModelLogic().getUserList());
		}
		public void observeChoiceBox(){
			UserChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue ov, Number value, Number new_value){
						if((new_value.intValue()>=0)&&(new_value.intValue()<=mainControl.getModelLogic().getUserList().size())){
							removedUser=mainControl.getModelLogic().getUserList().get(new_value.intValue());
						}
			}
			});
		}
		public void removeUser(){
			RemoveUserButton.setTooltip(new Tooltip("Select user"));
			RemoveUserButton.setOnAction(x->{
				String message = mainControl.getModelLogic().removeUserFromLogic(removedUser);
				MessageLabel.setTextFill(Color.RED);
				MessageLabel.setText(message);		
				UserChoiceBox.setItems(mainControl.getModelLogic().getUserList());	
				if(mainControl.getModelLogic().getUserList().size()>0){
					mainControl.getModelLogic().setCurrentUser(mainControl.getModelLogic().getUserList().get(0));	
				}
				String cUser =mainControl.getModelLogic().getCurrentUser().getName();
				cUser=cUser.substring(0, 1).toUpperCase() + cUser.substring(1);
				Button button = mainControl.getTopPanelController().getCurrentUserButton();
				button.setText(cUser);

			});
		}
			
		public void setMainControl(MainPanelController mainPanel){
			mainControl=mainPanel;
			choiceBoxInit();
			observeChoiceBox();
			removeUser();
		}
}
