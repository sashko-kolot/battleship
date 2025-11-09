package application;

class Cell {
	private int row;
	private int col;
	private boolean ship = false;
	private boolean hit = false;
	private boolean miss = false;
	private boolean blocked = false;
	private boolean hidden = true;
	private boolean direction = false;


	Cell() {}
	
	Cell(int row, int col) {
			this.row = row;
			this.col = col;
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
		
	public boolean isHidden() {
			return hidden;
		}
	
	public boolean isDirection() {
		return direction;
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
		
	public void setHiddenFalse() {
			hidden = false;
		}
	
	public void toggleDirection() {
		direction = !direction;
	}
}
