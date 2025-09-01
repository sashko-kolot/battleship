package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

class ShipTest {

	@Test
	void buildShipTest() {
		Cell cell1 = new Cell(1, 1);
		Cell cell2 = new Cell(1, 2);
		Cell cell3 = new Cell(1, 3);
		
		ArrayList<Cell> hull = new ArrayList<>(List.of(cell1, cell2 ,cell3));
		
		Ship ship = new Ship(3, hull);
		
		assertEquals(3, ship.getSize());
		assertEquals(1, ship.getHull().get(0).getCol());
		assertEquals(1, ship.getHull().get(0).getRow());
		assertEquals(1, ship.getHull().get(1).getCol());
		assertEquals(2, ship.getHull().get(1).getRow());
		assertEquals(1, ship.getHull().get(2).getCol());
		assertEquals(3, ship.getHull().get(2).getRow());
	}
	
	@Test
	void isKilledTest() {
		Cell cell1 = new Cell(1, 1);
		Cell cell2 = new Cell(1, 2);
		Cell cell3 = new Cell(1, 3);
		
		ArrayList<Cell> hull = new ArrayList<>(List.of(cell1, cell2 ,cell3));
		
		Ship ship = new Ship(3, hull);
		
		assertEquals(false, ship.isKilled());
	}
	
	@Test
	void setKilledTrueTest() {
		Cell cell1 = new Cell(1, 1);
		Cell cell2 = new Cell(1, 2);
		Cell cell3 = new Cell(1, 3);
		
		ArrayList<Cell> hull = new ArrayList<>(List.of(cell1, cell2 ,cell3));
		
		Ship ship = new Ship(3, hull);
		
		ship.setKilledTrue();
		assertEquals(true, ship.isKilled());
		
	}

}
