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

Sprite.prototype.render = function(ctx, index) {

  // Clear the canvas

  var row = Math.floor(this.frameIndex / this.numberOfCols);
  var col = this.frameIndex - (row * this.numberOfCols);
  var sx = col * this.width / this.numberOfCols;
  var sy = row * this.height;
  /*var y = board.canvas.width - (board.canvas.width / 8);
  var x = (index * 128 / 5) - board.canvas.height / 2.8;*/
  var yCenter = board.canvas.width - (board.canvas.width / 8);
  var xCenter = (/*index*/ 2 * 128 / 5)  - board.canvas.height / 2.8;
  var x;
  var y;
  if (index === 0) {
    x = xCenter + board.canvas.height / 40;
    y = yCenter;
  } else if (index === 1) {
    x = xCenter + board.canvas.height / 40;
    y = yCenter + board.canvas.width / 40;
  } else if (index === 2) {
    x = xCenter;
    y = yCenter + board.canvas.width / 40;
  } else if (index === 3) {
    x = xCenter - board.canvas.height / 40;
    y = yCenter + board.canvas.width / 40;
  } else if (index === 4) {
    x = xCenter;
    y = yCenter - board.canvas.width / 40;
  } else {
    x = xCenter - board.canvas.height / 40;
    y = yCenter;
  }
  var rotateParams = this.rotate(-1);
  // Draw the animation
  ctx.save();
  ctx.translate(rotateParams.tx, rotateParams.ty);
  ctx.rotate(rotateParams.angle * Math.PI / 180);
  ctx.drawImage(this.image, sx, sy, this.width / this.numberOfCols,
      this.height, x, y, this.width / this.numberOfCols, this.height);
  ctx.restore();
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

Sprite.prototype.rotate = function(dir) {
  var tx, ty, angle;
  switch (dir) {
    case -1:// left
      tx = 0;
      ty = this.height;
      angle = 270;
      break;
  }
  return {
    tx : tx,
    ty : ty,
    angle : angle
  };
}