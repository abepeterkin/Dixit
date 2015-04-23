package gamestuff;

import java.util.List;

/** Class representing a game player.
 */
public class Player implements Comparable<Player> {
  private int score;
  private final String CHAT_NAME;
  private final String COLOR;
  private boolean isStoryteller = false;
  private List<Card> hand;

  public Player(String chatName, String color) {
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
   * @return the number of cards in the players hand
   */
  public int cardsInHand() {
    return hand.size();
  }

  /**
   * @param increment the amount to increment the score by
   */
  public void incrementScore(int increment) {
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
  public boolean isStoryteller() {
    return isStoryteller;
  }

  /** Sets the storyteller status of the player
   * @param isStoryteller whether or not the player is the storyteller
   */
  public void setIsStoryteller(boolean isStoryteller) {
    for (Card c: this.hand) {
      c.setStoryteller(isStoryteller);
    }
    this.isStoryteller = isStoryteller;
  }

  /**Adds a card to the player's hand
   * @param c the card to add to the player's hand.
   */
  public void draw(Card c){
    hand.add(c);
  }

  /**
   * @param c the card to remove from the player's hand
   */
  public void removeFromHand(Card c) {
    hand.remove(c);
  }
  
  /**
   * @return List of cards in hand
   */
  public List<Card> getHand() {
    return hand;
  }

  @Override
  public int compareTo(Player other) {
    return Integer.compare(this.score, other.getScore());
  }

}
