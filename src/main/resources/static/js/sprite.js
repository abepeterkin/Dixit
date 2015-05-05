function Sprite(options) {

  this.frameIndex = 0;
  this.tickCount = 0;
  this.ticksPerFrame = options.ticksPerFrame;
  this.numberOfFrames = options.numberOfFrames;
  this.numberOfCols = options.numberOfCols;
  this.width = options.width;
  this.height = options.height;
  this.image = options.image;
}

Sprite.prototype.render = function(ctx, index, player) {

  var row = Math.floor(this.frameIndex / this.numberOfCols);
  var col = this.frameIndex - (row * this.numberOfCols);
  var sx = col * this.width / this.numberOfCols;
  var sy = row * this.height; 
  var w = board.canvas.width/10;
  var h =  board.canvas.height/5;
  var pos = this.getPosition(index, player.score);
  ctx.save();
  board.ctx.translate(pos.x,pos.y);
  board.ctx.translate(w/2,h/2);
  board.ctx.rotate(pos.angle);
  ctx.drawImage(this.image, sx, sy, this.width / this.numberOfCols,
      this.height, -w/2,-h/2,w ,h);
  ctx.restore();
}

Sprite.prototype.animate = function(ctx, index, player) {
  var newPos = this.getPosition(index, player.newScore);
  if(player.currPos.x < newPos.x){
    player.moving = false;
    player.score = player.newScore;
  }
  player.currPos.x -= board.canvas.width/4000;
  var row = Math.floor(this.frameIndex / this.numberOfCols);
  var col = this.frameIndex - (row * this.numberOfCols);
  var sx = col * this.width / this.numberOfCols;
  var sy = row * this.height; 
  var w = board.canvas.width/10;
  var h =  board.canvas.height/5;
  var pos = this.getPosition(index, player.score);
  ctx.save();
  board.ctx.translate(player.currPos.x,pos.y);
  board.ctx.translate(w/2,h/2);
  board.ctx.rotate(pos.angle);
  ctx.drawImage(this.image, sx, sy, this.width / this.numberOfCols,
      this.height, -w/2,-h/2,w ,h);
  ctx.restore();
}
Sprite.prototype.getPosition = function(index, score){
  var yCenter;
  var xCenter;
  var angle;
  switch (score) {
    case 0:
      xCenter = board.canvas.width - (board.canvas.width / 7.3);
      yCenter = board.canvas.height-(board.canvas.height/1.3);
      angle = 3*Math.PI/2;
      break;
    case 1:
      yCenter = board.canvas.height-(board.canvas.height/1.35);
      xCenter = board.canvas.width - (board.canvas.width / 4.5);
      angle = 3*Math.PI/2;
      break;
    case 2:
      yCenter = board.canvas.height-(board.canvas.height/1.35);
      xCenter = board.canvas.width - (board.canvas.width / 3.5);
      angle = 3*Math.PI/2;
      break;
    case 3:
      yCenter = board.canvas.height-(board.canvas.height/1.35);
      xCenter = board.canvas.width - (board.canvas.width / 2.9);
      angle = 3*Math.PI/2;
      break;
    case 4:
      yCenter = board.canvas.height-(board.canvas.height/1.4);
      xCenter = board.canvas.width - (board.canvas.width / 2.45);
      angle = 3*Math.PI/2;
      break;
    case 5:
      yCenter = board.canvas.height-(board.canvas.height/1.28);
      xCenter = board.canvas.width - (board.canvas.width / 2.2);
      angle = 3*Math.PI/2;
      break;
    case 6:
      yCenter = board.canvas.height-(board.canvas.height/1.45);
      xCenter = board.canvas.width - (board.canvas.width / 2);
      angle = 3*Math.PI/2;
      break;
    case 7:
      yCenter = board.canvas.height-(board.canvas.height/1.30);
      xCenter = board.canvas.width - (board.canvas.width / 1.8);
      angle = 3*Math.PI/2;
      break;
    case 8:
      yCenter = board.canvas.height-(board.canvas.height/1.45);
      xCenter = board.canvas.width - (board.canvas.width / 1.65);
      angle = 3*Math.PI/2;
      break;
    case 9:
      yCenter = board.canvas.height-(board.canvas.height/1.33);
      xCenter = board.canvas.width - (board.canvas.width / 1.5);
      angle = 3*Math.PI/2;
      break;
    case 10:
      yCenter = board.canvas.height-(board.canvas.height/1.38);
      xCenter = board.canvas.width - (board.canvas.width / 1.35);
      angle = 3*Math.PI/2;
      break;
    case 11:
      yCenter = board.canvas.height-(board.canvas.height/1.33);
      xCenter = board.canvas.width - (board.canvas.width / 1.24);
      angle = 3*Math.PI/2;
      break;
    case 12:
      yCenter = board.canvas.height-(board.canvas.height/1.38);
      xCenter = board.canvas.width - (board.canvas.width / 1.16);
      angle = 5*Math.PI/4;
      break;
    case 13:
      yCenter = board.canvas.height-(board.canvas.height/1.45);
      xCenter = board.canvas.width - (board.canvas.width / 1.06);
      angle = 3*Math.PI/2;
      break;
    case 14:
      yCenter = board.canvas.height-(board.canvas.height/1.3);
      xCenter = board.canvas.width - (board.canvas.width / 1.01);
      angle = 0;
      break;
    case 15:
      yCenter = board.canvas.height-(board.canvas.height/1.1);
      xCenter = board.canvas.width - (board.canvas.width / 1.01);
      angle = Math.PI/4;
      break;
    case 16:
      yCenter = board.canvas.height-(board.canvas.height/1.04);
      xCenter = board.canvas.width - (board.canvas.width / 1.08);
      angle = Math.PI/4;
      break;
    case 17:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 1.17);
      angle = Math.PI/2;
      break;
    case 18:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 1.3);
      angle = Math.PI/2;
      break;
    case 19:
      yCenter = board.canvas.height-(board.canvas.height/1.12);
      xCenter = board.canvas.width - (board.canvas.width / 1.37);
      angle = Math.PI/2;
      break;
    case 20:
      yCenter = board.canvas.height-(board.canvas.height/1.12);
      xCenter = board.canvas.width - (board.canvas.width / 1.47);
      angle = Math.PI/4;
      break;
    case 21:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 1.55 );
      angle = Math.PI/2; 
      break;
    case 22:
      yCenter = board.canvas.height-(board.canvas.height/1.05);
      xCenter = board.canvas.width - (board.canvas.width / 1.72  );
      angle = Math.PI/2;   
      break;
    case 23:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 1.92 );
      angle = Math.PI/2;   
      break;
    case 24:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 2.21 );
      angle = Math.PI/2; 
      break;
    case 25:
      yCenter = 0;
      xCenter = board.canvas.width - (board.canvas.width / 2.55 );
      angle = Math.PI/2;   
      break;
    case 26:
      yCenter = board.canvas.height-(board.canvas.height/1.08);
      xCenter = board.canvas.width - (board.canvas.width / 2.8 );
      angle = Math.PI/2  
      break;
    case 27:
      yCenter = board.canvas.height-(board.canvas.height/1.08);
      xCenter = board.canvas.width - (board.canvas.width / 3.2 );
      angle = Math.PI/2      
      break;
    case 28:
      yCenter = board.canvas.height-(board.canvas.height/1.05);
      xCenter = board.canvas.width - (board.canvas.width / 3.9 );
      angle = Math.PI/2      
      break;
    case 29:
      yCenter = board.canvas.height-(board.canvas.height/1.05);
      xCenter = board.canvas.width - (board.canvas.width / 5.2 );
      angle = Math.PI/2 
      break;
    case 30:
      yCenter = board.canvas.height-(board.canvas.height/1.01);
      xCenter = board.canvas.width - (board.canvas.width / 7 );
      angle = Math.PI/2 
      break;
    default:
      yCenter = board.canvas.width - (board.canvas.width / 8);
      xCenter = 51 - board.canvas.height / 2.8;
      angle = 3*Math.PI/2;
    }
    var x;
    var y;
    if (index === 0) {
      x = xCenter;
      y = yCenter;
    } else if (index === 1) {
      x = xCenter;
      y = yCenter+1.5*board.canvas.height/40;
    } else if (index === 2) {
      x = xCenter
      y = y = yCenter+3*board.canvas.height/40;
    } else if (index === 3) {
      x = xCenter+board.canvas.width/40;
      y = y = yCenter;
    } else if (index === 4) {
      x = xCenter+board.canvas.width/40;
      y = y = yCenter+1.5*board.canvas.height/40;
    } else {
      x = xCenter+board.canvas.width/40;
      y = y = yCenter+3*board.canvas.height/40;
    }
    return{x:x, y:y, angle:angle}
}
Sprite.prototype.update = function() {
  this.tickCount += 1;

  if (this.tickCount > this.ticksPerFrame) {
    this.tickCount = 0;
    // If the current frame index is in range
    if (this.frameIndex < this.numberOfFrames - 1) {
      // Go to the next frame
      this.frameIndex += 1;
    } else {
      this.frameIndex = 0;
    }
  }
}