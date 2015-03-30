package gamestuff;

import java.util.Collection;

public class Game {
  private int MAX_CARDS;
  private int numPlayers;
  private Collection<Player> players;
  private Phase phase = Phase.STORYTELLER;
  private Chat chat = new Chat();

  public Game(int numPlayers, int maxCards, Collection<Player> players) {
    this.numPlayers = numPlayers;
    this.MAX_CARDS = maxCards;
    this.players = players;
  }
}
