<#assign content>
  <h2>
  	PLAYER SCORES
  </h2>
  <#list players as player>
  	<p>
  		Player name: ${player["name"]}
   		<br />
  		Player color: ${player["color"]}
  		<br />
  		Player score: ${player["score"]}
  	</p>
  </#list>
</#assign>
<#assign js>
</#assign>
<#include "main.ftl">