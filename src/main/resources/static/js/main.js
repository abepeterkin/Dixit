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
    hand.push(new Card(i, handUrls[i], i * (board.canvas.width / 9)
        + (board.canvas.width / 4), board.canvas.height
        - (board.canvas.height / 4), board.canvas));
  }
  return hand;
}
window.onload = function() {
  var board = new Board(players, "board", player1.id);
  player1.addHand(makeTestHand());
  board.addListeners();
  board.draw();
}