var backImg = new Image();
backImg.src = "/images/back_of_card.jpg";
function Card(options) {
  this.id = options.id; // / dont take img as constructor so ppl dont CHEAT
  this.x = options.x;
  this.y = options.y;
  this.visible = options.visible;
  this.canvas = options.canvas;
  this.height = options.canvas.height / 5.5;
  this.width = options.canvas.width / 10;
  this.frontImg = new Image();
  if (options.img) {
    this.frontImg.src = options.img;
  }
  this.backImg = backImg;
  this.inHand = options.inHand;
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
  if (img.complete) {
    ctx.drawImage(img, x, y, w, h);
  } else {
    img.onload = function() {
      ctx.drawImage(img, x, y, w, h);
    }
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

Card.prototype.makeBig = function() {
  this.height = this.canvas.height / 3;
  this.width = this.canvas.width / 7;
}

Card.prototype.makeSmall = function() {
  this.height = this.canvas.height / 5;
  this.width = this.canvas.width / 10;
}

Card.prototype.highlight = function(ctx) {
  ctx.fillStyle = "rgba(237, 243, 43, 0.5)";
  ctx.fillRect(this.x, this.y, this.width, this.height);
}