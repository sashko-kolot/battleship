package application;

import java.util.ArrayList;

class Ship {
	private ArrayList<Cell> hull = new ArrayList<>();
	private boolean destroyed = false;
	private boolean damaged = false;
	private int hitPointCounter = 0;
	 
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
	 
	 public int getHitPointCounter() {
		 return hitPointCounter;
	 }
	 
//Setters
	 public void setDestroyedTrue() {
		 destroyed = true;
	 }
	 
	 public void setDamaged(boolean damaged) {
		 this.damaged = damaged;
	 }
	 
	 public void setHitPointCounter(int hitPoints) {
		 hitPointCounter = hitPoints;
	 }
	 
	 public void decrementHitPointCounter() {
	 hitPointCounter--;
	 }
}
