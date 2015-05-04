<#assign content>
<input type="text" id="gameName" value="${gameName}" style="display: none">
<input type="text" id="playerId" value="${playerId}" style="display: none">
<div id="container2">
	<div id="container1">
		<div id="col1">
		  <audio id="music" autoplay loop>
            <source src="http://www.googledrive.com/host/0B0SSL1NefHURd3dJRXozZ3h6anc" type="audio/mpeg">
            Your browser does not support the audio element.
          </audio>
          <img id="music-button" src="/images/volume-on.png" style="position: absolute">
		  <canvas id="board">No canvas support</canvas>
		</div>
		<div id="col2">
			<div id="chat">
				<div id="chat-text">
				</div>
				<div class="row" id="chat-input">
					<div class="col-md-12">
						<textarea id="msg-txt" class="form-control" rows="3"></textarea>
						<button id="send-msg-btn" class="form-control btn btn-primary">Send</button>
					</div>
					<br>
					<button id="advance-btn" class="form-control btn btn-primary">Next Round!</button>
				</div>
				<div id="player-names">
				</div>		
			</div>
	</div>
</div>
<div class="modal fade" id="cardModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">Hand Card </h4>
      </div>
      <div class="modal-body">
        <img id="cardImg" src="">
        <br>    <br>          
        <button id="send-card-btn" type="button" class="btn btn-primary" disabled>Send Card</button>
      </div>
      
    </div>
  </div>
</div>
<div class="modal fade" id="sendClueModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">Send Clue </h4>
      </div>
      <div class="modal-body">
      	<input type="text" id="clueInput" class="form-control" placeholder="Enter clue here..."/>
         <button id="send-clue-btn" type="button" class="btn btn-primary" disabled>Send Clue</button>
        <img id="cardImg" src="">
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
    <script src="/js/music.js"></script>
</#assign>
<#include "main.ftl">