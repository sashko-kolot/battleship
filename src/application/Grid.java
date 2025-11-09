package application;

class Grid {
	private int width = 10;
	private int height = 10;
	private Cell[][] grid = new Cell[height][width];
	private boolean active = false;
	
	public Grid() {
		setGrid();
	}
	
	
// Getters
	public Cell[][] getGrid() {
		return grid;
	}
	
	public boolean isActive() {
		return active;
	}
	
// Setters
	private void setGrid() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				grid[row][col]= new Cell(row, col);
				}
		}
		
	}
	
	public void toggleActive() {
		active = !active;
	}
	
}
