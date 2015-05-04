package edu.brown.cs.dixit.pages;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;
import gamestuff.Player;

/**
 * Presents the user with a page where they can change options of their player.
 */
public class DixitFinalScoresPage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(
      Request req,
      Response res) {
    String gameName = "";
    try {
      gameName = URLDecoder.decode(req.params(":gameName"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.err.println("url decoding error");
    }
    List<String> colors = new ArrayList<>();
    List<String> playerNames = new ArrayList<>();
    if (Main.gameExists(gameName)) {
      Game game = Main.getGame(gameName);
      List<Player> tempPlayerList = game.getPlayers();
      List<Map<String, Object>> tempMapList = new ArrayList<Map<String, Object>>();
      int index = 0;
      while (index < tempPlayerList.size()) {
        Player tempPlayer = tempPlayerList.get(index);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("name", tempPlayer.getChatName());
        tempMap.put("color", tempPlayer.getColor());
        tempMap.put("score", tempPlayer.getScore());
        tempMapList.add(tempMap);
        index++;
      }
      Map<String, Object> variables = ImmutableMap.of("title", "Dixit",
          "gameName", gameName, "players", tempMapList);
      return new ModelAndView(variables, "finalscores.ftl");
    } else {
      String response = "Game not found.";
      Map<String, Object> variables = ImmutableMap.of("response", response);
      return new ModelAndView(variables, "response.ftl");
    }
  }

}