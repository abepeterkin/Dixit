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
  messageText.keyup(function(e){
    var message = messageText.val();
    if(e.keyCode == 13){
      console.log(message.charCodeAt(message.length-1)===10);
      if(message.charCodeAt(message.length-1) === 10){
        console.log('inside if');
        message = message.substring(0, message.length-2);
      }
      console.log("message", message.length);
      addChatRequest(message, function(r){
        if(r){
          messageText.val("");
        }
      });
    }
  })
}
Chat.prototype.addMsg = function(msg, player) {
  var html;
	if(player == undefined){
		html = '<p><strong style="color:#7A7A7A">' + msg + '</strong></p>';
	}else{
	  html = '<p><span style="color:' + player.color + '"' + ">"
      + player.name + ": " + msg + "</span></p>";
	}
	this.messageBody.append(html);
	$("#chat-text").scrollTop($("#chat-text")[0].scrollHeight);
}

Chat.prototype.addSysMsg = function(msg, color) {
  console.log("system says", msg);
  var html = '<p style="color:' + color + '"' + ">" + msg + '</p>';
  this.messageBody.append((html));
  $("#chat-text").scrollTop($("#chat-text")[0].scrollHeight);
}