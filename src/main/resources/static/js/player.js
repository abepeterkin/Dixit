function Player(id, color, isStoryTeller, game) {
  this.id = id;
  this.color = color;
  this.isStoryTeller = isStoryTeller;
  this.hand = new Array(game.rules.numCards);
  this.game = game;
  // TODO: add a vote so that a player is linked to its vote card
}

Player.prototype.refresh = function(canvas) {
  var card;
  for (var i = 0; i < this.hand.length; i++) {
    card = this.hand[i];
    card.x = i * (canvas.width / 9) + (canvas.width / 4);
    card.y = canvas.height - (canvas.height / 4);
    card.resize(canvas);
  }
}
// makes player the storyteller;
Player.prototype.makeStoryTeller = function() {
  this.isStoryTeller = true;
}

// hand should be an array of card objects
Player.prototype.addHand = function(hand) {
  this.hand = hand;
}

// add card to player’s hand
Player.prototype.addCard = function(card) {
  if (this.hand.length < this.game.rules.numCards) {
    this.hand.push(card)
  }
}

// removes a card from player’s hand
Player.prototype.removeCard = function(id) {
  for (var i = 0; i < this.hand.length; i++) {
    if (this.hand[i] === id) {
      this.hand.splice(i, 1);
    }
  }
}

// draws the player’s hand on canvas
Player.prototype.drawHand = function(ctx) {
  for (var i = 0; i < this.hand.length; i++) {
    this.hand[i].draw(ctx);
  }
}
// sends the clue to the board if it is the storyteller
Player.prototype.sendClue = function(clue) {
  // TODO:
}
// cast player’s vote
Player.prototype.vote = function(cardId) {
  // TODO:
}