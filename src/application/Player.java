package application;


class Player {
	private String name;
	private boolean myTurn = false;
	private Grid myShips = new Grid(true, false);
	private Grid myShots = new Grid(false, true);
	
	Player() {}
	
	// Getters
	Grid getMyShips() {
		return myShips;
	}
	
	Grid getMyShots() {
		return myShots;
	}
}

	
