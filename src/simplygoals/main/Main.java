package simplygoals.main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simplygoals.controllers.mainPanel.MainPanelController;

/**Main class responsible for loading main stage */
public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simplygoals/view/mainPanel/MainPanel.fxml"));
    	Parent parent = (Parent) fxmlLoader.load();
	    Scene scene = new Scene(parent);
	    stage.setResizable(false);
	    stage.setScene(scene);
	    stage.setTitle("SimplyGoals");
	    stage.show();
	    MainPanelController mainPanelController = new MainPanelController();
	    mainPanelController=fxmlLoader.getController();
	    
	    /**Send instance object of type Stage to MainPanelController */
	    mainPanelController.setStage(stage);
	}
    public Stage getPrimaryStage() {
        return primaryStage;
    }    
	public static void main(String[] args) {
	    launch(args);    
	}
}
