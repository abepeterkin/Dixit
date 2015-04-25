package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddChatRequest implements Route {

  @Override
  public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    String message = qm.value("message");
    Game game = Main.getGame(gameName);
    Player player = game.getPlayerWithId(playerId);
    if (game == null) {
      return "false";
    } else {
      game.addToChat(player.getChatName(), message);
      return "true";
    }
  }
}