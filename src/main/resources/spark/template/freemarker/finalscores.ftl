<#assign content>
  <div class="container" id="final-scores">
  <h2>
  	PLAYER SCORES
  </h2>
  <#list players as player>
  	<p style="color:${player["color"]}">
  		Player name: ${player["name"]}
   		<br />
  		Player color: ${player["color"]}
  		<br />
  		Player score: ${player["score"]}
  	</p>
  </#list>
  <br>
  <a href="/" class="btn btn-primary">Return To Home</a>
  <div>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">