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
	void doesCurrentTargetExistTest() {
		ArrayList<Ship> fleet = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			Ship ship = new Ship();
			fleet.add(ship);
		}
		fleet.get(2).setDamaged(true);
		
		assertTrue(Utils.doesCurrentTargetExist(fleet));
	}
	
	@Test
	void getHitCellsTest() {
		Ship ship = new Ship();
		for(int i = 0; i < 5; i++) {
			Cell cell = new Cell(6, i);
			ship.getHull().add(cell);
		}
		ship.getHull().get(1).setHitTrue();
		ship.getHull().get(2).setHitTrue();
		ArrayList<Cell> cells = Utils.getHitCells(ship);
		assertEquals(2, cells.size());
	}
	
	@Test
	void isDamagedShipVertical() {
		ArrayList<Cell> vertical = new ArrayList<>();
		ArrayList<Cell> horizontal = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			Cell cellvert = new Cell(4,i);
			vertical.add(cellvert);
			Cell cellhor = new Cell(i, 3);
			horizontal.add(cellhor);
		}
		
		assertTrue(Utils.isDamagedShipVertical(vertical));
		assertFalse(Utils.isDamagedShipVertical(horizontal));
	}
	
	@Test
	void destroyCurrentTargetTest() {
	Grid shotGrid = new Grid();
	shotGrid.setGrid();
	Ship shipvert = new Ship();
	Ship shiphor = new Ship();
	
	for(int i = 0; i < 4; i++) {
		Cell cell = Utils.getCellByCoords(shotGrid, 4, i);
		cell.setShipTrue();
		shipvert.getHull().add(cell);
	}
	shipvert.setDamaged(true);
	
	for(int i = 0; i < 4; i++) {
		Cell cell = Utils.getCellByCoords(shotGrid, i, 7);
		cell.setShipTrue();
		shiphor.getHull().add(cell);
	}
	shiphor.setDamaged(true);
	
	ArrayList<Cell> vertical = new ArrayList<>();
	ArrayList<Cell> horizontal = new ArrayList<>();
	
	for(int i = 0; i < 2; i++) {
		Cell cell = new Cell();
		cell = shipvert.getHull().get(i);
		cell.setHitTrue();
		vertical.add(cell);
	}
	for(int i = 0; i < 2; i++) {
		Cell cell = new Cell();
		cell = shiphor.getHull().get(i);
		cell.setHitTrue();
		horizontal.add(cell);
	}
	Cell target;
	target = Utils.destroyCurrentTarget(shotGrid, vertical, Utils.isDamagedShipVertical(vertical));
	System.out.println("Vert ship");
	for(Cell cell : shipvert.getHull()) {
		System.out.println(cell.getCol() + ", " + cell.getRow() + ", " + cell.isHit());
	}
	System.out.println("Vert ship on grid");
	for(Cell cell : shipvert.getHull()) {
		Cell gridcell = Utils.getCellByCoords(shotGrid, cell.getCol(), cell.getRow());
		System.out.println(gridcell.getCol() + ", " + gridcell.getRow() + gridcell.isHit());
	}
	System.out.println("Target: " + target.getCol() + ", " + target.getRow());
	assertTrue(shipvert.getHull().contains(target) && !target.isHit());
	target = Utils.destroyCurrentTarget(shotGrid, horizontal, Utils.isDamagedShipVertical(horizontal));
	System.out.println("Hor ship");
	for(Cell cell : shiphor.getHull()) {
		System.out.println(cell.getCol() + ", " + cell.getRow() + ", " + cell.isHit());
	}
	System.out.println("Target: " + target.getCol() + ", " + target.getRow());
	assertTrue(shiphor.getHull().contains(target) && !target.isHit());
	}
	
	/*@Test
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
	*/
	/*@Test
	void blockCellsTest() {
		Grid grid = new Grid();
		ArrayList<Cell> blockedCells = new ArrayList<>();
		grid.setGrid();
		Ship ship = new Ship();
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 1));
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 2));
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 3));
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 4));
		ship.getHull().add(Utils.getCellByCoords(grid, 4, 5));
		
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
	*/
	/*@Test
	void getPossibleTargetsTest() {
		Grid grid = new Grid();
		grid.setGrid();
		
		ArrayList<Cell> targets = new ArrayList<>();
		targets = Utils.getPossibleTargets(grid);
		for(Cell cell : targets) {
		System.out.println(cell.getCol() + ", " + cell.getRow());
		}
		assertTrue(targets.size() >0);
		
	}*/
	
	/*@Test
	void shootNewTargetTest() {
		PlayerBot player = new PlayerBot();
		Cell target = new Cell();
		target = Utils.shootNewTarget(player.getShotGrid(), Utils.getPossibleTargets(player.getShotGrid()));
		System.out.println("Target: " + target.getCol() + ", " + target.getRow());
		assertTrue(target != null);
	}*/

}
