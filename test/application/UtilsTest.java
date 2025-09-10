package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class UtilsTest {

	@Test
	void randomCellSelectorTest() {
		Grid grid = new Grid();
		Cell randCell = new Cell();
		ArrayList<Integer> cellFilter = new ArrayList<>();
		grid.setGrid();
		for(int i = 0; i < 100; i ++) {
			cellFilter.add(i);
		}
		
		for (int i = 0; i < 100; i++) {
			randCell = Utils.randomCellSelector(grid,  cellFilter);
			assertTrue((randCell.getCol() >= 0 && randCell.getCol() <= 9) && (randCell.getRow() >= 0 && randCell.getRow() <=9));
			System.out.println(randCell.getCol() + ", " + randCell.getRow());
		}
		
	}
	
	@Test
	void getCellByCoordsTest() {
		Grid grid = new Grid();
		Cell cell = new Cell();
		int col = 3;
		int row = 5;
		grid.setGrid();
		
		cell = Utils.getCellByCoords(grid, col, row);
		assertEquals(col, cell.getCol());
		assertEquals(row, cell.getRow());
	}
	
	@Test
	void getShipDirectionTest() {
		Grid grid = new Grid();
		Cell cell = new Cell();
		grid.setGrid();
		
		
		// Ship size 5 and 4 possible directions, no blocked
		cell = Utils.getShipDirection(grid, 4, 4, 5);
		assertTrue((cell.getCol() == 0 && cell.getRow() == 4) || (cell.getCol() == 4 && cell.getRow() == 0) || 
				(cell.getCol() == 4 && cell.getRow() == 8) || (cell.getCol() == 8 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 3 possible directions, no left, no blocked
		cell = Utils.getShipDirection(grid, 0, 4, 5);
		assertTrue((cell.getCol() == 0 && cell.getRow() == 0) || (cell.getCol() == 4 && cell.getRow() == 4) || 
				(cell.getCol() == 0 && cell.getRow() == 8));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 3 possible directions, no right, no blocked
		cell = Utils.getShipDirection(grid, 8, 4, 5);
		assertTrue((cell.getCol() == 8 && cell.getRow() == 0) || (cell.getCol() == 8 && cell.getRow() == 8) || 
				(cell.getCol() == 4 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 3 possible directions, no bottom, no blocked
		cell = Utils.getShipDirection(grid, 4, 9, 5);
		assertTrue((cell.getCol() == 0 && cell.getRow() == 9) || (cell.getCol() == 8 && cell.getRow() == 9) || 
				(cell.getCol() == 4 && cell.getRow() == 5));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 3 possible directions, no up, no blocked
		cell = Utils.getShipDirection(grid, 4, 0, 5);
		assertTrue((cell.getCol() == 0 && cell.getRow() == 0) || (cell.getCol() == 8 && cell.getRow() == 0) || 
				(cell.getCol() == 4 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		

		// Ship size 5 and 2 possible directions, no up, no left, no blocked
		cell = Utils.getShipDirection(grid, 0, 0, 5);
		assertTrue((cell.getCol() == 4 && cell.getRow() == 0) || (cell.getCol() == 0 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 2 possible directions, no up, no right, no blocked
		cell = Utils.getShipDirection(grid, 8, 0, 5);
		assertTrue((cell.getCol() == 4 && cell.getRow() == 0) || (cell.getCol() == 8 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 2 possible directions, no bottom, no right, no blocked
		cell = Utils.getShipDirection(grid, 8, 9, 5);
		assertTrue((cell.getCol() == 4 && cell.getRow() == 9) || (cell.getCol() == 8 && cell.getRow() == 5));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 2 possible directions, no bottom, no left, no blocked
		cell = Utils.getShipDirection(grid, 0, 9, 5);
		assertTrue((cell.getCol() == 0 && cell.getRow() == 5) || (cell.getCol() == 4 && cell.getRow() == 9));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 1 possible direction, left, up and bottom are blocked
		Utils.getCellByCoords(grid, 4, 0).setBlockedTrue();
		Utils.getCellByCoords(grid, 0, 4).setBlockedTrue();
		Utils.getCellByCoords(grid, 4, 8).setBlockedTrue();
		cell = Utils.getShipDirection(grid, 4, 4, 5);
		assertTrue((cell.getCol() == 8 && cell.getRow() == 4));
		System.out.println("*" + cell.getCol() + ", " + cell.getRow());
		
		// Ship size 5 and 0 possible directions, all are blocked
		Utils.getCellByCoords(grid, 4, 0).setBlockedTrue();
		Utils.getCellByCoords(grid, 0, 4).setBlockedTrue();
		Utils.getCellByCoords(grid, 4, 8).setBlockedTrue();
		Utils.getCellByCoords(grid, 8, 4).setBlockedTrue();
		cell = Utils.getShipDirection(grid, 4, 4, 5);
		assertTrue(cell == null);
	}	
	
	@Test
	void cellExistsTest() {
		assertTrue(Utils.cellExists(0, 9));
		assertTrue(Utils.cellExists(9, 0));
		assertTrue(Utils.cellExists(0, 0));
		assertTrue(Utils.cellExists(9, 9));
		assertTrue(Utils.cellExists(4, 5));
		assertFalse(Utils.cellExists(-1, 10));
		assertFalse(Utils.cellExists(-1, 5));
		assertFalse(Utils.cellExists(4, 10));
	}
	
	@Test
	void blockCellsTest() {
		Grid grid = new Grid();
		ArrayList<Cell> blockedCells = new ArrayList<>();
		grid.setGrid();
		Ship ship = new Ship();
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 6));
		ship.getHull().add(Utils.getCellByCoords(grid, 9, 6));
		ship.getHull().add(Utils.getCellByCoords(grid, 5, 6));
		ship.getHull().add(Utils.getCellByCoords(grid, 6, 6));
		ship.getHull().add(Utils.getCellByCoords(grid, 7, 6));
		ship.getHull().add(Utils.getCellByCoords(grid, 8, 6));
		
		Utils.blockCells(grid, ship);
		for (Cell cell : grid.getGrid()) {
			if (cell.isBlocked()) {
				blockedCells.add(cell);
				System.out.println(cell.getCol() + ", " + cell.getRow());
			}
		}
		
		assertEquals(12, blockedCells.size());
		for (Cell cell : blockedCells) {
			assertTrue((cell.getCol() >= 3 && cell.getCol() <= 5) && (cell.getRow() >= 1 && cell.getRow() <= 5));
		}
	}
	
	@Test
	void isDirectionTest() {
		Grid grid = new Grid();
		grid.setGrid();
		Utils.getCellByCoords(grid, 8, 3).setBlockedTrue();
		assertFalse(Utils.isDirection(grid, Utils.getCellByCoords(grid, 8, 4), 4, 'u'));
		
	}

}
