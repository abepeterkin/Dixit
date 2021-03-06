function Clue(opt) {
  this.text = opt.text || "";
  this.fontface = opt.fontface || "Georgia";
  this.fontsizeBig = opt.fontface || 30;
  this.fontsizeMed = 20;
  this.fontsizeSmall = 18;
  this.x = opt.x || 200;
  this.y = opt.y || 30;
  this.card = opt.card;
  this.cardIndex = 0; // the index of the card in the player hand
}

Clue.prototype.draw = function(ctx) {
	//console.log('inside clue draw');
	//console.log(this);
  if (this.text != '""') {
    ctx.textAlign = "center";
    ctx.fillStyle = "black";
    var length = this.text.length;
    var fontsize;
    if (length < 70) {
    	fontsize = this.fontsizeBig;
    } else if (length < 120) {
    	fontsize = this.fontsizeMedium;
    } else {
    	fontsize = this.fontsizeSmall;
    }
    ctx.font = fontsize + "px " + this.fontface;
    ctx.fillText(this.text, this.x, this.y);
    ctx.textAlign = "left";
    if (this.card) {
      this.card.draw(ctx);
    }
  }
}

Clue.prototype.makeSmall = function(board) {
  if (this.card) {
    this.card.height = board.canvas.height / 12;
    this.card.width = board.canvas.width / 20;
    board.ctx.font = this.fontsize + "px " + this.fontface;
    this.card.x = this.x + board.ctx.measureText(this.text).width;
    this.card.y = 0;
  }
}

Clue.prototype.makeBig = function(board) {
  if (this.card) {
    this.card.height = board.canvas.height / 4;
    this.card.width = board.canvas.width / 7;
    this.card.x = this.x + board.ctx.measureText(this.text).width;
    this.card.y = 0;
  }
}
Clue.prototype.refresh = function(board) {
  // TODO: make this more responsive, handle multi-lines
  // and better y positioning
  this.x = board.canvas.width / 2;
  this.y = 30;
  if (this.card) {
    if (board.smallBoard) {
      this.makeBig(board);
    } else {
      this.makeSmall(board);
    }
  }
}