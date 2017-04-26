package simplygoals.controllers.mainPanel;
import simplygoals.controllers.leftPanel.LeftPanelTimeModeController;
import simplygoals.controllers.menuPanel.MenuPanelController;
import simplygoals.controllers.topPanel.AddCategoryController;
import simplygoals.controllers.topPanel.AddGoalController;
import simplygoals.controllers.topPanel.AddUserController;
import simplygoals.controllers.topPanel.CurrentUserController;
import simplygoals.controllers.topPanel.RemoveCategoryController;
import simplygoals.controllers.topPanel.RemoveGoalController;
import simplygoals.controllers.topPanel.RemoveUserController;
import simplygoals.controllers.topPanel.StatisticsController;
import simplygoals.controllers.topPanel.TopPanelController;
import simplygoals.database.MySQL;
import simplygoals.model.*;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
import simplygoals.modelComponents.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javafx.scene.control.MenuItem;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class MainPanelController implements Initializable {
	
	private ModelLogic modelLogic = new ModelLogic();
	private ObjectProperty<GoalType> typeInTableView = new SimpleObjectProperty<>();
	
    @FXML
    private TopPanelController topPanelController;
    
	@FXML
    private MenuPanelController menuPanelController;
	
    @FXML
    private LeftPanelTimeModeController leftPanelTimeModeController;
    
    @FXML
    private CenterPanelTableController centerPanelTableController;
    
	@FXML
	private Label typeOfGoal;
    
    
	public ModelLogic getModelLogic() {
		return modelLogic;
	}
	
	public GoalType getTypeInTableView() {
		return typeInTableView.get();
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
    	
    	getModelLogic().initDB();//if there is no database create it, otherwise not
    	getModelLogic().initUserList();//initialize user list in application(user list is take from database)
    	handleTopPanel();
    	handleLeftPanel();
    	handleTypeOfGoal();
    	centerPanelTableController.setMainControl(this);
    }

    public void handleTopPanel(){
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
    
    public void handleLeftPanel(){
    	showDailyGoalList();
    	showWeeklyGoalList();
    	showMonthlyGoalList();
    	showYearlyGoalList();
    }
    
    public void handleTypeOfGoal(){
    	typeOfGoal.setVisible(false);
    	typeOfGoal.textProperty().bind(typeInTableView.asString());
    	typeOfGoal.textProperty().addListener(new ChangeListener<String>(){
            @Override 
            public void changed(ObservableValue<? extends String> o,String oldVal, 
                    String newVal){
                if (newVal=="null"){
                	typeOfGoal.setVisible(false);
                }
                else{typeOfGoal.setVisible(true);}
           }
         });
    }
    
    public void refreshTableView(){
		if(Optional.ofNullable(getTypeInTableView()).isPresent()&&Optional.ofNullable(modelLogic.getCurrentUser()).isPresent()){
			getCenterPanelTableController().getCenterPanelTableView().setItems(getModelLogic().getGoalListByType(getTypeInTableView()));		
		}	else{	getCenterPanelTableController().getCenterPanelTableView().setItems(FXCollections.observableArrayList());}
		
    }
    
    public void showDailyGoalList(){
    	
    	Button button = getLeftPanelTimeModeController().getDailyButton();
    	button.setOnAction(event ->  {
    		getCenterPanelTableController().getCenterPanelTableView().setItems(modelLogic.getGoalListByType(GoalType.DAILY_GOAL));
    		setTypeInTableView(GoalType.DAILY_GOAL);
		});
    }
    
    public void showWeeklyGoalList(){
    	
    	Button button = getLeftPanelTimeModeController().getWeeklyButton();
    	button.setOnAction(event ->  {
    		getCenterPanelTableController().getCenterPanelTableView().setItems(modelLogic.getGoalListByType(GoalType.WEEKLY_GOAL));
    		setTypeInTableView(GoalType.WEEKLY_GOAL);
		});
    }
    
    public void showMonthlyGoalList(){
    	
    	Button button = getLeftPanelTimeModeController().getMonthlyButton();
    	button.setOnAction(event ->  {
    		getCenterPanelTableController().getCenterPanelTableView().setItems(modelLogic.getGoalListByType(GoalType.MONTHLY_GOAL));
    		setTypeInTableView(GoalType.MONTHLY_GOAL);
		});
    }
    
    public void showYearlyGoalList(){
    	
    	Button button = getLeftPanelTimeModeController().getYearlyButton();
    	button.setOnAction(event ->  {
    		getCenterPanelTableController().getCenterPanelTableView().setItems(modelLogic.getGoalListByType(GoalType.YEARLY_GOAL));
    		setTypeInTableView(GoalType.YEARLY_GOAL);
		});
    }
    
    public void showCurrentUser(){
    	Button button = getTopPanelController().getCurrentUserButton();
    	button.textFillProperty().set(Color.valueOf("222223"));
    	button.textProperty().bind(getModelLogic().getCurrentUserProperty().asString());
    	button.textProperty().addListener(new ChangeListener<String>(){
            @Override 
            public void changed(ObservableValue<? extends String> o,String oldVal, 
                    String newVal){
                if (newVal=="null"){
                	button.textFillProperty().setValue(Color.valueOf("222223"));
                }
                else{button.textFillProperty().setValue(Color.WHITE);}
           }
         });
    	if(getModelLogic().getUserList().size()>0){
    		getModelLogic().setCurrentUser(getModelLogic().getUserList().get(0));	 		
    	}   	
    }

    public void showAddUserPanel(){
    	Button button = getTopPanelController().getAddUserButton();
    	button.setOnAction(event ->  {
    		 				try {
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/AddUser.fxml"));
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
    		 				} catch(Exception e) {
    		 					e.printStackTrace();
    		 				}
    								});
    }
    
    public void showRemoveUserPanel(){
    							Button button = getTopPanelController().getRemoveUserButton();
    							button.setOnAction(event ->  {
    							try {
    		 					
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/RemoveUser.fxml"));	        	
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
    		 				} catch(Exception e) {
    		 					e.printStackTrace();
    		 				}
    								});
    }

    public void showAddCategoryPanel(){
    	Button button = getTopPanelController().getAddCategoryButton();
    	button.setOnAction(event ->  {
		 				try {
		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/AddCategory.fxml"));
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
		 				} catch(Exception e) {
		 					e.printStackTrace();
		 				}
								});
    }
    
    public void showRemoveCategoryPanel(){
    	Button button = getTopPanelController().getRemoveCategoryButton();
    	button.setOnAction(event ->  {
    		 				try {
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/RemoveCategory.fxml"));
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
    		    	        	
    		 				} catch(Exception e) {
    		 					e.printStackTrace();
    		 				}
    								});
    }
    
    public void showCurrentUserPanel(){
    	Button button = getTopPanelController().getCurrentUserButton();
    	button.setOnAction(event ->  {
    		 				try {
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/CurrentUser.fxml"));
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
    		 				} catch(Exception e) {
    		 					e.printStackTrace();
    		 				}
    								});
    }
    
    public void showAddGoalPanel(){
    	Button button = getTopPanelController().getAddGoalButton();
    	button.setOnAction(event ->  {
				try {
	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/AddGoal.fxml"));
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
				} catch(Exception e) {
					e.printStackTrace();
				}
					});
    }
    
    public void showRemoveGoalPanel(){
    	Button button = getTopPanelController().getRemoveGoalButton();
    	button.setOnAction(event ->  {
				try {
	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/RemoveGoal.fxml"));
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
				} catch(Exception e) {
					e.printStackTrace();
				}
					});
    }
    
    public void showStatistics(){
    	Button button = getTopPanelController().getStatisticsButton();
    	button.setOnAction(event ->  {
				try {
	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/topPanel/Statistics.fxml"));
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
	        	//Arrays.asList(3,2,5,12,33,1,45,13,4,10,11,12)
	        	statisticsController.setGoalsData(modelLogic.getReachedGoalsInTime());
	        	stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
					});
    }

}