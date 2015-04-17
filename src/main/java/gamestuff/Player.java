package gamestuff;

import java.util.List;

public class Player implements Comparable<Player> {
  private int score;
  private final String CHAT_NAME;
  private final Color COLOR;
  private boolean isStoryteller = false;
  private List<Card> hand;

  public Player(String chatName, Color color) {
    this.score = 0;
    this.CHAT_NAME = chatName;
    this.COLOR = color;
  }

  public int getScore() {
    return score;
  }

  public int cardsInHand() {
    return hand.size();
  }

  public void incrementScore(int increment) {
    this.score += increment;
  }

  public String getChatName() {
    return CHAT_NAME;
  }

  public Color getColor() {
    return COLOR;
  }

  public boolean isStoryteller() {
    return isStoryteller;
  }

  public void setIsStoryteller(boolean isStoryteller) {
    for (Card c: this.hand) {
      c.setStoryteller(isStoryteller);
    }
    this.isStoryteller = isStoryteller;
  }

  public void draw(Card c){
    hand.add(c);
  }

  public void removeFromHand(Card c) {
    hand.remove(c);
  }

  @Override
  public int compareTo(Player other) {
    return Integer.compare(this.score, other.score);
  }

}
