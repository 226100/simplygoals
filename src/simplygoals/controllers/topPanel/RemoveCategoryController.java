package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Category;

/** This controller is responsible for handling removing category in window */
public class RemoveCategoryController implements Initializable {

	/**
	 * Variable of MainPanelController, used to get instance of object of type
	 * MainPanelController
	 */
	private MainPanelController mainControl = new MainPanelController();

	/**
	 * Variable of Stage, used to get access to current stage. Required to show
	 * alerts
	 */
	private Stage stage;

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

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl and next call other methods
	 */
	public void setMainControl(MainPanelController mainPanel) {
		this.mainControl = mainPanel;
		removeCategory();
		setCategoryListView();
	}

	/**
	 * Get instance of current stage(RemoveCategory) from MainPanelController
	 * and set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/** Set current list of categories to listView */
	public void setCategoryListView() {
		removeCategoryView.setItems(mainControl.getModelLogic().getCategoryList());
	}

	/**
	 * After click on button removeButton if it's possible remove Category,
	 * otherwise show alert
	 */
	public void removeCategory() {
		removeButton.setOnAction(x -> {
			Category cat = removeCategoryView.getSelectionModel().getSelectedItem();
			Optional<Category> opCat = Optional.ofNullable(cat);
			if (opCat.isPresent()) {
				String message = mainControl.getModelLogic().removeCategoryFromLogic(cat);
				msgLabel.setTextFill(Color.GREEN);
				msgLabel.setText(message);
				setCategoryListView();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("No Selection");
				alert.setHeaderText("No Category Selected");
				alert.setContentText("Please select a Category in the list.");
				alert.showAndWait();
			}
		});
	}
}