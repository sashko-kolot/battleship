package application;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.function.Consumer;


final class ViewController {
	private ViewController() {} // To prevent instantiation
	private static View view;
	private static Consumer<ClickData> clickListener;
	
	
	// Setters
	public static void setView(View v) {
		view = v;
	}
	
	public  static void setClickListener(Consumer<ClickData> listener) {
		clickListener = listener;
	}
	
	public static void clearClickListener() {
		clickListener = null;
	}
	
	public static void updateShipGridPane(Grid shipGrid) {
		GridPane shipGridPane = view.getShipGridPane();
		for(Cell cell : shipGrid.getGrid()) {
			if(cell.isShip() && !cell.isHit()) {
				highlightShipCell(getRectangleByCoords(shipGridPane, cell.getCol(), cell.getRow()));
			} 
			 else if(cell.isBlocked() && !cell.isShip() && !cell.isMiss()) {
				 highlightBlockedCell(getRectangleByCoords(shipGridPane, cell.getCol(), cell.getRow()));
			}
			 else if(cell.isShip() && cell.isHit()) {
				 highlightHit(getRectangleByCoords(shipGridPane, cell.getCol(), cell.getRow()));
			 }
			 else if((cell.isMiss() && cell.isBlocked()) || (cell.isMiss() && !cell.isBlocked()))	{
				 markMiss(getRectangleByCoords(shipGridPane, cell.getCol(), cell.getRow()));
			 }
		}
	}
	
	public static void updateShotGridPane(Grid shotGrid) {
		GridPane shotGridPane = view.getShotGridPane();
		for(Cell cell : shotGrid.getGrid()) {
			if(cell.isHit()) {
				highlightHit(getRectangleByCoords(shotGridPane, cell.getCol(), cell.getRow()));
			} 
			 else if(cell.isMiss()) {
				 markMiss(getRectangleByCoords(shotGridPane, cell.getCol(), cell.getRow()));
			 }
		}
	}
	public static void mouseEventHandler(MouseEvent event) {
		Node target = (Node) event.getTarget();
		Rectangle rect = null;
		if(target instanceof Rectangle) {
			 rect = (Rectangle) target;
		} 
			else if(target instanceof StackPane) {
				StackPane stackPane = (StackPane) target;
				rect = (Rectangle) stackPane.getChildren().get(0);
			}

		int col = GridPane.getColumnIndex(rect.getParent());
		int row = GridPane.getRowIndex(rect.getParent());
	    	
	    	if(event.getEventType() == MouseEvent.MOUSE_ENTERED)  {
	    		selectCell(rect);
	    	}
	    	
	    	else if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
	    		unselectCell(rect);
	    	}
	    	
	    	else if(event.getEventType() == MouseEvent.MOUSE_CLICKED) {
	    		
	    		if(clickListener != null) {
	    			clickListener.accept(new ClickData(col,row));
	    		}
	    	}
	    }
	
   private static void selectCell(Rectangle rect) {
    	if(rect.getFill() == Color.LIGHTBLUE) {
    		rect.setFill(Color.LIGHTCYAN);
    	}
    }
    
    private static void unselectCell(Rectangle rect) {
    	if(rect.getFill() == Color.LIGHTCYAN) {
    		rect.setFill(Color.LIGHTBLUE);
    	}
    }
    
    private static void highlightDirection(Rectangle rect) {
    	rect.setFill(Color.PINK);
    }
    
   private static void removeHighlightDirection(Rectangle rect) {
    	rect.setFill(Color.LIGHTBLUE);
    }
    
   private static void highlightShipCell(Rectangle rect) {
    	rect.setFill(Color.SLATEGRAY);
    }
    
   private static void highlightBlockedCell(Rectangle rect ) {
   	rect.setFill(Color.LIGHTSEAGREEN);
   }
   
   private static void highlightHit(Rectangle rect) {
	   rect.setFill(Color.RED);
   }
   
   private static void markMiss(Rectangle rect) {
	   Circle circle  = createCircle();
	   if(rect.getParent() instanceof StackPane stackpane) {
		  stackpane.getChildren().add(circle);
	   }
   }
   
   private static Circle createCircle() {
	   Circle circle = new Circle(5, Color.BLACK);
	   return circle;
   }
    
    public static void showShipDirections(ArrayList<Cell> directions) {
    	Rectangle rect;
    	for(Cell cell : directions) {
    		rect = getRectangleByCoords(view.getShipGridPane(), cell.getCol(), cell.getRow());
    		highlightDirection(rect);
    	}
    }
    
    public static void hideShipDirections(ArrayList<Cell> directions) {
    	Rectangle rect;
    	for(Cell cell : directions) {
    		if(!cell.isBlocked()) {
		    	rect = getRectangleByCoords(view.getShipGridPane(), cell.getCol(), cell.getRow());
				removeHighlightDirection(rect);
    		}
    	}
    }
    
    private static Rectangle getRectangleByCoords(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane stackPane) {
                Integer colIndex = GridPane.getColumnIndex(stackPane);
                Integer rowIndex = GridPane.getRowIndex(stackPane);

                int c = (colIndex == null) ? 0 : colIndex;
                int r = (rowIndex == null) ? 0 : rowIndex;

                if (c == col && r == row) {
                    Node firstChild = stackPane.getChildren().get(0);
                    if (firstChild instanceof Rectangle rect) {
                        return rect;
                    }
                }
            }
        }
        return null;
    }
    
    public static void activateShipGrid() {
    	for(Node node : view.getShipGridPane().getChildren()) {
    		if(node instanceof StackPane stackPane) {
	    		if(!stackPane.getChildren().isEmpty() && stackPane.getChildren().get(0) instanceof Rectangle rect) {
	    		EventHandler<MouseEvent> handler = event -> ViewController.mouseEventHandler(event);
	    		rect.getProperties().put("handler", handler);
	    		rect.addEventHandler(MouseEvent.ANY, handler);
	    		}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static void deactivateShipGrid() {
    	for(Node node : view.getShipGridPane().getChildren()) {
    		if(node instanceof StackPane stackPane) {
	    		if(!stackPane.getChildren().isEmpty() && stackPane.getChildren().get(0) instanceof Rectangle rect) {
	    		Object obj = rect.getProperties().get("handler");
	    			if(obj instanceof EventHandler<?> handler) {
	    				rect.removeEventHandler(MouseEvent.ANY, (EventHandler<MouseEvent>) handler);
	    			}
	    		}
    		}
    	}
    }
    
    public static void activateShotGrid() {
    	for(Node node : view.getShotGridPane().getChildren()) {
    		if(node instanceof StackPane stackPane) {
	    		if(!stackPane.getChildren().isEmpty() && stackPane.getChildren().get(0) instanceof Rectangle rect) {
	    		EventHandler<MouseEvent> handler = event -> ViewController.mouseEventHandler(event);
	    		rect.getProperties().put("handler", handler);
	    		rect.addEventHandler(MouseEvent.ANY, handler);
	    		}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
    public static void deactivateShotGrid() {
    	for(Node node : view.getShotGridPane().getChildren()) {
    		if(node instanceof StackPane stackPane) {
	    		if(!stackPane.getChildren().isEmpty() && stackPane.getChildren().get(0) instanceof Rectangle rect) {
	    		Object obj = rect.getProperties().get("handler");
	    			if(obj instanceof EventHandler<?> handler) {
	    				rect.removeEventHandler(MouseEvent.ANY, (EventHandler<MouseEvent>) handler);
	    			}
	    		}
    		}
    	}
    }
    
    public static void setMessage(String text, Paint color) {
    	view.getMessage().setText(text);
    	view.getMessage().setTextFill(color);
    }
    
    public static void setMessageVisibility(boolean visibility) {
    	view.getMessage().setVisible(visibility);
    }
}
