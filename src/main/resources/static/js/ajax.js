
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
      "cardId": cardId, "clue": clue};
  $.post("/addStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function addNonStoryCardRequest(cardId, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "cardId": cardId};
  $.post("/addNonStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function removeNonStoryCardRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.post("/removeNonStoryCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function voteForCardRequest(cardId, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "cardId": cardId};
  $.post("/voteForCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function removeVoteForCardRequest(responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId};
  $.post("/removeVoteForCard", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function addChatRequest(message, responseFunction) {
  var tempMap = {"gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "message": message};
  $.post("/addChat", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  });
}

function readyRequest(responseFunction) {
  var tempMap = {
      "gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId
  }
  $.post("/playerReady", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  })
}

function addScoreRequest(score, responseFunction) {
  var tempMap = {
      "gameName": sessionStorage.gameName,
      "playerId": sessionStorage.playerId,
      "score": score
  }
  $.post("/addScore", tempMap, function(responseJson){
    responseObject = JSON.parse(responseJson);
    responseFunction(responseObject);
  })
}
