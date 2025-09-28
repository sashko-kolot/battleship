package application;

import javafx.application.Platform;

class Game {
	private PlayerBot bot  = new PlayerBot();
	private PlayerHuman human =  new PlayerHuman();
	private Player currentPlayer;
		
	public void playGame() {
		human.setFleet(bot, ()-> {
			
			bot.setFleet(human, ()-> {
		
		currentPlayer = (Utils.generateRandomInt(0,1) == 0) ? bot : human;
        nextTurn();
			});
		
		});
	}
		
		private void nextTurn() {
		    if (isWin()) {
		        endGame();
		        return;
		    }
		    
		    Player opponent = (currentPlayer == human) ? bot : human;


	        currentPlayer.shoot(opponent, hit -> {
	            if (!isWin()) {
	            	if(currentPlayer instanceof PlayerBot)Platform.runLater(()-> ViewController.updateShipGridPane(opponent.getShipGrid())); 
	            	if (!hit) currentPlayer = opponent;
	                Platform.runLater(this::nextTurn);
	            } else {
	                endGame();
	            }
	        });
		}
		
	    private boolean isWin() {
	        return (!bot.getFleet().isEmpty() && bot.getFleet().stream().allMatch(Ship::isDestroyed))
	            || (!human.getFleet().isEmpty() && human.getFleet().stream().allMatch(Ship::isDestroyed));
	    }
	    
	     private void endGame()
	     {
	    	 ViewController.deactivateShotGrid(); 
	     }
}
