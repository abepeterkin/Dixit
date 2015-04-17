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
// should hold to array of players to display their score and color
function Board(players, canvasId) {
  this.players = players;
  this.scores = {}; // maps of playerid to score
  this.cards = []// cards currently at play
  this.canvas = document.getElementById(canvasIs);
  this.canvas.width = (window.innerWidth
      || document.documentElement.clientWidth || document.body.clientWidth);
  this.canvas.height = (window.innerHeight
      || document.documentElement.clientHeight || document.body.clientHeight);
  this.ctx = this.canvas.getContext("2d");
  board = this;

}

// draws the simplified board with the appropriate scores,
Board.prototype.drawSmall = function() {

}

// draws the big board with the appropriate scores,
Board.prototype.drawBig = function() {

}
// to update the score
Board.prototype.setScore = function(score) {

}

// if we only need to change one player’s score, makes animation
Board.prototype.updateScore = function(ctx, playerId, newScore) {

}

// resets the score and positions all players at the beginning
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
}

Board.prototype.addListeners = function() {
  this.canvas.addEventListener("mousedown", mouseDownListener, false);
  this.canvas.addEventListener("dblclick", mouseDblClickListener, false);
}

function mouseDblClickListener(event) {
  var bRect = canvas.getBoundingClientRect();
  mouseX = (event.clientX - bRect.left) * (canvas.width / bRect.width);
  mouseY = (event.clientY - bRect.top) * (canvas.height / bRect.height);
  var card;
  for (var i = 0; i < clientPlayer.hand.length; i++) {
    card = clientPlayer.hand[i];
    if (card.clicked(mouseX, mouseY)) {
      if (card.visible) {
        var modal = $('#cardModal');
        modal.find('.modal-body img')[0].src = card.frontImg.src;
        modal.modal('show');
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
    for (var i = 0; i < clientPlayer.hand.length; i++) {
      if (clientPlayer.hand[i].clicked(mouseX, mouseY)) {
        if ((game.currPhase === game.phases.NonStoryCards && !clientPlayer.isStoryTeller)
            || (game.currPhase === game.phases.StoryTeller && clientPlayer.isStoryTeller)) {
          dragging = true;
          draggingCard = clientPlayer.hand[i];
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
  drawBoard();
}

function mouseUpListener(evt) {
  canvas.addEventListener("mousedown", mouseDownListener, false);
  window.removeEventListener("mouseup", mouseUpListener, false);
  if (dragging) {
    dragging = false;
    window.removeEventListener("mousemove", mouseMoveListener, false);
  }
}
function mouseMoveListener(evt) {
  var posX;
  var posY;
  var minX = 0;
  var maxX = canvas.width - draggingCard.width;
  var minY = 0;
  var maxY = canvas.height - draggingCard.height;

  // getting mouse position correctly
  var bRect = canvas.getBoundingClientRect();
  mouseX = (evt.clientX - bRect.left) * (canvas.width / bRect.width);
  mouseY = (evt.clientY - bRect.top) * (canvas.height / bRect.height);

  // clamp x and y positions to prevent object from dragging outside of canvas
  posX = mouseX - dragHoldX;
  posX = (posX < minX) ? minX : ((posX > maxX) ? maxX : posX);
  posY = mouseY - dragHoldY;
  posY = (posY < minY) ? minY : ((posY > maxY) ? maxY : posY);

  targetX = posX;
  targetY = posY;
}