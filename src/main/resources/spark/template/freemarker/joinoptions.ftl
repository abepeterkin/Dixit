<#assign content>
  <form method="POST" action="/addPlayer">
    <div class="form-group">
      <label for="gameName">Game Name</label>
      <input type="text" class="form-control" name="gameName" value="${gameName}" readonly required>
    </div>
    <div class="form-group">
      <label for="playerName">Player Name</label>
      <input type="text" class="form-control" name="playerName" placeholder="Your name" required>
    </div>
    <div class="form-group">
      <label for="color">Color</label>
      <select name="colorName" class="form-control">
          <option>Blue</option>
          <option>Red</option>
          <option>White</option>
          <option>Pink</option>
          <option>Yellow</option>
          <option>Green</option>
      </select>
    </div>
    <button type="submit" class="btn btn btn-primary">Join Game</button>
  </form>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">