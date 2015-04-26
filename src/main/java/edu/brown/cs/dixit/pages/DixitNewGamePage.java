package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

/**
 * Presents the user with a page letting them set options for a new game.
 */
public class DixitNewGamePage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Dixit");
    return new ModelAndView(variables, "newgame.ftl");
  }

}
