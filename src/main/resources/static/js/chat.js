function Chat(game) {
  var text = $("#text");
  var messageText = $("#msg-txt");
  var sendButton = $("#send-msg-btn");
  sendButton.on("click", function() {
    console.log("gets called");
    var color = "blue";
    var name = "Abe";
    var game = "???";
    var message = messageText.val();
    var postParameters = {
        game: game,
        playerName: name,
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