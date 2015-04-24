function viewGames(table) {
  $.get("/seeCurrentGames", function(responseJSON) {
    var responseObject = JSON.parse(responseJSON);
    var gameDataList = responseObject.data;
    for (var j = 0; j < gameDataList.length; j++) {
      var gameData = gameDataList[j];
      var playerNameString = "";
      var playerNames = gameData.playerNames;
      for (var i = 0; i < playerNames.length; i++) {
        playerNameString = playerNameString.concat(playerNames[i]);
        if ((i + 1) < playerNames.length) {
          playerNameString = playerNameString.concat(", ");
        }
      }
      var newRow = "<tr>"
         + "<td>" + gameData.gameName + "</td>"
         + "<td>" + playerNameString+ "</td>"
         + "<td>" + gameData.maxPlayers + "</td>"
         + "<td><a href=\"/joinOptions/" 
         + gameData.gameName + "\" class=\"btn btn-primary\">Join</td>"
         + "</tr>";
      table.html(table.html() + newRow);
    }
  });
}
window.onload = function() {
  var table = $('#table');
  viewGames(table);
}
