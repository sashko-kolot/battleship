module Battleship {
	requires javafx.controls;
	requires java.xml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
