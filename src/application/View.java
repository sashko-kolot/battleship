package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

class View {
	
	private GridPane shipGridPane = new GridPane();
	{
	shipGridPane = createGridPane();
	}
	private GridPane shotGridPane = new GridPane();
	{
	shotGridPane = createGridPane();
	}
	private Button playAgain = new Button("Play Again");
	{
		playAgain.setFont(Font.font(playAgain.getFont().getFamily(), 20));
		playAgain.setOnAction(e -> Main.getInstance().restartGame());
		playAgain.setVisible(true);
	}
	private Button quit = new Button("Quit");
	{
		quit.setFont(Font.font(quit.getFont().getFamily(), 20));
		quit.setOnAction(e -> System.exit(0));
		quit.setVisible(true);
	}
	private HBox gridBox = new HBox (0, shipGridPane, shotGridPane);
	{
		gridBox.setAlignment(Pos.CENTER);
	}
	private Label message = new Label();
	{
		message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, 40));
		message.setAlignment(Pos.CENTER);
		message.setVisible(false);
	}
	private HBox buttonBox = new HBox (10, playAgain, quit);
	{
		buttonBox.setAlignment(Pos.CENTER);
	}
	private VBox messageArea = new VBox(10, message);
	{
		messageArea.setAlignment(Pos.CENTER);
	}
	private VBox layout = new VBox(15, gridBox, messageArea, buttonBox);
	{
		layout.setAlignment(Pos.CENTER);
	}
	private Scene ui = new Scene(layout, 800, 600);	
	
	private GridPane createGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(50));
	    gridPane.setGridLinesVisible(true);
	    for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
	        for (int colIndex = 0; colIndex < 10; colIndex++) {
	            StackPane stackPane = createRectangle(colIndex, rowIndex);
	            gridPane.add(stackPane, colIndex, rowIndex);
	        }
	    }
	    
	    return gridPane;
	}
	
	private static StackPane createRectangle(int colIndex, int rowIndex) {
    	StackPane stackPane = new StackPane(); 
        Rectangle rect = new Rectangle(30, 30, Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);
        stackPane.getChildren().add(rect);

        return stackPane;
    }
	
	// Getters
	public View getView() {
		return this;
	}
	public Scene getUI() {
		return ui;
	}
	
	public GridPane getShipGridPane() {
		return shipGridPane;
	}
	
	public GridPane getShotGridPane() {
		return shotGridPane;
	}
	
	public Label getMessage() {
		return message;
	}
}
