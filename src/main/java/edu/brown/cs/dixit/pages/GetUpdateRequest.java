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
  private static final int OLD_UPDATES_TO_REMOVE = 1000;

  // Gets a new update id and increments the internal nextUpdateId by one.
  // Use this method to avoid synchronization problems.
  private synchronized long getUpdateId() {
    long output = nextUpdateId;
    nextUpdateId++;
    return output;
  }

  @Override
  public synchronized Object handle(
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
    tempUpdateList.removeUpdates(nextUpdateId - OLD_UPDATES_TO_REMOVE);

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
  public synchronized void handChanged(
      Game game,
      Player player) {
    addUpdate(game, new HandUpdate(player));
  }

  @Override
  public synchronized void playerChanged(
      Game game,
      Player player) {
    addUpdate(game, new PlayerUpdate(player, game));
    // System.out.println("MARK 1!!!!!!!!!!!!!!!!!!");
  }

  @Override
  public synchronized void tableCardsChanged(
      Game game) {
    addUpdate(game, new TableCardsUpdate(game));
  }

  @Override
  public synchronized void gameChanged(
      Game game) {
    System.out.println("game was changed");
    addUpdate(game, new GameUpdate(game));
  }

  @Override
  public synchronized void chatChanged(
      Game game) {
    addUpdate(game, new ChatUpdate(game));
  }

  @Override
  public synchronized void playerAdded(
      Game game,
      Player player) {
    addUpdate(game, new AddPlayerUpdate(player, game));
  }

  @Override
  public synchronized void votesChanged(
      Game game) {
    addUpdate(game, new VotesChangedUpdate(game));
  }

  /**
   * Contains convenience methods for managing many dixit updates.
   */
  private class DixitUpdateList {

    private Gson gson = new Gson();
    private List<DixitUpdate> dixitUpdates = new ArrayList<DixitUpdate>();

    public DixitUpdateList() {

    }

    public synchronized void addDixitUpdate(
        DixitUpdate dixitUpdate) {
      dixitUpdates.add(dixitUpdate);
    }

    public synchronized JsonElement getJson(
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

    public synchronized void removeUpdates(
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
    private Game game;
    private long id;

    public PlayerUpdate(Player player, Game game) {
      this.player = player;
      this.game = game;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializePlayer(player,
          game, inputPlayer);
      // System.out.println("MARK 2!!!!!!!!!!!!!!!!!!");
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
      JsonElement tempJson = DixitSerializationUtil.serializeTableCards(game,
          inputPlayer);
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
    private Game game;
    private long id;

    /**
     * @param player
     *          the player who was added
     */
    public AddPlayerUpdate(Player player, Game game) {
      this.addedPlayer = player;
      this.game = game;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializePlayer(
          this.addedPlayer, game, inputPlayer);
      return DixitSerializationUtil.serializeUpdate("added player", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

  /**
   * Represents a change to cards on the table.
   */
  private class VotesChangedUpdate implements DixitUpdate {

    private Game game;
    private long id;

    public VotesChangedUpdate(Game game) {
      this.game = game;
      id = getUpdateId();
    }

    @Override
    public JsonElement getJson(
        Player inputPlayer) {
      JsonElement tempJson = DixitSerializationUtil.serializeVotes(game,
          inputPlayer);
      return DixitSerializationUtil.serializeUpdate("votes", tempJson);
    }

    @Override
    public long getId() {
      return id;
    }

  }

}
