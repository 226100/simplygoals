package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Category;

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
	
	public void setMainControl(MainPanelController mainPanel){
		mainControl=mainPanel;
		addCategory();
		setCategoryListView();
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
	

}