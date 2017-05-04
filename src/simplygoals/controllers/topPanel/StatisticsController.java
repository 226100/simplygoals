package simplygoals.controllers.topPanel;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;
import simplygoals.modelComponents.GoalType;
import simplygoals.modelComponents.User;

/** This controller is responsible for showing data in statistic window */
public class StatisticsController implements Initializable {

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
	private Label weeklyInProcess;

	@FXML
	private Label yearlyFinished;

	@FXML
	private Label allGoalsFinished;

	@FXML
	private Label dailyNumber;

	@FXML
	private Label monthlyInProcess;

	@FXML
	private Label weeklyFinished;

	@FXML
	private Label monthlyFinished;

	@FXML
	private Label monthlyNumber;

	@FXML
	private Label dailyInProcess;

	@FXML
	private Label yearlyInProcess;

	@FXML
	private Label allGoalsNumber;

	@FXML
	private Label allGoalsInProcess;

	@FXML
	private Label dailyFinished;

	@FXML
	private Label weeklyNumber;

	@FXML
	private AnchorPane pane;

	@FXML
	private BarChart<String, Integer> chart;
	@FXML
	private CategoryAxis xAxis;

	@FXML
	private Label user;

	@FXML
	private Label yearlyNumber;

	/** This collection represent object with names of all months */
	private ObservableList<String> monthNames = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * Get instance of object(type MainPanelController) from MainPanelController
	 * and set to variable mainControl and next call other methods
	 */
	public void setMainControl(MainPanelController mainPanel) {
		mainControl = mainPanel;
		// This part of code set to XAxis in chart names of all months
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(months));
		xAxis.setCategories(monthNames);
		// Set label which cointain user name, with name of current user
		setUserLabel();
		// Set Integer values to all labels, which represent number of goals of
		// some type, this data is take from database
		User currUser = mainControl.getModelLogic().getCurrentUser();
		if (Optional.ofNullable(currUser).isPresent()) {
			allGoalsNumber.setText(
					Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoals(currUser.getName())));
			dailyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfGoalsByType(currUser.getName(), GoalType.DAILY_GOAL)));
			weeklyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfGoalsByType(currUser.getName(), GoalType.WEEKLY_GOAL)));
			monthlyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfGoalsByType(currUser.getName(), GoalType.MONTHLY_GOAL)));
			yearlyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfGoalsByType(currUser.getName(), GoalType.YEARLY_GOAL)));
			allGoalsFinished.setText(
					Integer.toString(mainControl.getModelLogic().getMySQL().amountOfFinishedGoals(currUser.getName())));
			dailyFinished.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfFinishedGoalsByType(currUser.getName(), GoalType.DAILY_GOAL)));
			weeklyFinished.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfFinishedGoalsByType(currUser.getName(), GoalType.WEEKLY_GOAL)));
			monthlyFinished.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfFinishedGoalsByType(currUser.getName(), GoalType.MONTHLY_GOAL)));
			yearlyFinished.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfFinishedGoalsByType(currUser.getName(), GoalType.YEARLY_GOAL)));
			allGoalsInProcess.setText(Integer
					.toString(mainControl.getModelLogic().getMySQL().amountOfNotFinishedGoals(currUser.getName())));
			dailyInProcess.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfNotFinishedGoalsByType(currUser.getName(), GoalType.DAILY_GOAL)));
			weeklyInProcess.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfNotFinishedGoalsByType(currUser.getName(), GoalType.WEEKLY_GOAL)));
			monthlyInProcess.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfNotFinishedGoalsByType(currUser.getName(), GoalType.MONTHLY_GOAL)));
			yearlyInProcess.setText(Integer.toString(mainControl.getModelLogic().getMySQL()
					.amountOfNotFinishedGoalsByType(currUser.getName(), GoalType.YEARLY_GOAL)));
		}
	}

	/**
	 * set stage for Statistic Controller, this stage is required to handle
	 * alerts
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/** this method set data to chart */
	public void setGoalsData(List<Integer> monthCounter) {

		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for (int i = 0; i < monthCounter.size(); i++) {
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter.get(i)));
		}
		chart.getData().add(series);
	}

	/** set to label name of current user */
	public void setUserLabel() {
		user.textProperty().bind(mainControl.getModelLogic().getCurrentUserProperty().asString());
	}
}