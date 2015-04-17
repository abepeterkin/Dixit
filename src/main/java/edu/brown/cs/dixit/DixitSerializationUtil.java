package edu.brown.cs.dixit;

import gamestuff.Card;
import gamestuff.ChatLine;
import gamestuff.Color;
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

  private final static Gson GSON = new Gson();

  public JsonElement serializeCard(
      Card card) {
    Map<String, Object> variables = new ImmutableMap.Builder()
        .put("id", card.getId()).put("image", card.getImage())
        .put("isStoryTeller", card.getStoryteller()).build();
    return GSON.toJsonTree(variables);
  }

  public JsonElement serializeHand(
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

  public JsonElement serializeColor(
      Color color) {
    String tempValue = "none";
    if (color == Color.RED) {
      tempValue = "RED";
    } else if (color == Color.WHITE) {
      tempValue = "WHITE";
    } else if (color == Color.YELLOW) {
      tempValue = "YELLOW";
    } else if (color == Color.GREEN) {
      tempValue = "GREEN";
    } else if (color == Color.BLUE) {
      tempValue = "BLUE";
    } else if (color == Color.PINK) {
      tempValue = "PINK";
    }
    Map<String, Object> variables = new ImmutableMap.Builder().put("value",
        tempValue).build();
    return GSON.toJsonTree(variables);
  }

  public Color deserializeColor(
      String json) {
    String tempText = GSON.fromJson(json, String.class);
    if (tempText.equals("RED")) {
      return Color.RED;
    } else if (tempText.equals("WHITE")) {
      return Color.WHITE;
    } else if (tempText.equals("YELLOW")) {
      return Color.YELLOW;
    } else if (tempText.equals("GREEN")) {
      return Color.GREEN;
    } else if (tempText.equals("BLUE")) {
      return Color.BLUE;
    } else if (tempText.equals("PINK")) {
      return Color.PINK;
    }
    return null;
  }

  public JsonElement serializePlayer(
      Player player,
      Player currentPlayer) {
    Map<String, Object> variables = new ImmutableMap.Builder()
        .put("score", player.getScore()).put("chatName", player.getChatName())
        .put("isStoryTeller", player.isStoryteller()).build();
    return GSON.toJsonTree(variables);
  }

  public JsonElement deepSerializePlayer(
      Player player,
      Player currentPlayer) {
    // TODO: Serialize.
    // Cannot serialize yet because player has no accessor for hand.
    return GSON.toJsonTree("");
  }

  public JsonElement serializePhase(
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
    } else if (phase == Phase.CLEANUP) {
      tempValue = "CLEANUP";
    }
    Map<String, Object> variables = new ImmutableMap.Builder().put("value",
        tempValue).build();
    return GSON.toJsonTree(variables);
  }

  public JsonElement serializeGame(
      Game game,
      Player currentPlayer) {
    // TODO: Serialize.
    // Need accessors for serialization.
    return GSON.toJsonTree("");
  }

  public JsonElement deepSerializeGame(
      Game Game,
      Player currentPlayer) {
    // TODO: Serialize.
    // Need accessors for serialization.
    return GSON.toJsonTree("");
  }

  public JsonElement serializeChatLine(
      ChatLine chatLine) {
    return GSON.toJsonTree(chatLine.getMessage());
  }

  public JsonElement serializeUpdate(
      String updateName,
      JsonElement json) {
    List<JsonElement> tempList = new ArrayList<JsonElement>();
    tempList.add(GSON.toJsonTree(updateName));
    tempList.add(json);
    return GSON.toJsonTree(tempList);
  }

}
