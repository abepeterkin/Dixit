package edu.brown.cs.dixit;

import gamestuff.Card;
import gamestuff.ChatLine;
import gamestuff.Color;
import gamestuff.Game;
import gamestuff.Phase;
import gamestuff.Player;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * Serializes various classes into strings. Deep serialization means that nested
 * objects are recursively serialized.
 */
public class DixitSerializationUtil {

  private final static Gson GSON = new Gson();

  public String serializeCard(Card card) {
    Map<String, Object> variables = new ImmutableMap.Builder()
        .put("id", card.getId()).put("image", card.getImage())
        .put("isStoryTeller", card.getStoryteller()).build();
    return GSON.toJson(variables);
  }

  public String serializeHand(List<Card> hand) {
    ImmutableList.Builder tempBuilder = new ImmutableList.Builder();
    int index = 0;
    while (index < hand.size()) {
      Card tempCard = hand.get(index);
      tempBuilder.add(serializeCard(tempCard));
      index += 1;
    }
    return GSON.toJson(tempBuilder.build());
  }

  public String serializeColor(Color color) {
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
    return GSON.toJson(variables);
  }

  public String serializePlayer(Player player, Player currentPlayer) {
    Map<String, Object> variables = new ImmutableMap.Builder()
        .put("score", player.getScore()).put("chatName", player.getChatName())
        .put("isStoryTeller", player.isStoryteller()).build();
    return GSON.toJson(variables);
  }

  public String deepSerializePlayer(Player player, Player currentPlayer) {
    // TODO: Serialize.
    // Cannot serialize yet because player has no accessor for hand.
    return "";
  }

  public String serializePhase(Phase phase) {
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
    return GSON.toJson(variables);
  }

  public String serializeGame(Game game, Player currentPlayer) {
    // TODO: Serialize.
    // Need accessors for serialization.
    return "";
  }

  public String deepSerializeGame(Game Game, Player currentPlayer) {
    // TODO: Serialize.
    // Need accessors for serialization.
    return "";
  }

  public String serializeChatLine(ChatLine chatLine) {
    return GSON.toJson(chatLine.getMessage());
  }

}
