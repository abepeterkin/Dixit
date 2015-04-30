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
	if(player == undefined){
		this.messageBody.append('<p><strong style="color:#7A7A7A">' + msg + '</strong></p>');
	}else{
  this.messageBody.append('<p><span style="color:' + player.color + '"' + ">"
      + player.name + ": " + msg + "</span></p>");
	}
}

Chat.prototype.addSysMsg = function(msg, color) {
  console.log("system says", msg);
  this.messageBody
      .append(('<p style="color:' + color + '"' + ">" + msg + '</p>'));
}