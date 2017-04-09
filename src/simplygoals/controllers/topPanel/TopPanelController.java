package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopPanelController implements Initializable {
	 
    @FXML
    private Button RemoveGoalButton;
    
    @FXML
    private Button currentUserButton;
    
    @FXML
    private Button AddCategoryButton;
    
    @FXML
    private Button AddGoalButton;
    
    @FXML
    private Button RemoveUserButton;
    
    @FXML
    private Button AddUserButton;
    
    @FXML
    private Button RemoveCategoryButton;
    
    @FXML
    private HBox TopPanelBox;

	@FXML
    private Button StatisticsButton;
	
	
	public Button getAddCategoryButton() {
		return AddCategoryButton;
	}

	public Button getAddGoalButton() {
		return AddGoalButton;
	}

	public Button getRemoveUserButton() {
		return RemoveUserButton;
	}

	public Button getAddUserButton() {
		return AddUserButton;
	}

    public Button getRemoveCategoryButton() {
		return RemoveCategoryButton;
	}

	public Button getStatisticsButton() {
		return StatisticsButton;
	}

	public Button getCurrentUserButton() {
		return currentUserButton;
	}
    public Button getRemoveGoalButton() {
		return RemoveGoalButton;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}