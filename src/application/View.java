package application;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

class View {
	private Scene ui;
	
    private static Label message;
    
    View(Game game) {

    	
    	Button playAgain = new Button("Play Again");
    	playAgain.setFont(Font.font(playAgain.getFont().getFamily(), 20));
    	playAgain.setOnAction(e -> Main.getInstance().restartGame());
    	playAgain.setVisible(true);
    	
    	Button quit = new Button("Quit");
    	quit.setFont(Font.font(quit.getFont().getFamily(), 20));
    	quit.setOnAction(e -> System.exit(0));
    	quit.setVisible(true);
    	
    	HBox gridBox = new HBox (100, game.getPlayer2().getShipGridView().getCanvas(), game.getPlayer2().getShotGridView().getCanvas());
    	gridBox.setAlignment(Pos.CENTER);
    	
    	message = new Label();
    	message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, 40));
    	message.setAlignment(Pos.CENTER);
    	message.setVisible(false);
    	
    	HBox buttonBox = new HBox (10, playAgain, quit);
    	buttonBox.setAlignment(Pos.CENTER);
    	
    	VBox messageArea = new VBox(10, message);
    	messageArea.setAlignment(Pos.CENTER);
    
    	VBox layout = new VBox(15, gridBox, messageArea, buttonBox);
    	layout.setAlignment(Pos.CENTER);
    	
    	ui = new Scene(layout, 800, 600);
	}
	
	// Getters
	public View getView() {
		return this;
	}
	public Scene getUI() {
		return ui;
	}
	
	
	// Setters
	public static void setMessage(String text, Paint color) {
    	message.setText(text);
    	message.setTextFill(color);
    }
    
    public static void setMessageVisibility(boolean visibility) {
    	message.setVisible(visibility);
    }
}
