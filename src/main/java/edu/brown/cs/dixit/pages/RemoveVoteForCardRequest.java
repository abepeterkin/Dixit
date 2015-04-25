package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class RemoveVoteForCardRequest implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");

    Game game = Main.getGame(gameName);
    Player player = game.getPlayerByName(playerName);
    if (player.isStoryteller()) {
      Map<String, Object> variables = ImmutableMap.of("response", "false");
      return new ModelAndView(variables, "response.ftl");
    }
    // TODO: Remove vote.

    Map<String, Object> variables = ImmutableMap.of("response", "true");
    return new ModelAndView(variables, "response.ftl");
  }
}