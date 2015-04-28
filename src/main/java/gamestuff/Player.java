package gamestuff;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Class representing a game player.
 */
public class Player implements Comparable<Player> {
  private final String ID;
  private int score;
  private final String CHAT_NAME;
  private final String COLOR;
  private boolean isStoryteller = false;
  private List<Card> hand = new ArrayList<>();

  public Player(String id, String chatName, String color) {
    this.ID = id;
    this.score = 0;
    this.CHAT_NAME = chatName;
    this.COLOR = color;
  }

  /**
   * @return the player's score
   */
  public int getScore() {
    return score;
  }

  /**
   * @return the player's unique id
   */
  public String getId() {
    return ID;
  }

  /**
   * @return the number of cards in the players hand
   */
  public int cardsInHand() {
    return hand.size();
  }

  /**
   * @param increment
   *          the amount to increment the score by
   */
  synchronized public void incrementScore(
      int increment) {
    this.score += increment;
  }

  /**
   * @return the player's chat name
   */
  public String getChatName() {
    return CHAT_NAME;
  }

  /**
   * @return the player's color
   */
  public String getColor() {
    return COLOR;
  }

  /**
   * @return whether or not the player is a storyteller
   */
  synchronized public boolean isStoryteller() {
    return isStoryteller;
  }

  /**
   * Sets the storyteller status of the player
   * 
   * @param isStoryteller
   *          whether or not the player is the storyteller
   */
  synchronized public void setIsStoryteller(
      boolean isStoryteller) {
    for (Card c : this.hand) {
      c.setStoryteller(isStoryteller);
    }
    this.isStoryteller = isStoryteller;
  }

  /**
   * Adds a card to the player's hand
   * 
   * @param c
   *          the card to add to the player's hand.
   */
  synchronized public void draw(
      Card c) {
    hand.add(c);
  }

  /**
   * @param c
   *          the card to remove from the player's hand
   */
  synchronized public void removeFromHand(
      Card c) {
    hand.remove(c);
  }

  /**
   * @return List of cards in hand
   */
  synchronized public List<Card> getHand() {
    return ImmutableList.copyOf(hand);
  }

  @Override
  synchronized public int compareTo(
      Player other) {
    return Integer.compare(this.score, other.getScore());
  }

}
