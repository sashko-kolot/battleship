package application;

import java.util.ArrayList;

class PlayerBot extends Player {
	
	private ArrayList<Integer> cellFilter = new ArrayList<>();
	{
		resetCellFilter();
	}
	
	// Getters
	public ArrayList<Integer> getCellFilter() {
		return cellFilter;
	}
	// Setters
	private void resetCellFilter() {
		if(cellFilter.size() > 0) {
			cellFilter.clear();
		}
		for(int i = 0; i < 100; i ++) {
			cellFilter.add(i);
		}
	}
	
	@Override
	protected Ship placeShip(Grid grid, int shipSize) {
		Cell stern;
		Cell prow;
		Cell direction = new Cell();
		direction = null;
		Ship ship = new Ship();
	
	
		do {
		stern = Utils.randomCellSelector(grid, cellFilter);
		direction = Utils.getShipDirection(grid, stern.getCol(), stern.getRow(), shipSize);
		} while (direction == null);
	
		stern.setShipTrue();
		stern.setBlockedTrue();
		ship.getHull().add(stern);
		
		prow = direction;
		prow.setShipTrue();
		prow.setBlockedTrue();
		ship.getHull().add(prow);
		
		if(stern.getCol() == prow.getCol()) {
			if(stern.getRow() > prow.getRow()) {
				for(int i = prow.getRow() + 1; i < stern.getRow(); i++) {
					Utils.getCellByCoords(grid, prow.getCol(), i).setShipTrue();
					Utils.getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
					ship.getHull().add(Utils.getCellByCoords(grid, prow.getCol(), i));
				} 		
			} else {
				for (int i = prow.getRow() - 1; i > stern.getRow(); i--) {
					Utils.getCellByCoords(grid, prow.getCol(), i).setShipTrue();
					Utils.getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
					ship.getHull().add(Utils.getCellByCoords(grid, prow.getCol(), i));
				}
			}
		} else {
			if(stern.getCol() > prow.getCol()) {
				for(int i = prow.getCol() + 1; i < stern.getCol(); i++) {
					Utils.getCellByCoords(grid, i, prow.getRow()).setShipTrue();
					Utils.getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getRow()));
				}
			} else {
				for(int i = prow.getCol() - 1; i > stern.getCol(); i--) {
					Utils.getCellByCoords(grid, i, prow.getRow()).setShipTrue();
					Utils.getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getRow()));
				}
			}
		}
		return ship;
	}
	
	@Override 
	public void setFleet(Player opponent) {
		int shipSize = 0;
		do { 
			switch (getFleet().size()) {
			
			case 0 -> shipSize = 5;
			
			case 1 -> shipSize = 4;
			
			case 2 -> shipSize = 3;
			
			case 3 -> shipSize = 3;
			
			case 4 -> shipSize = 2;
			}
			getFleet().add(placeShip(getShipGrid(), shipSize));
			Utils.blockCells(getShipGrid(), getFleet().getLast());
			// debug start
			System.out.println(getFleet().getLast().getHull().size());
			/*System.out.println("Blocked cells");
			for (Cell cell : getShipGrid().getGrid()) {
				if (cell.isBlocked()) {
					System.out.println(cell.getCol() + ", " + cell.getRow());
				}
				}*/
			// debug end
		} while (getFleet().size() < 5);
		opponent.getShotGrid().alignGrids(getShipGrid().getGrid());
		//opponent.getPlayer().shotGrid().getGrid() = getShipGrid().getGrid();
		//resetCellFilter();
	}
	
	@Override
	public void shoot(Player opponent) {
		Cell target = new Cell();
		if(Utils.doesCurrentTargetExist(opponent.getFleet())) {
			Ship damagedShip = null;
			for(Ship ship : opponent.getFleet()) {
				if(ship.isDamaged()) {
					damagedShip = ship;
				}
			}
				ArrayList<Cell> hitCells = Utils.getHitCells(damagedShip);
				 if(hitCells.size() > 1) {
					 target = Utils.destroyCurrentTarget(getShotGrid(), hitCells, Utils.isDamagedShipVertical(hitCells)); 
				 } else {
					 
					 target = Utils.shootNewTargetWithOneHitCell(getShotGrid(), hitCells.get(0));
				 	}
		} else {
			target = Utils.shootNewTarget(getShotGrid(), Utils.getPossibleTargets(getShotGrid()));
		}
		
		if(target.isHit()) {
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHitTrue();
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHiddenFalse();
		} else {
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setMissTrue();
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHiddenFalse();
			setMyTurn(false);
			opponent.setMyTurn(true);
		}
	}
}
