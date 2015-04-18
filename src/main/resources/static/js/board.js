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
var selectedCard;
var sentCard = false;
// should hold to array of game.players to display their score and color
function Board(game, canvasId, playerId) {
  this.playerId = playerId; // the player id who is using this client
  this.game = game;
  this.scores = {}; // maps of playerid to score
  this.cards = []// cards currently at play
  this.canvas = document.getElementById(canvasId);
  this.canvas.width = (window.innerWidth
      || document.documentElement.clientWidth || document.body.clientWidth);
  this.canvas.height = (window.innerHeight
      || document.documentElement.clientHeight || document.body.clientHeight);
  this.ctx = this.canvas.getContext("2d");
  this.img = new Image();
  this.img.src = "/images/board.jpg";
  board = this;
  this.icons = {};
  this.icons.zoom = new Image();
  this.icons.zoom.src = "/images/zoom.png";
  this.sendBtn = $('#send-card-btn');
  this.cardModal = $('#cardModal');
  this.clueModal = $('#sendClueModal');
  this.smallBoard = true;
}
// draws the entire game board, including client player's hand
Board.prototype.draw = function() {
  this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
  // TODO: draw clue
  // TODO: draw game.players
  if (!this.smallBoard) {
    this.drawBig(this.game.players[this.playerId - 1]);
  } else {
    this.drawSmall();

  }
}
// draws the simplified board with the appropriate scores,
Board.prototype.drawSmall = function() {
  this.drawPlayersSmall();
  this.game.players[this.playerId - 1].drawHand(this.ctx);
}
// draws the scoreboard gets passed other objects to ensure
// background board always gets drawn first
Board.prototype.drawBig = function(player) {
  var img = this.img;
  var x = 0;
  var y = 0;
  var w = this.canvas.width;
  var h = this.canvas.height / 1.7;
  if (this.img.complete) {
    this.ctx.drawImage(img, x, y, w, h);
    player.drawHand(this.ctx);
    this.drawPlayers();
    this.drawClue();
  } else {
    img.onload = function() {
      board.ctx.drawImage(img, x, y, w, h);
      player.drawHand(board.ctx);
      board.drawPlayers();
      board.drawClue();
    }
  }
}
Board.prototype.drawPlayersBig = function() {
  for (var i = 0; i < this.game.players.length; i++) {
    this.game.players[i].drawIdle(this, i);
  }
}

Board.prototype.drawPlayersSmall = function() {
  for (var i = 0; i < this.game.players.length; i++) {
    this.game.players[i].drawSmall(this, i);
  }
}
Board.prototype.drawClue = function() {
  this.ctx.font = "30px Georgia"; // make this responsive
  this.ctx.fillText(this.game.currClue, this.canvas.width / 2, 30);
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
  this.game.players[this.playerId - 1].refresh(this.canvas);
  board.draw();
}

Board.prototype.addListeners = function() {
  // this.canvas.addEventListener("mousedown", mouseDownListener, false);
  this.canvas.addEventListener("click", mouseClickListener, false);
  this.canvas.addEventListener("mousemove", mouseMoveListener, false);
  $(window).on('resize', function(e) {
    board.refresh();
  })
  this.sendBtn.click(sendBtn);
  $('#clueBtn')
      .click(
          function(e) {
            var card;
            for (var i = 0; i < board.game.players[board.playerId - 1].hand.length; i++) {
              card = board.game.players[board.playerId - 1].hand[i];
              board.clueModal.find('#card' + i)[0].src = card.frontImg.src;
            }
            board.clueModal.modal('show');
          })
}

function sendBtn(event) {
  if (!sentCard) {
    selectedCard.x = board.playerId * (board.canvas.width / 7)
        - selectedCard.width + board.playerId * selectedCard.width / 10;
    selectedCard.y = board.canvas.height - (board.canvas.height / 2.5);
    sentCard = true;
    board.cardModal.modal('hide');
    board.sendBtn.text("Return Card");
  } else {
    var index = board.game.players[board.playerId - 1].hand
        .indexOf(selectedCard);
    selectedCard.x = index * (board.canvas.width / 9)
        + (board.canvas.width / 4);
    selectedCard.y = board.canvas.height - (board.canvas.height / 5);
    board.cardModal.modal('hide');
    board.sendBtn.text("Send Card");
    sentCard = false;
  }
  board.draw();
}

function mouseClickListener(event) {
  var bRect = board.canvas.getBoundingClientRect();
  mouseX = (event.clientX - bRect.left) * (board.canvas.width / bRect.width);
  mouseY = (event.clientY - bRect.top) * (board.canvas.height / bRect.height);
  var card;
  for (var i = 0; i < board.game.players[board.playerId - 1].hand.length; i++) {
    card = board.game.players[board.playerId - 1].hand[i];
    if (card.clicked(mouseX, mouseY)) {
      if (card.visible) {
        board.cardModal.find('.modal-body img')[0].src = card.frontImg.src;
        board.cardModal.modal('show');
        selectedCard = card;
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
    for (var i = 0; i < board.game.players[board.playerId - 1].hand.length; i++) {
      if (board.game.players[board.playerId - 1].hand[i]
          .clicked(mouseX, mouseY)) {
        if ((game.currPhase === game.phases.NonStoryCards && !board.game.players[board.playerId - 1].isStoryTeller)
            || (game.currPhase === game.phases.StoryTeller && board.game.players[board.playerId - 1].isStoryTeller)) {
          dragging = true;
          draggingCard = board.game.players[board.playerId - 1].hand[i];
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

function onTimerTick() {
  // because of reordering, the dragging shape is the last one in the array.
  draggingCard.x = draggingCard.x + 0.45 * (targetX - draggingCard.x);
  draggingCard.y = draggingCard.y + 0.45 * (targetY - draggingCard.y);

  // stop the timer when the target position is reached (close enough)
  if ((!dragging) && (Math.abs(draggingCard.x - targetX) < 0.1)
      && (Math.abs(draggingCard.y - targetY) < 0.1)) {
    draggingCard.x = targetX;
    draggingCard.y = targetY;
    // stop timer:
    clearInterval(timer);
  }
  board.draw();
}

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
  for (var i = 0; i < board.game.players[board.playerId - 1].hand.length; i++) {
    card = board.game.players[board.playerId - 1].hand[i];
    if (card.clicked(mouseX, mouseY)) {
      // board.ctx.drawImage(board.icons.zoom, card.x, card.y, 25, 25);
      hoveringCard = true;
    }
  }
  if (hoveringCard) {
    board.canvas.style.cursor = "pointer";
  } else {
    board.canvas.style.cursor = "default";
  }
}
/*
 * function mouseMoveListener(evt) { var posX; var posY; var minX = 0; var maxX =
 * board.canvas.width - draggingCard.width; var minY = 0; var maxY =
 * board.canvas.height - draggingCard.height; // getting mouse position
 * correctly var bRect = board.canvas.getBoundingClientRect(); mouseX =
 * (evt.clientX - bRect.left) * (board.canvas.width / bRect.width); mouseY =
 * (evt.clientY - bRect.top) * (board.canvas.height / bRect.height); // clamp x
 * and y positions to prevent object from dragging outside of canvas posX =
 * mouseX - dragHoldX; posX = (posX < minX) ? minX : ((posX > maxX) ? maxX :
 * posX); posY = mouseY - dragHoldY; posY = (posY < minY) ? minY : ((posY >
 * maxY) ? maxY : posY);
 * 
 * targetX = posX; targetY = posY; }
 */