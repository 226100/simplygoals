package simplygoals.controllers.leftPanel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
public class LeftPanelTimeModeController implements Initializable {

	    @FXML
	    private Button dailyButton;

	    @FXML
	    private Button monthlyButton;

	    @FXML
	    private VBox LeftPanelTimeModeBox;

	    @FXML
	    private Button yearlyButton;

	    @FXML
	    private Button weeklyButton;
	    
		public Button getDailyButton() {
			return dailyButton;
		}

		public void setDailyButton(Button dailyButton) {
			this.dailyButton = dailyButton;
		}

		public Button getMonthlyButton() {
			return monthlyButton;
		}

		public void setMonthlyButton(Button monthlyButton) {
			this.monthlyButton = monthlyButton;
		}

		public Button getYearlyButton() {
			return yearlyButton;
		}

		public void setYearlyButton(Button yearlyButton) {
			this.yearlyButton = yearlyButton;
		}

		public Button getWeeklyButton() {
			return weeklyButton;
		}

		public void setWeeklyButton(Button weeklyButton) {
			this.weeklyButton = weeklyButton;
		}

		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			
		}	
}


