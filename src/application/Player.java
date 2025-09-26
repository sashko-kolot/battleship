package application;

import java.util.ArrayList;
import java.util.function.Consumer;

abstract class Player {
	
	private Grid shipGrid = new Grid();
	private Grid shotGrid = new Grid();
	private ArrayList<Ship> fleet = new ArrayList<>();
	
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
	
// Shared methods
	protected abstract Ship placeShip(Grid grid, int shipSize);
	
	public abstract void setFleet(Player opponent, Runnable onComplete);
	
	public abstract void shoot(Player opponent, Consumer<Boolean> resultCallback);
}
