package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class PlayerReadyRequest implements Route {

  @Override
  public Object handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    Game game = Main.getGame(gameName);
    if (game == null) {
      return "false";
    }
    Player player = game.getPlayerWithId(playerId);
    if (player == null) {
      return "false";
    } else {
      return game.confirmPlayerReady(player);
    }
  }

}
