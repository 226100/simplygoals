package simplygoals.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import simplygoals.controllers.MainPanelController;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.User;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
public class AddCategoryController implements Initializable {

	private MainPanelController mainControl = new MainPanelController();
    @FXML
    private Label msg;
	@FXML
	private BorderPane borderPane;
	    
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Label messageAddCategory;

	@FXML
	private Button buttonAddCategory;

	@FXML
	private ListView<Category> listViewAddCategory;

	@FXML
	private TextField textFieldAddCategory;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	public void addCategory(){
		buttonAddCategory.setOnAction(x->{
			String text = textFieldAddCategory.getText();
			Optional<String> opText=Optional.of(text);
			opText.ifPresent(t->{
				String message=mainControl.getModelLogic().addCategoryToLogic(new Category(text));
				messageAddCategory.setTextFill(Color.RED);
				messageAddCategory.setText(message);	
				setCategoryListView();
			});
		});
	}
	public void setCategoryListView(){
	
		listViewAddCategory.setItems(mainControl.getModelLogic().getCategoryList());
	}
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		addCategory();
		setCategoryListView();
		
	}
}