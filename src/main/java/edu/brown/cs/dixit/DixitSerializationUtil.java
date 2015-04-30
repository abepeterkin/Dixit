package edu.brown.cs.dixit;

import gamestuff.Card;
import gamestuff.Chat;
import gamestuff.ChatLine;
import gamestuff.Game;
import gamestuff.Phase;
import gamestuff.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Serializes various classes into strings. Deep serialization means that nested
 * objects are recursively serialized.
 */
public class DixitSerializationUtil {

  private static final Gson GSON = new Gson();

  /**
   * Converts a card to JSON.
   *
   * @param card
   *          The card to convert.
   * @return JSON.
   */
  public static JsonElement serializeCard(
      Card card) {
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("id", card.getId()).put("image", card.getImage()).build();
    return GSON.toJsonTree(variables);
  }

  /**
   * Converts a hand to JSON.
   *
   * @param hand
   *          The hand to convert.
   * @return JSON.
   */
  public static JsonElement serializeHand(
      List<Card> hand) {
    ImmutableList.Builder<JsonElement> tempBuilder = new ImmutableList.Builder<JsonElement>();
    int index = 0;
    while (index < hand.size()) {
      Card tempCard = hand.get(index);
      tempBuilder.add(serializeCard(tempCard));
      index += 1;
    }
    return GSON.toJsonTree(tempBuilder.build());
  }

  /**
   * Serializes the table cards of a game
   * 
   * @param game
   *          a game to serialize
   * @return the serialized table cards of the game
   */
  public static JsonElement serializeTableCards(
      Game game,
      Player currentPlayer) {
    List<Card> tempCardList = game.getTableCards();
    Phase tempPhase = game.getPhase();
    if (tempPhase == Phase.VOTING || tempPhase == Phase.SCORING
        || tempPhase == Phase.WAITING) {
      ImmutableList.Builder<JsonElement> tempBuilder = new ImmutableList.Builder<JsonElement>();
      int index = 0;
      while (index < tempCardList.size()) {
        Card tempCard = tempCardList.get(index);
        tempBuilder.add(serializeCard(tempCard));
        index += 1;
      }
      String tempCardId = game.getTableCardByPlayer(currentPlayer).getId();
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("faceUp", true).put("currentPlayerCardId", tempCardId)
          .put("cards", tempBuilder.build()).build();
      return GSON.toJsonTree(variables);
    } else {
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("faceUp", false).put("cardAmount", tempCardList.size()).build();
      return GSON.toJsonTree(variables);
    }
  }

  /**
   * Shallow conversion of player to JSON. Only includes top level properties.
   *
   * @param player
   *          The player to convert.
   * @param currentPlayer
   *          The player who will be viewing the JSON.
   * @return JSON.
   */
  public static JsonElement serializePlayer(
      Player player,
      Player currentPlayer) {
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("score", player.getScore()).put("chatName", player.getChatName())
        .put("isStoryTeller", player.isStoryteller()).put("id", player.getId())
        .put("color", player.getColor()).build();
    return GSON.toJsonTree(variables);
  }

  /**
   * Deep conversion of player to JSON. Includes nested objects. Will hide cards
   * from other players.
   *
   * @param player
   *          The player to convert.
   * @param currentPlayer
   *          The player who will be viewing the JSON.
   * @return JSON.
   */
  public static JsonElement deepSerializePlayer(
      Player player,
      Player currentPlayer) {
    ImmutableMap.Builder<String, Object> tempBuilder = new ImmutableMap.Builder<String, Object>()
        .put("score", player.getScore()).put("chatName", player.getChatName())
        .put("isStoryTeller", player.isStoryteller()).put("id", player.getId())
        .put("color", player.getColor());
    if (currentPlayer == player) {
      tempBuilder.put("hand", serializeHand(player.getHand()));
    }
    return GSON.toJsonTree(tempBuilder.build());
  }

  /**
   * Converts the game phase to JSON.
   *
   * @param phase
   *          The phase of the game.
   * @return JSON.
   */
  public static JsonElement serializePhase(
      Phase phase) {
    String tempValue = "none";
    if (phase == Phase.STORYTELLER) {
      tempValue = "STORYTELLER";
    } else if (phase == Phase.NONSTORYCARDS) {
      tempValue = "NONSTORYCARDS";
    } else if (phase == Phase.VOTING) {
      tempValue = "VOTING";
    } else if (phase == Phase.SCORING) {
      tempValue = "SCORING";
    } else if (phase == Phase.WAITING) {
      tempValue = "WAITING";
    } else if (phase == Phase.PREGAME) {
      tempValue = "PREGAME";
    } else if (phase == Phase.GAMEOVER) {
      tempValue = "GAMEOVER";
    }
    return GSON.toJsonTree(tempValue);
  }

  /**
   * Shallow conversion of game to JSON. Only includes top level properties.
   *
   * @param game
   *          The game to convert.
   * @param currentPlayer
   *          The player who will view the JSON.
   * @return JSON.
   */
  public static JsonElement serializeGame(
      Game game,
      Player currentPlayer) {
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("name", game.getName())
        .put("phase", serializePhase(game.getPhase()))
        .put("story", game.getStory()).build();
    return GSON.toJsonTree(variables);
  }

  /**
   * Deep conversion of game to JSON. Includes nested properties. Hides certain
   * information from currentPlayer.
   *
   * @param game
   *          The game to convert.
   * @param currentPlayer
   *          The player who will view the JSON.
   * @return JSON.
   */
  public static JsonElement deepSerializeGame(
      Game game,
      Player currentPlayer) {
    List<Player> playerList = game.getPlayers();
    ImmutableList.Builder<JsonElement> tempBuilder = new ImmutableList.Builder<JsonElement>();
    int index = 0;
    while (index < playerList.size()) {
      Player tempPlayer = playerList.get(index);
      tempBuilder.add(deepSerializePlayer(tempPlayer, currentPlayer));
      index += 1;
    }
    JsonElement playerJsonList = GSON.toJsonTree(tempBuilder.build());
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("name", game.getName())
        .put("phase", serializePhase(game.getPhase()))
        .put("story", game.getStory()).put("players", playerJsonList)
        .put("chat", serializeChat(game.getChat()))
        .put("handsize", Integer.toString(game.getHandSize()))
        .put("tablecards", serializeTableCards(game, currentPlayer)).build();
    return GSON.toJsonTree(variables);
  }

  /**
   * Converts a chat line to JSON.
   *
   * @param chatLine
   *          The chat line to convert.
   * @return JSON.
   */
  public static JsonElement serializeChatLine(
      ChatLine chatLine) {
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("playerId", chatLine.getPlayerId())
        .put("message", chatLine.getMessage()).build();
    return GSON.toJsonTree(variables);
  }

  /**
   * Converts a chat object to JSON.
   *
   * @param chat
   *          The chat to convert.
   * @return JSON.
   */
  public static JsonElement serializeChat(
      Chat chat) {
    List<ChatLine> chatLineList = chat.getLines();
    ImmutableList.Builder<JsonElement> tempBuilder = new ImmutableList.Builder<JsonElement>();
    int index = 0;
    while (index < chatLineList.size()) {
      ChatLine tempChatLine = chatLineList.get(index);
      tempBuilder.add(serializeChatLine(tempChatLine));
      index += 1;
    }
    return GSON.toJsonTree(tempBuilder.build());
  }

  /**
   * Serializes an update for GetUpdateRequest.
   *
   * @param updateName
   *          The name of the update.
   * @param json
   *          The JSON contained in the update.
   * @return JSON.
   */
  public static JsonElement serializeUpdate(
      String updateName,
      JsonElement json) {
    List<JsonElement> tempList = new ArrayList<JsonElement>();
    tempList.add(GSON.toJsonTree(updateName));
    tempList.add(json);
    return GSON.toJsonTree(tempList);
  }

}
