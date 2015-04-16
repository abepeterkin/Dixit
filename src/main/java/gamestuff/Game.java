package gamestuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game {
  private final int HAND_SIZE;
  private final int MAX_PLAYERS;
  private List<Player> players;
  private String story;
  private Phase phase;
  private Chat chat = new Chat();
  private Stack<Card> deck;
  private Stack<Card> trash;
  private List<Card> tableCards;
  private List<Vote> votes;

  public Game(int maxPlayers, int handSize, List<Player> players) {
    this.MAX_PLAYERS = maxPlayers;
    this.HAND_SIZE = handSize;
    this.players = players;
    this.deck = new Stack<Card>();
    this.trash = new Stack<Card>();
    //perhaps tableCards should more effectively store position, aka 1,2,3,4 for voting?
    this.tableCards = new ArrayList<Card>();
    this.votes = new ArrayList<Vote>();
  }

  public Chat getChat() {
    return this.chat;
  }

  public Phase getPhase() {
    return this.phase;
  }

  public String getStory() {
    return this.story;
  }

  /**
   * @param p the player to add
   * @return whether the player was sucessfully removed
   */
  public boolean addPlayer(Player p) {
    if (players.size() < MAX_PLAYERS) {
      players.add(p);
      return true;
    } else {
      return false;
    }
  }

  public void removePlayer() {

  }

  /**
   * sets up game board
   * deck is filled w/ all possible cards
   * trash is empty
   * all players given x cards depending on custom hand size
   */
  public void newGame() {
    //fill deck lines here ******
    Collections.shuffle(this.deck);
    for(int i = 0; i < this.HAND_SIZE; i++) {
      for(Player p: this.players) {
        p.draw(this.deck.pop());
      }
    }
    updatePhase(Phase.WAITINGFORFIRSTSTORY);
  }



  /**
   * When first player volunteers to share story,
   * Player is set to storyteller
   * Story is stored
   *
   * phase to NONSTORYCARDS and continues as normal
   *
   * @param player   Player who submitted first story
   * @param s        Story submitted
   */
  public void firstStory(Player player, String s, Card c) {
    for (Player p: this.players) {
      if (player.equals(p)) {
        p.setIsStoryteller(true);
      } else {
        p.setIsStoryteller(false);
      }
    }
    submitStory(s, c);
    updatePhase(Phase.NONSTORYCARDS);
  }

  public void submitStory(String s, Card c) {
    for (Player p: this.players) {
      if (p.isStoryteller()) {
        addCardToTable(p,c);
      }
    }
    this.story = s;
  }

  public void votingPhase() {
    Collections.shuffle(this.tableCards);
    updatePhase(Phase.VOTING);
  }

  /**
   * again we need to figure out how we're doing votes, player will submit vote
   * if votes full, advance to scoring
   *
   * @param v the vote being cast
   */
  public void castVote(Player p, Card c) {
    Vote vote = new Vote(p, c);
    votes.add(vote);
    if (this.votes.size() == this.players.size() - 1) {
      scoringPhase();
    }
  }

  /**
   * Tallies up the votes and increases the scores of the players accordingly.
   *
   * If nobody or everybody finds the correct picture,
   * the storyteller scores 0, and each of the other players scores 2.
   * Otherwise the storyteller and all players who found the correct answer
   * score 3. Players other than the storyteller score 1 point for each vote
   * their own pictures receive.
   */
  public void scoringPhase() {
    updatePhase(Phase.SCORING);
    int totalCorrectFinds = 0;
    List<Player> correctPlayers = new ArrayList<Player>();
    for (Vote v : this.votes) {
      Card voteCard = v.getCard();
      if (voteCard.getStoryteller()) {
        totalCorrectFinds++;
      }
    }
    if (totalCorrectFinds == 0 || totalCorrectFinds == players.size()) {
      for (Player p : players) {
        if (!p.isStoryteller()) {
          p.incrementScore(2);
        }
      }
    } else {
      for (Player p : players) {
        if (p.isStoryteller() || correctPlayers.contains(p)) {
          p.incrementScore(3);
        }
      }
      for (Vote v : this.votes) {
        Player votePlayer = v.getPlayer();
        votePlayer.incrementScore(1);
      }
      prepareForNextRound();
    }
  }

  /**
   * How will we communicate that the game is over?
   */
  public void prepareForNextRound() {
    updatePhase(Phase.CLEANUP);
    // game ends when the deck is empty
    if (deck.isEmpty()) {
      List<Player> winners = determineWinners();
      //TODO: end game somehow
    } else {
      for(Player p: this.players) {
        p.draw(this.deck.pop());
      }
      trashTable();
      this.story = "";
      votes.clear();
      cycleStoryteller();
      updatePhase(Phase.STORYTELLER);
    }
  }

  private List<Player> determineWinners() {
    int highestScore = 0;
    for (Player p : players){
      if (p.getScore() > highestScore) {
        highestScore = p.getScore();
      }
    }
    List<Player> winningPlayers = new ArrayList<Player>();
    for (Player p : players){
      if (p.getScore() == highestScore) {
        winningPlayers.add(p);
      }
    }
    return winningPlayers;
  }

  private void cycleStoryteller() {
    for (int i = 0; i < players.size(); i++) {
      Player current = players.get(i);
      if (current.isStoryteller()) {
        current.setIsStoryteller(false);
        if (i == players.size() - 1) {
          players.get(0).setIsStoryteller(true);
        } else {
          players.get(i + 1).setIsStoryteller(true);
        }
      }
    }
  }

  /**
   * Puts all the cards in the table in the trash.
   */
  public void trashTable() {
    trash.addAll(this.tableCards);
    this.tableCards.clear();
  }

  /**
   * again is it selected Card we pass from front end or the ID?
   *
   * @param p
   * @param c
   */
  public void addCardToTable(Player p, Card c) {
    this.tableCards.add(c);
    p.removeFromHand(c);
    p.draw(drawFromDeck());
    if (this.tableCards.size() == this.players.size()) {
      votingPhase();
    }
  }

  /**
   * @return a Card from the deck
   */
  public Card drawFromDeck() {
    if (deck.isEmpty()) {
      this.refillDeck();
    }
    return this.deck.pop();
  }

  /**
   * Moves every card in the trash to the deck and shuffles them.
   */
  public void refillDeck() {
    this.deck.addAll(this.trash);
    Collections.shuffle(this.deck);
    this.trash.clear();
  }

  /**
   * updates phase in game and in all players
   * ABRAHAM: I don't think we need to pass the phase to the player.
   */
  public void updatePhase(Phase p) {
    this.phase = p;
    /*for (Player player: this.players) {
      player.setPhase(p);
    }*/
  }

  /**
   * Class representing a vote, containing a player and a card
   */
  private class Vote {
    private final Player player;
    private final Card card;

    public Vote(Player player, Card card) {
      this.player = player;
      this.card = card;
    }

    public Player getPlayer() {
      return player;
    }

    public Card getCard() {
      return card;
    }
  }
}
