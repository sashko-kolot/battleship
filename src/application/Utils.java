package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
//import java.util.HashMap;
import java.util.List;

final class Utils {
	
	private Utils() {} // To prevent instantiation
	
	public static Cell randomCellSelector(Grid grid, ArrayList<Integer> cellFilter) {
		Cell randCell;
		int row;
		int col;
		int min = 0;
		int max = 9;
		
		do {
			row = (min +(int)(Math.random() * ((max-min) + 1)));
			col = (min +(int)(Math.random() * ((max-min) + 1)));
			randCell = grid.getGrid()[row][col];
		} while(randCell.isBlocked());
		
		return randCell;
	}
	
	public static Cell getCellByCoords(Grid grid, int row, int col) {
		return grid.getGrid()[row][col];
	}
	 
	public static ArrayList<Cell> getShipDirections(Grid grid, int row, int col, int size) {
		Cell upCell;
		Cell downCell; 
		Cell leftCell;
		Cell rightCell;
		char direction;
		ArrayList<Cell> directions = new ArrayList<>();
		if(row - (size - 1) >= 0) {
			upCell = getCellByCoords(grid, row - (size - 1), col);
			if(!upCell.isBlocked()) {
				direction = 'u';
				if (isDirection(grid, upCell, size, direction)) {
				directions.add(upCell);
				}
			}
		}
		
		if(row + (size - 1) <= 9) {
			downCell = getCellByCoords(grid, row + (size - 1), col);
			if(!downCell.isBlocked()) {
				direction = 'd';
				if (isDirection(grid, downCell, size, direction)) {
				directions.add(downCell);
				}
			}
		}
		if(col - (size - 1) >= 0) {
			leftCell = getCellByCoords(grid, row, col - (size - 1));
			if(!leftCell.isBlocked()) {
				direction = 'l';
				if (isDirection(grid, leftCell, size, direction)) {
				directions.add(leftCell);
				}
			}
		}
		
		if(col + (size - 1) <= 9) {
			rightCell = getCellByCoords(grid, row, col + (size - 1));
			if(!rightCell.isBlocked()) {
				direction = 'r';
				if (isDirection(grid, rightCell, size, direction)) {
				directions.add(rightCell);
				}	
			}
		}
		return directions;
	}
	
	public static Cell selectRandomDirection(ArrayList<Cell> directions) {
		if(directions.size() > 1) {
			int min = 0;
			int max = directions.size() - 1;

		return directions.get((min + (int)(Math.random() * ((max - min) + 1))));
		} 
		else if (directions.size() == 1){
			return directions.get(0);
		} else {
			return null;
		}
	}
	
	private static boolean isDirection(Grid grid, Cell cell, int size, char direction) {
		if (size - 2 > 0) {
		switch (direction) {
		case 'u':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid,  cell.getRow() + i, cell.getCol()).isBlocked()) {
					return false;
				}
			}
			return true;
			
		case 'd':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid, cell.getRow() - i, cell.getCol()).isBlocked()) {
					return false;
				}
			}
			return true;
		
		case 'l':
			for (int i = size - 2; i > 0; i--) {
				if (getCellByCoords(grid, cell.getRow(), cell.getCol() + i).isBlocked()) {
					return false;
				}
			}
			return true;
			
		case 'r': for (int i = size - 2; i > 0; i--) {
			if (getCellByCoords(grid, cell.getRow(), cell.getCol() - i).isBlocked()) {
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
		int col1 = hull.get(0).getCol();
		int col2 = hull.get(1).getCol();
		int row1 = hull.get(0).getRow();
		int row2 = hull.get(1).getRow();
		
		if(col1 == col2) {
			if(row1 > row2) {
				for(int i = (row1 + 1); i >= (row2 - 1); i--) {
					if(cellExists(i, col1 + 1)) {
						getCellByCoords(grid, i, col1 + 1).setBlockedTrue();
					}
					if(cellExists(i, col1 - 1)) {
						getCellByCoords(grid, i, col1 - 1).setBlockedTrue();
					}
				}
				if(cellExists(row1 + 1, col1)) {
						getCellByCoords(grid, row1 + 1, col1).setBlockedTrue();
				}
				if(cellExists(row2 - 1, col1)) {
						getCellByCoords(grid, row2 - 1, col2).setBlockedTrue();
				}
				 
			} else {
				for(int i = (row1 - 1); i <= (row2 + 1); i++) {
					if(cellExists(i, col1 + 1)) {
						getCellByCoords(grid, i, col1 + 1).setBlockedTrue();
					}
					if(cellExists(i, col1 - 1)) {
						getCellByCoords(grid, i, col1 - 1).setBlockedTrue();
					}
				}	
				if(cellExists(row1 - 1, col1)) {
					getCellByCoords(grid, row1 - 1, col1).setBlockedTrue();
				}
				if(cellExists(row2 + 1, col2)) {
					getCellByCoords(grid, row2 + 1, col2).setBlockedTrue();
				}
			}
		} else {
			if(col1 > col2) {
				for(int i = (col1 + 1); i >= (col2 - 1); i--) {
					if(cellExists(row1 + 1, i)) {
						getCellByCoords(grid, row1 + 1, i).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(grid, row1 - 1, i).setBlockedTrue();
					}
				}
				if(cellExists(row1, col1 + 1)) {
					getCellByCoords(grid, row1, col1 + 1).setBlockedTrue();
				}
				if(cellExists(row2, col2 - 1)) {
					getCellByCoords(grid, row2, col2 - 1).setBlockedTrue();
				}
				
			} else {
				for(int i = (col1 - 1); i <= (col2 + 1); i++) {
					if(cellExists(row1 + 1, i)) {
						getCellByCoords(grid, row1 + 1, i).setBlockedTrue();
					}
					if(cellExists(i, row1 - 1)) {
						getCellByCoords(grid, row1 - 1, i).setBlockedTrue();
					}
				}	
				if(cellExists(row1, col1 - 1)) {
					getCellByCoords(grid, row1, col1 - 1).setBlockedTrue();
				}
				if(cellExists(row2, col2 + 1)) {
					getCellByCoords(grid, row2, col2 + 1).setBlockedTrue();
				}
			}
		}
	}
	
	public static boolean cellExists(int row, int col) {
		   if(row < 0 || row > 9 || col < 0 || col > 9) {
			   return false;
		   } else {
			   return true;
		   }
	   }
	
	public static boolean doesCurrentTargetExist(ArrayList<Ship> fleet) {
		for (Ship ship : fleet) {
			if (ship.isDamaged()) {
				return true;
			}
		}
		return false;
	}
	
	public static Cell shootNewTarget(Grid grid, ArrayList<Cell> possibleTargets) {
		Cell cell = new Cell();
		cell = possibleTargets.get(generateRandomInt(0, possibleTargets.size() - 1));
		updateTargetCell(grid, cell);
		return Utils.getCellByCoords(grid, cell.getRow(), cell.getCol());
	}
	
	public static Cell shootNewTargetWithOneHitCell(Grid grid, Cell hitCell, ArrayList<Ship>fleet) {
		Cell selectedCell;
		Cell startingPointR = new Cell();
		Cell startingPointC = new Cell();
		ArrayList<Cell> hitCellRow = new ArrayList<>();
		ArrayList<Cell> hitCellCol = new ArrayList<>();
		ArrayList<Integer> currentShipSizes = new ArrayList<>();
		ArrayList<Cell> possibleTargets = new ArrayList<>();
		int left = 0;
		int right = 0;
		int up = 0;
		int down = 0;
		
		for(Cell[] row : grid.getGrid()){
			for(Cell cell : row) {
				if(cell.getRow() == hitCell.getRow()) {
					hitCellRow.add(cell);
				}
			}
		}
		
		
		for(Cell[] row : grid.getGrid()) {
			for(Cell cell : row) {
				if(cell.getCol() == hitCell.getCol()) {
					hitCellCol.add(cell);
				}
			}
		}
		
		for(Ship ship : fleet) {
			currentShipSizes.add(ship.getHull().size());
		}
		
		for(Cell cell : hitCellRow) {
			if(cell.getCol() == hitCell.getCol() && cell.getRow() == hitCell.getRow()) {
				startingPointR = cell;
			}
		}
		
		if((hitCellRow.indexOf(startingPointR) != (hitCellRow.size() - 1)) || (hitCellRow.indexOf(startingPointR) == 0)) {
			for(int i = (hitCellRow.indexOf(startingPointR) + 1); i < hitCellRow.size(); i++) {
				if(hitCellRow.get(i).isHidden()) {
					right++;
				} else {
					break;
				}
			}
		}
		
		if((hitCellRow.indexOf(startingPointR) != 0) || (hitCellRow.indexOf(startingPointR) == (hitCellRow.size() - 1))) {
			for(int i = (hitCellRow.indexOf(startingPointR) - 1); i >= 0; i--) {
				if(hitCellRow.get(i).isHidden()) {
					left++;
				} else {
					break;
				}
			}
		}
		
		for(Cell cell : hitCellCol) {
			if(cell.getCol() == hitCell.getCol() && cell.getRow() == hitCell.getRow()) {
				startingPointC = cell;
			}
		}
		
		if((hitCellCol.indexOf(startingPointC) != (hitCellCol.size() - 1)) || (hitCellCol.indexOf(startingPointC) == 0)) {
			for(int i = (hitCellCol.indexOf(startingPointC) + 1); i < hitCellCol.size(); i++) {
				if(hitCellCol.get(i).isHidden()) {
					down++;
				} else {
					break;
				}
			}
		}
		
		if((hitCellCol.indexOf(startingPointC) != 0) || (hitCellCol.indexOf(startingPointC) == (hitCellCol.size() - 1))) {
			for(int i = (hitCellCol.indexOf(startingPointC) - 1); i >= 0; i--) {
				if(hitCellCol.get(i).isHidden() ) {
					up++;
				} else {
					break;
				}
			}
		}
		
		if(shipSizeMatch((left + right + 1), currentShipSizes)) {
			if(left > 0) {
				possibleTargets.add(hitCellRow.get((hitCellRow.indexOf(startingPointR) - 1)));
			}
			if(right > 0) {
				possibleTargets.add(hitCellRow.get((hitCellRow.indexOf(startingPointR) + 1)));
			}
		}
		
		if(shipSizeMatch((up + down + 1), currentShipSizes)) {
			if(up > 0) {
				possibleTargets.add(hitCellCol.get((hitCellCol.indexOf(startingPointC) - 1)));
			}
			if(down > 0) {
				possibleTargets.add(hitCellCol.get((hitCellCol.indexOf(startingPointC) + 1)));
			}
		}
		if(possibleTargets.size() == 1) {
			selectedCell = possibleTargets.get(0);
		} else {
			selectedCell = possibleTargets.get(generateRandomInt(0, possibleTargets.size() - 1));
			}
		updateTargetCell(grid, selectedCell);
		return getCellByCoords(grid, selectedCell.getRow(), selectedCell.getCol());
	}
	
	public static boolean shipSizeMatch(int space, ArrayList<Integer> currentShipSizes) {
		for(int i : currentShipSizes) {
			if(space >= i) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<Cell> getHitCells(Ship ship) {
		ArrayList<Cell> hitCells = new ArrayList<>();
		for(Cell cell : ship.getHull()) {
			if(cell.isHit()) {
				hitCells.add(cell);
			}
		}
		return hitCells;
	}
	
	public static boolean isDamagedShipVertical(ArrayList<Cell> hitCells) {
		if(hitCells.getFirst().getCol() == hitCells.getLast().getCol()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Cell destroyCurrentTarget(Grid grid, ArrayList<Cell> hitCells, boolean shipVertical) {
		Cell targetCell1 = null;
		Cell targetCell2 = null;
		
		if(shipVertical) {
			int[] cellRows = new int[hitCells.size()];
			for(int i = 0; i < hitCells.size(); i++) {
				cellRows[i] = hitCells.get(i).getRow();
			}
			int maxRow = Arrays.stream(cellRows).max().getAsInt();
			int minRow = Arrays.stream(cellRows).min().getAsInt();
				if(cellExists(maxRow + 1, hitCells.get(0).getCol())) {
				targetCell1 = getCellByCoords(grid, maxRow + 1, hitCells.get(0).getCol());
				}
				if(cellExists(minRow - 1, hitCells.get(0).getCol())) {
					targetCell2 = getCellByCoords(grid, minRow - 1, hitCells.get(0).getCol());
				}
			return aimFire(grid,targetCell1, targetCell2);
			} else {
				int[] cellCols = new int[hitCells.size()];
				for(int i = 0; i < hitCells.size(); i++) {
					cellCols[i] = hitCells.get(i).getCol();
			}
			int maxCol = Arrays.stream(cellCols).max().getAsInt();
			int minCol = Arrays.stream(cellCols).min().getAsInt();
				if(cellExists(hitCells.get(0).getRow(), maxCol + 1)) {
					targetCell1 = getCellByCoords(grid, hitCells.get(0).getRow(), maxCol + 1);
				}
				if(cellExists(hitCells.get(0).getRow(), minCol - 1)) {
					targetCell2 = getCellByCoords(grid, hitCells.get(0).getRow(), minCol - 1);
				}
			return aimFire(grid, targetCell1, targetCell2);
		}
	}
	
	public static int generateRandomInt(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}
	
	public static void updateTargetCell(Grid shotGrid, Cell targetCell) {
		if(Utils.getCellByCoords(shotGrid, targetCell.getRow(), targetCell.getCol()).isShip()) {
			Utils.getCellByCoords(shotGrid, targetCell.getRow(), targetCell.getCol()).setHiddenFalse();
			Utils.getCellByCoords(shotGrid, targetCell.getRow(), targetCell.getCol()).setHitTrue();
		} else {
			Utils.getCellByCoords(shotGrid, targetCell.getRow(),targetCell.getCol()).setHiddenFalse();
			Utils.getCellByCoords(shotGrid, targetCell.getRow(), targetCell.getCol()).setMissTrue();
		}
	}
	
	public static Cell aimFire(Grid shotGrid, Cell targetCell1, Cell targetCell2) {
		if(targetCell1 == null) {
			updateTargetCell(shotGrid, targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getRow(), targetCell2.getCol());
		}
		if(targetCell2 == null) {
			updateTargetCell(shotGrid, targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getRow(),targetCell1.getCol());
		}
		if(targetCell1.isHidden() && targetCell2.isHidden()) {
			switch(generateRandomInt(0, 1)) {
			case 0: updateTargetCell(shotGrid,targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getRow(),targetCell1.getCol());
			
			case 1: updateTargetCell(shotGrid,targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getRow(),targetCell2.getCol());
			}
		} 
		else if(targetCell1.isHidden()) {
			updateTargetCell(shotGrid, targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getRow(), targetCell1.getCol());
		}
		else if(targetCell2.isHidden()) {
			updateTargetCell(shotGrid, targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getRow(), targetCell2.getCol());
		}
		return null; // impossible option
	}
	
	public static Ship getDamagedShipByHitCell(ArrayList<Ship> fleet, Cell hitCell) {
		for(Ship ship : fleet) {
			for(Cell cell : ship.getHull()) {
				if(cell.getCol() == hitCell.getCol() && cell.getRow() == hitCell.getRow()) {
					return ship;
				}
			}
		}
		return null;
	}
	
	public static void updateOpponentShotGrid(Grid grid, Player opponent) {
		Grid shotGrid = opponent.getShotGrid();
		for(Cell[] row : grid.getGrid()) {
			for (Cell cell : row) {
				if(cell.isShip()) {
					Utils.getCellByCoords(shotGrid, cell.getRow(), cell.getCol()).setShipTrue();
					Utils.getCellByCoords(shotGrid, cell.getRow(), cell.getCol()).setBlockedTrue();
					}
				else if(cell.isBlocked() && !cell.isShip()) {
					Utils.getCellByCoords(shotGrid, cell.getRow(), cell.getCol()).setBlockedTrue();
					}
				}
			}
		}
	
	public static void excludeAdjacentCells(Grid shotGrid, Player opponent, Ship damagedShip, Cell target) {
			if(Utils.cellExists(target.getRow() - 1, target.getCol() - 1)) {
				Utils.getCellByCoords(shotGrid, target.getRow() - 1, target.getCol() - 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getRow() - 1, target.getCol() - 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() - 1, target.getCol() - 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() - 1, target.getCol() - 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getRow() + 1, target.getCol() - 1)) {
				Utils.getCellByCoords(shotGrid, target.getRow() + 1, target.getCol() - 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getRow() + 1, target.getCol() - 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() + 1, target.getCol() - 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() + 1, target.getCol() - 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getRow() - 1, target.getCol() + 1)) {
				Utils.getCellByCoords(shotGrid, target.getRow() - 1, target.getCol() + 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getRow() - 1, target.getCol() + 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() - 1, target.getCol() + 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() - 1, target.getCol() + 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getRow() + 1, target.getCol() + 1)) {
				Utils.getCellByCoords(shotGrid, target.getRow() + 1, target.getCol() + 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getRow() + 1, target.getCol() + 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() + 1, target.getCol() + 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getRow() + 1, target.getCol() + 1).setHiddenFalse();
			}
			
			if(Utils.isLastHit(opponent, target)) {
				if(Utils.isDamagedShipVertical(damagedShip.getHull())) {
					if(damagedShip.getHull().get(0).getRow() > damagedShip.getHull().get(1).getRow()) {
						if(Utils.cellExists(damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol())) {
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol()).setMissTrue();
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol())) {
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol()).setMissTrue();
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol())) {
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol()).setMissTrue();
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow() + 1, damagedShip.getHull().get(0).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol())) {
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol()).setMissTrue();
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow() - 1, damagedShip.getHull().get(1).getCol()).setHiddenFalse();
						}
					} else {
						if(Utils.cellExists(damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol())) {
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol()).setMissTrue();
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol())) {
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol()).setMissTrue();
							Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol())) {
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol()).setMissTrue();
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow() + 1, damagedShip.getHull().get(1).getCol()).setHiddenFalse();
						}
						if(Utils.cellExists(damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol())) {
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol()).setMissTrue();
							Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow() - 1, damagedShip.getHull().get(0).getCol()).setHiddenFalse();
						}
					}
			} else {
				
				if(damagedShip.getHull().get(0).getCol() > damagedShip.getHull().get(1).getCol()) {
					if(Utils.cellExists(damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1)) {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1)) {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1)) {
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() + 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1)) {
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() - 1).setHiddenFalse();
					}
				} else {
					if(Utils.cellExists(damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1)) {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1)) {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1)) {
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getRow(), damagedShip.getHull().get(1).getCol() + 1).setHiddenFalse();
					}
					if(Utils.cellExists(damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1)) {
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getRow(), damagedShip.getHull().get(0).getCol() - 1).setHiddenFalse();
					}
				}
			}
			}
		}
			
		public static boolean isLastHit(Player opponent, Cell hitCell) {
			Ship ship = Utils.getDamagedShipByHitCell(opponent.getFleet(), hitCell);
			int hitCounter = 0;
			for(Cell cell : ship.getHull()) {
				if(cell.isHit()) hitCounter++;
			}
			if(hitCounter == ship.getHull().size()) {
				return true;
			} else {
				return false;
			}
		}
		
		public static ArrayList<Cell> getPossibleTargets(Grid grid, ArrayList<Ship> fleet) {
			ArrayList<Cell> possibleTargets = new ArrayList<Cell>();
			ArrayList<Cell> currentRowCol = new ArrayList<Cell>();
			ArrayList<Cell> temp = new ArrayList<Cell>();
			int maxShipSize;
			int currentRowColNum = 0;
			//Find maxShipSize
			List<Integer>shipSizes = new ArrayList<>();
			for(Ship ship : fleet) {
				if(!ship.isDestroyed()) shipSizes.add(ship.getHull().size());
			}
			
			maxShipSize = shipSizes.stream().max(Comparator.naturalOrder()).get();
			
			//Analyze rows
			do{
			//Get current row
			for(Cell[] row : grid.getGrid()) {
				for(Cell cell : row) {
					if(cell.getRow() == currentRowColNum) {
						currentRowCol.add(cell);
					}
				}
			}
			currentRowColNum++;
			
			//Analyze current row
			for(Cell cell : currentRowCol) {
				if(cell.isHidden()) {
					temp.add(cell);
				} else {
					if(temp.size() >= maxShipSize) {
						possibleTargets.addAll(temp);
					}
					temp.clear();
				}
			}
			if(temp.size() >= maxShipSize) {
			possibleTargets.addAll(temp);
			}
			temp.clear();
			currentRowCol.clear();
			} while(currentRowColNum < 10);
			
			currentRowColNum = 0;
			//Analyze columns
			do{
				//Get current column
				for(Cell[] row : grid.getGrid()) {
					for(Cell cell : row) {
						if(cell.getCol() == currentRowColNum) {
							currentRowCol.add(cell);
						}
					}
				}
				currentRowColNum++;
				
				//Analyze current column
				for(Cell cell : currentRowCol) {
					if(cell.isHidden()) {
						temp.add(cell);
					} else {
						if(temp.size() >= maxShipSize) {
							possibleTargets.addAll(temp);
						}
						temp.clear();
					}
				}
				if(temp.size() >= maxShipSize) {
				possibleTargets.addAll(temp);
				}
				temp.clear();
				currentRowCol.clear();
				} while(currentRowColNum < 10);
			
			return possibleTargets;
		}
}
