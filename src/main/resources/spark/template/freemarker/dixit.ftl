<#assign content>
	<div class="container">	
	 <div class="right">
	   <div class="border"id="music">music controls go here</div>
	   <div class="chat">
	     <div class="border" id="text"> chat text goes here</div>
	     <div class="msg border">
	       <textarea id="msg-txt"></textarea>
	       <input class="btn btn-primary" id="send-msg-btn" type="button" value="Send">
	     </div>
	   </div>
	 </div>
	 <div class="left">
	   <canvas width="880" height="775" class="border" id="board"></canvas>
	 </div>
	</div>
</#assign>
<#include "main.ftl">