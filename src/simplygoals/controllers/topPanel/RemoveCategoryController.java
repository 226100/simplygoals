package simplygoals.controllers.topPanel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Category;

public class RemoveCategoryController implements Initializable{

	private MainPanelController mainControl = new MainPanelController();
	    
	@FXML
	private BorderPane BorderPaneWindow;

	@FXML
	private Button removeButton;

	@FXML
	private ListView<Category> removeCategoryView;
	
	@FXML
	private Label msgLabel;

	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
	}
	
	public void setMainControl(MainPanelController mainPanel){
		this.mainControl=mainPanel;
		removeCategory();
		setCategoryListView();	
	}
	
	public void setCategoryListView(){
		removeCategoryView.setItems(mainControl.getModelLogic().getCategoryList());
	}
		
	public void removeCategory(){
		removeButton.setOnAction(x->{
			Category cat = removeCategoryView.getSelectionModel().getSelectedItem();
			Optional<Category> opCat=Optional.of(cat);
			System.out.println(cat);
			opCat.ifPresent(t->{
				String message=mainControl.getModelLogic().removeCategoryFromLogic(cat);
				msgLabel.setTextFill(Color.RED);
				msgLabel.setText(message);	
				setCategoryListView();
			});
		});
	}

}