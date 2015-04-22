package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class AddChatRequest implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String gameName = qm.value("gameName");
      String playerName = qm.value("playerName");
      String message = qm.value("message");
      Game game = Main.getGame(gameName);
      if (game == null) {
        Map<String, Object> variables = ImmutableMap.of("response", "failure");
        return new ModelAndView(variables, "response.ftl");
      } else {
        game.addToChat(playerName, message);
        Map<String, Object> variables = ImmutableMap.of("response", "success");
        return new ModelAndView(variables, "response.ftl");
      }
    }
}