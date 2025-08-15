module Battleship {
	requires javafx.controls;
	requires java.xml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
