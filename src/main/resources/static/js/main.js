window.onload = function(){
  var mouseX;
  var mouseY;
  var dragHoldX;
  var dragHoldY;
  var timer;
  var targetX;
  var targetY;
  var dragging;
  var canvas = document.getElementById("board");  
  var ctx = canvas.getContext("2d");
  var card = new Card(1, "/images/cards/dixit_card_01_001.jpg", 0, 0);
  card.draw(ctx);
  canvas.addEventListener("mousedown", mouseDownListener, false);
  
  function mouseDownListener(event){
  	var bRect = canvas.getBoundingClientRect();
	mouseX = (event.clientX - bRect.left)*(canvas.width/bRect.width);
	mouseY = (event.clientY - bRect.top)*(canvas.height/bRect.height);
	if(card.clicked(mouseX, mouseY)){
		dragging = true;
		window.addEventListener("mousemove", mouseMoveListener, false);
		dragHoldX = mouseX - card.x;
		dragHoldY = mouseY - card.y;
		//The "target" position is where the object should be if it were to move there instantaneously. But we will
		//set up the code so that this target position is approached gradually, producing a smooth motion.
		targetX = mouseX - dragHoldX;
		targetY = mouseY - dragHoldY;
		//start timer
		timer = setInterval(onTimerTick, 1000/30);
	}
	canvas.removeEventListener("mousedown", mouseDownListener, false);
	window.addEventListener("mouseup", mouseUpListener, false);	
	//code below prevents the mouse down from having an effect on the main browser window:
	if (event.preventDefault) {
		event.preventDefault();
	} //standard
	else if (event.returnValue) {
		event.returnValue = false;
	} //older IE
	return false;
	}
  
  function onTimerTick() {
		//because of reordering, the dragging shape is the last one in the array.
		card.x = card.x + 0.45*(targetX - card.x);
		card.y = card.y + 0.45*(targetY - card.y);
		
		//stop the timer when the target position is reached (close enough)
		if ((!dragging)&&(Math.abs(card.x - targetX) < 0.1) && (Math.abs(card.y - targetY) < 0.1)) {
			card.x = targetX;
			card.y = targetY;
			//stop timer:
			clearInterval(timer);
		}
		drawBoard();
	}
	
	function mouseUpListener(evt) {
		canvas.addEventListener("mousedown", mouseDownListener, false);
		window.removeEventListener("mouseup", mouseUpListener, false);
		if (dragging) {
			dragging = false;
			window.removeEventListener("mousemove", mouseMoveListener, false);
		}
	}
	function mouseMoveListener(evt) {
		var posX;
		var posY;
		var minX = 0;
		var maxX = canvas.width - card.width;
		var minY = 0;
		var maxY = canvas.height - card.height;
		
		//getting mouse position correctly 
		var bRect = canvas.getBoundingClientRect();
		mouseX = (evt.clientX - bRect.left)*(canvas.width/bRect.width);
		mouseY = (evt.clientY - bRect.top)*(canvas.height/bRect.height);
		
		//clamp x and y positions to prevent object from dragging outside of canvas
		posX = mouseX - dragHoldX;
		posX = (posX < minX) ? minX : ((posX > maxX) ? maxX : posX);
		posY = mouseY - dragHoldY;
		posY = (posY < minY) ? minY : ((posY > maxY) ? maxY : posY);
		
		targetX = posX;
		targetY = posY;
	}
	
	function drawBoard(){
  		ctx.clearRect(0, 0 ,canvas.width, canvas.height);
		card.draw(ctx);	
	}
}