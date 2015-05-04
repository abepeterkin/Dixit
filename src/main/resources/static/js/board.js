var mouseX;
var mouseY;
var dragHoldX;
var dragHoldY;
var timer;
var targetX;
var targetY;
var dragging;
var dragIndex;
var draggingCard;
var board;
var selectedCard = {};
var sentCard = false;
// should hold to array of game.players to display their score and color
function Board(options) {
  this.clientPlayer = game.players[sessionStorage.playerId]; // the player this
  // client
  this.game = options.game;
  this.cards = [];// cards currently at play
  this.canvas = document.getElementById(options.canvasId);
  this.canvas.width = (window.innerWidth
      || document.documentElement.clientWidth || document.body.clientWidth);
  this.canvas.height = (window.innerHeight
      || document.documentElement.clientHeight || document.body.clientHeight);
  this.ctx = this.canvas.getContext("2d");
  this.img = new Image();
  var numPlayers = game.rules.maxPlayers;
//  this.img.src = "/images/board.jpg";
  switch (numPlayers) {
  case 3:
	  this.img.src = "/images/board3Player.jpg";
	  break;
  case 4:
	  this.img.src = "/images/board4Player.jpg";
	  break;
  case 5:
	  this.img.src = "/images/board5Player.jpg";
	  break;
  case 6:
	  this.img.src = "/images/board6Player.jpg";
	  break;
  }
  board = this;
  this.icons = [];
  this.iconsMap = {};
  this.sendBtn = $('#send-card-btn');
  this.sendClueBtn = $('#send-clue-btn');
  this.clue = new Clue({
    x : this.canvas.width / 2
  });
  this.clue.makeBig(this.canvas);
  this.clueInput = $('#clueInput');
  this.cardModal = $('#cardModal');
  this.clueModal = $('#sendClueModal');
  this.smallBoard = false;
  this.modalContent = $('.modal-content');
  this.advanceBtn = $('#advance-btn');
}
// draws the entire game board, including client player's hand
Board.prototype.draw = function() {
  board.ctx.clearRect(0, 0, board.canvas.width, board.canvas.height);
  window.requestAnimationFrame(Board.prototype.draw);

  // TODO: draw clue
  // TODO: draw game.players
  if (!board.smallBoard) {
    board.drawBig();
  } else {
    board.drawSmall();
  }
}

// Draws important messages such as "Pick a story card"
// or "Press advance to next round".
Board.prototype.drawAlertMessage = function() {
  this.ctx.font = "30px Georgia";
  this.ctx.fillStyle = "#000000";
  if (this.game.currPhase == this.game.phases["StoryTeller"]
    && this.clientPlayer.isStoryTeller) {
    this.ctx.fillText("Pick a story card!", this.canvas.width / 2.1,
        this.canvas.height / 1.5);
  }
  if (this.game.currPhase == this.game.phases["Waiting"]
    && !this.advanceBtn.prop("disabled")) {
    var tempText1;
    var tempText2;
    if (this.game.playerHasWon()) {
      tempText1 = "Click view";
      tempText2 = "results button!";
    } else {
      tempText1 = "Click next";
      tempText2 = "round button!";
    }
    this.ctx.fillText(tempText1, this.canvas.width / 1.4,
        this.canvas.height / 1.5);
    this.ctx.fillText(tempText2, this.canvas.width / 1.4,
        this.canvas.height / 1.5 + 35);
  }
  
}

/*
 * Board.prototype.draws = function() { if (!this.smallBoard) {
 * window.requestAnimationFrame(Board.prototype.draws); var player; // \for (var
 * i = 0; i < board.game.players.length; i++) { player = board.game.players[0];
 * player.idle.update(); player.idle.render(board.ctx, player.id); } else {
 * this.drawSmall(); } // } }
 */
// draws the simplified board with the appropriate scores,
Board.prototype.drawSmall = function() {
  board.icons = [];
  board.iconsMap = {};
  var xMin = board.canvas.width - 20;
  var yMin = 20;
  var wMin = board.canvas.width / 100;
  var hMin = board.canvas.height / 400;
  board.ctx.fillStyle = "black";
  board.ctx.fillRect(xMin, yMin, wMin, hMin);
  board.ctx.fillRect(xMin + wMin / 2, yMin - wMin / 2, hMin, wMin);
  var ind = board.icons.push(new Icon({
    x : xMin,
    y : yMin,
    width : wMin,
    height : hMin,
    callback : function() {
      board.smallBoard = false;
      board.adjustCardsPos();
      board.clue.makeSmall(board);
    },
    name : "max"
  }))
  board.iconsMap['max'] = ind - 1;
  this.drawPlayersSmall();
  this.clientPlayer.drawHand(this.ctx);
  this.drawCards();
  this.clue.draw(this.ctx);
  this.drawAlertMessage();
}
// draws the scoreboard gets passed other objects to ensure
// background board always gets drawn first
Board.prototype.drawBig = function() {
  if (board.img.complete) {
    drawBigHelper();
  } else {
    board.img.onload = drawBigHelper;
  }
}
//TODO: maybe remove this function because it isn't being called,
//i took away the yellow highlighting.
Board.prototype.drawVote = function() {
  if (board.cardVoted) {
    board.cardVoted.highlight(board.ctx);
  }
}

var drawBigHelper = function() {
  board.icons = [];
  board.iconsMap = {};
  var img = board.img;
  var x = 0;
  var y = 0;
  var w = board.canvas.width;
  var h = board.canvas.height / 1.7;
  var player = board.clientPlayer
  var xMin = board.canvas.width - 20;
  var yMin = 20;
  var wMin = board.canvas.width / 100;
  var hMin = board.canvas.height / 400;
  board.ctx.drawImage(img, x, y, w, h);
  board.ctx.fillStyle = "black";
  board.ctx.fillRect(xMin, yMin, wMin, hMin);
  var ind = board.icons.push(new Icon({
    x : xMin,
    y : yMin,
    width : wMin,
    height : hMin,
    callback : function() {
      board.smallBoard = true;
      board.adjustCardsPos();
      board.clue.makeBig(board);
    },
    name : "min"
  }))
  board.iconsMap['min'] = ind - 1;
  player.drawHand(board.ctx);
  board.drawPlayersBig();
  board.drawCards();
  board.clue.draw(board.ctx);
  board.drawAlertMessage();
}
Board.prototype.drawPlayersBig = function() {
  var i = 0;
  var player;
  var nameToDisplay;
  for ( var id in board.game.players) {
    if (board.game.players.hasOwnProperty(id)) {
      player = board.game.players[id];
      player.idle.update();
      player.idle.render(board.ctx, i, player);
      board.ctx.fillStyle = player.color;
      board.ctx.font = "20px Georgia" // TODO: make this responsive
      if(player.isStoryTeller){
    	  nameToDisplay = player.name + " - Story Teller";
      } else {
    	  nameToDisplay = player.name;
      }
      board.ctx.fillText(nameToDisplay, 20, i * 20 + 20);

      i++;
    }
  }
}

Board.prototype.drawPlayersSmall = function() {
  var i = 0;
  var player;
  var nameToDisplay;
  for ( var id in this.game.players) {
    if (this.game.players.hasOwnProperty(id)) {
      player = board.game.players[id];
      this.game.players[id].drawSmall(this, i);
      board.ctx.fillStyle = player.color;
      board.ctx.font = "20px Georgia" // TODO: make this responsive
    	  if(player.isStoryTeller){
        	  nameToDisplay = player.name + " - Story Teller";
          } else {
        	  nameToDisplay = player.name;
          }
          board.ctx.fillText(nameToDisplay, 20, i * 20 + 20);
      i++;
    }
  }
}
// draws the cards that are at play in the board
Board.prototype.drawCards = function() {
  for (var i = 0; i < board.cards.length; i++) {
    board.cards[i].draw(board.ctx);
  }
}
// to update the score
Board.prototype.setScore = function(score) {

}

// resets the score and positions all game.players at the beginning
Board.prototype.reset = function() {

}
// sends current cards to the trash, animation.
Board.prototype.trash = function() {

}
Board.prototype.refresh = function() {
  this.canvas.width = (window.innerWidth
      || document.documentElement.clientWidth || document.body.clientWidth);
  this.canvas.height = (window.innerHeight
      || document.documentElement.clientHeight || document.body.clientHeight);
  this.clientPlayer.refresh(this.canvas);
  this.clue.refresh(this);
  this.adjustCardsPos();
}

Board.prototype.addGenericCard = function() {
  this.cards.push(new Card({
    visible : false,
    inHand : false
  }));
  board.adjustCardsPos();
}
Board.prototype.addCard = function(card) {
  this.cards.push(card);
  board.adjustCardsPos();
}
Board.prototype.adjustCardsPos = function() {
  if (board.cards.length >= 1) {
    if (board.smallBoard) {
      board.cards[0].x = board.canvas.width / 5;
      board.cards[0].y = board.canvas.height / 14;
      board.cards[0].makeBig(board.canvas);
    } else {
      board.cards[0].x = board.canvas.width / 25;
      board.cards[0].y = board.canvas.height / 1.7;
      board.cards[0].makeSmall(board.canvas);
    }
    var width = board.cards[0].width;
    var height = board.cards[0].height
    for (var i = 1; i < board.cards.length; i++) {
      if (board.smallBoard) {
        board.cards[i].makeBig(board.canvas);
        if (i < 3) {
          board.cards[i].x = board.cards[i - 1].x + width + width / 10;
          board.cards[i].y = board.cards[i - 1].y;
        } else {
          if (i == 3) {
            board.cards[i].x = board.cards[0].x;
            board.cards[i].y = board.cards[0].y + height + height / 10;
          } else {
            board.cards[i].x = board.cards[i - 1].x + width + width / 10;
            board.cards[i].y = board.cards[i - 1].y;
          }
        }
      } else {
        board.cards[i].makeSmall(board.canvas);
        board.cards[i].x = board.cards[i - 1].x + width + width / 1.5;
        board.cards[i].y = board.cards[i - 1].y;
      }
    }
  }
}

Board.prototype.displayFinalScores = function() {
  // TODO: display the final scores.
  alert("SCORES GO HERE!");
}

Board.prototype.addListeners = function() {
  // this.canvas.addEventListener("mousedown", mouseDownListener, false);
  this.canvas.addEventListener("click", mouseClickListener, false);
  this.canvas.addEventListener("mousemove", mouseMoveListener, false);
  $(window).on('resize', function(e) {
    board.refresh();
  })
  this.sendBtn.click(sendBtn);
  $('#clueBtn').click(function(e) {
    var card;
    for (var i = 0; i < board.clientPlayer.hand.length; i++) {
      card = board.clientPlayer.hand[i];
      board.clueModal.find('#card' + i)[0].src = card.frontImg.src;
    }
    board.clueModal.modal('show');
  })
  this.sendClueBtn.click(sendClue);
  $("img").mousedown(function() {
    return false;
  });
  this.clueInput.on('input', function() {
    if (board.clueInput.val().length == 0) {
      board.sendClueBtn.prop('disabled', true);
    } else {
      board.sendClueBtn.prop('disabled', false);
    }
  })
  $('#advancePhase').click(function() {
    board.game.nextPhase();
  })

  $('#carousel-example-generic').on('slid.bs.carousel', function(e) {
    board.clue.cardIndex = e.relatedTarget.children.item(0).value;
  })
  
  this.updateAdvanceBtn();
}

Board.prototype.updateAdvanceBtn = function() {
  this.advanceBtn.unbind("click");
  if (this.game.playerHasWon()) {
    board.advanceBtn.html("View Results!");
    this.advanceBtn.click(this.displayFinalScores);
  } else {
    this.advanceBtn.html("Next Round!");
    this.advanceBtn.click(function() {
      board.advanceBtn.prop('disabled', true);
      readyRequest(function(e) {
        console.log(e);
      });
    });
  }
}

Board.prototype.changePhase = function(phase) {
  //make sure the clue is up to date
	board.clue.text = '"'+board.game.currClue+'"';
	console.log(phase);
	this.updateAdvanceBtn();
  switch (phase) {
  case 'STORYTELLER':
	board.smallBoard = false;
	board.sendClueBtn.prop("disabled", true);
	board.adjustCardsPos();
	board.advanceBtn.attr("disabled", true);
  board.advanceBtn.css('display', 'none');
  board.advanceBtn.prop('disabled', true);
    //TODO: should the board start as small or big?
  	//board.smallBoard = true;
    //board.adjustCardsPos();
    if (!this.clientPlayer.isStoryTeller) {
      this.sendBtn.prop("disabled", true);
    }
    this.sendBtn.prop("disabled", true);
    break;
  case 'NONSTORYCARDS':
		board.advanceBtn.css('display', 'none');

    if (!this.clientPlayer.isStoryTeller) {
      this.sendBtn.prop("disabled", false);
    } else {
      this.sendBtn.prop("disabled", true);
    }
    board.clue.text = '"' + game.currClue + '"';
    break;
  case 'VOTING':
	board.advanceBtn.css('display', 'none');
	board.advanceBtn.attr("disabled", true);
	board.smallBoard = true;
	board.adjustCardsPos();
  	board.smallBoard = true;
  	board.adjustCardsPos();
  	break;
  case 'WAITING':
    board.smallBoard = true;
    board.adjustCardsPos();
    board.cardVoted = null;
    board.advanceBtn.css('display', 'block');
    board.advanceBtn.removeAttr("disabled");
    board.advanceBtn.prop('disabled', false);
    break;
  }
}
Board.prototype.doVotes = function(votes) {
  console.log('doing votes');
  console.log(votes);
  var card;
  //clear votes
  for(var i=0;i<this.cards.length;i++){
	  this.cards[i].votes=[];
  }
  for (var i = 0; i < votes.length; i++) {
    for (var j = 0; j < this.cards.length; j++) {
      card = this.cards[j];
      if (card.id === votes[i].cardId) {
        card.votes.push(board.game.players[votes[i].playerId].color);
      }
    }
  }
}

Board.prototype.tableCardsUpdate = function(options) {
  board.cards = [];
  if(options.currentPlayerCardId){
	  selectedCard.id = options.currentPlayerCardId;
  }
  if (!options.faceUp) {
    for (var i = 0; i < options.cardAmount; i++) {
      board.addGenericCard();
    }
  } else {
    var card;
    var cardObj;
    for (var i = 0; i < options.cards.length; i++) {
      card = options.cards[i];
      card.visible = true;
      card.inHand = false;
      cardObj = new Card(card);
      board.addCard(cardObj);
      if (card.ownerId) {
        var tempPlayer = board.game.players[card.ownerId];
        cardObj.outline = tempPlayer.color;
        cardObj.isStoryTeller = tempPlayer.isStoryTeller;
      }
    }
  }
}
function sendBtn(event) {
    selectedCard.visible = false;
    selectedCard.inHand = false;
    addNonStoryCardRequest(selectedCard.id, function(e) {
      board.sendBtn.prop("disabled", true)
    });
    board.cardModal.modal('hide');
}

function sendClue(event) {
  board.clue.text = '"' + board.clueInput.val() + '"';
  board.clueModal.modal('hide');
  board.clue.refresh(board);
  board.clientPlayer.refresh(board.canvas);
  addStoryCardRequest(board.clientPlayer.hand[board.clue.cardIndex].id,
      board.clueInput.val(), function(e) {
	  	board.clueInput.val("");
      });
  
}

function mouseClickListener(event) {
  var bRect = board.canvas.getBoundingClientRect();
  mouseX = (event.clientX - bRect.left) * (board.canvas.width / bRect.width);
  mouseY = (event.clientY - bRect.top) * (board.canvas.height / bRect.height);
  var card;
  for (var i = 0; i < board.clientPlayer.hand.length; i++) {
    card = board.clientPlayer.hand[i];
    if (card.clicked(mouseX, mouseY)) {
      if (card.visible) {
        if(!board.clientPlayer.isStoryTeller || board.game.currPhase != 'STORYTELLER'){
        if (game.currPhase != game.phases['NonStoryCards'] && !board.clientPlayer.isStoryTeller) {
          board.sendBtn.prop('disabled', true);
        }
        var modalImg = board.cardModal.find('.modal-body img')[0];
        modalImg.src = card.frontImg.src;
        modalImg.height = $(window).height() * 0.75;
        modalImg.width = $(window).height() * 0.50;
        board.modalContent.css('height', $(window).height() * .95);
        board.modalContent.css('width', modalImg.width * 1.1);
        board.cardModal.modal('show');
        selectedCard = card;
      } else {
        var modalImg = board.clueModal.find('.modal-body img')[0];
        modalImg.src = card.frontImg.src;
        modalImg.height = $(window).height() * 0.75;
        modalImg.width = $(window).height() * 0.50;
        board.modalContent.css('height', $(window).height() * .95);
        board.modalContent.css('width', modalImg.width * 1.1);
        board.clue.cardIndex = i;
        console.log(board.clue.cardIndex);
        board.clueModal.modal('show');
      }
      }
    }
  }
  if (board.game.currPhase === game.phases['Voting']) {
    if (!board.clientPlayer.isStoryTeller) {
      for (var i = 0; i < board.cards.length; i++) {
        card = board.cards[i];
        if (card.clicked(mouseX, mouseY) && selectedCard.id != card.id) {
          if (board.cardVoted == card) {
            board.cardVoted = null;
            removeVoteForCardRequest(function(e) {
              if (e)
                console.log("removed vote");
            });
          } else {
            board.cardVoted = card;
            voteForCardRequest(card.id, function(e) {
              console.log(e);
              if (e)
                console.log("voted");
            });
          }
        }
      }
    }
  }
  for (var i = 0; i < board.icons.length; i++) {
    if (board.icons[i].clicked(mouseX, mouseY)) {
      board.icons[i].callback();
      if (board.icons[i].name === "min" || board.icons[i].name === "min") {
        board.icons.splice(board.iconsMap[board.icons[i].name], 1);
      }
    }
  }
}
function mouseDownListener(event) {
  var bRect = board.canvas.getBoundingClientRect();
  mouseX = (event.clientX - bRect.left) * (board.canvas.width / bRect.width);
  mouseY = (event.clientY - bRect.top) * (board.canvas.height / bRect.height);
  if (game.currPhase === game.phases.NonStoryCards
      || game.currPhase === game.phases.StoryTeller) {
    for (var i = 0; i < board.clientPlayer.hand.length; i++) {
      if (board.clientPlayer.hand[i].clicked(mouseX, mouseY)) {
        if ((game.currPhase === game.phases.NonStoryCards && !board.clientPlayer.isStoryTeller)
            || (game.currPhase === game.phases.StoryTeller && board.clientPlayer.isStoryTeller)) {
          dragging = true;
          draggingCard = board.clientPlayer.hand[i];
        }
      }
    }
  } // TODO: else if it is voting time then go through the voting objects to
  // see if it was hit
  // also should change dragging card to dragging object to ouse the same
  // listeners.
  if (dragging) {
    dragging = true;
    window.addEventListener("mousemove", mouseMoveListener, false);
    dragHoldX = mouseX - draggingCard.x;
    dragHoldY = mouseY - draggingCard.y;
    // The "target" position is where the object should be if it were to move
    // there instantaneously. But we will
    // set up the code so that this target position is approached gradually,
    // producing a smooth motion.
    targetX = mouseX - dragHoldX;
    targetY = mouseY - dragHoldY;
    // start timer
    timer = setInterval(onTimerTick, 1000 / 30);
  }
  board.canvas.removeEventListener("mousedown", mouseDownListener, false);
  window.addEventListener("mouseup", mouseUpListener, false);
  // code below prevents the mouse down from having an effect on the main
  // browser window:
  if (event.preventDefault) {
    event.preventDefault();
  } // standard
  else if (event.returnValue) {
    event.returnValue = false;
  } // older IE
  return false;
}
/*
 * function onTimerTick() { // because of reordering, the dragging shape is the
 * last one in the array. draggingCard.x = draggingCard.x + 0.45 * (targetX -
 * draggingCard.x); draggingCard.y = draggingCard.y + 0.45 * (targetY -
 * draggingCard.y); // stop the timer when the target position is reached (close
 * enough) if ((!dragging) && (Math.abs(draggingCard.x - targetX) < 0.1) &&
 * (Math.abs(draggingCard.y - targetY) < 0.1)) { draggingCard.x = targetX;
 * draggingCard.y = targetY; // stop timer: clearInterval(timer); }
 * board.draw(); }
 */
function mouseUpListener(evt) {
  board.canvas.addEventListener("mousedown", mouseDownListener, false);
  window.removeEventListener("mouseup", mouseUpListener, false);
  if (dragging) {
    dragging = false;
    window.removeEventListener("mousemove", mouseMoveListener, false);
  }
}

function mouseMoveListener(evt) {
  var bRect = board.canvas.getBoundingClientRect();
  var mouseX = (evt.clientX - bRect.left) * (board.canvas.width / bRect.width);
  var mouseY = (evt.clientY - bRect.top) * (board.canvas.height / bRect.height);
  var hoveringCard = false;
  var card;
  for (var i = 0; i < board.clientPlayer.hand.length; i++) {
    card = board.clientPlayer.hand[i];
    if (card.clicked(mouseX, mouseY)) {
      // board.ctx.drawImage(board.icons.zoom, card.x, card.y, 25, 25);
      hoveringCard = true;
    }
  }
  if (board.game.currPhase === game.phases['Voting']) {
    for (var i = 0; i < board.cards.length; i++) {
      card = board.cards[i];
      if (card.clicked(mouseX, mouseY) && card.id != selectedCard.id) {
        // board.ctx.drawImage(board.icons.zoom, card.x, card.y, 25, 25);
        if (!board.clientPlayer.isStoryTeller) {
          hoveringCard = true;
        }
      }
    }
  }
  if (!board.smallBoard) {
    if (board.clue.card) {
      if (board.clue.card.clicked(mouseX, mouseY)) {
        hoveringCard = true;
      }
    }
  }

  for (var i = 0; i < board.icons.length; i++) {
    if (board.icons[i].clicked(mouseX, mouseY)) {
      hoveringCard = true;
    }
  }
  if (hoveringCard) {
    board.canvas.style.cursor = "pointer";
  } else {
    board.canvas.style.cursor = "default";
  }
}
