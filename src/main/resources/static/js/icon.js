function Icon(options) {
  this.x = options.x;
  this.y = options.y;
  this.width = options.width;
  this.height = options.height;
  this.callback = options.callback;
  this.name = options.name;
}

Icon.prototype.clicked = function(clickX, clickY) {
  return ((clickX > this.x) && (clickX < this.x + this.width)
      && (clickY > this.y - 5) && (clickY < this.y + this.height + 5));
}