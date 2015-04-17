package edu.brown.cs.dixit.pages;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.Color;
import gamestuff.Game;
import gamestuff.Player;

public class JoinGameRequest implements TemplateViewRoute {

  private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");
    String colorName = qm.value("colorName");

    String response;
    if (!Main.gameExists(gameName)) {
      response = "false";
    } else {
      Color tempColor = serializationUtil.deserializeColor(colorName);
      Player tempPlayer = new Player(playerName, tempColor);
      Game tempGame = Main.getGame(gameName);
      if (tempGame.addPlayer(tempPlayer)) {
        response = "true";
      } else {
        response = "false";
      }
    }

    Map<String, Object> variables = ImmutableMap.of("response", response);
    return new ModelAndView(variables, "response.ftl");
  }
}
