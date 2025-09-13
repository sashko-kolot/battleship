package application;

import java.util.ArrayList;

class Grid {
	private ArrayList<Cell> grid = new ArrayList<>();
	
	
// Getters
	public ArrayList<Cell> getGrid() {
		return grid;
	}
	
// Setters
	public void setGrid() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				Cell cell = new Cell(col, row);
	            grid.add(cell);
				}
			}
	}
	
	public void alignGrids(ArrayList<Cell> grid) {
		this.grid = grid;
	}
}
