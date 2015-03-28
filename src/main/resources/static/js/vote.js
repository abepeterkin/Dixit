//at a minimum a vote should contain x and y properties so that they can be dragged around
function Vote(x, y){
	this.x = x;
	this.y = y;
}

Vote.prototype.setCard = function(card){
	//TODO: set the card this vote should be linked to.
}

Vote.prototype.reset = function(card) {
	//TODO: return voting object to player (animation) and clear the this.card property
}