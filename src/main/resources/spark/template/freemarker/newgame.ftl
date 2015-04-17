<#assign content>
  <div class="container">
  	<form>
  <div class="form-group">
    <label for="gameName">Game name</label>
    <input type="text" class="form-control" id="gameName" placeholder="choose a nice name">
  </div>
  <div class="form-group">
    <label for="playerName">Player Name</label>
    <input type="text" class="form-control" id="playerName" placeholder="Your name">
  </div>
    <div class="form-group">
    <label for="color">Color</label>
    <select class="form-control">
    	<option>Blue</option>
    	<option>Red</option>
    	<option>White</option>
    	<option>Pink</option>
    	<option>Yellow</option>
    	<option>Green</option>
    </select>
  </div>
  <div class="radio">
    <label>
      <input type="radio" name="numPlayers" id="threePlayers" value=3> 3 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numPlayers" id="fourPlayers" value=4> 4 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numPlayers" id="fivePlayers" value=5> 5 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numPlayers" id="sixPlayers" value=6> 6 Players
    </label>
  </div>
  <button type="submit" class="btn btn-default">Create Game</button>
</form>
  </div>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">