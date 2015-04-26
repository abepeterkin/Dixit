package edu.brown.cs.dixit.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import edu.brown.cs.dixit.DixitGameSubscriber;
import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;
import gamestuff.ChatLine;
import gamestuff.Game;
import gamestuff.Player;

/**
 * Retrieves all of the game updates which have occurred since the player last
 * called this page.
 */
public class GetUpdateRequest implements Route, DixitGameSubscriber {

  private Map<Game, DixitUpdateList> dixitUpdateListMap = new HashMap<Game, DixitUpdateList>();
  private Map<Player, Long> playerTimeMap = new HashMap<Player, Long>();

  @Override
  public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");

    Game tempGame = Main.getGame(gameName);
    if (tempGame == null) {
      return "false";
    }
    DixitUpdateList tempUpdateList = dixitUpdateListMap.get(tempGame);
    Player tempPlayer = tempGame.getPlayerWithId(playerId);
    if (tempPlayer == null) {
      return "false";
    }
    long tempTime;
    if (playerTimeMap.containsKey(tempPlayer)) {
      tempTime = playerTimeMap.get(tempPlayer);
    } else {
      tempTime = System.currentTimeMillis();
    }
    JsonElement tempJson = tempUpdateList.getJson(tempTime, tempPlayer);
    playerTimeMap.put(tempPlayer, System.currentTimeMillis());

    return tempJson.toString();
  }

  private void addUpdate(
      Game game,
      DixitUpdate dixitUpdate) {
    DixitUpdateList tempList;
    if (!dixitUpdateListMap.containsKey(game)) {
      tempList = new DixitUpdateList();
      dixitUpdateListMap.put(game, tempList);
    } else {
      tempList = dixitUpdateListMap.get(game);
    }
    tempList.addDixitUpdate(dixitUpdate);
  }

  @Override
  public void handChanged(
      Game game,
      Player player) {
    addUpdate(game, new HandUpdate(player));
  }

  @Override
  public void playerChanged(
      Game game,
      Player player) {
    addUpdate(game, new PlayerUpdate(player));
  }

  @Override
  public void gameChanged(
      Game game) {
    addUpdate(game, new GameUpdate(game));
  }

  @Override
  public void chatChanged(
      Game game) {
    addUpdate(game, new ChatUpdate(game));
  }

  /**
   * Contains convenience methods for managing many dixit updates.
   */
  private class DixitUpdateList {

    private Gson gson = new Gson();
    private List<DixitUpdate> dixitUpdates = new ArrayList<DixitUpdate>();

    public DixitUpdateList() {

    }

    public void addDixitUpdate(
        DixitUpdate dixitUpdate) {
      dixitUpdates.add(dixitUpdate);
    }

    public JsonElement getJson(
        long startTime,
        Player player) {
      List<JsonElement> tempJsonList = new ArrayList<JsonElement>();
      int index = dixitUpdates.size() - 1;
      while (index >= 0) {
        DixitUpdate tempUpdate = dixitUpdates.get(index);
        if (tempUpdate.getTime() < startTime) {
          break;
        }
        tempJsonList.add(tempUpdate.getJson(player));
        index--;
      }
      return gson.toJsonTree(tempJsonList);
    }

    public void removeUpdates(
        long time) {
      int index = dixitUpdates.size() - 1;
      while (index >= 0) {
        DixitUpdate tempUpdate = dixitUpdates.get(index);
        if (tempUpdate.getTime() < time) {
          dixitUpdates.remove(index);
        }
        index--;
      }
    }

  }

  /**
   * Stores a change to the dixit environment.
   */
  private interface DixitUpdate {

    // Returns a JSON representation of the update.
    // If the update should not be seen by the given player,
    // this function returns null.
    JsonElement getJson(
        Player player);

    long getTime();

  }

  /**
   * Represents a change to a player hand.
   */
  private static class HandUpdate implements DixitUpdate {

    private Player player;
    private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();
    private long time = System.currentTimeMillis();

    public HandUpdate(Player player) {
      this.player = player;
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      if (player == inputPlayer) {
        // There is no accessor for Hand in Player yet.
        JsonElement tempJson = serializationUtil.serializeHand(null);
        return serializationUtil.serializeUpdate("hand", tempJson);
      } else {
        return null;
      }
    }

    @Override
    public long getTime() {
      return time;
    }

  }

  /**
   * Represents a change to a player.
   */
  private static class PlayerUpdate implements DixitUpdate {

    private Player player;
    private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();
    private long time = System.currentTimeMillis();

    public PlayerUpdate(Player player) {
      this.player = player;
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = serializationUtil.serializePlayer(player,
          inputPlayer);
      return serializationUtil.serializeUpdate("player", tempJson);
    }

    @Override
    public long getTime() {
      return time;
    }

  }

  /**
   * Represents a change to a game.
   */
  private static class GameUpdate implements DixitUpdate {

    private Game game;
    private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();
    private long time = System.currentTimeMillis();

    public GameUpdate(Game game) {
      this.game = game;
    }

    @Override
    public JsonElement getJson(
        Player player) {
      JsonElement tempJson = serializationUtil.serializeGame(game, player);
      return serializationUtil.serializeUpdate("game", tempJson);
    }

    @Override
    public long getTime() {
      return time;
    }

  }

  /**
   * Represents a change to chat.
   */
  private static class ChatUpdate implements DixitUpdate {

    private Game game;
    private DixitSerializationUtil serializationUtil = new DixitSerializationUtil();
    private long time = System.currentTimeMillis();
    private ChatLine chatLine;

    public ChatUpdate(Game game) {
      this.game = game;
      chatLine = null;
    }

    @Override
    public JsonElement getJson(
        Player player) {
      JsonElement tempJson = serializationUtil.serializeChatLine(chatLine);
      return serializationUtil.serializeUpdate("chat", tempJson);
    }

    @Override
    public long getTime() {
      return time;
    }

  }

}
