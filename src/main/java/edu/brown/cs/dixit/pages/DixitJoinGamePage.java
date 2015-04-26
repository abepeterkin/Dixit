package edu.brown.cs.dixit.pages;

import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.Main;

/**
 * Presents the user with a page where they can join a game.
 */
public class DixitJoinGamePage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    List<String> tempList = Main.getGameNameList();
    Map<String, Object> variables = ImmutableMap.<String, Object> builder()
        .put("title", "Dixit").put("games", tempList).build();
    return new ModelAndView(variables, "joingame.ftl");
  }
}
