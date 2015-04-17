package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;

public class GetGameRequest implements TemplateViewRoute {

  private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");

    Game tempGame = Main.getGame(gameName);
    // Need accessor for player.
    Player tempPlayer = null;
    JsonElement tempJson = serializationUtil.deepSerializeGame(tempGame,
        tempPlayer);

    Map<String, Object> variables = ImmutableMap.of("response",
        tempJson.toString());
    return new ModelAndView(variables, "response.ftl");
  }
}
