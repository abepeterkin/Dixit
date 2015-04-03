package gamestuff;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class Game {
  private int MAX_CARDS;
  private int numPlayers;
  private Collection<Player> players;
  private String clue;
  private Phase phase = Phase.STORYTELLER;
  private Chat chat = new Chat();
  private List<Card> deck;
  private List<Card> trash;
  private int handSize;

  public Game(int numPlayers, int maxCards, int handSize, Collection<Player> players) {
    this.numPlayers = numPlayers;
    this.MAX_CARDS = maxCards;
    this.players = players;
    this.handSize = handSize;
    this.deck = new Stack<Card>();
    this.trash = new Stack<Card>();
  }
  
  /**
   * sets up game board
   * deck is filled w/ all possible cards
   * trash is empty
   * all players given x cards depending on custom hand size
   */
  public void initiate() {
    
  }
}
