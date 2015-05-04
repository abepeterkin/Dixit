<#assign content>
  <div class="container">
  	<form method="POST" action="/createGame">
  <div class="form-group">
    <label for="gameName">Game name</label>
    <input type="text" class="form-control" name="gameName" placeholder="choose a nice name" required>
  </div>
  <div class="form-group">
    <label for="playerName">Player Name</label>
    <input type="text" class="form-control" name="playerName" placeholder="Your name" required>
  </div>
    <div class="form-group">
    <label for="color">Color</label>
    <select name="colorName" class="form-control" required>
        <option value="">Pick a color</option>
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
      <input type="radio" name="numberOfPlayers" id="twoPlayers" value=2> 2 Players
    </label>
  <div class="radio">
    <label>
      <input type="radio" name="numberOfPlayers" id="threePlayers" value=3 checked="checked"> 3 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numberOfPlayers" id="fourPlayers" value=4> 4 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numberOfPlayers" id="fivePlayers" value=5> 5 Players
    </label>
  </div>
    <div class="radio">
    <label>
      <input type="radio" name="numberOfPlayers" id="sixPlayers" value=6> 6 Players
    </label>
  </div>
  Number of cards in hand: 
  <select name="numberOfCards">
    <option value="3">3</option>
    <option value="4">4</option>
    <option value="5">5</option>
    <option value="6" selected="selected">6</option>
  </select> <br>
  <button type="submit" class="btn btn-primary">Create Game</button>
</form>
  </div>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">