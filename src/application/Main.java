package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	 @Override
	public void start(Stage primaryStage) {
		 View view = new View();
		 ViewController.setView(view);
		 Game game = new Game();
		 
		 primaryStage.setScene(view.getSceneGrids());
		 primaryStage.setTitle("Battleship");
		 primaryStage.show();
		 
		 new Thread(() -> {game.playGame();
		 }).start();
	 }	

	public static void main(String[] args) {
		Application.launch(args);
	}

}
