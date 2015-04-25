function Chat(player) {
  var gameName = player.game.name;
  var text = $("#text");
  var messageText = $("#msg-txt");
  var sendButton = $("#send-msg-btn");
  sendButton.on("click", function() {
    var color = player.color;
    var playerName = player.name;
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