package application;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;

class View {
	
	private GridPane shipGridPane = createGridPane();
	private GridPane shotGridPane = createGridPane();
	private Scene sceneGrids = setGridsScene(shipGridPane, shotGridPane);	
	
	private  Scene setGridsScene(GridPane shipGridPane, GridPane shotGridPane) {
		HBox layout = new HBox(0, shipGridPane, shotGridPane);
		
		return new Scene(layout, 800, 600);
	}
	
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
	public Scene getSceneGrids() {
		return sceneGrids;
	}
	
	public GridPane getShipGridPane() {
		return shipGridPane;
	}
	
	public GridPane getShotGridPane() {
		return shotGridPane;
	}
}
