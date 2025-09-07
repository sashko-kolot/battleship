package application;

import java.util.ArrayList;

class Ship {
	private ArrayList<Cell> hull = new ArrayList<>();
	private boolean destroyed = false;
	private boolean damaged = false;
	 
//Getters
	public boolean isDestroyed() {
		 return destroyed;
	 }
	
	public boolean isDamaged() {
		return damaged;
	}
	 
	 ArrayList<Cell> getHull() {
		 return hull;
	 }
	 
//Setters
	 public void setDestroyedTrue() {
		 destroyed = true;
	 }
	 
	 public void setDamagedTrue() {
		 damaged = true;
	 }
}
