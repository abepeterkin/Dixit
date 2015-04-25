function Game(id, rules) {
  this.id = id;
  this.rules = rules;
  this.currPhase = -1;
  this.currClue = "";
  this.board = null;
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
// this is just for testing
phases = [ 'StoryTeller', 'NonStoryCards', 'Voting', 'Scoring', 'CleanUp' ];

// sets the storyTeller
Game.prototype.setStoryTeller = function(player) {
  player.makeStoryTeller();
  this.storyTeller = player;
  $('#storyTeller').text("Story Teller: " + this.storyTeller.name);
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

Game.prototype.nextPhase = function() {
  this.currPhase++
  // TODO: add checks so that it cannot go pass the cleanup phase,aka game over
  $('#currentPhase').text('Current phase: ' + phases[this.currPhase]);
  this.board.changePhase(this.currPhase);
}