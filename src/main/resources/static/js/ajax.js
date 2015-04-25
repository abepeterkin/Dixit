
function getGameRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.get("/getGame", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function getUpdateRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.get("/getUpdate", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function addStoryCardRequest(cardId, clue, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "cardId": cardId, "clue", clue};
  $.get("/addStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function addNonStoryCardRequest(cardId, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "cardId": cardId};
  $.get("/addNonStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function removeNonStoryCardRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.get("/removeNonStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function voteForCardRequest(cardId, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "cardId": cardId};
  $.get("/voteForCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function removeVoteForCardRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.get("/removeVoteForCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function addChatRequest(message, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "message": message};
  $.get("/addChat", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}
