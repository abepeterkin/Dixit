package edu.brown.cs.dixit.pages;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;

public class DixitJoinOptionsPage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) {
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
      colors = game.getColorsInUse();
      playerNames = game.getPlayerNames();
      Map<String, Object> variables = ImmutableMap.of("title", "Dixit",
          "gameName", gameName, "playerNames", playerNames, "usedColors", colors);
      return new ModelAndView(variables, "joinoptions.ftl");
    } else {
      String response = "Game not found.";
      Map<String, Object> variables = ImmutableMap.of("response", response);
      return new ModelAndView(variables, "response.ftl");
    }
  }

}
