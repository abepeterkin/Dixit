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
	<div class="modal fade" id="cardModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel"> </h4>
      </div>
      <div class="modal-body">
        <img id="cardImg" src="">
      </div>
    </div>
  </div>
</div>
</#assign>
<#include "main.ftl">