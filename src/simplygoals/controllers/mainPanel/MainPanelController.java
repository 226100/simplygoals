package simplygoals.controllers.mainPanel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simplygoals.controllers.leftPanel.LeftPanelTimeModeController;
import simplygoals.controllers.topPanel.AddCategoryController;
import simplygoals.controllers.topPanel.AddGoalController;
import simplygoals.controllers.topPanel.AddUserController;
import simplygoals.controllers.topPanel.CurrentUserController;
import simplygoals.controllers.topPanel.RemoveCategoryController;
import simplygoals.controllers.topPanel.RemoveGoalController;
import simplygoals.controllers.topPanel.RemoveUserController;
import simplygoals.controllers.topPanel.StatisticsController;
import simplygoals.controllers.topPanel.TopPanelController;
import simplygoals.model.ModelLogic;
import simplygoals.modelComponents.GoalType;

/** Main Controller responsible for show other stages. This is main window. */
public class MainPanelController implements Initializable {

	/** Object responsible for logic of application */
	private ModelLogic modelLogic = new ModelLogic();

	/** Last type of goal set in table view */
	private ObjectProperty<GoalType> typeInTableView = new SimpleObjectProperty<>();

	/**
	 * Ininstance of object of type Stage(MainPanelController), required to show
	 * alert in MainPanelController
	 */
	private Stage stage;

	@FXML
	private TopPanelController topPanelController;

	@FXML
	private LeftPanelTimeModeController leftPanelTimeModeController;

	@FXML
	private CenterPanelTableController centerPanelTableController;

	/** Label responsible for showing type of current goals in table view */
	@FXML
	private Label typeOfGoal;

	public ModelLogic getModelLogic() {
		return modelLogic;
	}

	public GoalType getTypeInTableView() {
		return typeInTableView.get();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setTypeInTableView(GoalType typeInTableView) {
		this.typeInTableView.set(typeInTableView);
	}

	public ObjectProperty<GoalType> getTypeInTableViewProperty() {
		return typeInTableView;
	}

	public TopPanelController getTopPanelController() {
		return topPanelController;
	}

	public LeftPanelTimeModeController getLeftPanelTimeModeController() {
		return leftPanelTimeModeController;
	}

	public void setLeftPanelTimeModeController(LeftPanelTimeModeController leftPanelTimeModeController) {
		this.leftPanelTimeModeController = leftPanelTimeModeController;
	}

	public CenterPanelTableController getCenterPanelTableController() {
		return centerPanelTableController;
	}

	public void setCenterPanelTableController(CenterPanelTableController centerPanelTableController) {
		this.centerPanelTableController = centerPanelTableController;
	}

	public Label getTypeOfGoal() {
		return typeOfGoal;
	}

	public void setTypeOfGoal(Label typeOfGoal) {
		this.typeOfGoal = typeOfGoal;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/**
		 * If there is no database create it, otherwise not
		 */
		getModelLogic().initDB();

		/**
		 * If there is connection with databasse initialize user list(user list
		 * is take from database), otherwise show alert for application user.
		 */
		if (getModelLogic().getMySQL().isConnEstablished() == true) {
			getModelLogic().initUserList();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(stage);
			alert.setTitle("Error Connection with DB");
			alert.setHeaderText("Connection with Database not established");
			alert.setContentText("Please check your connection with database");
			alert.showAndWait();
		}

		/** Handling buttons in top panel */
		handleTopPanel();

		/** Handling buttons in left panel */
		handleLeftPanel();
		/** Handling type of goal which is choose in table view */
		handleTypeOfGoal();

		/** Set controller to table, required to show data in table */
		centerPanelTableController.setMainControl(this);
	}

	public void handleTopPanel() {
		showCurrentUser();
		showRemoveUserPanel();
		showAddUserPanel();
		showAddCategoryPanel();
		showRemoveCategoryPanel();
		showCurrentUserPanel();
		showAddGoalPanel();
		showRemoveGoalPanel();
		showStatistics();
	}

	public void handleLeftPanel() {
		showDailyGoalList();
		showWeeklyGoalList();
		showMonthlyGoalList();
		showYearlyGoalList();
	}

	/** Show above table type of current select goal */
	public void handleTypeOfGoal() {
		typeOfGoal.setVisible(false);
		typeOfGoal.textProperty().bind(typeInTableView.asString());
		typeOfGoal.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String oldVal, String newVal) {
				if (newVal == "null") {
					typeOfGoal.setVisible(false);
				} else {
					typeOfGoal.setVisible(true);
				}
			}
		});
	}

	/**
	 * If there is type of goal in table View and current user is set refresh
	 * table view with exact type of goals for current user
	 */
	public void refreshTableView() {
		if (Optional.ofNullable(getTypeInTableView()).isPresent()
				&& Optional.ofNullable(modelLogic.getCurrentUser()).isPresent()) {
			getCenterPanelTableController().getCenterPanelTableView()
					.setItems(getModelLogic().getGoalListByType(getTypeInTableView()));
		} else {
			getCenterPanelTableController().getCenterPanelTableView().setItems(FXCollections.observableArrayList());
		}
	}

	/** Show in table view goals with specific type */
	public void showDailyGoalList() {

		Button button = getLeftPanelTimeModeController().getDailyButton();
		button.setOnAction(event -> {
			getCenterPanelTableController().getCenterPanelTableView()
					.setItems(modelLogic.getGoalListByType(GoalType.DAILY_GOAL));
			setTypeInTableView(GoalType.DAILY_GOAL);
		});
	}

	/** Show in table view goals with specific type */
	public void showWeeklyGoalList() {

		Button button = getLeftPanelTimeModeController().getWeeklyButton();
		button.setOnAction(event -> {
			getCenterPanelTableController().getCenterPanelTableView()
					.setItems(modelLogic.getGoalListByType(GoalType.WEEKLY_GOAL));
			setTypeInTableView(GoalType.WEEKLY_GOAL);
		});
	}

	/** Show in table view goals with specific type */
	public void showMonthlyGoalList() {

		Button button = getLeftPanelTimeModeController().getMonthlyButton();
		button.setOnAction(event -> {
			getCenterPanelTableController().getCenterPanelTableView()
					.setItems(modelLogic.getGoalListByType(GoalType.MONTHLY_GOAL));
			setTypeInTableView(GoalType.MONTHLY_GOAL);
		});
	}

	/** Show in table view goals with specific type */
	public void showYearlyGoalList() {

		Button button = getLeftPanelTimeModeController().getYearlyButton();
		button.setOnAction(event -> {
			getCenterPanelTableController().getCenterPanelTableView()
					.setItems(modelLogic.getGoalListByType(GoalType.YEARLY_GOAL));
			setTypeInTableView(GoalType.YEARLY_GOAL);
		});
	}

	/**
	 * If user is select, show name of user below button(set User), otherwise //
	 * set text invisible for application user
	 */
	public void showCurrentUser() {
		Button button = getTopPanelController().getCurrentUserButton();
		button.textFillProperty().set(Color.valueOf("222223"));
		button.textProperty().bind(getModelLogic().getCurrentUserProperty().asString());
		button.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String oldVal, String newVal) {
				if (newVal == "null") {
					button.textFillProperty().setValue(Color.valueOf("222223"));
				} else {
					button.textFillProperty().setValue(Color.WHITE);
				}
			}
		});
		if (getModelLogic().getUserList().size() > 0) {
			getModelLogic().setCurrentUser(getModelLogic().getUserList().get(0));
		}
	}

	/**
	 * Show stage with panel dedicated to adding user and send required instance
	 * of objects: type Stage and MainPanelController
	 */
	public void showAddUserPanel() {
		Button button = getTopPanelController().getAddUserButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/AddUser.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Add User");
				stage.setScene(new Scene(root1));
				AddUserController addUserController = fxmlLoader.getController();
				addUserController.setMainControl(MainPanelController.this);
				addUserController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to remove users and send required
	 * instance of objects: type Stage and MainPanelController
	 */
	public void showRemoveUserPanel() {
		Button button = getTopPanelController().getRemoveUserButton();
		button.setOnAction(event -> {
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/RemoveUser.fxml"));
				Parent root2 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Remove User");
				stage.setScene(new Scene(root2));

				RemoveUserController removeUserController = fxmlLoader.getController();
				removeUserController.setMainControl(MainPanelController.this);
				removeUserController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to add Category and send required
	 * instance of objects: type Stage and MainPanelController
	 */
	public void showAddCategoryPanel() {
		Button button = getTopPanelController().getAddCategoryButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/AddCategory.fxml"));
				Parent root3 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Add Category");
				stage.setScene(new Scene(root3));
				AddCategoryController addCategoryController = fxmlLoader.getController();
				addCategoryController.setMainControl(MainPanelController.this);
				addCategoryController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to remove Categories and send required
	 * instance of objects: type Stage and MainPanelController
	 */
	public void showRemoveCategoryPanel() {
		Button button = getTopPanelController().getRemoveCategoryButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/RemoveCategory.fxml"));
				Parent root4 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Remove Category");
				stage.setScene(new Scene(root4));
				RemoveCategoryController removeCategoryController = fxmlLoader.getController();
				removeCategoryController.setMainControl(MainPanelController.this);
				removeCategoryController.setStage(stage);
				stage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to set current User in application and
	 * send required instance of objects: type Stage and MainPanelController
	 */
	public void showCurrentUserPanel() {
		Button button = getTopPanelController().getCurrentUserButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/CurrentUser.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Remove Category");
				stage.setScene(new Scene(root));
				CurrentUserController currentUserController = fxmlLoader.getController();
				currentUserController.setMainControl(MainPanelController.this);
				currentUserController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to add Goal and send required instance of
	 * objects: type Stage and MainPanelController
	 */
	public void showAddGoalPanel() {
		Button button = getTopPanelController().getAddGoalButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/AddGoal.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Add Goal");
				stage.setScene(new Scene(root));
				AddGoalController addGoalController = fxmlLoader.getController();
				addGoalController.setMainControl(MainPanelController.this);
				addGoalController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to remove Goals and send required
	 * instance of objects: type Stage and MainPanelController
	 */

	public void showRemoveGoalPanel() {
		Button button = getTopPanelController().getRemoveGoalButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/RemoveGoal.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Remove Goal");
				stage.setScene(new Scene(root));
				RemoveGoalController removeGoalController = fxmlLoader.getController();
				removeGoalController.setMainControl(MainPanelController.this);
				removeGoalController.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Show stage with panel dedicated to statistics, send required instance of
	 * objects: type Stage and MainPanelController and data to plot graph
	 */
	public void showStatistics() {
		Button button = getTopPanelController().getStatisticsButton();
		button.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/simplygoals/view/topPanel/Statistics.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setTitle("Statistics");
				stage.setScene(new Scene(root));
				StatisticsController statisticsController = fxmlLoader.getController();
				statisticsController.setMainControl(MainPanelController.this);
				statisticsController.setStage(stage);
				statisticsController.setGoalsData(modelLogic.getReachedGoalsInTime());
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}