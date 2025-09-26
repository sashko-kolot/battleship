package application;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

class PlayerBot extends Player {
	
	private ArrayList<Integer> cellFilter = generateCellFilter();
	
	
	
	// Getters
	public ArrayList<Integer> getCellFilter() {
		return cellFilter;
	}
	// Setters
	/*private void resetCellFilter() {
		cellFilter.clear();
		for(int i = 0; i < 100; i ++) {
			cellFilter.add(i);
		}
	}*/
	
	private ArrayList<Integer> generateCellFilter() {
		ArrayList<Integer> cellFilter= new ArrayList<>();
		for(Integer i = 0; i < 100; i ++) {
			cellFilter.add(i);
		}
		return cellFilter;
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
		direction = Utils.selectRandomDirection(Utils.getShipDirections(grid, stern.getCol(), stern.getRow(), shipSize));
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
		ship.setHitPointCounter(ship.getHull().size());
		
		return ship;
	}
	
	@Override 
	public void setFleet(Player opponent, Runnable onComplete) {
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
		} while (getFleet().size() < 5);
		Utils.updateOpponentShotGrid(getShipGrid(), opponent);
		onComplete.run();
	}
	
	@Override
	public void shoot(Player opponent, Consumer<Boolean> resultCallback) {
		Cell target = new Cell();
		Ship damagedShip = new Ship();
		//debug
		System.out.println("Current Target: " + Utils.doesCurrentTargetExist(opponent.getFleet()));
		
		if(Utils.doesCurrentTargetExist(opponent.getFleet())) {
			for(Ship ship : opponent.getFleet()) {
				if(ship.isDamaged()) {
					damagedShip = ship;
				}
			}
			//debug
			System.out.println("Damaged ship: ");
			for(Cell cell : damagedShip.getHull()) {
				System.out.print(cell.getCol() + ":" + cell.getRow() + " ");
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
		boolean hit = target.isHit();
		if(target.isHit()) {
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHitTrue();
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHiddenFalse();
			if(damagedShip.getHull().size() == 0) {
				damagedShip = Utils.getDamagedShipByHitCell(opponent.getFleet(), target);
				damagedShip.setDamaged(true);
				damagedShip.decrementHitPointCounter();
			} else {
				damagedShip.decrementHitPointCounter();
				//debug
				System.out.println("Damaged ship size: " + damagedShip.getHull().size());
				
				if(damagedShip.getHitPointCounter() == 0) {
					damagedShip.setDamaged(false);
					damagedShip.setDestroyedTrue();
				}
			}
					
		} else {
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setMissTrue();
			Utils.getCellByCoords(opponent.getShipGrid(), target.getCol(), target.getRow()).setHiddenFalse();
			}
		Platform.runLater(()-> ViewController.updateShipGridPane(opponent.getShipGrid()));
		System.out.println("DEBUG: Callback hit = " + hit + "Target: " + target.getCol() + ", " + target.getRow());//debug
		 
		resultCallback.accept(hit);
	}
}
