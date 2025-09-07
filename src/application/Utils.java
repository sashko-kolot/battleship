package application;

import java.util.ArrayList;

import java.util.random.RandomGenerator;

class Utils {
	private static RandomGenerator rangen = RandomGenerator.getDefault();
	public static Cell getRandomCell(Grid grid, int min, int max) {
		int randCol;
		int randRow;
		randCol = rangen.nextInt(min, max);
		randRow = rangen.nextInt(min, max);
		
		return getCellByCoords(grid, randCol, randRow);	
	}
	
	public static Cell getCellByCoords(Grid grid, int col, int row) {
		Cell cell  = new Cell();
		for(Cell curCell : grid.getGrid()) {
			if(curCell.getCol() == col && curCell.getRow() == row) {
				cell = curCell;
			}
		}
		return cell;
	}
	 
	public static Cell getShipDirection(Grid grid, int col, int row, int size) {
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
			//debug start
			for (Cell cell : directions) {
			System.out.println(cell.getCol() + ", " + cell.getRow());
			}
			//debug end
		return directions.get(rangen.nextInt(0, (directions.size() - 1)));
		} 
		else if (directions.size() == 1){
			return directions.get(0);
		} else {
			return null;
		}
	}

	public static void blockCells(Grid grid, Ship ship) {
		ArrayList<Cell> hull = ship.getHull();
		int col1 = hull.getFirst().getCol();
		int col2 = hull.getLast().getCol();
		int row1 = hull.getFirst().getRow();
		int row2 = hull.getLast().getRow();
		
		if(col1 == col2) {
			if(row1 > row2) {
				for(int i = (row1 + 1); i >= (row2 - 1); i--) {
					if(cellExists(col1 + 1, i)) {
						getCellByCoords(grid, col1 + 1, i).setBlockedTrue();
					}
					if(cellExists(col1 - 1, i)) {
						getCellByCoords(grid, col1 - 1, i).setBlockedTrue();
					}
				}
				if(cellExists(col1, row1 + 1)) {
						getCellByCoords(grid, col1, row1 + 1).setBlockedTrue();
				}
				if(cellExists(col1, row1 - 1)) {
						getCellByCoords(grid, col2, row2 - 1).setBlockedTrue();
				}
				 
			} else {
				for(int i = (row1 - 1); i <= (row2 + 1); i++) {
					if(cellExists(col1 + 1, i)) {
						getCellByCoords(grid, col1 + 1, i).setBlockedTrue();
					}
					if(cellExists(col1 - 1, i)) {
						getCellByCoords(grid, col1 - 1, i).setBlockedTrue();
					}
				}	
				if(cellExists(col1, row1 - 1)) {
					getCellByCoords(grid, col1, row1 - 1).setBlockedTrue();
				}
				if(cellExists(col2, row2 + 1)) {
					getCellByCoords(grid, col2, row2 + 1).setBlockedTrue();
				}
			}
		} else {
			if(col1 > col2) {
				for(int i = (col1 + 1); i >= (col2 - 1); i--) {
					if(cellExists(i, row1 + 1)) {
						getCellByCoords(grid, i, row1 + 1).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(grid, i, row1 - 1).setBlockedTrue();
					}
				}
				if(cellExists(col1 + 1, row1)) {
					getCellByCoords(grid, col1 + 1, row1).setBlockedTrue();
				}
				if(cellExists(col2 - 1, row2)) {
					getCellByCoords(grid, col2 - 1, row2).setBlockedTrue();
				}
				
			} else {
				for(int i = (col1 - 1); i <= (col2 + 1); i++) {
					if(cellExists(i, row1 + 1)) {
						getCellByCoords(grid, i, row1 + 1).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(grid, i, row1 - 1).setBlockedTrue();
					}
				}	
				if(cellExists(col1 - 1, row1)) {
					getCellByCoords(grid, col1 - 1, row1).setBlockedTrue();
				}
				if(cellExists(col2 + 1, row2)) {
					getCellByCoords(grid, col2 + 1, row2).setBlockedTrue();
				}
			}
		}
	}
	
	public static boolean cellExists(int col, int row) {
		   if(col < 0 || col > 9 || row < 0 || row > 9) {
			   return false;
		   } else {
			   return true;
		   }
	   }
}
