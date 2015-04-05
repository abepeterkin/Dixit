package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class AddNonStoryCardRequest implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request req, Response res) {
		QueryParamsMap qm = req.queryMap();
		String gameName = qm.value("gameName");
		String playerName = qm.value("playerName");
		int cardId = Integer.parseInt(qm.value("cardId"));

		// TODO: Add non story card.

		Map<String, Object> variables = ImmutableMap.of("response", "true");
		return new ModelAndView(variables, "response.ftl");
	}
}