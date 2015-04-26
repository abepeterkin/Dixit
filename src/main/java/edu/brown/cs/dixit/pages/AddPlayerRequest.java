package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;

/**
 * Lets a player join a game.
 */
public class AddPlayerRequest implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");
    String colorName = qm.value("colorName");

    if (!Main.gameExists(gameName)) {
      return failure("game does not exist");
    } else {
      String newId = Main.newId();
      Player player = new Player(newId, playerName, colorName);
      Game game = Main.getGame(gameName);
      if (game.addPlayer(player)) {
        Map<String, Object> variables = ImmutableMap.of("response",
            "Game join successful.", "gameName", game.getName(), "playerId",
            newId);
        return new ModelAndView(variables, "board.ftl");
      } else {
        return failure("player could not be added");
      }
    }

  }

  private ModelAndView failure(String message) {
    Map<String, Object> variables = ImmutableMap.of("success", "false",
        "error", message);
    return new ModelAndView(variables, "reponse.ftl");
  }
}
