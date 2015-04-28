function Game(name, rules) {
  this.name = name;
  this.rules = rules;
  this.currPhase = -1;
  this.currClue = "";
  this.board = null;
  this.players = {};
  this.score = {};
  this.storyTeller = null;

}

Game.prototype.phases = {
  'StoryTeller' : "STORYTELLER",
  'NonStoryCards' : "NONSTORYCARDS",
  'Voting' : "VOTING",
  'Scoring' : "SCORING",
  'CleanUp' : "CLEANUP",
  'Pregame' : "PREGAME",
  'GameOver' : "GAMEOVER"
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
  for (var i = 0; i < players.length; i++) {
    this.addPlayer(players[i]);
  }
}

Game.prototype.addPlayer = function(player) {
  this.score[player.id] = 0;
  this.players[player.id] = new Player(player);
}

// if we only need to change one player’s score
Game.prototype.updateScore = function(playerId, newScore) {
  this.score[playerId] = newScore;
  this.players[playerId].score = newScore;
}
// updates all scores, newScores should be a js obj mapping player id to score
Game.prototype.updateScores = function(newScores) {
  this.score = newScores;
}

Game.prototype.doPhase = function(phase) {
  this.currPhase = phase;
  // TODO: add checks so that it cannot go pass the cleanup phase,aka game over
  $('#currentPhase').text('Current phase: ' + phases[this.currPhase]);
  this.board.changePhase(this.currPhase);
}

// gets a player object given the player id
Game.prototype.getPlayer = function(id) {
  return this.players[id];

}