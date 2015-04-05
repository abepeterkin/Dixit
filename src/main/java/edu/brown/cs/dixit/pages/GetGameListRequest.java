package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class GetGameListRequest implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request req, Response res) {
		// QueryParamsMap qm = req.queryMap();

		// TODO: Get a list of games.

		Map<String, Object> variables = ImmutableMap.of("response", "[]");
		return new ModelAndView(variables, "response.ftl");
	}
}