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
  private Map<Player, Long> playerUpdateIdMap = new HashMap<Player, Long>();
  private long nextUpdateId = 0;

  // Gets a new update id and increments the internal nextUpdateId by one.
  // Use this method to avoid synchronization problems.
  synchronized private long getUpdateId() {
    long output = nextUpdateId;
    nextUpdateId++;
    return output;
  }

  @Override
  synchronized public Object handle(
      Request req,
      Response res) {
    QueryParamsMap qm = req.queryMap();
    String gameName = qm.value("gameName");
    String playerId = qm.value("playerId");

    Game tempGame = Main.getGame(gameName);
    if (tempGame == null) {
      return "false";
    }
    DixitUpdateList tempUpdateList;
    if (dixitUpdateListMap.containsKey(tempGame)) {
      tempUpdateList = dixitUpdateListMap.get(tempGame);
    } else {
      tempUpdateList = new DixitUpdateList();
      dixitUpdateListMap.put(tempGame, tempUpdateList);
    }
    Player tempPlayer = tempGame.getPlayerWithId(playerId);
    if (tempPlayer == null) {
      return "false";
    }
    long tempUpdateId;
    if (playerUpdateIdMap.containsKey(tempPlayer)) {
      tempUpdateId = playerUpdateIdMap.get(tempPlayer);
    } else {
      tempUpdateId = nextUpdateId - 1;
    }
    JsonElement tempJson = tempUpdateList.getJson(tempUpdateId, tempPlayer);
    playerUpdateIdMap.put(tempPlayer, nextUpdateId - 1);

    // Remove old updates so they don't pollute the system.
    tempUpdateList.removeUpdates(nextUpdateId - 1000);

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
  public void tableCardsChanged(
      Game game) {
    addUpdate(game, new TableCardsUpdate(game));
  }

  @Override
  public void gameChanged(
      Game game) {
    System.out.println("game was changed");
    addUpdate(game, new GameUpdate(game));
  }

  @Override
  public void chatChanged(
      Game game) {
    addUpdate(game, new ChatUpdate(game));
  }

  @Override
  public void playerAdded(
      Game game,
      Player player) {
    addUpdate(game, new AddPlayerUpdate(player));
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
        long startId,
        Player player) {
      List<JsonElement> tempJsonList = new ArrayList<JsonElement>();
      int index = 0;
      while (index < dixitUpdates.size()) {
        DixitUpdate tempUpdate = dixitUpdates.get(index);
        if (tempUpdate.getId() > startId) {
          JsonElement tempJson = tempUpdate.getJson(player);
          if (tempJson != null) {
            tempJsonList.add(tempJson);
          }
        }
        index++;
      }
      return gson.toJsonTree(tempJsonList);
    }

    public void removeUpdates(
        long id) {
      int index = dixitUpdates.size() - 1;
      while (index >= 0) {
        DixitUpdate tempUpdate = dixitUpdates.get(index);
        if (tempUpdate.getId() < id) {
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

    long getId();

  }

  /**
   * Represents a change to a player hand.
   */
  private class HandUpdate implements DixitUpdate {

    private Player player;
    private long id;

    public HandUpdate(Player player) {
      this.player = player;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      if (player == inputPlayer) {
        JsonElement tempJson = DixitSerializationUtil.serializeHand(player
            .getHand());
        return DixitSerializationUtil.serializeUpdate("hand", tempJson);
      } else {
        return null;
      }
    }

    @Override
    public long getId() {
      return id;
    }

  }

  /**
   * Represents a change to a player.
   */
  private class PlayerUpdate implements DixitUpdate {

    private Player player;
    private long id;

    public PlayerUpdate(Player player) {
      this.player = player;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializePlayer(player,
          inputPlayer);
      return DixitSerializationUtil.serializeUpdate("player", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

  /**
   * Represents a change to cards on the table.
   */
  private class TableCardsUpdate implements DixitUpdate {

    private Game game;
    private long id;

    public TableCardsUpdate(Game game) {
      this.game = game;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializeHand(game
          .getTableCards());
      return DixitSerializationUtil.serializeUpdate("tablecards", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

  /**
   * Represents a change to a game.
   */
  private class GameUpdate implements DixitUpdate {

    private Game game;
    private long id;

    public GameUpdate(Game game) {
      this.game = game;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player player) {
      JsonElement tempJson = DixitSerializationUtil.serializeGame(game, player);
      return DixitSerializationUtil.serializeUpdate("game", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

  /**
   * Represents a change to chat.
   */
  private class ChatUpdate implements DixitUpdate {

    // private Game game;
    private long id;
    private ChatLine chatLine;

    public ChatUpdate(Game game) {
      // this.game = game;
      id = getUpdateId();
      List<ChatLine> tempList = game.getChat().getLines();
      chatLine = tempList.get(tempList.size() - 1);
    }

    @Override
    public JsonElement getJson(
        Player player) {
      JsonElement tempJson = DixitSerializationUtil.serializeChatLine(chatLine);
      return DixitSerializationUtil.serializeUpdate("chat", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

  private class AddPlayerUpdate implements DixitUpdate {

    private Player addedPlayer;
    private long id;

    public AddPlayerUpdate(Player player) {
      this.addedPlayer = player;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializePlayer(
          this.addedPlayer, inputPlayer);
      return DixitSerializationUtil.serializeUpdate("added player", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }
}
