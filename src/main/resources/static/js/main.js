//TODO: get these from the server
var game = new Game(1, {
  numCards : 5
});

var player1 = new Player("1", "red", true, game);
var player2 = new Player("2", "blue", false, game);
var player3 = new Player("3", "pink", false, game);
var player4 = new Player("4", "blue", false, game);
var players = {};
players[player1.id] = player1;
players[player2.id] = player2;
players[player3.id] = player3;
players[player4.id] = player4;
game.currPhase = 1; // make it nonstorycards so cards can move
window.onload = function() {
  var board = new Board(players, "board");
  board.addListeners();

  var canvas = document.getElementById("board");
  var ctx = canvas.getContext("2d");
  var clientPlayer;
  init();
  canvas.addEventListener("mousedown", mouseDownListener, false);
  canvas.addEventListener("dblclick", mouseDblClickListener, false);

  function init() {
    clientPlayer = new Player(1, "#000000", false, game);
    clientPlayer.addHand(makeTestHand());
    drawBoard();
    // testing git integration
  }

  function makeTestHand() {
    var hand = [ "/images/cards/dixit_card_01_001.jpg",
        "/images/cards/dixit_card_01_002.jpg",
        "/images/cards/dixit_card_01_003.jpg",
        "/images/cards/dixit_card_01_004.jpg",
        "/images/cards/dixit_card_01_005.jpg" ];
    return makeHandArray(hand);
  }

  function makeHandArray(handUrls) {
    var hand = [];
    for (var i = 0; i < handUrls.length; i++) {
      hand.push(new Card(i, handUrls[i], i * 100 + 160, canvas.height - 260));
    }
    return hand;
  }

  f

  function drawBoard() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    // TODO: draw clue
    // TODO: draw scoreboard
    clientPlayer.drawHand(ctx);
  }
}