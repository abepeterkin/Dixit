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
<div class="modal fade" id="rulesModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">Dixit Rules </h4>
      </div>
      <div class="modal-body">
      	<div role="tabpanel">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li id="initTab" role="presentation" class="active"><a href="#init" aria-controls="init" role="tab" data-toggle="tab">Initialization</a></li>
    <li id="storyTellerTab" role="presentation"><a href="#storyteller" aria-controls="storyteller" role="tab" data-toggle="tab">Storyteller</a></li>
    <li id="nonStoryTellerTab" role="presentation"><a href="#nonstoryteller" aria-controls="nonstoryteller" role="tab" data-toggle="tab">Card Selection</a></li>
    <li id="votingTab" role="presentation"><a href="#voting" aria-controls="voting" role="tab" data-toggle="tab">Voting</a></li>
  	<li id="scoringTab" role="presentation"><a href="#scoring" aria-controls="scoring" role="tab" data-toggle="tab">Scoring</a></li>
  	
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="init">
    	<p>Dixit will commence once the specified number of players have joined the game. Until then, use the chat to talk with your party!</p>
    </div>
    <div role="tabpanel" class="tab-pane" id="storyteller">
    	<p>Players wait for the storyteller to pick a card and submit an according clue. The purpose of the clue is to draw votes during the voting phase. If a clue is too difficult and no players vote for the storyteller's card, all players will receive 2 points and the storyteller will receive none. The same will occur if a clue is too simple and all players vote for the storyteller's card. Pick a challenging clue that will still draw votes!</p>
		<img src="https://boardgamegeek.com/camo/c8c35517fe4cdc5c44c94f18e237a5638082d6e2/687474703a2f2f7777772e6f682d63617264732d696e737469747574652e6f72672f77702d636f6e74656e742f75706c6f6164732f323031322f30382f32303132303830382d636f70652d37382d393435392d726e642e6a7067" alt="man going inside egg">
		    	<p>Example of an overly simple clue: “Man stepping into egg.”</p>
		<p>Example of an overly difficult clue: “Strife.”</p>
    	
    </div>
    <div role="tabpanel" class="tab-pane" id="nonstoryteller">
    	<p>All non-storyteller players will submit a card they believe best matches the current story. Any votes on their card during the voting phase will earn them 1 point, so the more convincing the card, the better! Once all players have submitted cards, the game will submit to voting phase.</p>
    </div>
    <div role="tabpanel" class="tab-pane" id="voting">
    	<p>All non-storyteller players vote for which card they believe belongs to the storyteller. Players cannot vote for their own card. Votes will remain anonymous during the voting phase, though other players will be able to see who has casted their vote. Once all players have casted votes, the game will advance to scoring phase.</p>
    </div>
  	<div role="tabpanel" class="tab-pane" id="scoring">
  	<p>Players will increment score based on points earned during voting round. If no player crosses 30 points during scoring phase, the game will wait until all players have clicked the “next round” button, and then will advance to the next round. If any player ends scoring phase with at least 30 points, the game will end and a scoreboard will display.</p>
	<p>Each round is scored according to the following scoring cases:
		<ul>
			<li>If all players vote for the storyteller card: each player will receive 2 points and the storyteller will receive 0 points.</li>
			<li>If no players vote for the storyteller card: each player will receive 2 points and the storyteller will receive 0 points. (Non-storyteller players will also receive points for votes on their card.)</li>
			<li>If the storyteller receives some but not all votes on her card – she will receive 2 points for the initial vote and 1 point for each extra vote.</li>
			<li>Non-storyteller players receive 2 points if they vote for the storyteller's card.</li>
			<li>Non-storyteller players receive 1 point for each vote on their card.</li>
		</ul>
	</p>
</div>
  	
  </div>

</div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="finalScoresModal" tabindex="-1" role="dialog" aria-labelledby="cardModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title" id="exampleModalLabel">Final Scores!</h4>
      </div>
      <div class="modal-body" id="finalScores">
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
