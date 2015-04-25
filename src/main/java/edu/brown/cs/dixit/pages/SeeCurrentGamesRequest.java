package edu.brown.cs.dixit.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.Game;

public class SeeCurrentGamesRequest implements Route {

  private final static Gson GSON = new Gson();

  @Override
  public Object handle(
      Request req,
      Response res) {
    // QueryParamsMap qm = req.queryMap();

    List<Game> gameList = Main.getGameList();
    //DixitSerializationUtil serializer  = new DixitSerializationUtil();
    //List<String> serializedGameList = new ArrayList<String>();
    List<BasicGameData> gameDataList = new ArrayList<>();
    for (Game game : gameList) {
      BasicGameData data = new BasicGameData(game.getName(),
          game.getPlayerNames(), game.getColorsInUse(),
          game.getNumberOfPlayers(), game.getMaxPlayers());
      gameDataList.add(data);
    }
    Map<String, Object> variables = ImmutableMap.of("title","Dixit",
        "data", gameDataList);
    return GSON.toJson(variables);
  }

  private class BasicGameData {
    String gameName;
    List<String> playerNames;
    List<String> colors;
    int numberOfPlayers;
    int maxPlayers;

    public BasicGameData(String gameName, List<String> playerNames,
        List<String> colors, int numberOfPlayers, int maxPlayers) {
      this.gameName = gameName;
      this.playerNames = playerNames;
      this.colors = colors;
      this.numberOfPlayers = numberOfPlayers;
      this.maxPlayers = maxPlayers;
    }
  }
}