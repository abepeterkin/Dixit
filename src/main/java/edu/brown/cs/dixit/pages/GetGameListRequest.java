package edu.brown.cs.dixit.pages;

import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.dixit.Main;

public class GetGameListRequest implements TemplateViewRoute {

  private final static Gson GSON = new Gson();

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    // QueryParamsMap qm = req.queryMap();

    List<String> tempList = Main.getGameNameList();

    Map<String, Object> variables = ImmutableMap.of("response",
        GSON.toJson(tempList));
    return new ModelAndView(variables, "response.ftl");
  }
}