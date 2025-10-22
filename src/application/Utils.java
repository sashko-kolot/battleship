package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

final class Utils {
	
	private Utils() {} // To prevent instantiation
	
	public static Cell randomCellSelector(Grid grid, ArrayList<Integer> cellFilter) {
		Integer cellIndex = null;
		Cell randCell = new Cell();
		do {
			int min = 0;
			int max = cellFilter.size() - 1;
			cellIndex = (min +(int)(Math.random() * ((max-min) + 1)));
			
			if(grid.getGrid().get(cellIndex).isBlocked()) {
			cellFilter.remove(cellIndex);
			cellIndex = null;
				}
			
			} while (cellIndex == null);
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
	 
	public static ArrayList<Cell> getShipDirections(Grid grid, int col, int row, int size) {
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
				if (getCellByCoords(grid, cell.getCol(), cell.getRow() + i).isBlocked()) {
					return false;
				}
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
		int col1 = hull.get(0).getCol();
		int col2 = hull.get(1).getCol();
		int row1 = hull.get(0).getRow();
		int row2 = hull.get(1).getRow();
		
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
				if(cellExists(col1, row2 - 1)) {
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
		return Utils.getCellByCoords(grid, cell.getCol(), cell.getRow());
	}
	
	public static Cell shootNewTargetWithOneHitCell(Grid grid, Cell hitCell) {
		ArrayList<Cell> possibleTargets = new ArrayList<>();
		Cell cell = new Cell();
		if(Utils.cellExists(hitCell.getCol() + 1, hitCell.getRow())) {
		cell = getCellByCoords(grid, hitCell.getCol() + 1, hitCell.getRow());
			if(cell.isHidden()) {
				possibleTargets.add(cell);
			}
		}
		if(Utils.cellExists(hitCell.getCol() - 1, hitCell.getRow())) {
		cell = getCellByCoords(grid, hitCell.getCol() - 1, hitCell.getRow());
			if(cell.isHidden()) {
				possibleTargets.add(cell);
			}
		}
		if(Utils.cellExists(hitCell.getCol(), hitCell.getRow() + 1)) {
		cell = getCellByCoords(grid, hitCell.getCol(), hitCell.getRow() + 1);
			if(cell.isHidden()) {
				possibleTargets.add(cell);
			}
		}
		if(Utils.cellExists(hitCell.getCol(), hitCell.getRow()  - 1)) {
		cell = getCellByCoords(grid, hitCell.getCol(), hitCell.getRow()  - 1);
			if(cell.isHidden()) {
				possibleTargets.add(cell);
			}
		}
		if(possibleTargets.size() > 1) {
		cell = possibleTargets.get(generateRandomInt(0, possibleTargets.size() - 1));
		updateTargetCell(grid, cell);
		return Utils.getCellByCoords(grid, cell.getCol(), cell.getRow());
		} else {
			cell = possibleTargets.get(0);
		}
		updateTargetCell(grid, cell);
		
		return Utils.getCellByCoords(grid, cell.getCol(), cell.getRow());
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
				if(cellExists(hitCells.get(0).getCol(), maxRow + 1)) {
				targetCell1 = getCellByCoords(grid, hitCells.get(0).getCol(), maxRow + 1);
				}
				if(cellExists(hitCells.get(0).getCol(), minRow - 1)) {
					targetCell2 = getCellByCoords(grid, hitCells.get(0).getCol(), minRow - 1);
				}
			return aimFire(grid,targetCell1, targetCell2);
			} else {
				int[] cellCols = new int[hitCells.size()];
				for(int i = 0; i < hitCells.size(); i++) {
					cellCols[i] = hitCells.get(i).getCol();
			}
			int maxCol = Arrays.stream(cellCols).max().getAsInt();
			int minCol = Arrays.stream(cellCols).min().getAsInt();
				if(cellExists(maxCol + 1, hitCells.get(0).getRow())) {
					targetCell1 = getCellByCoords(grid, maxCol + 1, hitCells.get(0).getRow());
				}
				if(cellExists(minCol - 1, hitCells.get(0).getRow())) {
					targetCell2 = getCellByCoords(grid, minCol - 1, hitCells.get(0).getRow());
				}
			return aimFire(grid, targetCell1, targetCell2);
		}
	}
	
	public static int generateRandomInt(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}
	
	public static void updateTargetCell(Grid shotGrid, Cell targetCell) {
		if(Utils.getCellByCoords(shotGrid, targetCell.getCol(), targetCell.getRow()).isShip()) {
			Utils.getCellByCoords(shotGrid, targetCell.getCol(), targetCell.getRow()).setHiddenFalse();
			Utils.getCellByCoords(shotGrid, targetCell.getCol(), targetCell.getRow()).setHitTrue();
		} else {
			Utils.getCellByCoords(shotGrid, targetCell.getCol(), targetCell.getRow()).setHiddenFalse();
			Utils.getCellByCoords(shotGrid, targetCell.getCol(), targetCell.getRow()).setMissTrue();
		}
	}
	
	public static Cell aimFire(Grid shotGrid, Cell targetCell1, Cell targetCell2) {
		if(targetCell1 == null) {
			updateTargetCell(shotGrid, targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getCol(), targetCell2.getRow());
		}
		if(targetCell2 == null) {
			updateTargetCell(shotGrid, targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getCol(), targetCell1.getRow());
		}
		if(targetCell1.isHidden() && targetCell2.isHidden()) {
			switch(generateRandomInt(0, 1)) {
			case 0: updateTargetCell(shotGrid,targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getCol(), targetCell1.getRow());
			
			case 1: updateTargetCell(shotGrid,targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getCol(), targetCell2.getRow());
			}
		} 
		else if(targetCell1.isHidden()) {
			updateTargetCell(shotGrid, targetCell1);
			return Utils.getCellByCoords(shotGrid, targetCell1.getCol(), targetCell1.getRow());
		}
		else if(targetCell2.isHidden()) {
			updateTargetCell(shotGrid, targetCell2);
			return Utils.getCellByCoords(shotGrid, targetCell2.getCol(), targetCell2.getRow());
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
		for(Cell cell : grid.getGrid()) {
			if(cell.isShip()) {
				Utils.getCellByCoords(shotGrid, cell.getCol(), cell.getRow()).setShipTrue();
				Utils.getCellByCoords(shotGrid, cell.getCol(), cell.getRow()).setBlockedTrue();
				}
			else if(cell.isBlocked() && !cell.isShip()) {
				Utils.getCellByCoords(shotGrid, cell.getCol(), cell.getRow()).setBlockedTrue();
				}
			}
		}
	
	public static void excludeAdjacentCells(Grid shotGrid, Player opponent, Ship damagedShip, Cell target) {
			if(Utils.cellExists(target.getCol() - 1, target.getRow() - 1)) {
				Utils.getCellByCoords(shotGrid, target.getCol() - 1, target.getRow() - 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getCol() - 1, target.getRow() - 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() - 1, target.getRow() - 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() - 1, target.getRow() - 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getCol() - 1, target.getRow() + 1)) {
				Utils.getCellByCoords(shotGrid, target.getCol() - 1, target.getRow() + 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getCol() - 1, target.getRow() + 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() - 1, target.getRow() + 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() - 1, target.getRow() + 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getCol() + 1, target.getRow() - 1)) {
				Utils.getCellByCoords(shotGrid, target.getCol() + 1, target.getRow() - 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getCol() + 1, target.getRow() - 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() + 1, target.getRow() - 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() + 1, target.getRow() - 1).setHiddenFalse();
			}
			
			if(Utils.cellExists(target.getCol() + 1, target.getRow() + 1)) {
				Utils.getCellByCoords(shotGrid, target.getCol() + 1, target.getRow() + 1).setMissTrue();
				Utils.getCellByCoords(shotGrid, target.getCol() + 1, target.getRow() + 1).setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() + 1, target.getRow() + 1).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), target.getCol() + 1, target.getRow() + 1).setHiddenFalse();
			}
			
			if(Utils.isLastHit(opponent, target)) {
				if(Utils.isDamagedShipVertical(damagedShip.getHull())) {
					if(damagedShip.getHull().get(0).getRow() > damagedShip.getHull().get(1).getRow()) {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() + 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() + 1).setHiddenFalse();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() - 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() - 1).setHiddenFalse();
						
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() + 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() + 1).setHiddenFalse();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() - 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() - 1).setHiddenFalse();
					} else {
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() + 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() + 1).setHiddenFalse();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() - 1).setMissTrue();
						Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() - 1).setHiddenFalse();
						
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() + 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol(), damagedShip.getHull().get(1).getRow() + 1).setHiddenFalse();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() - 1).setMissTrue();
						Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol(), damagedShip.getHull().get(0).getRow() - 1).setHiddenFalse();
						
					}
			} else {
				
				if(damagedShip.getHull().get(0).getCol() > damagedShip.getHull().get(1).getCol()) {
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol() + 1, damagedShip.getHull().get(0).getRow()).setMissTrue();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol() + 1, damagedShip.getHull().get(0).getRow()).setHiddenFalse();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol() - 1, damagedShip.getHull().get(1).getRow()).setMissTrue();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol() - 1, damagedShip.getHull().get(1).getRow()).setHiddenFalse();
					
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol() + 1, damagedShip.getHull().get(0).getRow()).setMissTrue();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol() + 1, damagedShip.getHull().get(0).getRow()).setHiddenFalse();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol() - 1, damagedShip.getHull().get(1).getRow()).setMissTrue();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol() - 1, damagedShip.getHull().get(1).getRow()).setHiddenFalse();
				
				} else {
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol() + 1, damagedShip.getHull().get(1).getRow()).setMissTrue();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(1).getCol() + 1, damagedShip.getHull().get(1).getRow()).setHiddenFalse();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol() - 1, damagedShip.getHull().get(0).getRow()).setMissTrue();
					Utils.getCellByCoords(shotGrid, damagedShip.getHull().get(0).getCol() - 1, damagedShip.getHull().get(0).getRow()).setHiddenFalse();
					
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol() + 1, damagedShip.getHull().get(1).getRow()).setMissTrue();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(1).getCol() + 1, damagedShip.getHull().get(1).getRow()).setHiddenFalse();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol() - 1, damagedShip.getHull().get(0).getRow()).setMissTrue();
					Utils.getCellByCoords(opponent.getShipGrid(), damagedShip.getHull().get(0).getCol() - 1, damagedShip.getHull().get(0).getRow()).setHiddenFalse();
	
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
			for(Cell cell : grid.getGrid()) {
				if(cell.getRow() == currentRowColNum) {
					currentRowCol.add(cell);
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
				for(Cell cell : grid.getGrid()) {
					if(cell.getCol() == currentRowColNum) {
						currentRowCol.add(cell);
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
