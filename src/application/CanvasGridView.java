package application;

import java.util.function.Consumer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class CanvasGridView {
	private final Canvas canvas;
	private final double cellSize;
	private final GraphicsContext gc;
	private Grid grid;
	private int hoveredRow = -1;
	private int hoveredCol = -1;
	private Consumer<ClickData> clickListener;
	
	public CanvasGridView (Grid grid, int cols, int rows, double cellSize) {
		this.cellSize = cellSize;
		this.canvas = new Canvas(cols * cellSize, rows * cellSize);
		this.gc = canvas.getGraphicsContext2D();
		this.grid = grid;
		
		// Event handlers
		canvas.setOnMouseMoved(this::handleMouseMoved);
		canvas.setOnMouseClicked(this::handleMouseClicked);
		canvas.setOnMouseExited(this::handleMouseExited);
	}
	
	public Canvas getCanvas() {return canvas;}
	
	public void drawGrid() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int row = 0; row < grid.getGrid().length; row++) {
			for(int col = 0; col < grid.getGrid()[row].length; col++) {
			drawCell(grid.getGrid()[row][col]);
			}
		}
	}
	
	private void drawCell(Cell cell) {
		double x = cell.getCol() * cellSize;
		double y = cell.getRow() * cellSize;
		
		if(cell.isHit()) {
			gc.setFill(Color.RED);
		}
		
		else if(cell.isShip() && !cell.isHidden()) {
			gc.setFill(Color.SLATEGRAY);
		}
		
		else if(cell.isDirection()) {
			gc.setFill(Color.PINK);
		}
		
		else {
			gc.setFill(Color.LIGHTBLUE);
		}
		
		gc.fillRect(x, y, cellSize, cellSize);
		
		
		if(cell.isMiss()) {
			drawMiss(gc, cell.getCol(), cell.getRow(), cellSize);
		}
			
		if (cell.getRow() == hoveredRow && cell.getCol() == hoveredCol && cell.isHidden()) {
		    gc.setFill(Color.LIGHTCYAN); // or translucent color
		    gc.fillRect(x, y, cellSize, cellSize);
		}
		
		gc.setStroke(Color.BLACK);
		gc.strokeRect(x, y, cellSize, cellSize);
	}
	
	private void drawMiss(GraphicsContext gc, int col, int row, double cellSize) {
		double radius = 5;
		double centerX = col * cellSize + cellSize / 2;
		double centerY = row * cellSize + cellSize / 2;
		
		gc.setFill(Color.BLACK);
		gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
	}
	
	private void handleMouseMoved(MouseEvent event) {
		if (grid.isActive()) {
			int col = (int) (event.getX() / cellSize);
			int row = (int) (event.getY() / cellSize);
			
			if(hoveredCol != col || hoveredRow != row) {
				if(hoveredCol >= 0 && hoveredRow >= 0) {
		            drawCell(grid.getGrid()[hoveredRow][hoveredCol]);
		        }
				
				hoveredCol = col;
				hoveredRow = row;
				
				if(grid.getGrid()[row][col].isHidden()) {
				drawCell(grid.getGrid()[row][col]);
				}
			}
		}
	}
	
	private void handleMouseClicked(MouseEvent event) {
		int col = (int) (event.getX() / cellSize);
		int row = (int) (event.getY() / cellSize);
		
		if(grid.isActive()) {
			clickListener.accept(new ClickData(row, col));
		}
	}
	
	private void handleMouseExited(MouseEvent event) {
		hoveredCol = -1;
		hoveredRow = -1;
	}
	
	public void setClickListener(Consumer<ClickData> listener) {
		clickListener = listener;
	}
}
