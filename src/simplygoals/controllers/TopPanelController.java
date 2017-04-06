package simplygoals.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.HBox;
import simplygoals.modelComponents.User;

import java.net.URL;
import java.util.ResourceBundle;

public class TopPanelController extends MainController implements Initializable {
	 
    @FXML
    private Button RemoveGoalButton;
    public Button getRemoveGoalButton() {
		return RemoveGoalButton;
	}
    @FXML
    private Button AddCategoryButton;
	public Button getAddCategoryButton() {
		return AddCategoryButton;
	}


    @FXML
    private Button AddGoalButton;
	public Button getAddGoalButton() {
		return AddGoalButton;
	}
    @FXML
    private Button RemoveUserButton;
	public Button getRemoveUserButton() {
		return RemoveUserButton;
	}
    @FXML
    private Button AddUserButton;
	public Button getAddUserButton() {
		return AddUserButton;
	}
    @FXML
    private Button RemoveCategoryButton;
    public Button getRemoveCategoryButton() {
		return RemoveCategoryButton;
	}
    @FXML
    private HBox TopPanelBox;


	@FXML
    private Button StatisticsButton;
	public Button getStatisticsButton() {
		return StatisticsButton;
	}
    @FXML
    private Button currentUserButton;
    
	public Button getCurrentUserButton() {
		return currentUserButton;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

















}
