var backImg = new Image();
backImg.src = "/images/back_of_card.jpg";
function Card(id, img, x, y, canvas) {
  this.id = id; // / dont take img as constructor so ppl dont CHEAT
  this.x = x;
  this.y = y;
  this.visible = true;
  this.height = canvas.height / 5.5;
  this.width = canvas.width / 10;
  this.frontImg = new Image();
  this.frontImg.src = img;
  this.backImg = backImg;
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
  this.height = canvas.height / 5.5;
  this.width = canvas.width / 10;
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