import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Homepage extends Application{
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//U know it:)
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomePagePane.fxml"));
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
		//StackPaneController.bgImgTrans();
	}

}
