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

public class AddChatRequest implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    String message = qm.value("message");
    Game game = Main.getGame(gameName);
    Player player = game.getPlayerWithId(playerId);
    if (game == null) {
      Map<String, Object> variables = ImmutableMap.of("response", "false");
      return new ModelAndView(variables, "response.ftl");
    } else {
      game.addToChat(player.getChatName(), message);
      Map<String, Object> variables = ImmutableMap.of("response", "true");
      return new ModelAndView(variables, "response.ftl");
    }
  }
}