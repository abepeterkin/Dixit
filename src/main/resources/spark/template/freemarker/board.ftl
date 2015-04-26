<#assign content>
<input type="text" id="gameName" value="${gameName}" style="display: none">
<input type="text" id="playerId" value="${playerId}" style="display: none">
<div id="container2">
	<div id="container1">
		<div id="col1">
			<canvas id="board">No canvas support</canvas>
		</div>
		<div id="col2">
			<div id="music">music buttons go here</div>
			<div id="chat">
				<div id="chat-text">
					<p>chat text which sometimes could be very long because you know people like to make jokes and stuff but the text will still wrap nicely</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
					<p>chat text</p>
				</div>
				<div class="row" id="chat-input">
					<div class="col-md-12">
						<textarea class="form-control" rows="3"></textarea>
						<button class="form-control btn btn-primary">Send</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<button class ="btn btn-default" id="advancePhase">advance phase</button>
<p id="currentPhase"></p>
<p id="storyTeller"></p>
<p id="clientPlayer"></p>
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
<div class="modal fade" id="clueCardModal" tabindex="-1" role="dialog" aria-labelledby="clueCardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="clueTitle"></h4>
      </div>
      <div class="modal-body">
      	<p id="clueText"></p>
        <img id="cardImg" src="">
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
</#assign>
<#assign js>
	<script src="/js/infostore.js"></script>
	<script src="/js/icon.js"></script>
    <script src="/js/card.js"></script>
    <script src="/js/clue.js"></script>
    <script src="/js/game.js"></script>
    <script src="/js/sprite.js"></script>
    <script src="/js/player.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/board.js"></script>
    <script src="/js/chat.js"></script>
    <script src="/js/ajax.js"></script>
</#assign>
<#include "main.ftl">