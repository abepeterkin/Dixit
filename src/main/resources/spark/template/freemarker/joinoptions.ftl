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
      <select name="colorName" class="form-control" required>
            <option value="">Pick a color</option>
          <#if !usedColors?seq_contains("Blue")>
          	<option>Blue</option>
          </#if>
          <#if !usedColors?seq_contains("Red")>
          	<option>Red</option>
          </#if>
          <#if !usedColors?seq_contains("White")>
          	<option>White</option>
          </#if>
          <#if !usedColors?seq_contains("Pink")>
          	<option>Pink</option>
          </#if>
          <#if !usedColors?seq_contains("Yellow")>
          	<option>Yellow</option>
          </#if>
          <#if !usedColors?seq_contains("Green")>
          	<option>Green</option>
          </#if>
      </select>
    </div>
    <button type="submit" class="btn btn btn-primary">Join Game</button>
  </form>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">