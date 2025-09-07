package application;

class PlayerBot extends Player {
	
	@Override
	protected Ship placeShip(Grid grid, int shipSize) {
		Cell stern;
		Cell prow;
		Ship ship = new Ship();
	
	do {	
		do {
			stern = Utils.getRandomCell(grid, 0, 9);
		} while (!stern.isBlocked());
		prow = Utils.getShipDirection(grid, stern.getCol(), stern.getRow(), shipSize); 
	} while(prow == null);
		
		stern.setShipTrue();
		stern.setBlockedTrue();
		ship.getHull().add(stern);
		
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
			
			case 2 -> shipSize = 4;
			
			case 3 -> shipSize = 3;
			
			case 4 -> shipSize = 3;
			
			case 5 -> shipSize = 2;
			
			case 6 -> shipSize = 2;
			
			case 7 -> shipSize = 2;
			}
			getFleet().add(placeShip(getShipGrid(), shipSize));
			Utils.blockCells(getShipGrid(), getFleet().getLast());
		} while (getFleet().size() <= 7);
	}
}
