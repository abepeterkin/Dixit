window.onload = function() {
  var music = $('#music');
  var ctrl = $('#music-button');

  ctrl.click(function() {
    if (music.prop('muted')) {
      ctrl.attr('src', '/images/volume-on.png');
      music.prop('muted', false);
    } else {
      ctrl.attr('src', '/images/volume-off.png');
      music.prop('muted', true);
    }
  });
}