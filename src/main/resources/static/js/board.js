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
  this.clientPlayer = game.players[playerId - 1]; // the player this client
  this.game = game;
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
  this.clueCardModal = $('#clueCardModal');
  this.clueModal = $('#sendClueModal');
  this.smallBoard = true;
  $('#clientPlayer').text('Client player: ' + this.clientPlayer.name);
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
  board.ctx.fillRect(xMin, yMin, wMin, hMin);
  board.ctx.fillRect(xMin + wMin / 2, yMin - wMin / 2, hMin, wMin);
  var ind = board.icons.push(new Icon({
    x : xMin,
    y : yMin,
    width : wMin,
    height : hMin,
    callback : function() {
      board.smallBoard = false;
      for (var i = 0; i < board.cards.length; i++) {
        board.cards[i].makeSmall(i);
      }
      board.clue.makeSmall(board);
    },
    name : "max"
  }))
  board.iconsMap['max'] = ind - 1;
  this.drawPlayersSmall();
  this.clientPlayer.drawHand(this.ctx);
  this.drawCards();
  this.clue.draw(this.ctx);
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
      for (var i = 0; i < board.cards.length; i++) {
        board.cards[i].makeBig(i);
      }
      board.clue.makeBig(board);
    },
    name : "min"
  }))
  board.iconsMap['min'] = ind - 1;
  player.drawHand(board.ctx);
  board.drawPlayersBig();
  board.clue.draw(board.ctx);
  board.drawCards();
}
Board.prototype.drawPlayersBig = function() {
  for (var i = 0; i < this.game.players.length; i++) {
    board.game.players[i].idle.update();
    board.game.players[i].idle.render(board.ctx, i);
  }
}

Board.prototype.drawPlayersSmall = function() {
  for (var i = 0; i < this.game.players.length; i++) {
    this.game.players[i].drawSmall(this, i);
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

Board.prototype.addCard = function(card) {
  if (this.cards.length < this.game.players.length) {
    this.cards.push(card);
    board.adjustCardsPos();
  } else {
    console.log("trying to push more cards on board than there are players!");
  }
}
Board.prototype.adjustCardsPos = function() {
  if (board.smallBoard) {
    board.cards[0].x = board.canvas.width / 5;
    board.cards[0].y = board.canvas.height / 20;
    board.cards[0].makeBig(0);
  } else {

  }
  var width = board.cards[0].width;
  var height = board.cards[0].height
  for (var i = 1; i < board.cards.length; i++) {
    if (board.smallBoard) {
      board.cards[i].makeBig(i);
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

    }
  }
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
}

Board.prototype.changePhase = function(phase) {
  switch (phase) {
    case this.game.phases['StoryTeller']:
      if (this.clientPlayer.isStoryTeller) {
        var card;
        for (var i = 0; i < board.clientPlayer.hand.length; i++) {
          card = board.clientPlayer.hand[i];
          board.clueModal.find('#card' + i)[0].src = card.frontImg.src;
        }
        board.clueModal.modal('show');
        break;
      } else {
        this.sendBtn.prop("disabled", true);
      }
    case this.game.phases['NonStoryCards']:
      if (!this.clientPlayer.isStoryTeller) {
        this.sendBtn.prop("disabled", false);
      }
  }
}

function sendBtn(event) {
  if (!sentCard) {
    if (!board.smallBoard) {
      selectedCard.x = board.clientPlayer.id * (board.canvas.width / 7)
          - selectedCard.width + board.clientPlayer.id * selectedCard.width
          / 10;
      selectedCard.y = board.canvas.height - (board.canvas.height / 2.5);
    } else {
      selectedCard.x = 0;
      selectedCard.y = 0;
    }
    sentCard = true;
    board.cardModal.modal('hide');
    board.sendBtn.text("Return Card");
  } else {
    var index = board.clientPlayer.hand.indexOf(selectedCard);
    selectedCard.x = index * (board.canvas.width / 9)
        + (board.canvas.width / 4);
    selectedCard.y = board.canvas.height - (board.canvas.height / 5);
    board.cardModal.modal('hide');
    board.sendBtn.text("Send Card");
    sentCard = false;
  }
}

function sendClue(event) {
  board.clue.text = '"' + board.clueInput.val() + '"';
  board.clueModal.modal('hide');
  board.clue.card = board.clientPlayer.hand[board.clue.cardIndex];
  board.clue.refresh(board);
  board.clientPlayer.hand.splice(board.clue.cardIndex, 1);
  board.clientPlayer.refresh(board.canvas);
  board.game.nextPhase();
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
        board.cardModal.find('.modal-body img')[0].src = card.frontImg.src;
        if (game.currPhase != game.phases['NonStoryCards']) {
          board.sendBtn.prop('disabled', true);
        }
        board.cardModal.modal('show');
        selectedCard = card;
      }
    }
  }
  if (!board.smallBoard) {
    if (board.clue.card) {
      if (board.clue.card.clicked(mouseX, mouseY)) {
        board.clueCardModal.find('.modal-body img')[0].src = board.clue.card.frontImg.src;
        board.clueCardModal.modal('show');
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