package application;

import java.util.ArrayList;
import java.util.Random;

class AI {
	private ArrayList<Cell> shipGrid = new ArrayList<>();
	private ArrayList<Cell> shotGrid = new ArrayList<>();
	private ArrayList<Cell> ship = new ArrayList<>();
	private ArrayList<Cell> fleet = new ArrayList<>();
	private int shipSize = 5;
	private final int fleetSize = 7;
	
	Random random;

	AI() {
		shipGrid = buildGrid();
		shotGrid = buildGrid();
	    
	}


	private ArrayList<Cell> buildGrid() {
		ArrayList<Cell> grid = new ArrayList<>();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
            Cell cell = new Cell(col, row);
            grid.add(cell);
			}
		}
		return grid;
	}
	
	private Cell getRandomCell(ArrayList<Cell> grid, int min, int max) {
		int randCol;
		int randRow;
		
		randCol = random.nextInt(min, max);
		randRow = random.nextInt(min, max);
		
		return getCellByCoords(grid, randCol, randRow);
		
	}
	
	private Cell getCellByCoords(ArrayList<Cell> grid, int col, int row) {
		Cell cell  = new Cell();
		for(Cell curCell: grid) {
			if(curCell.getCol() == col && curCell.getRow() == row) {
				cell = curCell;
			}
		}
		return cell;
	}
	
	private ArrayList<Cell> placeShip(ArrayList<Cell> grid) {
		Cell stern;
		Cell prow;
	
	do {	
		do {
			stern = getRandomCell(grid, 0, 9);
		} while (!stern.isBlocked());
		prow = getShipDirection(grid, stern.getCol(), stern.getRow(), shipSize); 
	} while(prow == null);
		
		stern.setShipTrue();
		stern.setBlockedTrue();
		ship.add(stern);
		
		prow.setShipTrue();
		prow.setBlockedTrue();
		ship.add(prow);
		
		if(stern.getCol() == prow.getCol()) {
			if(stern.getRow() > prow.getRow()) {
				for(int i = prow.getRow() + 1; i < stern.getRow(); i++) {
					getCellByCoords(grid, prow.getCol(), i).setShipTrue();
					getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
					ship.add(getCellByCoords(grid, prow.getCol(), i));
				} 		
			} else {
				for (int i = prow.getRow() - 1; i > stern.getRow(); i--) {
					getCellByCoords(grid, prow.getCol(), i).setShipTrue();
					getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
					ship.add(getCellByCoords(grid, prow.getCol(), i));
				}
			}
		} else {
			if(stern.getCol() > prow.getCol()) {
				for(int i = prow.getCol() + 1; i < stern.getCol(); i++) {
					getCellByCoords(grid, i, prow.getRow()).setShipTrue();
					getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
					ship.add(getCellByCoords(grid, i, prow.getRow()));
				}
			} else {
				for(int i = prow.getCol() - 1; i > stern.getCol(); i--) {
					getCellByCoords(grid, i, prow.getRow()).setShipTrue();
					getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
					ship.add(getCellByCoords(grid, i, prow.getRow()));
				}
			}
		}
		return ship;
	}
	
	private Cell getShipDirection(ArrayList<Cell> grid, int col, int row, int size) {
		Cell upCell;
		Cell downCell;
		Cell leftCell;
		Cell rightCell;
		ArrayList<Cell> directions = new ArrayList<>();
		if(row - (size - 1) >= 0) {
			upCell = getCellByCoords(grid, col, row - (size - 1));
			if(!upCell.isBlocked()) {
				directions.add(upCell);
			}
		}
		
		if(row + (size - 1) <= 9) {
			downCell = getCellByCoords(grid, col, row + (size- 1));
			if(!downCell.isBlocked()) {
				directions.add(downCell);
			}
		}
		
		if(col - (size - 1) >= 0) {
			leftCell = getCellByCoords(grid, col - (size - 1), row);
			if(!leftCell.isBlocked()) {
				directions.add(leftCell);
			}
		}
		
		if(col + (size - 1) <= 9) {
			rightCell = getCellByCoords(grid, col + (size - 1), row);
			if(!rightCell.isBlocked()) {
				directions.add(rightCell);
			}	
		}
		
		if(directions.size() > 1) {
		return directions.get(random.nextInt(0, (directions.size() - 1)));
		} 
		else if (directions.size() == 1){
			return directions.get(0);
		} else {
			return null;
		}
	}
	
	private void blockCells(ArrayList<Cell> ship) {
		int col1 = ship.get(0).getCol();
		int col2 = ship.get(1).getCol();
		int row1 = ship.get(0).getRow();
		int row2 = ship.get(1).getRow();
		
		if(col1 == col2) {
			if(row1 > row2) {
				for(int i = (row1 + 1); i >= (row2 - 1); i--) {
					if(cellExists(col1 + 1, i)) {
						getCellByCoords(shipGrid, col1 + 1, i).setBlockedTrue();
					}
					if(cellExists(col1 - 1, i)) {
						getCellByCoords(shipGrid, col1 - 1, i).setBlockedTrue();
					}
				}
				if(cellExists(col1, row1 + 1)) {
						getCellByCoords(shipGrid, col1, row1 + 1).setBlockedTrue();
				}
				if(cellExists(col1, row1 - 1)) {
						getCellByCoords(shipGrid, col2, row2 - 1).setBlockedTrue();
				}
				 
			} else {
				for(int i = (row1 - 1); i <= (row2 + 1); i++) {
					if(cellExists(col1 + 1, i)) {
						getCellByCoords(shipGrid, col1 + 1, i).setBlockedTrue();
					}
					if(cellExists(col1 - 1, i)) {
						getCellByCoords(shipGrid, col1 - 1, i).setBlockedTrue();
					}
				}	
				if(cellExists(col1, row1 - 1)) {
					getCellByCoords(shipGrid, col1, row1 - 1).setBlockedTrue();
				}
				if(cellExists(col2, row2 + 1)) {
					getCellByCoords(shipGrid, col2, row2 + 1).setBlockedTrue();
				}
			}
		} else {
			if(col1 > col2) {
				for(int i = (col1 + 1); i >= (col2 - 1); i--) {
					if(cellExists(i, row1 + 1)) {
						getCellByCoords(shipGrid, i, row1 + 1).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(shipGrid, i, row1 - 1).setBlockedTrue();
					}
				}
				if(cellExists(col1 + 1, row1)) {
					getCellByCoords(shipGrid, col1 + 1, row1).setBlockedTrue();
				}
				if(cellExists(col2 - 1, row2)) {
					getCellByCoords(shipGrid, col2 - 1, row2).setBlockedTrue();
				}
				
			} else {
				for(int i = (col1 - 1); i <= (col2 + 1); i++) {
					if(cellExists(i, row1 + 1)) {
						getCellByCoords(shipGrid, i, row1 + 1).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(shipGrid, i, row1 - 1).setBlockedTrue();
					}
				}	
				if(cellExists(col1 - 1, row1)) {
					getCellByCoords(shipGrid, col1 - 1, row1).setBlockedTrue();
				}
				if(cellExists(col2 + 1, row2)) {
					getCellByCoords(shipGrid, col2 + 1, row2).setBlockedTrue();
				}
			}
		}
	}
	
	// The method is a copy from Grid.java. Think about moving this method to Cell.java
	private boolean cellExists(int col, int row) {
		   if(col < 0 || col > 9 || row < 0 || row > 9) {
			   return false;
		   } else {
			   return true;
		   }
	   }
	
	private void placeFleet() {
		do {
			placeShip(shipGrid);
		
	} while(shipSize !=7);
}
}



	