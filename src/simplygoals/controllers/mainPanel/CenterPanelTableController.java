package simplygoals.controllers.mainPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import simplygoals.modelComponents.Goal;

/** Controller is responsible for show data in table view */
public class CenterPanelTableController implements Initializable {

	/**
	 * Variable of MainPanelController, used to get instance of object of type
	 * MainPanelController
	 */
	private MainPanelController mainControl = new MainPanelController();

	@FXML
	private TableColumn<Goal, String> plannedEndColumn;

	@FXML
	private TableColumn<Goal, String> notesColumn;

	@FXML
	private TableColumn<Goal, String> nameColumn;

	@FXML
	private TableColumn<Goal, String> executedColumn;

	@FXML
	private TableColumn<Goal, String> realEndColumn;

	@FXML
	private TableColumn<Goal, String> categoryColumn;

	@FXML
	private TableColumn<Goal, String> typeColumn;

	/** Table to show data about goals and to change goals */
	@FXML
	private TableView<Goal> centerPanelTableView;

	public TableColumn<Goal, String> getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(TableColumn<Goal, String> nameColumn) {
		this.nameColumn = nameColumn;
	}

	public TableColumn<Goal, String> getPlannedEndColumn() {
		return plannedEndColumn;
	}

	public void setPlannedEndColumn(TableColumn<Goal, String> plannedEndColumn) {
		this.plannedEndColumn = plannedEndColumn;
	}

	public TableColumn<Goal, String> getNotesColumn() {
		return notesColumn;
	}

	public void setNotesColumn(TableColumn<Goal, String> notesColumn) {
		this.notesColumn = notesColumn;
	}

	public TableColumn<Goal, String> getExecutedColumn() {
		return executedColumn;
	}

	public void setExecutedColumn(TableColumn<Goal, String> executedColumn) {
		this.executedColumn = executedColumn;
	}

	public TableColumn<Goal, String> getRealEndColumn() {
		return realEndColumn;
	}

	public void setRealEndColumn(TableColumn<Goal, String> realEndColumn) {
		this.realEndColumn = realEndColumn;
	}

	public TableColumn<Goal, String> getCategoryColumn() {
		return categoryColumn;
	}

	public void setCategoryColumn(TableColumn<Goal, String> categoryColumn) {
		this.categoryColumn = categoryColumn;
	}

	public TableColumn<Goal, String> getTypeColumn() {
		return typeColumn;
	}

	public void setTypeColumn(TableColumn<Goal, String> typeColumn) {
		this.typeColumn = typeColumn;
	}

	public TableView<Goal> getCenterPanelTableView() {
		return centerPanelTableView;
	}

	public void setCenterPanelTableView(TableView<Goal> centerPanelTableView) {
		this.centerPanelTableView = centerPanelTableView;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/** Set properties to cells to display data as String */
		getNameColumn().setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		getPlannedEndColumn()
				.setCellValueFactory(cellData -> cellData.getValue().plannedDateOfEndProperty().asString());
		getRealEndColumn().setCellValueFactory(cellData -> cellData.getValue().realEndDateProperty().asString());
		getCategoryColumn().setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());
		getTypeColumn().setCellValueFactory(cellData -> cellData.getValue().typeProperty().asString());
		getExecutedColumn().setCellValueFactory(cellData -> cellData.getValue().finishedProperty().asString());
		getNotesColumn().setCellValueFactory(cellData -> cellData.getValue().notesProperty());

		/**
		 * First letter in cell uppercase If item==null set text null
		 */
		getNameColumn().setCellFactory(column -> {
			return new TableCell<Goal, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						String name = item;
						name = name.substring(0, 1).toUpperCase() + name.substring(1);
						setText(name);
					}
				}
			};
		});
		/**
		 * If there is double click on row in table view(on goal) show details
		 * about this goal
		 */
		centerPanelTableView.setRowFactory(tv -> {
			TableRow<Goal> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Goal rowData = row.getItem();
					showGoalDetails(rowData);
				}
			});
			return row;
		});
	}

	/**
	 * Get instance of object from MainPanelController and set to variable
	 * mainControl
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
	}

	/**
	 * Method responsible for show deatils about goal, double clicked in table
	 * by user and send to new stage objects: mainControl, stage, goal required
	 * in new stage to handle goal change
	 */
	public void showGoalDetails(Goal goal) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/simplygoals/view/mainPanel/GoalDetails.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Remove Goal");
			stage.setScene(new Scene(root));
			GoalDetailsController goalDetailsController = fxmlLoader.getController();
			goalDetailsController.setGoal(goal);
			goalDetailsController.setMainControl(mainControl);
			goalDetailsController.setStage(stage);

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
