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
	public void setFleet() {
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
		
		resetCellFilter();
	}
	
	@Override
	public void shoot(Player opponent) {
		for (Ship ship : opponent.getFleet()) {
			if (ship.isDamaged()) {
				
			}
		}
	}
}
