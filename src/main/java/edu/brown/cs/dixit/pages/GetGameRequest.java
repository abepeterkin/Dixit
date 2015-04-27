package edu.brown.cs.dixit.pages;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.JsonElement;

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;

/**
 * Retrieves a deep serialization of a game.
 */
public class GetGameRequest implements Route {
  private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();

  @Override
  public Object handle(Request req, Response res) {
    System.out.println("getting game request");
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");
    Game tempGame = Main.getGame(gameName);
    if (tempGame == null) {
      return "false";
    }
    System.out.println("getting player");
    Player tempPlayer = tempGame.getPlayerWithId(playerId);
    System.out.println("got player " + tempPlayer.getChatName() + " "
        + tempPlayer.getId());
    if (tempPlayer == null) {
      return "false";
    }
    System.out.println("serializing");
    JsonElement tempJson = serializationUtil.deepSerializeGame(tempGame,
        tempPlayer);
    System.out.println(tempJson);
    return tempJson.toString();
  }
}
