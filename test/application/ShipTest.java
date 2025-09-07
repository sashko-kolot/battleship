package application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ShipTest {
	
	@Test
	void isDestroyedTest() {
		
		Ship ship = new Ship();
		
		assertFalse(ship.isDestroyed());
	}
	
	@Test
	void setDestroyedTrueTest() {
		
		Ship ship = new Ship();
		
		ship.setDestroyedTrue();
		assertTrue(ship.isDestroyed());
	}
}
