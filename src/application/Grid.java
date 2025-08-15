package application;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

class Grid {
	private GridPane gridPane = new GridPane();
	private boolean gridActive = false;
	private boolean shootingGrid = false;
	private int clickCounter = 0;
	private int shipSize = 5;
	private ArrayList<Rectangle> placeShipDirections = new ArrayList<>();
	private ArrayList<Rectangle> ship = new ArrayList<>();
	private ArrayList<ArrayList<Rectangle>> fleet = new ArrayList<>();
	
	
	Grid(boolean gridActive, boolean shootingGrid) {
	this.gridActive = gridActive;
	this.shootingGrid = shootingGrid;
	
    gridPane.setPadding(new Insets(50));
    gridPane.setGridLinesVisible(true);
    
    for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
        for (int colIndex = 0; colIndex < 10; colIndex++) {
            StackPane stackPane = createRectangle(colIndex, rowIndex);
            gridPane.add(stackPane, colIndex, rowIndex);
        }
    }
}
	
    private StackPane createRectangle(int colIndex, int rowIndex) {
    	StackPane stackPane = new StackPane(); 
        Rectangle rect = new Rectangle(30, 30, Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);
        
        rect.addEventHandler(MouseEvent.ANY, event  -> {mouseEventProcessor(rect, event);
        
        });

        stackPane.getChildren().add(rect);

        return stackPane;
    }
    
    private void mouseEventProcessor(Rectangle rect, Event event) {
    	
    	if(event.getEventType() == MouseEvent.MOUSE_ENTERED && gridIsActive())  {
    		selectCell(rect);
    	}
    	
    	else if(event.getEventType() == MouseEvent.MOUSE_EXITED && gridIsActive()) {
    		unselectCell(rect);
    	}
    	
    	else if(event.getEventType() == MouseEvent.MOUSE_CLICKED && gridIsActive() && !cellIsBlocked(rect)) {
    		mouseClickManager(GridPane.getColumnIndex(rect.getParent()), GridPane.getRowIndex(rect.getParent()), rect);
    	}
    }
   
    // Getters
    
   GridPane getGridPane() {
	   return gridPane;
   }
   
   private Rectangle getRectByCoords(int col, int row) {
	   Integer colIndex;
	   Integer rowIndex;
	   for(Node node : gridPane.getChildren()) {
		   
		   if (node instanceof StackPane) {
			   colIndex = GridPane.getColumnIndex(node);
			   rowIndex = GridPane.getRowIndex(node);
    		
			   if(colIndex == col && rowIndex == row) {
    			StackPane sp = (StackPane) node;
    				if (!sp.getChildren().isEmpty()) {
                    return (Rectangle) sp.getChildren().get(0); 
    				}
			   }
		    }
    	}
    	return null;
    }
   
   private boolean cellExists(int col, int row) {
	   if(col < 0 || col > 9 || row < 0 || row > 9) {
		   return false;
	   } else {
		   return true;
	   }
   }
    
   private boolean gridIsActive() {
    	return gridActive;
    }
    
   private boolean cellIsHit(Rectangle rect) {
	   StackPane parent = (StackPane) rect.getParent();
	   if(parent.getChildren().size() == 1) {
		return false;
	   } else {
		return true;
	}	  
   }
    
    void deactivateGrid() {
    	gridActive = false;
    }
    
    void selectCell(Rectangle rect) {
    	if(rect.getFill() == Color.LIGHTBLUE) {
    		rect.setFill(Color.LIGHTCYAN);
    	}
    }
    
    void unselectCell(Rectangle rect) {
    	if(rect.getFill() == Color.LIGHTCYAN) {
    		rect.setFill(Color.LIGHTBLUE);
    	}
    }
    boolean cellIsBlocked(Rectangle rect) {
    	if(rect.getFill() == Color.LIGHTSEAGREEN || rect.getFill() == Color.SLATEGRAY) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    void highlightBlockedCell(Rectangle rect ) {
    	rect.setFill(Color.LIGHTSEAGREEN);
    }
    
    void highlightDirection(Rectangle rect) {
    	rect.setFill(Color.PINK);
    }
    
    void removeHighlightDirection(Rectangle rect) {
    	rect.setFill(Color.LIGHTBLUE);
    }
    
    void highlightShipCell(Rectangle rect) {
    	rect.setFill(Color.SLATEGRAY);
    }
    
    void removeShipCellHighlight(Rectangle rect) {
    	rect.setFill(Color.LIGHTBLUE);
    }
    
    private ArrayList<Rectangle> getPlaceShipDirections(int col, int row, int size) {
		// Check cells (up, down, left, and right)
    	Rectangle upCell;
		Rectangle downCell;
		Rectangle leftCell;
		Rectangle rightCell;
		ArrayList<Rectangle> placeShipDirections = new ArrayList<>();
		
		// Check vertical directions
		if(row - (size - 1) >= 0) {
			upCell = getRectByCoords(col, row - (size - 1));
			if(!cellIsBlocked(upCell)) {
				highlightDirection(upCell);
				placeShipDirections.add(upCell);
			}
		}
		
		if(row + (size - 1) <= 9) {
			downCell = getRectByCoords(col, row + (size- 1));
			if(!cellIsBlocked(downCell)) {
				highlightDirection(downCell);
				placeShipDirections.add(downCell);
			}
		}
		
		//Check horizontal directions
		if(col - (size - 1) >= 0) {
			leftCell = getRectByCoords(col - (size - 1), row);
			if(!cellIsBlocked(leftCell)) {
				highlightDirection(leftCell);
				placeShipDirections.add(leftCell);
			}
		}
		
		if(col + (size - 1) <= 9) {
			rightCell = getRectByCoords(col + (size - 1), row);
			if(!cellIsBlocked(rightCell)) {
				highlightDirection(rightCell);
				placeShipDirections.add(rightCell);
			}
		}
		return placeShipDirections;
	}
    
    private ArrayList<Rectangle> placeShip(int col, int row, Rectangle rect) {
    	if(placeShipDirections.contains(rect)) {
    		highlightShipCell(rect);
    		ship.add(rect);
    		if(GridPane.getColumnIndex(ship.get(0).getParent()) == GridPane.getColumnIndex(rect.getParent())) {
    			if(GridPane.getRowIndex(ship.get(0).getParent()) > GridPane.getRowIndex(rect.getParent())) {
    				for(int i = GridPane.getRowIndex(rect.getParent()) + 1; i < GridPane.getRowIndex(ship.get(0).getParent()); i++) {
    				highlightShipCell(getRectByCoords(GridPane.getColumnIndex(rect.getParent()),i));
    				ship.add(getRectByCoords(GridPane.getColumnIndex(rect.getParent()),i));
    				}
    			} else {
    				for(int i = GridPane.getRowIndex(rect.getParent()) - 1; i > GridPane.getRowIndex(ship.get(0).getParent()); i--) {
    					highlightShipCell(getRectByCoords(GridPane.getColumnIndex(rect.getParent()),i));
    					ship.add(getRectByCoords(GridPane.getColumnIndex(rect.getParent()),i));
        			}
    			}
    		} else {
    			if(GridPane.getColumnIndex(ship.get(0).getParent()) > GridPane.getColumnIndex(rect.getParent())) {
    				for(int i = GridPane.getColumnIndex(rect.getParent()) + 1; i < GridPane.getColumnIndex(ship.get(0).getParent()); i++) {
    					highlightShipCell(getRectByCoords(i, GridPane.getRowIndex(rect.getParent())));
    					ship.add(getRectByCoords(i, GridPane.getRowIndex(rect.getParent())));
    				} 
    				
    			}else {
    					for(int i = GridPane.getColumnIndex(rect.getParent()) - 1; i > GridPane.getColumnIndex(ship.get(0).getParent()); i--) {
    						highlightShipCell(getRectByCoords(i, GridPane.getRowIndex(rect.getParent())));
            				ship.add(getRectByCoords(i, GridPane.getRowIndex(rect.getParent())));
    					}
    				}
    		}
    			
    	} else {
    		removeShipCellHighlight(ship.get(0));
    		ship.clear();
    		for(Rectangle dircell : placeShipDirections) {
        		removeHighlightDirection(dircell);
        		}
    		placeShipDirections.clear();
    		clickCounter = 0;
    	}
    	for(Rectangle dircell : placeShipDirections) {
    		if(dircell.getFill() != Color.SLATEGRAY) {
    		removeHighlightDirection(dircell);
    		}
    	}
    	placeShipDirections.clear();
    	return ship;
    }
    
    private void blockCells(ArrayList<Rectangle> ship) {
    		int col1 = GridPane.getColumnIndex(ship.get(0).getParent());
    		int col2 = GridPane.getColumnIndex(ship.get(1).getParent());
    		int row1 = GridPane.getRowIndex(ship.get(0).getParent());
    		int row2 = GridPane.getRowIndex(ship.get(1).getParent());
  
    		if(col1 == col2) {
    			if(row1 > row2) {
    				for(int i = (row1 + 1); i >= (row2 - 1); i--) {
    					if(cellExists(col1 + 1, i)) {
    						highlightBlockedCell(getRectByCoords(col1 + 1, i));
    						//System.out.println((col1 + 1) + " " + i );
    					}
    					if(cellExists(col1 - 1, i)) {
    						highlightBlockedCell(getRectByCoords(col1 - 1, i));
    					}
    				}	
    				if(cellExists(col1, row1 + 1)) {
    					highlightBlockedCell(getRectByCoords(col1, row1 + 1));
    				}
    				if(cellExists(col2, row2 - 1)) {
    					highlightBlockedCell(getRectByCoords(col2, row2 - 1));
    				}
    				
    			} else {
    				for(int i = (row1 - 1); i <= (row2 + 1); i++) {
    					if(cellExists(col1 + 1, i)) {
    						highlightBlockedCell(getRectByCoords(col1 + 1, i));
    					}
    					if(cellExists(col1 - 1, i)) {
    						highlightBlockedCell(getRectByCoords(col1 - 1, i));
    					}
    				}	
    				if(cellExists(col1, row1 - 1)) {
    					highlightBlockedCell(getRectByCoords(col1, row1 - 1));
    				}
    				if(cellExists(col2, row2 + 1)) {
    					highlightBlockedCell(getRectByCoords(col2, row2 + 1));
    				}
    			}
    			
    		} else {
    			if(col1 > col2) {
    				for(int i = (col1 + 1); i >= (col2 - 1); i--) {
    					if(cellExists(i, row1 + 1)) {
    						highlightBlockedCell(getRectByCoords(i, row1 + 1));
    					}
    					if(cellExists(i, row1 - 1)) {
    						highlightBlockedCell(getRectByCoords(i, row1 - 1));
    					}
    					
    				}
    				if(cellExists(col1 + 1, row1)) {
						highlightBlockedCell(getRectByCoords(col1 + 1, row1));
					}
					if(cellExists(col2 - 1, row2)) {
						highlightBlockedCell(getRectByCoords(col2 - 1, row2));
					}
    				
    			} else {
    				for(int i = (col1 - 1); i <= (col2 + 1); i++) {
    					if(cellExists(i, row1 + 1)) {
    						highlightBlockedCell(getRectByCoords(i, row1 + 1));
    					}
    					if(cellExists(i, row1 - 1)) {
    						highlightBlockedCell(getRectByCoords(i, row1 - 1));
    					}
    				}	
    				if(cellExists(col1 - 1, row1)) {
    					highlightBlockedCell(getRectByCoords(col1 - 1, row1));
    				}
    				if(cellExists(col2 + 1, row2)) {
    					highlightBlockedCell(getRectByCoords(col2 + 1, row2));
    				}
    			}
    		}
    }
    
   private  void mouseClickManager(int col, int row, Rectangle rect) {
    	if(shootingGrid) {
			//shoot(col, row);
		} else {
			clickCounter++;
			if(clickCounter == 1) {
				highlightShipCell(rect);
				ship.add(rect);
				placeShipDirections = getPlaceShipDirections(col, row, shipSize);
			} else {
				ship = placeShip(col, row, rect);
				if(!ship.isEmpty()) {
					blockCells(ship);
					fleet.add(new ArrayList<>(ship));
					switch(fleet.size()) {
						case 1:
							shipSize = 4; // battleship
							break;
						case 2:
							shipSize = 3; // cruiser1
							break;
						case 3:
							break; // cruiser2
						case 4:
							shipSize = 2; // destroyer1
						case 5:
							break; // destroyer2
						case 6: 
							break; // destroyer3
					}
					clickCounter = 0;
					ship.clear();
					if(fleet.size() == 7) {
						StackPane sp; 
						Rectangle r;
						for(Node node : gridPane.getChildren()) {
							if (node instanceof StackPane) {
								sp  = (StackPane) node;
								r = getRectByCoords(GridPane.getColumnIndex(sp), GridPane.getRowIndex(sp));
									if(r.getFill() == Color.LIGHTSEAGREEN) {
										r.setFill(Color.LIGHTBLUE);
									}
							}
						}
						deactivateGrid();
					}
				}
    	
			}
		
		}
    }
}
