package gamestuff;

import java.util.List;

public class Player {
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

  public void setScore(int score) {
    this.score = score;
  }

  public String getChatName() {
    return CHAT_NAME;
  }

  public Color getColor() {
    return COLOR;
  }

  public boolean getIsStoryteller() {
    return isStoryteller;
  }

  public void setIsStoryteller(boolean isStoryteller) {
    this.isStoryteller = isStoryteller;
  }
  
  public void draw(Card c){
    hand.add(c);
  }

}
