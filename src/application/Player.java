package application;

import java.util.ArrayList;

abstract class Player {
	
	private Grid shipGrid = new Grid();
	private Grid shotGrid = new Grid();
	private ArrayList<Ship> fleet = new ArrayList<>();
	private boolean myTurn = false;
	
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
	
	public boolean isMyTurn() {
		return myTurn;
	}
	
	public Player getPlayer() {
		return this;
	}
	
// Setters
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	
// Shared methods
	protected abstract Ship placeShip(Grid grid, int shipSize);
	
	public abstract void setFleet(Player opponent);
	
	public abstract void shoot(Player opponent);
}
