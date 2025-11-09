package application;

import javafx.scene.paint.Color;


class Game {
	private Player player1  = new PlayerBot();
	private Player player2 =  new PlayerHuman();
	private Player currentPlayer = player1;
	private Player opponent = player2;
	
	enum GameState{
		PLACE_SHIPS,
		BATTLE,
		GAME_OVER
	}
	GameState currentState = GameState.PLACE_SHIPS;
	
	public void playGame() {
				checkGameState();
	}
	
	private void checkGameState() {
			
			switch(currentState) {
			case PLACE_SHIPS:
				currentPlayer.setFleet(opponent, () -> {
					if(currentPlayer.getFleet().size() == 5 && opponent.getFleet().size() == 5) {
						firstToShoot();
						currentState = GameState.BATTLE;
					}
					toggleTurn();
					checkGameState();
				});
				break;
			
			case BATTLE:
					currentPlayer.shoot(opponent, hit -> {
						if(!isWin()) { 
							if(!hit) {
								toggleTurn();
								checkGameState();
							} else {
								checkGameState();
							}
						}else {
							currentState = GameState.GAME_OVER;
							checkGameState();
						}
						});
				break;
					
			case GAME_OVER:
				gameOver();
				}
	}
	
	private void toggleTurn() {
		Player temp = currentPlayer;
		currentPlayer = opponent;
		opponent = temp;
	}
	
	private void firstToShoot() {
		int turn = Utils.generateRandomInt(1, 2);
		if(turn == 1) {
			currentPlayer = player1;
			opponent = player2;
		} else {
			currentPlayer = player2;
			opponent = player1;
		}
	}
	
	  private boolean isWin() {
	        return (player1.getFleet().stream().allMatch(Ship::isDestroyed)) || (player2.getFleet().stream().allMatch(Ship::isDestroyed));
	    }
	  
	  private boolean humanWins() {
	    	return player1.getFleet().stream().allMatch(Ship::isDestroyed)
	    	           && player2.getFleet().stream().anyMatch(ship -> !ship.isDestroyed());
	    }
	    
	   private void gameOver(){
	    	 if(humanWins()) {
	    		 View.setMessage("Victory!", Color.GREEN);
		     } else {
		    	 View.setMessage("Defeat!", Color.RED);
		     	}
	    	 View.setMessageVisibility(true);
	     }
	  
	  public Player getPlayer2() {
		  return player2;
	  }
}