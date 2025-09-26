package application;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import javafx.application.Platform;

class PlayerHuman extends Player {
	
	
	@Override
	protected Ship placeShip(Grid grid, int shipSize) {return null;}
	
	// Overloaded
	protected void placeShip(Grid grid, int shipSize, Consumer<Ship> callback) {
		Ship ship = new Ship();
		AtomicReference<Cell> sternRef = new AtomicReference<>();
		AtomicReference<ArrayList<Cell>> directionsRef = new AtomicReference<>();
		ViewController.activateShipGrid();
		ViewController.setClickListener(clickData -> {
			Cell clickedCell = Utils.getCellByCoords(grid, clickData.getCol(), clickData.getRow());
			
			if(sternRef.get() == null) {
				if(!clickedCell.isBlocked()) {
					sternRef.set(clickedCell);
					ArrayList<Cell> directions = Utils.getShipDirections (grid, clickedCell.getCol(), clickedCell.getRow(), shipSize);
					directionsRef.set(directions);
					
					Platform.runLater(() -> {ViewController.showShipDirections(directions);});
				}
				
				return;
			}
			
			if(directionsRef.get().contains(clickedCell)) {
				Cell stern = sternRef.get();
				Cell prow = clickedCell;
				
				stern.setShipTrue();
				stern.setBlockedTrue();
				stern.setHiddenFalse();
				ship.getHull().add(stern);
				
				prow.setShipTrue();
	            prow.setBlockedTrue();
	            prow.setHiddenFalse();
	            ship.getHull().add(prow);
	            
	            if(stern.getCol() == prow.getCol()) {
	    			if(stern.getRow() > prow.getRow()) {
	    				for(int i = prow.getRow() + 1; i < stern.getRow(); i++) {
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setShipTrue();
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, prow.getCol(), i));
	    				} 		
	    			} else {
	    				for (int i = prow.getRow() - 1; i > stern.getRow(); i--) {
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setShipTrue();
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setBlockedTrue();
	    					Utils.getCellByCoords(grid, prow.getCol(), i).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, prow.getCol(), i));
	    				}
	    			}
	    		} else {
	    			if(sternRef.get().getCol() > prow.getCol()) {
	    				for(int i = prow.getCol() + 1; i < sternRef.get().getCol(); i++) {
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setShipTrue();
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getRow()));
	    				}
	    			} else {
	    				for(int i = prow.getCol() - 1; i > stern.getCol(); i--) {
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setShipTrue();
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setBlockedTrue();
	    					Utils.getCellByCoords(grid, i, prow.getRow()).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getRow()));
	    				}
	    			}
	    		}
	            ship.setHitPointCounter(ship.getHull().size());
	            callback.accept(ship);
	            ViewController.hideShipDirections(directionsRef.get());
			}
		});
	}

	@Override
	public void setFleet(Player opponent, Runnable onComplete) {
	    placeNextShip(opponent, 0, onComplete); 
	}
	
	private void placeNextShip(Player opponent, int index,  Runnable onComplete) {
	    if (index >= 5) {
	        Utils.updateOpponentShotGrid(getShipGrid(), opponent);
	        ViewController.deactivateShipGrid();
	        ViewController.activateShotGrid();
	        onComplete.run();
	        return;
	    }

	    int shipSize = switch (index) {
	        case 0 -> 5;
	        case 1 -> 4;
	        case 2, 3 -> 3;
	        case 4 -> 2;
	        default -> throw new IllegalStateException("Unexpected index: " + index);
	    };

	    placeShip(getShipGrid(), shipSize, ship -> {
	        getFleet().add(ship);
	        Utils.blockCells(getShipGrid(), ship);
	        ViewController.updateShipGridPane(getShipGrid());

	        placeNextShip(opponent, index + 1, onComplete);
	    });
		
	}
	
	@Override
	public void shoot(Player opponent, Consumer<Boolean> resultCallback) {
		ViewController.setClickListener(clickData -> {
			Cell clickedCell = Utils.getCellByCoords(getShotGrid(), clickData.getCol(), clickData.getRow());
			
			if(!clickedCell.isHidden()) return; 
				
			 if(clickedCell.isShip()) {
				clickedCell.setHitTrue();
				
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getCol(), clickedCell.getRow()).setHitTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getCol(), clickedCell.getRow()).setHiddenFalse();
				if(!Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).isDamaged()) {
					Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).setDamaged(true);
				}
				Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).decrementHitPointCounter();
				if(Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).getHitPointCounter() == 0) {
					Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).setDamaged(false);
					Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell).setDestroyedTrue();
				}
			}else {
				clickedCell.setMissTrue();
				clickedCell.setHiddenFalse();
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getCol(), clickedCell.getRow()).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getCol(), clickedCell.getRow()).setHiddenFalse();
				}
			 clickedCell.setHiddenFalse();
			 ViewController.updateShotGridPane(getShotGrid());
			 boolean hit = clickedCell.isHit();
			 System.out.println("DEBUG: Callback hit = " + hit);//debug
			 resultCallback.accept(hit);
		});
	}
}
