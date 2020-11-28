import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
/**
 * File Name: NewGameController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class NewGameController extends GameWindow implements Initializable {
	
	/*private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;*/
	//FXML bindings
	
	@FXML
    public BorderPane BP;
	@FXML
	public TextField TestField;
	@FXML
    public Button AddButton;
	@FXML
    public Button Button1;
	@FXML
    public Button Button2;
	@FXML
    public Button Button3;
	@FXML
    public Button Button4;
	@FXML
    public Button BackButton;
	@FXML
    public Button StartButton;
	@FXML
    public Button createButton;
	@FXML
    public VBox playerBox;
	@FXML
    public ComboBox searchBox;
	
	private int playerNumber=0;
	
	private Button[] playerNoButton = new Button[4];
	
	private ArrayList<PlayerData> playerQuene = new ArrayList<PlayerData>();
	private ArrayList<HBox> playerRows = new ArrayList<HBox>();
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//there was a stupid error without this and I didn't have time to solve it
		playerNoButton[0]=Button1;
		playerNoButton[1]=Button2;
		playerNoButton[2]=Button3;
		playerNoButton[3]=Button4;
	}
	
	private void addPlayerInQuene(PlayerData playerToAdd) {
		playerBox.getChildren().add(makePlayerRows(playerToAdd));
	}
	
	private HBox makePlayerRows(PlayerData playerInfo) {
		HBox row = new HBox();
		row.setPrefSize(496, 22);
		
		row.getChildren().addAll(makeLabel(playerInfo.getName()),makeButton(row, playerInfo));
		playerRows.add(row);
		return row;
	}
	
	private Button makeButton(HBox row, PlayerData player) {
		Button removeButton = new Button();
		removeButton.setText("remove");
		removeButton.setPrefSize(138, 25);
		removeButton.setTextFill(Color.WHITE);
		removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		removeButton.setOnAction(e->{
			playerQuene.remove(player);
		    playerBox.getChildren().remove(row);
		});
		removeButton.setOnMouseEntered(e->{
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		removeButton.setOnMouseExited(e->{
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		return removeButton;
	}
	
	private Label makeLabel(String name) {
		Label playerName = new Label();
		playerName.setText(name);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPrefSize(426, 25);
		playerName.setTextFill(Color.WHITE);
		playerName.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		return playerName;
	}
	
	@FXML
	public void addButtonClick(ActionEvent event) throws IOException {
		String playerName = (String) searchBox.getValue();
		if(playerName==null) {
			wrongInputAnimation();
		}else {
			PlayerData player = new PlayerData(playerName,9,10,"/img/firefly.png");
		    addPlayerInQuene(player);
		}
	}
	
	private void wrongInputAnimation() {
		Label errorMess = new Label("Incorrect input");
		errorMess.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		errorMess.setTextFill(Color.RED);
		errorMess.setFont(new Font("Arial", 24));
		errorMess.setMouseTransparent(true);
		homepane.getChildren().add(errorMess);
		FadeTransition appear = new FadeTransition(Duration.millis(1000),errorMess);
		appear.setFromValue(0);
		appear.setToValue(1);
		appear.setCycleCount(2);
		appear.setAutoReverse(true);
		appear.setOnFinished(e->{
			homepane.getChildren().remove(errorMess);
		});
		appear.play();
	}
	
	@FXML
	public void createButtonClick(ActionEvent event) throws IOException {
		//switchPane("/fxml/CreatePlayer.fxml",BP, "back");<-should be something like that
	}
	@FXML
	public void mouseOnCreate() {
		createButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOffCreate() {
		createButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	public void changeButtonColor(int number) {
		playerNumber=number;
		for(int i=1;i<5;i++) {
			if(i==number) {
				System.out.println(playerNumber);
				playerNoButton[i-1].setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 2.5px; -fx-text-fill: black");
			}else {
				playerNoButton[i-1].setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-border-color: BLACK; -fx-background-insets: 0;");
			}
		}
	}
	
	/**
	 * This method is called when the back button is click
	 * it will call the switchPane() method which switch to HomePagePane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml",BP, "back");
	}
	
	/**
	 * This method is called when the start button is click
	 * it will call the switchPane() method which switch to GameBoardPane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionS(ActionEvent event) throws IOException {
		System.out.println("Click game");
		switchPane("/fxml/GameBoardPane.fxml",BP, "forward");
	}
	
	/**
	 * This method is called when mouse is on the textfield
	 * it will change the color of the textfield
	 */
	@FXML
	public void mouseOnTF() {
		TestField.setStyle("-fx-background-color: rgba(211, 211, 211, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");
		
	}
	
	/**
	 * This method is called when mouse is off the textfield
	 * it will change the color of the textfield back
	 */
	@FXML
	public void mouseOFFTF() {
		TestField.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");
		
	}
	
	/**
	 * This method is called when mouse is on the add button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnA() {
		AddButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the add button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFA() {
		AddButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is on the back button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnB() {
		BackButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the back button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFB() {
		BackButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is on the start button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnS() {
		StartButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the start button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFS() {
		StartButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when the button 1 is clicked
	 * it will change the color of the button
	 */
	@FXML
	public void buttonOnAction1(ActionEvent event) throws IOException {
		changeButtonColor(1);
	}
	
	/**
	 * This method is called when the button 2 is clicked
	 * it will change the color of the button
	 */
	@FXML
	public void buttonOnAction2(ActionEvent event) throws IOException {
		changeButtonColor(2);
	}
	
	/**
	 * This method is called when the button 3 is clicked
	 * it will change the color of the button
	 */
	@FXML
	public void buttonOnAction3(ActionEvent event) throws IOException {
		changeButtonColor(3);
	}
	
	/**
	 * This method is called when the button 4 is clicked
	 * it will change the color of the button
	 */
	@FXML
	public void buttonOnAction4(ActionEvent event) throws IOException {
		changeButtonColor(4);
 	}
	
	

}
