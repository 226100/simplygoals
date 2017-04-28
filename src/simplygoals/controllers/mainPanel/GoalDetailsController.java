package simplygoals.controllers.mainPanel;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
import simplygoals.util.DateUtil;

public class GoalDetailsController implements Initializable {

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

	/** Variable of Goal, use to get instance of clicked goal */
	private Goal goal = null;

	@FXML
	private ChoiceBox<GoalType> typeList;

	@FXML
	private ChoiceBox<Category> categoryList;

	@FXML
	private CheckBox isFinished;

	@FXML
	private DatePicker realDateOfEnd;
	@FXML
	private TextArea notes;

	@FXML
	private Button save;

	@FXML
	private Button today;

	@FXML
	private TextField name;

	@FXML
	private TextField plannedDateOfEnd;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * Get instance of object(type MainPanelController) from
	 * CenterPanelTableController and set to variable mainControl
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		showGoalDetails();
		saveGoal();
		setToday();
	}

	/**
	 * Get instance of current stage(GoalDetails) from
	 * CenterPanelTableController and set to variable this.stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Get instance of object of type Goal from CenterPanelTableController and
	 * set to variable this.goal
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	/**
	 * Show details about goal in Window if goal is exist, if doesn't exist
	 * fields remain empty
	 */
	public void showGoalDetails() {

		if (Optional.ofNullable(goal).isPresent()) {

			name.setText(this.goal.toString());
			plannedDateOfEnd.setText(this.goal.getPlannedDateOfEnd().toString());
			realDateOfEnd.setValue(this.goal.getRealEndDate());
			categoryList.setItems(mainControl.getModelLogic().getCategoryList());
			categoryList.setValue(this.goal.getCategory());
			;
			isFinished.setSelected(this.goal.getFinished());
			typeList.setItems(FXCollections.observableArrayList(GoalType.DAILY_GOAL, GoalType.MONTHLY_GOAL,
					GoalType.WEEKLY_GOAL, GoalType.YEARLY_GOAL));
			typeList.setValue(this.goal.getType());
			notes.setText(this.goal.getNotes());
		} else {

			name.setText("");
			plannedDateOfEnd.setText("");
			notes.setText("");
		}
	}

	/**
	 * After click on button Today set data in field realDateOfEnd on current
	 * day
	 */
	public void setToday() {
		today.setOnAction(x -> {
			realDateOfEnd.setValue(LocalDate.now());
		});
	}

	/**
	 * If data in field realDateOfEnd is valid set control bit isDateValid to
	 * true, otherwise show alert to user If all field requirements are true set
	 * data to goal object and update this object in database Show warning that
	 * user was update If conditions are false show alert to user about filling
	 * all field
	 */
	public void saveGoal() {
		save.setOnAction(x -> {

			boolean isDateValid = false;
			if (realDateOfEnd.getValue() != null) {
				if (DateUtil.validDate(realDateOfEnd.getValue().format(DateUtil.DATE_FORMATTER))) {
					isDateValid = true;
				} else {
					isDateValid = false;
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(stage);
					alert.setTitle("Error in Data");
					alert.setHeaderText("Data is invalid");
					alert.setContentText("Please insert valid data in format dd-mm-yyyy or get data from DataPicker");
					alert.showAndWait();

				}
			}

			if (name.getText().length() > 0 & plannedDateOfEnd.getText().length() > 0 && isDateValid
					&& categoryList.getSelectionModel().isEmpty() == false
					&& typeList.getSelectionModel().isEmpty() == false) {
				String date = realDateOfEnd.getValue().format(DateUtil.DATE_FORMATTER);
				this.goal.setRealEndDate(DateUtil.parse(date));
				this.goal.setCategory(categoryList.getValue());
				this.goal.setFinished(isFinished.isSelected());
				this.goal.setType(typeList.getValue());
				this.goal.setNotes(notes.getText());

				mainControl.getModelLogic().updateGoal(this.goal);
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("Goal saved");
				alert.setContentText("Goal was saved");
				alert.showAndWait();
				mainControl.refreshTableView();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(stage);
				alert.setTitle("No inputs");
				alert.setHeaderText("No all fields filled");
				alert.setContentText("Please fill all fields with data");
				alert.showAndWait();
			}
		});
	}
}