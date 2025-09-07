package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlayerBotTest {
	
	@Test
	void getCellByCoordsTest() {
		PlayerBot bot = new PlayerBot();
		bot.getShipGrid().setGrid();
		Cell cell = Utils.getCellByCoords(bot.getShipGrid(), 3, 5);
		assertEquals(3, cell.getCol());
		assertEquals(5, cell.getRow());
	}
	
	@Test
	void getRandomCellTest() {
		PlayerBot bot = new PlayerBot();
		bot.getShipGrid().setGrid();
		Cell randCell = new Cell();
		randCell = Utils.getRandomCell(bot.getShipGrid(), 0, 10);
		assertTrue((randCell.getCol() >= 0 && randCell.getCol() < 10) && (randCell.getRow() >= 0 && randCell.getRow() < 10));
		
	}

	@Test
	void setFleetTest() {
		
	}

}
