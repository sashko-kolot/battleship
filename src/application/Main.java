package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	private static Main instance;
	private Stage primaryStage;
	private View view;
	private Timeline viewUpdate; 
	private  Game game;
	private final ExecutorService gameExecutor = Executors.newSingleThreadExecutor();
	
	public Main() {}
	
	public static Main getInstance() {
		return instance;
	}
	
	 @Override
	public void start(Stage primaryStage) {
		 instance = this;
		 this.primaryStage = primaryStage;
		 game = new Game();
		 view = new View(game);
	
		 primaryStage.setScene(view.getUI());
		 primaryStage.setTitle("Battleship");
		 primaryStage.show();
		 
		 viewUpdate = new Timeline(new KeyFrame(Duration.millis(50), e -> { game.getPlayer2().getShipGridView().drawGrid(); game.getPlayer2().getShotGridView().drawGrid();}));
		 viewUpdate.setCycleCount(Animation.INDEFINITE);
		 viewUpdate.play();
		 
		 gameExecutor.submit(()-> {game.playGame();});
	 }	

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void restartGame() {
		
		game = new Game();
		view = new View(game);
		 
		 primaryStage.setScene(view.getUI());
		 primaryStage.setTitle("Battleship");
		 primaryStage.show();
		 gameExecutor.submit(()-> {game.playGame();});
	}
}
