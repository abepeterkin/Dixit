<#assign content>
${response}
<br>
<a href="/board" class ="btn btn-primary">Play the game</a>
<input type="text" id="gameName" value="${gameName}" style="display: none">
<input type="text" id="playerId" value="${playerId}" style="display: none">
</#assign>
<#assign js>
<script src="/js/infostore.js"></script>
</#assign>
<#include "main.ftl">