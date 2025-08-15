package application;

import java.util.ArrayList;

class Ship {
	private int size;
	private ArrayList<Cell> hull = new ArrayList<>();
	private boolean killed = false;

	Ship(int size) {}

	 void setShip(ArrayList<Cell> hull) {
		this.hull = hull;
	}
	 
	 
	 // Getters
	 public boolean isKilled() {
		 return this.killed;
	 }
	 
	 public int getSize() {
		 return this.size;
	 }
}
