package application;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javafx.scene.paint.Color;

class PlayerHuman extends Player {
	
	@Override
	protected Ship placeShip(Grid grid, int shipSize) {return null;}
	
	// Overloaded
	protected void placeShip(Grid grid, CanvasGridView shipGridView,int shipSize, Consumer<Ship> callback) {
		Ship ship = new Ship();
		AtomicReference<Cell> sternRef = new AtomicReference<>();
		AtomicReference<ArrayList<Cell>> directionsRef = new AtomicReference<>();
		shipGridView.setClickListener(clickData -> {
			Cell clickedCell = Utils.getCellByCoords(grid, clickData.getRow(), clickData.getCol());
			
			if(sternRef.get() == null) {
				if(!clickedCell.isBlocked()) {
					sternRef.set(clickedCell);
					ArrayList<Cell> directions = Utils.getShipDirections (grid, clickedCell.getRow(), clickedCell.getCol(), shipSize);
					directionsRef.set(directions);
					for(Cell cell : directionsRef.get()) {
						cell.toggleDirection();
					}
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
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setShipTrue();
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setBlockedTrue();
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getCol()));
	    				} 		
	    			} else {
	    				for (int i = prow.getRow() - 1; i > stern.getRow(); i--) {
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setShipTrue();
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setBlockedTrue();
	    					Utils.getCellByCoords(grid, i, prow.getCol()).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, i, prow.getCol()));
	    				}
	    			}
	    		} else {
	    			if(sternRef.get().getCol() > prow.getCol()) {
	    				for(int i = prow.getCol() + 1; i < sternRef.get().getCol(); i++) {
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setShipTrue();
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setBlockedTrue();
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, prow.getRow(), i));
	    				}
	    			} else {
	    				for(int i = prow.getCol() - 1; i > stern.getCol(); i--) {
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setShipTrue();
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setBlockedTrue();
	    					Utils.getCellByCoords(grid, prow.getRow(), i).setHiddenFalse();
	    					ship.getHull().add(Utils.getCellByCoords(grid, prow.getRow(), i));
	    				}
	    			}
	    		}
	            ship.setHitPointCounter(ship.getHull().size());
	            for(Cell cell : directionsRef.get()) {
					cell.toggleDirection();
				}
	            callback.accept(ship);
			}
		});
	}

	@Override
	public void setFleet(Player opponent, Runnable onComplete) {
		getShipGrid().toggleActive();
	    placeNextShip(opponent, 0, onComplete); 
	}
	
	private void placeNextShip(Player opponent, int index, Runnable onComplete) {
	    if (index >= 5) {
	        Utils.updateOpponentShotGrid(getShipGrid(), opponent);
	        getShipGrid().toggleActive();
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

	    placeShip(getShipGrid(), shipGridView, shipSize, ship -> {
	        getFleet().add(ship);
	        Utils.blockCells(getShipGrid(), ship);

	        placeNextShip(opponent, index + 1, onComplete);
	    });
	}
	
	@Override
	public void shoot(Player opponent, Consumer<Boolean> resultCallback) {
		View.setMessage("Your turn, Admiral!", Color.GREEN);
		View.setMessageVisibility(true);
		getShotGrid().toggleActive();
		shotGridView.setClickListener(clickData -> {
			Cell clickedCell = Utils.getCellByCoords(getShotGrid(), clickData.getRow(), clickData.getCol());
			
			if(!clickedCell.isHidden()) return; 
				
			 if(clickedCell.isShip()) {
				clickedCell.setHitTrue();
				
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getRow(), clickedCell.getCol()).setHitTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getRow(), clickedCell.getCol()).setHiddenFalse();
				Utils.excludeAdjacentCells(getShotGrid(), opponent, Utils.getDamagedShipByHitCell(opponent.getFleet(), clickedCell),clickedCell);
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
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getRow(), clickedCell.getCol()).setMissTrue();
				Utils.getCellByCoords(opponent.getShipGrid(), clickedCell.getRow(), clickedCell.getCol()).setHiddenFalse();
				}
			 clickedCell.setHiddenFalse();
			 boolean hit = clickedCell.isHit();
			 View.setMessageVisibility(false);
			 getShotGrid().toggleActive();
			 resultCallback.accept(hit);
		});
	}
}
