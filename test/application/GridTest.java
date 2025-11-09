package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class GridTest {

	@Test
	void setGridTest() {
		Grid grid = new Grid();
		int gridCellCount = 0;
		
	// Check grid size
		assertEquals(100, grid.getGrid().size());
		
	// Check coordinates for each cell in the grid
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					assertEquals(row, grid.getGrid().get(gridCellCount).getRow());
					assertEquals(col, grid.getGrid().get(gridCellCount).getCol());
					
					System.out.println(col + " " + row + " " + gridCellCount);
					
					gridCellCount++;
				}
			}
	}

}
