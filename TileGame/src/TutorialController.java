import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TutorialController extends GameWindow implements Initializable{

	
	/*private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;*/
	//Counter for arrayList and the progress dots
	private int counter;
	//ArrayList for imageView and Circle
	private ArrayList<ImageView> imageArr = new ArrayList<ImageView>();
	private ArrayList<Circle> circleArr = new ArrayList<Circle>();
	//Variables for store and delete ImageView
	private ImageView currentImage;
	private ImageView previousImage;
	//A bunch of FXML bindings
	@FXML
	public BorderPane BP;
	@FXML
	public StackPane SP;
	@FXML
	public ImageView imageView1;
	//2nd and 3rd ImageView
	public ImageView imageView2;
	public ImageView imageView3;
	@FXML
	public Button Right;
	@FXML
	public Button Left;
	@FXML
	public Button Back;
	@FXML
	public Circle C1; 
	@FXML
	public Circle C2; 
	@FXML
	public Circle C3; 
	
	//The method that runs when this class is created
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		counter=0;
		currentImage=imageView1;
		
		imageView2 = setImageView("/img/Instruction2.jpg", imageView2);
		imageView3 = setImageView("/img/Instruction3.jpg", imageView3);
		
		imageArr.add(imageView1);
		imageArr.add(imageView2);
		imageArr.add(imageView3);
		
		circleArr.add(C1);
		circleArr.add(C2);
		circleArr.add(C3);
	}
	//"back" button action method
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml",BP);
	}
	//">" button action method
	@FXML
	public void buttonOnActionR(ActionEvent event) throws IOException {
		int nextcounter=setCounter((counter+1));
		switchImage(imageArr.get(nextcounter), true);
		setCircleDot(nextcounter);
		counter=nextcounter;
		//System.out.println(SP.getChildren());
	}
	//"<" button action method
	@FXML
	public void buttonOnActionL(ActionEvent event) throws IOException {
		int nextcounter=setCounter((counter-1));
		switchImage(imageArr.get(nextcounter), false);
		setCircleDot(nextcounter);
		counter=nextcounter;
		//System.out.println(SP.getChildren());
	}
	//Again a bunch of mouse on effects
	@FXML
	public void mouseOnR() {
		Right.setStyle("-fx-background-color: GRAY;");
		
	}
	@FXML
	public void mouseOFFR() {
		Right.setStyle("-fx-background-color: rgba(220, 220, 220, 0.2);");
		
	}
	@FXML
	public void mouseOnL() {
		Left.setStyle("-fx-background-color: GRAY;");
		
	}
	@FXML
	public void mouseOFFL() {
		Left.setStyle("-fx-background-color: rgba(220, 220, 220, 0.2);");
		
	}
	@FXML
	public void mouseOnB() {
		Back.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFB() {
		Back.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	//Refine the counter b4 it is used as index of arrayList
	private int setCounter(int n) {
		if(n<0) {
			return 2;
		}else if(n>2) {
			return 0;
		}
		return n;
	}
	//Method that get the Homepage stackPane
	private StackPane getHomeController() {
		currentScene = Back.getScene();
		return ((StackPane)currentScene.getRoot());
	}
	//Method that switchImage
	private void switchImage(ImageView simageView2, Boolean right) {
		homepane=getHomeController();
		//System.out.println(homeContainer.getChildren());
		SP.getChildren().add(0,simageView2);
		animeImage(currentImage,simageView2, right);
		previousImage=currentImage;
		currentImage=simageView2;
		
		//System.out.println(currentImage);
	}
	//Method that initialize the ImageView
	private ImageView setImageView(String imgPath, ImageView imageView) {
		imageView = new ImageView(new Image(imgPath));
		imageView.setFitHeight(300);
		imageView.setFitWidth(430);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(true);
		return imageView;
	}
	//Method that animates the image switchings
	private void animeImage(ImageView i1, ImageView i2, Boolean right) {
		int s1,e1,s2,e2;
		//Decide whether it is switch from left to right or the other way around
		if(right) {
			s1=0;
			e1=(-600);
			s2=600;
			e2=0;
		}else {
			s1=0;
			e1=600;
			s2=(-600);
			e2=0;
		}
		//Animations
		TranslateTransition trans = new TranslateTransition();
		trans.setDuration(Duration.seconds(0.5));
		trans.setNode(i1);
		//System.out.println("Image1xb4: "+trans.getByX());
		trans.setFromX(s1);
		trans.setToX(e1);
		//System.out.println("Image1x: "+trans.getByX());
		
		TranslateTransition trans2 = new TranslateTransition();
		trans2.setDuration(Duration.seconds(0.5));
		trans2.setNode(i2);
		//System.out.println("Image1xb4: "+trans2.getByX());
		trans2.setFromX(s2);
		trans2.setToX(e2);
		//System.out.println("Image2x: "+trans2.getByX());
		
		ParallelTransition paral = new ParallelTransition();
		paral.getChildren().addAll(trans,trans2);
		//System.out.println("B4 SP:"+SP.getChildren());
		paral.setOnFinished(e->SP.getChildren().remove(i1));
		paral.play();
	}
	
	//Progress dots setting and change color
	private void setCircleDot(int i) {
		for(int n=0;n<3;n++) {
			if(n!=i) {
				//System.out.println(circleArr.get(n));
				FillTransition ft = new FillTransition(Duration.millis(50), circleArr.get(n), Color.LIGHTSALMON, Color.WHITE);
				ft.play();
			}else {
				FillTransition ft = new FillTransition(Duration.millis(50), circleArr.get(n), Color.WHITE, Color.LIGHTSALMON);
				ft.play();
			}
		}
	}
}
