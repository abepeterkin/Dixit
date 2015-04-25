window.onload = function() {
  if(typeof(Storage) !== "undefined") {
    sessionStorage.gameName = $("#gameName").val();
    sessionStorage.playerId = $("#playerId").val();
    console.log(sessionStorage.gameName + " " + sessionStorage.playerId);
  } else {
    alert("ERROR: This browser doesn't support HTML5 local storage." 
        + "The game cannot be played properly.");
  }
}