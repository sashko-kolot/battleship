package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.Test;

class PlayerBotTest {
	
	@Test
	void placeShipTest() {
		PlayerBot bot = new PlayerBot();
		bot.getShipGrid().setGrid();
		System.out.println(bot.getShipGrid().getGrid().size());
		for (int i = 5; i > 1; i--) {
			Ship ship = bot.placeShip(bot.getShipGrid(), i);
			System.out.println(ship.getHull().size());
			assertEquals (i, ship.getHull().size());
			
			for (Cell cell : ship.getHull()) {
			assertTrue(cell.isShip());
			assertTrue(cell.isBlocked());
			assertTrue((cell.getCol() == ship.getHull().getFirst().getCol()) || (cell.getRow() == ship.getHull().getFirst().getRow()));
			}
		}
	}

	@Test
	void setFleetTest() {
		PlayerBot bot = new PlayerBot();
		PlayerBot opponent = new PlayerBot();
		bot.getShipGrid().setGrid();
		bot.setFleet(opponent);
		// debug start
		System.out.println("Ships");
		for (Ship ship : bot.getFleet()) {
			for (Cell cell : ship.getHull()) {
				System.out.println(cell.getCol() + ", " + cell.getRow());
			}
			
		}
		/*System.out.println("Blocked cells");
		for (Cell cell : bot.getShipGrid().getGrid()) {
			if (cell.isBlocked()) {
				System.out.println(cell.getCol() + ", " + cell.getRow());
			}
		}*/
		
		// debug end
		assertEquals(5, bot.getFleet().size());
		assertEquals(5, bot.getFleet().get(0).getHull().size());
		assertEquals(4, bot.getFleet().get(1).getHull().size());
		assertEquals(3, bot.getFleet().get(2).getHull().size());
		assertEquals(3, bot.getFleet().get(3).getHull().size());
		assertEquals(2, bot.getFleet().get(4).getHull().size());
		
		
		for (Ship ship : bot.getFleet()) {
			
			for (Cell cell : ship.getHull()) {
				assertTrue(cell.isShip());
				assertTrue(cell.isBlocked());
				assertTrue((cell.getCol() == ship.getHull().getFirst().getCol()) || (cell.getRow() == ship.getHull().getFirst().getRow()));
				}
		}
		
		assertEquals(bot.getShipGrid().getGrid(), opponent.getShotGrid().getGrid());
	}

}
