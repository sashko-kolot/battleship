package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
     Player player = new Player();
     
     HBox root = new HBox(0, player.getMyShips().getGridPane(), player.getMyShots().getGridPane());

     // Add the HBox containing two grids to the Scene
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Battleship");
    primaryStage.show();
     
        
    }
       

    public static void main(String[] args) {
        Application.launch(args);
    }

    }
