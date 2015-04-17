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

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.Color;
import gamestuff.Game;
import gamestuff.Player;

public class CreateGameRequest implements TemplateViewRoute {

  private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerName = qm.value("playerName");
    String colorName = qm.value("colorName");
    int numberOfPlayers = Integer.parseInt(qm.value("numberOfPlayers"));
    int numberOfCards = Integer.parseInt(qm.value("numberOfCards"));

    String response;
    if (Main.gameExists(gameName)) {
      response = "false";
    } else {
      Color tempColor = serializationUtil.deserializeColor(colorName);
      Player tempPlayer = new Player(playerName, tempColor);
      List<Player> tempPlayerList = new ArrayList<Player>();
      Game tempGame = new Game(numberOfPlayers, numberOfCards, tempPlayerList);
      Main.addGame(gameName, tempGame);
      response = "true";
    }

    Map<String, Object> variables = ImmutableMap.of("response", response);
    return new ModelAndView(variables, "response.ftl");
  }
}
