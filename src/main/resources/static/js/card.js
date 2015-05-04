var backImg = new Image();
backImg.src = "/images/back_of_card.jpg";
function Card(options) {
  this.id = options.id; // / dont take img as constructor so ppl dont CHEAT
  this.x = -100;
  this.y = -100;
  this.visible = options.visible;
  this.frontImg = new Image();
  if (options.image) {
    this.frontImg.src = "/images/cards/" + options.image;
  }
  this.backImg = backImg;
  this.inHand = options.inHand;
  // if there should be an outline drawn, this should be the color
  this.outline = null;
  // Set this property externally to show that the card is the
  // storyteller card.
  this.isStoryTeller = false;
  this.votes = [] // array of color names to denote votes;
}
// returns img obj of card
Card.prototype.getImg = function() {
  if (this.visible) {
    return this.frontImg;
  } else {
    return this.backImg;
  }
}

Card.prototype.resize = function(canvas) {
  if (this.inHand) {
    this.height = canvas.height / 5.5;
    this.width = canvas.width / 10;
  } else {

  }
}
// function for drawing the card
Card.prototype.draw = function(ctx) {
  var img = this.getImg();
  var x = this.x;
  var y = this.y;
  var w = this.width;
  var h = this.height;
  var card = this;
  if (img.complete) {
    ctx.drawImage(img, x, y, w, h);
    this.drawOutline(ctx);
    this.drawVotes(ctx);
  } else {
    img.onload = function() {
      ctx.drawImage(img, x, y, w, h);
      card.drawOutline(ctx);
      card.drawVotes(ctx);
    }
  }
}

Card.prototype.drawOutline = function(ctx) {
  if (this.outline) {
    ctx.lineWidth = 5;
    ctx.strokeStyle = this.outline;
    ctx.strokeRect(this.x, this.y, this.width, this.height);
  }
  if (this.isStoryTeller) {
    ctx.fillStyle = "#FFFFFF";
    ctx.fillRect(this.x - 5, this.y - 12, 150, 25);
    ctx.font = "20px Georgia";
    ctx.fillStyle = "#000000";
    ctx.fillText("Storyteller card!", this.x, this.y + 8);
  }
}
Card.prototype.getCenter = function() {
  var x = this.x + this.width / 2;
  var y = this.y + this.height / 2;
  return {
    x : x,
    y : y
  }
}
Card.prototype.drawVotes = function(ctx) {
  var voteWidth = this.width / 8;
  for (var i = 0; i < this.votes.length; i++) {
    ctx.beginPath();
    ctx.arc(voteWidth + this.x + i * voteWidth, this.y + this.height,
        voteWidth, 0, 2 * Math.PI);
    ctx.fillStyle = this.votes[i];
    ctx.fill();
  }
}
// to see if this card was clicked
Card.prototype.clicked = function(clickX, clickY) {
  return ((clickX > this.x) && (clickX < this.x + this.width)
      && (clickY > this.y) && (clickY < this.y + this.height));
}

Card.prototype.reveal = function() {
  this.visible = true;
}

Card.prototype.makeBig = function(canvas) {
  this.height = canvas.height / 3;
  this.width = canvas.width / 7;
}

Card.prototype.makeSmall = function(canvas) {
  this.height = canvas.height / 5;
  this.width = canvas.width / 10;
}

Card.prototype.highlight = function(ctx) {
  ctx.fillStyle = "rgba(237, 243, 43, 0.5)";
  ctx.fillRect(this.x, this.y, this.width, this.height);
}
