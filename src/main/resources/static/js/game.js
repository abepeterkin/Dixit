function Game(id, rules) {
  this.id = id;
  this.rules = rules;
  this.currPhase = 0;
  this.currClue = '"Harry Potter"';
  board = null;
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
}