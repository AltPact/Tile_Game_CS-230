import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class PlayerDataController extends GameWindow implements Initializable {
	@FXML
	private BorderPane bPane;
	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private GridPane playerGrid;
	@FXML
	private Button backButton;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	@FXML
	public void backButtonClick(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", bPane, "back");
	}

}
