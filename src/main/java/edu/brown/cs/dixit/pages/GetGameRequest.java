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

public class GetGameRequest implements Route {

  private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();

  @Override
  public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerId");

    Game tempGame = Main.getGame(gameName);
    Player tempPlayer = tempGame.getPlayerByName(playerName);
    JsonElement tempJson = serializationUtil.deepSerializeGame(tempGame,
        tempPlayer);

    return tempJson.toString();
  }
}
