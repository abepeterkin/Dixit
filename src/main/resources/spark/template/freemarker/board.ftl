<#assign content>
	<div class="container">	
	 <div class="rightCol">
	   <div class="border"id="music">music controls go here</div>
	   <div class="chat">
	     <div class="border" id="text"> chat text goes here</div>
	     <div class="msg border">
	       <textarea id="msg-txt"></textarea>
	       <input class="btn btn-primary" id="send-msg-btn" type="button" value="Send">
	     </div>
	   </div>
	 </div>
	 <div class="leftCol">
	   <canvas class="border" id="board"></canvas>
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
      <div class="modal-footer">
      	<button id="send-card-btn" type="button" class="btn btn-default" disabled>Send Card</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="sendClueModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true"data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="exampleModalLabel">Send Clue </h4>
      </div>
      <div class="modal-body">
      	<input type="text" id="clueInput" class="form-control" placeholder="Enter clue here..."/>
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="false">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
        <li data-target="#carousel-example-generic" data-slide-to="4"></li>
    
    
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <input id="cardId" value="0"hidden/>
      <img id="card0" src="">
    </div>
    <div class="item">
     <input id="cardId" value="1"hidden/>
       <img id="card1" src="">
    </div>
     <div class="item">
      <input id="cardId" value="2"hidden/>
       <img id="card2" src="">
    </div>
     <div class="item">
      <input id="cardId" value="3"hidden/>
       <img id="card3" src="">
    </div>
     <div class="item">
      <input id="cardId" value="4"hidden/>
       <img id="card4" src="">

    </div>
  </div>
  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
      </div>
      <div class="modal-footer">
      	<button id="send-clue-btn" type="button" class="btn btn-default" disabled>Send Clue</button>
      </div>
    </div>
  </div>
</div>
<button class ="btn btn-default" id="advancePhase">advance phase</button>
<p id="currentPhase"></p>
<p id="storyTeller"></p>
<p id="clientPlayer"></p>
</#assign>
<#assign js>
	<script src="/js/icon.js"></script>
    <script src="/js/card.js"></script>
    <script src="/js/clue.js"></script>
    <script src="/js/game.js"></script>
    <script src="/js/sprite.js"></script>
    <script src="/js/player.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/board.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">