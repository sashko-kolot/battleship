package application;

import java.util.ArrayList;

class Ship {
	private int size;
	private ArrayList<Cell> hull = new ArrayList<>();
	private boolean killed = false;

//Constructors
	 Ship() {}
	 Ship(int size, ArrayList<Cell> hull) {
		 this.size = size;
		 this.hull = hull;
	 }
	 
//Getters
	 public boolean isKilled() {
		 return this.killed;
	 }
	 
	 public int getSize() {
		 return this.size;
	 }
	 
	 public ArrayList<Cell> getHull() {
		 return hull;
	 }
	 
//Setters
	 public void setKilledTrue() {
		 killed = true;
	 }
}
