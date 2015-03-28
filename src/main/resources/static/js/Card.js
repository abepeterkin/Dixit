var backImg = new Image();
backImg.src = "/images/cards/dixit_card_01_000.jpg";
function Card(id, img, x, y) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.visible = true;
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
	var img = this.getImg();
	var x = this.x;
	var y = this.y;
	var w = this.width;
	var h = this.height;
	if(img.complete){
		ctx.drawImage(img, x, y, w, h);
	} else {
		img.onload = function(){
			ctx.drawImage(img, x, y, w, h);
		}
	}	
}

//to see if this card was clicked
Card.prototype.clicked = function(clickX, clickY){
	return((clickX > this.x)&&
		   (clickX < this.x + this.width)&&
		    (clickY > this.y)&&
		    (clickY < this.y + this.height));
}

Card.prototype.reveal = function (){
	this.visible = true;	
}