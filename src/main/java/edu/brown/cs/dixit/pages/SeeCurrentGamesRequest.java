package edu.brown.cs.dixit.pages;

import edu.brown.cs.dixit.Main;
import gamestuff.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * Retrieves a list of all games. Includes player names.
 */
public class SeeCurrentGamesRequest implements Route {

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(
      Request req,
      Response res) {
    // QueryParamsMap qm = req.queryMap();

    List<Game> gameList = Main.getGameList();
    // List<String> serializedGameList = new ArrayList<String>();
    List<BasicGameData> gameDataList = new ArrayList<>();
    for (Game game : gameList) {
      BasicGameData data = new BasicGameData(game.getName(),
          game.getPlayerNames(), game.getColorsInUse(),
          game.getNumberOfPlayers(), game.getMaxPlayers());
      gameDataList.add(data);
    }
    Map<String, Object> variables = ImmutableMap.of("title", "Dixit", "data",
        gameDataList);
    return GSON.toJson(variables);
  }

  /**
   * Class containing basic data about a game
   */
  private class BasicGameData {
    @SuppressWarnings("unused")
    private String gameName;
    @SuppressWarnings("unused")
    private List<String> playerNames;
    @SuppressWarnings("unused")
    private List<String> colors;
    @SuppressWarnings("unused")
    private int numberOfPlayers;
    @SuppressWarnings("unused")
    private int maxPlayers;

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
