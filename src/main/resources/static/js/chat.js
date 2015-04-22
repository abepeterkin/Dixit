function Chat(game) {
  var text = $("#text");
  var messageText = $("#msg-txt");
  var sendButton = $("#send-msg-btn");
  sendButton.on("click", function() {
    var color = "blue"; //TEMPORARY
    var playerName = "Abe"; //TEMPORARY
    var gameName = "game"; //TEMPORARY
    var message = messageText.val();
    var postParameters = {
        gameName: gameName,
        playerName: playerName,
        message: message
    } 
    $.post("/addChat", postParameters, function(responseJSON){ 
      //do something with a response?
    });
    /*text.html(text.html() + "<p style=\"color:" + color + "\">"
        + name + ": " + messageText.val() + "<\p>");*/
    messageText.val("");
  });
}