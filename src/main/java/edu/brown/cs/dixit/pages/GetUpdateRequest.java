package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.DixitGameSubscriber;
import gamestuff.Game;
import gamestuff.Player;

public class GetUpdateRequest implements TemplateViewRoute, DixitGameSubscriber {

	@Override
	public ModelAndView handle(Request req, Response res) {
		QueryParamsMap qm = req.queryMap();
		String gameName = qm.value("gameName");
		String playerName = qm.value("playerName");

		// TODO: Get game updates.

		Map<String, Object> variables = ImmutableMap.of("response", "[]");
		return new ModelAndView(variables, "response.ftl");
	}

	@Override
	public void handChanged(Game game, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerChanged(Game game, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameChanged(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void chatChanged(Game game) {
		// TODO Auto-generated method stub

	}
}
