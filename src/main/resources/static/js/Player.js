function Player(id, color, isStoryTeller, game){
	this.id = id;
	this.color = color;
	this.isStoryTeller = isStoryTeller;
	this.hand = new Array(game.rules.numCards);
	this.game = game;
}

Player.prototype.makeStoryTeller = function(){
	this.isStoryTeller = true;
}

//hand should be an array of card objects
Player.prototype.addHand = function(hand){
	this.hand = hand;
}
Player.prototype.addCard = function(card){
	if(this.hand.length < this.game.rules.numCards){
		this.hand.push(card)
	}
}

Player.prototype.removeCard = function(id){
	for(var i=0; i < this.hand.length; i++){
		if(this.hand[i] === id){
			this.hand.splice(i, 1);
		}
	}
}

Player.prototype.drawHand = function (ctx){
	for(var i = 0; i< this.hand.length; i++){
		this.hand[i].draw(ctx);
		}
}