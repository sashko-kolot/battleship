package application;

import java.util.ArrayList;
import java.util.function.Consumer;

abstract class Player {
	
	private Grid shipGrid = new Grid();
	private Grid shotGrid = new Grid();
	private ArrayList<Ship> fleet = new ArrayList<>();
	
	double cellSize = 30;
	int cols = 10;
	int rows = 10;
	CanvasGridView shipGridView = new CanvasGridView(getShipGrid(), cols, rows, cellSize);
	CanvasGridView shotGridView = new CanvasGridView(getShotGrid(), cols, rows, cellSize);
	
// Getters
	public Grid getShipGrid() {
		return shipGrid;
	}
	
	public Grid getShotGrid() {
		return shotGrid;
	}
	
	public ArrayList<Ship> getFleet() {
		return fleet;
	}
	
	public Player getPlayer() {
		return this;
	}
	
	public CanvasGridView getShipGridView() {
		return shipGridView;
	}
	
	public CanvasGridView getShotGridView() {
		return shotGridView;
	}
	
// Shared methods
	protected abstract Ship placeShip(Grid grid, int shipSize);
	
	public abstract void setFleet(Player opponent, Runnable onComplete);
	
	public abstract void shoot(Player opponent, Consumer<Boolean> resultCallback);
}
