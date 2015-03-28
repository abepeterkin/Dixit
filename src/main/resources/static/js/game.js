function Game(id,rules){
	this.id: id;
	this.rules: rulles;
	currPhase: 0;
	currClue: '';
	board: null;
	//TODO: add a ctx and canvas so that those can be easily be referenced?
}

Game.prototype.phases = {
	'StoryTeller':0,
	'NonStoryCards':1,
	'Voting': 2,
	'Scoring': 3,
	'CleanUp': 4,
}

//sets the storyTeller
Game.prototype.setStoryTeller = function(player){
	this.storyTeller = player;
}