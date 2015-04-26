package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

/**
 * Presents the user with a board page.
 */
public class DixitMainPage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Dixit");
    return new ModelAndView(variables, "board.ftl");
  }

}
