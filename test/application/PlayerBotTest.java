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
	void botShootTest() {
		PlayerBot bot = new PlayerBot();
		PlayerHuman opponent = new PlayerHuman();
		Ship ship3vert = new Ship();
		Ship ship2vert = new Ship();
		Ship ship4hor = new Ship();
		
		//Construct ship3vert (damaged) and add to opponent's fleet
		Cell cell0ship3vert = new Cell(0, 0);
		cell0ship3vert.setShipTrue();
		cell0ship3vert.setBlockedTrue();
		cell0ship3vert.setHitTrue();
		ship3vert.getHull().add(cell0ship3vert);
		Cell cell1ship3vert = new Cell(0, 2);
		cell1ship3vert.setShipTrue();
		cell1ship3vert.setBlockedTrue();
		ship3vert.getHull().add(cell1ship3vert);
		Cell cell2ship3vert = new Cell(0, 1);
		cell2ship3vert.setShipTrue();
		cell2ship3vert.setBlockedTrue();
		cell2ship3vert.setHitTrue();
		ship3vert.getHull().add(cell2ship3vert);
		ship3vert.setDamaged(true);
		opponent.getFleet().add(ship3vert);
		
		//Construct ship2vert (not damaged) and add to opponent's fleet
		Cell cell0ship2vert = new Cell(6, 2);
		cell0ship2vert.setShipTrue();
		cell0ship3vert.setBlockedTrue();
		ship2vert.getHull().add(cell0ship2vert);
		Cell cell1ship2vert = new Cell(6, 3);
		cell1ship2vert.setShipTrue();
		cell1ship2vert.setBlockedTrue();
		cell1ship2vert.setHitTrue();
		ship2vert.getHull().add(cell1ship2vert);
		opponent.getFleet().add(ship2vert);
		
		//Construct ship4hor (not damaged) and add to opponent's fleet
		Cell cell0ship4hor = new Cell(6, 9);
		cell0ship4hor.setShipTrue();
		cell0ship4hor.setBlockedTrue();
		cell0ship4hor.setHitTrue();
		ship4hor.getHull().add(cell0ship4hor);
		Cell cell1ship4hor = new Cell(9, 9);
		cell1ship4hor.setShipTrue();
		cell1ship4hor.setBlockedTrue();
		ship4hor.getHull().add(cell1ship4hor);
		Cell cell2ship4hor = new Cell(7, 9);
		cell2ship4hor.setShipTrue();
		cell2ship4hor.setBlockedTrue();
		cell2ship4hor.setHitTrue();
		Cell cell3ship4hor = new Cell(9, 9);
		cell3ship4hor.setShipTrue();
		cell3ship4hor.setBlockedTrue();
		ship4hor.getHull().add(cell3ship4hor);
		opponent.getFleet().add(ship4hor);
		
		//Update shotGrid
		for(Ship ship : opponent.getFleet()) {
			for(Cell cell : ship.getHull()) {
				Utils.getCellByCoords(bot.getShotGrid(), cell.getCol(), cell.getRow()).setShipTrue();
				Utils.getCellByCoords(bot.getShotGrid(), cell.getCol(), cell.getRow()).setBlockedTrue();
				if(cell.isHit()) {
					Utils.getCellByCoords(bot.getShotGrid(), cell.getCol(), cell.getRow()).setHitTrue();
				}
			}
		}
		//Case 1: ship3vert is damaged
		bot.shoot(opponent, hit->{});
		assertTrue(Utils.getCellByCoords(bot.getShotGrid(), 0, 2).isHit());
	}

	/*@Test
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
		}
		
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
	}*/

}
