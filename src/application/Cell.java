package application;

class Cell {
	private int col;
	private int row;
	private boolean ship = false;
	private boolean hit = false;
	private boolean miss = false;
	private boolean blocked = false;


	Cell() {}
	
	Cell(int col, int row) {
			this.col = col;
			this.row = row;
		}

// Getters
		public int getCol() {
			return col;
		}

		public int getRow() {
			return row;
		}
		
		public boolean isBlocked() {
			return blocked;
		}
		
		public boolean isShip() {
			return ship;
		}
		
		public boolean isHit() {
			return hit;
		}
		
		public boolean isMiss() {
			return miss;
		}
		
		public Cell getCell() {
			return this;
		}
		
// Setters
		public void setShipTrue() {
			ship = true;
		}
		
		public void setBlockedTrue() {
			blocked = true;
		}
		
		public void setHitTrue() {
			hit = true;
		}
		
		public void setMissTrue() {
			miss = true;
		}
}
