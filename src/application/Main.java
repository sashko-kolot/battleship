package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public static Main instance;
	private Stage primaryStage;
	private View view;
	private  Game game;
	private Thread gameThread;
	
	public Main() {
		instance = this;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	 @Override
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
		 view = new View();
		 ViewController.setView(view);
		 game = new Game();
		 
		 primaryStage.setScene(view.getUI());
		 primaryStage.setTitle("Battleship");
		 primaryStage.show();
		 
		gameThread = new Thread(() -> game.playGame());
		gameThread.start();
	 }	

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void restartGame() {
		if (gameThread != null && gameThread.isAlive()) {
	        game.stop(); // 
	        try {
	            gameThread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
		
		view = new View();
		ViewController.setView(view);
		game = new Game();
		 
		 primaryStage.setScene(view.getUI());
		 primaryStage.setTitle("Battleship");
		 primaryStage.show();
		 
		 gameThread = new Thread(() -> game.playGame());
		 gameThread.start();
	}
}
