function Chat(player) {
  this.messageBody = $('#chat-text');
  var messageText = $("#msg-txt");
  var sendButton = $("#send-msg-btn");
  sendButton.on("click", function() {
    var color = player.color;
    var message = messageText.val();
    addChatRequest(message, function(responseObj) {
      console.log(responseObj);
    })
    /*
     * text.html(text.html() + "<p style=\"color:" + color + "\">" + name + ": " +
     * messageText.val() + "<\p>");
     */
    messageText.val("");
  });
}
Chat.prototype.addMsg = function(msg) {
  this.messageBody.append('<p>' + msg + '</p>');
}