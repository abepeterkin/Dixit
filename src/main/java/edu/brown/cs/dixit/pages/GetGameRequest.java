package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class GetGameRequest implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request req, Response res) {
		QueryParamsMap qm = req.queryMap();
		String gameName = qm.value("gameName");

		// TODO: Retrieve the game.

		Map<String, Object> variables = ImmutableMap.of("response", "{}");
		return new ModelAndView(variables, "response.ftl");
	}
}
