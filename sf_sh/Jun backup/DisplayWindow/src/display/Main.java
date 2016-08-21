package display;



import javax.naming.Name;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	Stage window;
	Button button;
	private Stop[] stops = { new Stop(0, Color.WHITE ), new Stop(1, Color.web("#00ff7f"))};
	private LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);


	
	public static void main(String[] args) {
	
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		window.setTitle("test");
		VBox robotMenu = new VBox(10);
		Button buttonStart = new Button("start");
		Button buttonStop = new Button("stop");
		Button buttonCheck = new Button("check");
		robotMenu.getChildren().addAll(buttonStart, buttonStop, buttonCheck);
		robotMenu.setLayoutX(500);
		robotMenu.setLayoutY(400);
		
//		Task task = new Task<Void>(){
//			@Override
//			protected Void call() throws Exception {
//				DisplayPane startPane = new DisplayPane();
//				return null;
//			}	
//		};
//		ProgressBar bar = new ProgressBar();
//		bar.progressProperty().bind(task.progressProperty());
//		new Thread(task).start();
		
		
		DisplayPane startPane = new DisplayPane();
		startPane.setPrefSize(500, 500);
		startPane.getChildren().add(robotMenu);
		startPane.setLayoutX(10);
		startPane.setLayoutY(10);
//		Pane clLayout = new Pane();
//		for (int i=0; i<10 ; i++){
//			for(int j=0; j<10; j++){
//				Rectangle test = new Rectangle(j*50, i*50, 40, 40);
//				test.setFill(Cl1);
//				clLayout.getChildren().add(test);
//			}
//		}
		
//		Rectangle test2 = new Rectangle(100, 100, 100,100);
//		test2.getFill();
//		clLayout.getChildren().add(test2);
	
//		startPane.getChildren().add(clLayout);
//		layout.getChildren().add(startPane);
//		Scene scene = new Scene(layout, 600, 600);
		Scene scene = new Scene(startPane, 600, 600);
		scene.getStylesheets().add("a.css");
		window.setScene(scene);
		window.show();
		
	}

}
