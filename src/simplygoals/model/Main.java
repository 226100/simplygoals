package simplygoals.model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
	 
public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		Parent parent = (Parent)FXMLLoader.load(getClass().getResource("/simplygoals/view/mainPanel/MainPanel.fxml"));
	    Scene scene = new Scene(parent);
	    stage.setResizable(false);
	    stage.setScene(scene);
	    stage.setTitle("SimplyGoals");
	    stage.show();
	}
    public Stage getPrimaryStage() {
        return primaryStage;
    }    
	public static void main(String[] args) {
	    launch(args);    
	}
}

