function Game(name, rules) {
  this.name = name;
  this.rules = rules;
  this.currPhase = 0;
  this.currClue = '"Harry Potter"';
  board = null;
  this.players = [];
  this.score = {};
  // TODO: add a ctx and canvas so that those can be easily be referenced?
  // currstorysteller
}

Game.prototype.phases = {
  'StoryTeller' : 0,
  'NonStoryCards' : 1,
  'Voting' : 2,
  'Scoring' : 3,
  'CleanUp' : 4,
}

// sets the storyTeller
Game.prototype.setStoryTeller = function(player) {
  this.players.indexOf(player).makeStoryTeller();
  this.storyTeller = player;
}

Game.prototype.addPlayers = function(players) {
  this.players = players;
  for (var i = 0; i < players.length; i++) {
    this.score[players[i].id] = 0;
  }
}

// if we only need to change one player’s score
Game.prototype.updateScore = function(playerId, newScore) {
  this.score[playerId] = newScore;
}
// updates all scores, newScores should be a js obj mapping player id to score
Game.prototype.updateScores = function(newScores) {
  this.score = newScores;
}