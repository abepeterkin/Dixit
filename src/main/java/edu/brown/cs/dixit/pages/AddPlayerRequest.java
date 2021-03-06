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
 *
 * Lets a player join a game.
 */
public class AddPlayerRequest implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");
    String colorName = qm.value("colorName");

    if (!Main.gameExists(gameName)) {
      return failure("That game does not exist.");
    } else {
      Game game = Main.getGame(gameName);
      if (game.getColorsInUse().contains(colorName)) {
        return failure("That color is already being used. "
            + "Please select a different color.");
      }
      String newId = Main.newId();
      Player player = new Player(newId, playerName, colorName);
      if (game.addPlayer(player)) {
        Map<String, Object> variables = ImmutableMap.of("response",
            "Game join successful.", "gameName", game.getName(), "playerId",
            newId);
        return new ModelAndView(variables, "board.ftl");
      } else {
        return failure("Sorry, you could not join this game.");
      }
    }

  }

  private ModelAndView failure(
      String message) {
    Map<String, Object> variables = ImmutableMap.of("response", message);
    return new ModelAndView(variables, "response.ftl");
  }
}
