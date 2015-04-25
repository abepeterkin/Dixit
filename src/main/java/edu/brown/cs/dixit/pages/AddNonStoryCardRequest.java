package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Card;
import gamestuff.Game;
import gamestuff.Player;

import java.util.Map;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;

public class AddNonStoryCardRequest implements Route {

  @Override
  public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    String cardId = qm.value("cardId");

    Game game = Main.getGame(gameName);
    if (game == null) {
      return "false";
    }
    Player player = game.getPlayerWithId(playerId);
    if (player == null) {
      return "false";
    }
    if (player.isStoryteller()) {
      return "false";
    }
    Card card = game.getCardWithId(cardId);
    if (card == null) {
      return "false";
    }
    game.addCardToTable(player, card);

    Map<String, Object> variables = ImmutableMap.of("response", "true");
    return "true";
  }
}