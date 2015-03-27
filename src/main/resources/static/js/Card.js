var backImg = new Image();
backImg.src = "/images/cards/dixit_card_01_000.jpg";
function Card(id, img, x, y) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.visible = false;
	this.height = 120; //change this to make responsive with canvas dim
	this.width = 90; //ditto
	this.frontImg = new Image();
	this.frontImg.src = img;
	this.backImg = backImg;
}
//returns img obj of card 
Card.prototype.getImg = function(){
	if (this.visible){
		return this.frontImg;
	} else {
		return this.backImg;
	}
}

//function for drawing the card
Card.prototype.draw = function(ctx){
	ctx.drawImage(this.getImg(), parseInt(this.x), parseInt(this.y), this.width, this.height);	
}

//to see if this card was clicked
Card.prototype.clicked = function(clickX, clickY){
	return((clickX > this.x - this.width)&&
		   (clickX < this.x + this.width)&&
		    (clickY > this.y - this.height)&&
		    (clickY < this.y + this.height));
}