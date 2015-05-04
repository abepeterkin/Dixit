<#assign content>
	<div class="container">
		<h2>Current Games</h2>
		<table id="table" class="table table-hover table-striped">
			<tr>
				<th>Game name</th>
				<th>Current players</th>
				<th>Max players</th>
				<th></th>
			</tr>
		</table>
		<p>
			<strong>Note:</strong> Games will be automatically deleted after a long period of inactivity.
		</p>
	</div>
</#assign>
<#assign js>
<script src="/js/viewgames.js"></script>
</#assign>
<#include "main.ftl">