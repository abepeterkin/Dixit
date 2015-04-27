function Chat() {
  this.messageBody = $('#chat-text');
  var messageText = $("#msg-txt");
  var sendButton = $("#send-msg-btn");
  sendButton.on("click", function() {
    var message = messageText.val();
    addChatRequest(message, function(responseObj) {
    })
    messageText.val("");
  });
}
Chat.prototype.addMsg = function(msg, player) {
  this.messageBody.append('<p><span style="color:' + player.color + '"' + ">"
      + player.name + ":</span> " + msg + '</p>');
}