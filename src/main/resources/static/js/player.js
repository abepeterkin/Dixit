function Player(options) {
  this.id = options.id;
  this.name = options.chatName;
  this.color = Player.getColor(options.color);
  this.isStoryTeller = options.isStoryTeller;
  this.hand = [];
  if (options.hand !== undefined) {
    this.setHandFromAjax(options.hand);
  }
  this.img = {};
  this.img.idle = new Image();
  this.img.idle.src = "/images/rabbits/" + options.color.toLowerCase() + "/idle.png";
  this.idle = new Sprite({
    width : 1024,
    height : 128,
    image : this.img.idle,
    numberOfFrames : 29,
    ticksPerFrame : 3,
    numberOfCols : 8
  })
  this.score = options.score;
  this.hasVoted = options.hasVoted;
  // TODO: add a vote so that a player is linked to its vote card
}

Player.getColor = function(color){
	switch(color){
	case 'Blue':
		return "#4967E2";
	case 'Pink':
		return "#DA8AE7";
	case 'Red':
		return "#CB726D";
	case 'White':
		return "#D7D7D7";
	case 'Yellow':
		return "#DAD650";
	case 'Green':
		return "#83C653";
	}
}

Player.prototype.refresh = function(canvas) {
  var card;
  for (var i = 0; i < this.hand.length; i++) {
    card = this.hand[i];
    card.x = i * (canvas.width / 9) + (canvas.width / 4);
    card.y = canvas.height - (canvas.height / 5);
    card.resize(canvas);
  }
}
// makes player the storyteller;
Player.prototype.makeStoryTeller = function() {
  this.isStoryTeller = true;
}

Player.prototype.setHandFromAjax = function(cardOptionsList) {
  var index = 0;
  while (index < cardOptionsList.length) {
    var tempCardOptions = cardOptionsList[index];
    tempCardOptions.visible = true;
    tempCardOptions.inHand = true;
    this.addCard(new Card(tempCardOptions));
    index++;
  }
  // board.adjustCardsPos();
}

// hand should be an array of card objects
Player.prototype.addHand = function(hand) {
  this.hand = hand;
}

// add card to player’s hand
Player.prototype.addCard = function(card) {
  this.hand.push(card)
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

// draw the player idle standing by
Player.prototype.drawIdle = function(board, index) {
  if (this.img.idle.complete) {
    board.ctx.drawImage(this.img.idle, 0, 0, 128, 128, board.canvas.width
        - (board.canvas.width / 8),
        (index * 128 / 6) + board.canvas.height / 4, 128, 128);
  } else {
    this.img.idle.onload = function() {
      board.ctx.drawImage(board.img.idle, 0, 0, 128, 128, board.canvas.width
          - (board.canvas.width / 20), 0, 128, 128);

    }
  }
}

// submits a line to the chat box
Player.prototype.addChatLine = function(message) {
  console.log("gets called");
  /*
   * var color = "blue"; var name = "Abe";
   */
  $("#text").html(
      text.html() + "<p style=\"color:" + this.color + "\">" + this.name + ": "
          + message + "<\p>");
  // messageText.val("");
}

Player.prototype.drawSmall = function(board, index) {
  var img = this.img.idle;
  var sx = 0;
  var sy = 0;
  var sw = 128;
  var sh = 128;
  var w = board.canvas.width / 6;
  var h = board.canvas.height / 5;
  var x = board.canvas.width - w;
  var y = (index * h / 2.2) - h / 5;
  var player = this;
  if (board.game.currPhase == board.game.phases["Voting"]) {
	  var tokenX = x + w / 4;
	  var tokenY = y + h / 1.9;		
	  if (player.hasVoted) {  
		  this.drawVoteToken(board.ctx, tokenX, tokenY);
	  }
	  if (player.isStoryTeller) {	  
		  this.drawStoryToken(board.ctx, tokenX, tokenY);
	  }
  }
  if (img.complete) {
    board.ctx.drawImage(img, sx, sy, sw, sh, x, y, w, h);
    board.ctx.font = "30px Georgia"; // make this responsive
    board.ctx.fillStyle = "black";
    board.ctx.fillText(player.score, x + w / 1.5, y + h / 1.7);
  } else {
    img.onload = function() {
      board.ctx.drawImage(img, sx, sy, sw, sh, x, y, w, h);
      board.ctx.font = "30px Georgia"; // make this responsive
      board.ctx.fillStyle = "black";
      board.ctx.fillText(player.score, x + w / 1.5, y + h);
    }
  }
}

Player.prototype.drawStoryToken = function(ctx, x, y) {
  	var voteWidth = board.ctx.canvas.width / 80;
  	ctx.beginPath();
  	ctx.arc(x, y,
        voteWidth, 0, 2 * Math.PI);
  	ctx.fillStyle = "gold";
  	ctx.fill();
  	ctx.beginPath();
  	ctx.arc(x, y,
        voteWidth/1.2, 0, 2 * Math.PI);
  	ctx.fillStyle = "white";
  	ctx.fill();
  	ctx.beginPath();
  	ctx.arc(x, y,
        voteWidth/1.4, 0, 2 * Math.PI);
  	ctx.fillStyle = "gold";
  	ctx.fill();
  	//golden S in center?
}

Player.prototype.drawVoteToken = function(ctx, x, y) {
	  	var voteWidth = ctx.canvas.width / 80;
	  	ctx.beginPath();
	  	ctx.arc(x, y,
	        voteWidth, 0, 2 * Math.PI);
	  	ctx.fillStyle = this.color;
	  	ctx.fill();
	  	ctx.beginPath();
	  	ctx.arc(x, y,
	        voteWidth/1.2, 0, 2 * Math.PI);
	  	ctx.fillStyle = "white";
	  	ctx.fill();
	  	ctx.beginPath();
	  	ctx.arc(x, y,
	        voteWidth/1.4, 0, 2 * Math.PI);
	  	ctx.fillStyle = this.color;
	  	ctx.fill();
	}
