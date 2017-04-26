package simplygoals.controllers.topPanel;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;

public class StatisticsController implements Initializable {

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
	    private BarChart<String,Integer> chart;
	    @FXML
	    private CategoryAxis xAxis;

	    @FXML
	    private Label user;

	    @FXML
	    private Label yearlyNumber;

	    private MainPanelController mainControl = new MainPanelController();
	    
	    private Stage stage;
	    
	    private ObservableList<String> monthNames = FXCollections.observableArrayList();
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			
		}
		public void setMainControl(MainPanelController mainPanel){
			mainControl=mainPanel;
			String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
	        monthNames.addAll(Arrays.asList(months));
	        xAxis.setCategories(monthNames);
	        setUserLabel();
	        allGoalsNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoals(mainControl.getModelLogic().getCurrentUser().getName())));
	        dailyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoalsByType(mainControl.getModelLogic().getCurrentUser().getName(),GoalType.DAILY_GOAL)));
	        weeklyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoalsByType(mainControl.getModelLogic().getCurrentUser().getName(),GoalType.WEEKLY_GOAL)));
	        monthlyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoalsByType(mainControl.getModelLogic().getCurrentUser().getName(),GoalType.MONTHLY_GOAL)));
	        yearlyNumber.setText(Integer.toString(mainControl.getModelLogic().getMySQL().amountOfGoalsByType(mainControl.getModelLogic().getCurrentUser().getName(),GoalType.YEARLY_GOAL)));
		}
		
		public void setStage(Stage stage){
			this.stage=stage;
		}
		public void setGoalsData(List<Integer> monthCounter) {


	        XYChart.Series<String, Integer> series = new XYChart.Series<>();
	        for (int i = 0; i < monthCounter.size(); i++) {
	            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter.get(i)));
	        }

	        chart.getData().add(series);
	    }
		public void setUserLabel(){
			user.textProperty().bind(mainControl.getModelLogic().getCurrentUserProperty().asString());
		}
}