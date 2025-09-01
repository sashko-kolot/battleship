package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CellTest {

	@Test
	void createCellWithCoords() {
		
		Cell cell = new Cell(2, 3);
		assertEquals(2, cell.getCol());
		assertEquals(3, cell.getRow());
	 //System.out.println("Cell coordinates: " + cell.getCol() + ", " + cell.getRow());	
	}
	
	@Test
	void isShipTest() {
		Cell cell = new Cell();
		assertEquals(false, cell.isShip());
	}
	
	@Test
	void getCellTest() {
		Cell cell = new Cell();
		assertEquals(cell, cell.getCell());
	}
	
	@Test
	void isBlockedTest() {
		Cell cell = new Cell();
		assertEquals(false, cell.isBlocked());
		
	}
	
	@Test
	void isHitTest() {
		Cell cell = new Cell();
		assertEquals(false, cell.isHit());
	}
	
	@Test
	void isMissTest() {
		Cell cell = new Cell();
		assertEquals(false, cell.isMiss());
	}
	
	@Test
	void setShipTrueTest() {
		Cell cell = new Cell();
		cell.setShipTrue();
		assertEquals(true, cell.isShip());
	}
	
	@Test
	void setBlockedTrueTest() {
		Cell cell = new Cell();
		cell.setBlockedTrue();
		assertEquals(true, cell.isBlocked());
	}
	
	@Test
	void setHitTrueTest() {
		Cell cell = new Cell();
		cell.setHitTrue();
		assertEquals(true, cell.isHit());
	}
	
	@Test
	void setMissTrueTest() {
		Cell cell = new Cell();
		cell.setMissTrue();
		assertEquals(true, cell.isMiss());
	}
}