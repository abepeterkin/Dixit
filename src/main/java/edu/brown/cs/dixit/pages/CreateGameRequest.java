package edu.brown.cs.dixit.pages;

import java.util.ArrayList;
import java.util.List;
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
 * Creates a new game.
 */
public class CreateGameRequest implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");
    String colorName = qm.value("colorName");
    int numberOfPlayers = Integer.parseInt(qm.value("numberOfPlayers"));
    int numberOfCards = Integer.parseInt(qm.value("numberOfCards"));

    if (Main.gameExists(gameName)) {
      return failure("A game with that name already exists.");
    } else {
      String newId = Main.newId();
      Player tempPlayer = new Player(newId, playerName, colorName);
      List<Player> tempPlayerList = new ArrayList<Player>();
      tempPlayerList.add(tempPlayer);
      Game tempGame = new Game(gameName, numberOfPlayers, numberOfCards,
          tempPlayerList);
      Main.addGame(gameName, tempGame);
      Map<String, Object> variables = ImmutableMap.of("response",
          "Game created.", "gameName", gameName, "playerId", newId);
      return new ModelAndView(variables, "board.ftl");
    }
  }

  private ModelAndView failure(String message) {
    Map<String, Object> variables = ImmutableMap.of("response", message);
    return new ModelAndView(variables, "response.ftl");
  }
}
