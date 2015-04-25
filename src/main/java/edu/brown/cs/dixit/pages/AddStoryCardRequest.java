package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Card;
import gamestuff.Game;
import gamestuff.Player;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddStoryCardRequest implements Route {

  @Override
  public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    String cardId = qm.value("cardId");
    String clue = qm.value("clue");

    Game game = Main.getGame(gameName);
    Player player = game.getPlayerWithId(playerId);
    if (!player.isStoryteller()) {
      return "false";
    }
    Card card = game.getCardWithId(cardId);
    game.submitStory(clue, card);

    return "true";
  }
}
