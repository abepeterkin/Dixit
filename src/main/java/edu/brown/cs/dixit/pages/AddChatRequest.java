package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class AddChatRequest implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request req, Response res) {
		QueryParamsMap qm = req.queryMap();
		String gameName = qm.value("gameName");
		String playerName = qm.value("playerName");
		String message = qm.value("message");

		// TODO: Add chat.

		Map<String, Object> variables = ImmutableMap.of("response", "true");
		return new ModelAndView(variables, "response.ftl");
	}
}