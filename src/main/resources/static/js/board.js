//should hold to array of players to display their score and color
function Board(players){
	this.players = players;
	this.scores = {}; // maps of playerid to score
	this.cards = []//cards currently at play
}

//draws the simplified board with the appropriate scores, 
Board.prototype.drawSmall = function(ctx){

}

//draws the big board with the appropriate scores, 
Board.prototype.drawBig = function(ctx){

}
//to update the score
Board.prototype.setScore = function(score){
	
}

//if we only need to change one player’s score, makes animation
Board.prototype.updateScore = function(ctx, playerId, newScore){

}

//resets the score and positions all players at the beginning
Board.prototype.reset = function(ctx){

}
//sends current cards to the trash, animation.
Board.prototype.trash = function(ctx){

}