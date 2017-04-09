package simplygoals.controllers;
import simplygoals.controllers.AddCategoryController;
import simplygoals.controllers.AddUserController;
import simplygoals.controllers.RemoveCategoryController;
import simplygoals.database.MySQL;
import simplygoals.model.*;
import simplygoals.modelComponents.Category;
import simplygoals.modelComponents.Goal;
import simplygoals.modelComponents.GoalType;
import simplygoals.modelComponents.User;

import java.util.ArrayList;
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
public class MainPanelController extends MainController implements Initializable {
	private ModelLogic modelLogic = new ModelLogic();
	private ObjectProperty<GoalType> typeInTableView = new SimpleObjectProperty<>();
	
	
	public GoalType getTypeInTableView() {
		return typeInTableView.get();
	}
	public void setTypeInTableView(GoalType typeInTableView) {
		this.typeInTableView.set(typeInTableView);
	}
	 public ObjectProperty<GoalType> getTypeInTableViewProperty() {
	        return typeInTableView;
	    }
	public ModelLogic getModelLogic() {
		return modelLogic;}
    @FXML
    private TopPanelController topPanelController;
	public TopPanelController getTopPanelController() {
		return topPanelController;
	}
	
   
	@FXML
    private MenuPanelController menuPanelController;
    
    @FXML
    private LeftPanelTimeModeController leftPanelTimeModeController;
    public LeftPanelTimeModeController getLeftPanelTimeModeController() {
		return leftPanelTimeModeController;
	}
	public void setLeftPanelTimeModeController(LeftPanelTimeModeController leftPanelTimeModeController) {
		this.leftPanelTimeModeController = leftPanelTimeModeController;
	}
	
    @FXML
    private CenterPanelTableController centerPanelTableController;
	public CenterPanelTableController getCenterPanelTableController() {
		return centerPanelTableController;
	}
	public void setCenterPanelTableController(CenterPanelTableController centerPanelTableController) {
		this.centerPanelTableController = centerPanelTableController;
	}
	@FXML
	private Label typeOfGoal;
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
    	//setPropertiesToTableView();//set properties to table view
    	handleTopPanel();
    	handleLeftPanel();
    	handleTypeOfGoal();
    	
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
    public void handleTopPanel(){
    	showCurrentUser();
    	showRemoveUserPanel();
    	showAddUserPanel();
    	showAddCategoryPanel();
    	showRemoveCategoryPanel();
    	showCurrentUserPanel();
    	showAddGoalPanel();
    	showRemoveGoalPanel();
    }
    public void handleLeftPanel(){
    	showDailyGoalList();
    	showWeeklyGoalList();
    	showMonthlyGoalList();
    	showYearlyGoalList();
    }
    public void setPropertiesToTableView(){
    	getCenterPanelTableController().getNameColumn().setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		getCenterPanelTableController().getPlannedEndColumn().setCellValueFactory(cellData -> cellData.getValue().plannedDateOfEndProperty().asString());
		getCenterPanelTableController().getRealEndColumn().setCellValueFactory(cellData -> cellData.getValue().realEndDateProperty().asString());
		getCenterPanelTableController().getCategoryColumn().setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());
		getCenterPanelTableController().getTypeColumn().setCellValueFactory(cellData -> cellData.getValue().typeProperty().asString());
		getCenterPanelTableController().getExecutedColumn().setCellValueFactory(cellData -> cellData.getValue().executedProperty().asString());
		getCenterPanelTableController().getNotesColumn().setCellValueFactory(cellData -> cellData.getValue().notesProperty());
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
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/AddUser.fxml"));
    		    	        	Parent root1 = (Parent) fxmlLoader.load();
    		    	        	Stage stage = new Stage();
    		    	        	stage.setResizable(false);
    		    	        	stage.setTitle("Add User");
    		    	        	stage.setScene(new Scene(root1));  
    		    	        	AddUserController addUserController = fxmlLoader.getController();
    		    	        	addUserController.setMainControl(MainPanelController.this);
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
    		 					
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/RemoveUser.fxml"));	        	
    		    	        	Parent root2 = (Parent) fxmlLoader.load();
    		    	        	Stage stage = new Stage();
    		    	        	stage.setResizable(false);
    		    	        	stage.setTitle("Remove User");
    		    	        	stage.setScene(new Scene(root2));
    		    	        	RemoveUserController removeUserController = fxmlLoader.getController();
        		 				removeUserController.setMainControl(MainPanelController.this);
    		    	        	
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
		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/AddCategory.fxml"));
		    	        	Parent root3 = (Parent) fxmlLoader.load();
		    	        	Stage stage = new Stage();
		    	        	stage.setResizable(false);
		    	        	stage.setTitle("Add Category");
		    	        	stage.setScene(new Scene(root3));  
		    	        	AddCategoryController addCategoryController = fxmlLoader.getController();
		    	        	addCategoryController.setMainControl(MainPanelController.this);
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
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/RemoveCategory.fxml"));
    		    	        	Parent root4 = (Parent) fxmlLoader.load();
    		    	        	Stage stage = new Stage();
    		    	        	stage.setResizable(false);
    		    	        	stage.setTitle("Remove Category");
    		    	        	stage.setScene(new Scene(root4));  
    		    	        	RemoveCategoryController removeCategoryController = fxmlLoader.getController();
    		    	        	removeCategoryController.setMainControl(MainPanelController.this);
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
    		    	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/CurrentUser.fxml"));
    		    	        	Parent root = (Parent) fxmlLoader.load();
    		    	        	Stage stage = new Stage();
    		    	        	stage.setResizable(false);
    		    	        	stage.setTitle("Remove Category");
    		    	        	stage.setScene(new Scene(root));  
    		    	        	CurrentUserController currentUserController = fxmlLoader.getController();
    		    	        	currentUserController.setMainControl(MainPanelController.this);
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
	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/AddGoal.fxml"));
	        	Parent root = (Parent) fxmlLoader.load();
	        	Stage stage = new Stage();
	        	stage.setResizable(false);
	        	stage.setTitle("Add Goal");
	        	stage.setScene(new Scene(root));  
	        	AddGoalController addGoalController = fxmlLoader.getController();
	        	addGoalController.setMainControl(MainPanelController.this);
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
	        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/RemoveGoal.fxml"));
	        	Parent root = (Parent) fxmlLoader.load();
	        	Stage stage = new Stage();
	        	stage.setResizable(false);
	        	stage.setTitle("Remove Goal");
	        	stage.setScene(new Scene(root));  
	        	RemoveGoalController removeGoalController = fxmlLoader.getController();
	        	removeGoalController.setMainControl(MainPanelController.this);
	        	stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
					});
    }
}