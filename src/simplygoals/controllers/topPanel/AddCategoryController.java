package simplygoals.controllers.topPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.Category;

/** Controller responsible for handle action in window add category */
public class AddCategoryController implements Initializable {

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

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl and next call addCategory() method and
	 * setCategoryListView() method
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		addCategory();
		setCategoryListView();
	}

	/**
	 * Get instance of current stage(AddCategory) from MainPanelController and
	 * set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * If user click on button buttonAddCategory add Category to apllication if
	 * it's possible, otherwise show alert
	 */
	public void addCategory() {
		buttonAddCategory.setOnAction(x -> {
			String text = textFieldAddCategory.getText();

			if (text.length() > 0) {
				String message = mainControl.getModelLogic().addCategoryToLogic(new Category(text));
				messageAddCategory.setTextFill(Color.GREEN);
				messageAddCategory.setText(message);
				setCategoryListView();
			} else {

				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("No Input");
				alert.setHeaderText("No Category in Text Field");
				alert.setContentText("Please insert a name of category in the text field");
				alert.showAndWait();
			}
		});
	}

	/** Set list of categories to list view */
	public void setCategoryListView() {
		listViewAddCategory.setItems(mainControl.getModelLogic().getCategoryList());
	}

}