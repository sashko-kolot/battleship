package application;

import java.util.ArrayList;

class Utils {
	/*private static RandomGenerator rangen = RandomGenerator.getDefault();
	public static Cell getRandomCell(Grid grid, ArrayList<Integer> cellFilter) {
		int cellNumber;
		Cell randCell = new Cell();
		do {
		cellNumber = rangen.nextInt(0, cellFilter.size() - 1);
		cellFilter.remove(cellNumber);
		
		} while (grid.getGrid().get(cellNumber).isBlocked());
		randCell = grid.getGrid().get(cellNumber);
		
		return randCell;
	}*/
	public static Cell randomCellSelector(Grid grid, ArrayList<Integer> cellFilter) {
		int cellIndex;
		Cell randCell = new Cell();
		do {
			int min = 0;
			int max = cellFilter.size() - 1;
			cellIndex = (min +(int)(Math.random() * ((max-min) + 1)));
			cellFilter.remove(cellIndex);
			
			} while (grid.getGrid().get(cellIndex).isBlocked());
			randCell = grid.getGrid().get(cellIndex);
			
			return randCell;
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
		char direction;
		ArrayList<Cell> directions = new ArrayList<>();
		if(row - (size - 1) >= 0) {
			upCell = getCellByCoords(grid, col, row - (size - 1));
			if(!upCell.isBlocked()) {
				direction = 'u';
				if (isDirection(grid, upCell, size, direction)) {
				directions.add(upCell);
				}
			}
		}
		
		if(row + (size - 1) <= 9) {
			downCell = getCellByCoords(grid, col, row + (size- 1));
			if(!downCell.isBlocked()) {
				direction = 'd';
				if (isDirection(grid, downCell, size, direction)) {
				directions.add(downCell);
				}
			}
		}
		if(col - (size - 1) >= 0) {
			leftCell = getCellByCoords(grid, col - (size - 1), row);
			if(!leftCell.isBlocked()) {
				direction = 'l';
				if (isDirection(grid, leftCell, size, direction)) {
				directions.add(leftCell);
				}
			}
		}
		
		if(col + (size - 1) <= 9) {
			rightCell = getCellByCoords(grid, col + (size - 1), row);
			if(!rightCell.isBlocked()) {
				direction = 'r';
				if (isDirection(grid, rightCell, size, direction)) {
				directions.add(rightCell);
				}	
			}
		}
		
		if(directions.size() > 1) {
			int min = 0;
			int max = directions.size() - 1;
			
			//debug start
			System.out.println("Directions");
			for (Cell cell : directions) {	
			System.out.println(cell.getCol() + ", " + cell.getRow());
			}
			//debug end 
		return directions.get((min +(int)(Math.random() * ((max-min) + 1))));
		} 
		else if (directions.size() == 1){
			return directions.get(0);
		} else {
			return null;
		}
	}
	
	/*private*/ static boolean isDirection(Grid grid, Cell cell, int size, char direction) {
		if (size - 2 > 0) {
		switch (direction) {
		case 'u':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid, cell.getCol(), cell.getRow() + i).isBlocked()) {
					return false;
				}
				// debug start
				System.out.println(cell.getCol() + ", " + (cell.getRow() + i));
				//debug end
			}
			return true;
			
		case 'd':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid, cell.getCol(), cell.getRow() - i).isBlocked()) {
					return false;
				}
			}
			return true;
		
		case 'l':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid, cell.getCol() + i, cell.getRow()).isBlocked()) {
					return false;
				}
			}
			return true;
			
		case 'r': for (int i = size - 2; i > 0; i--) {
			if (getCellByCoords(grid, cell.getCol() - i, cell.getRow()).isBlocked()) {
				return false;
			}
		}
		return true;
		
		default:
			return false;
		}
			
		} else {
			return true;
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
