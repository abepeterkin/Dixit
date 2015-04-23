package gamestuff;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
 * IMPORTANT NOTE/TODO:
 * the game object as it is now cannot handle multiple threads. Once we've
 * got multiple clients making ajax requests, we need to find some way to keep
 * everything here from exploding.
 * 
 * @zhammer: I'm going to mark all the methods that (as far as I can tell)
 * would explode with //EXP so we can better assess the solution.
 *
 * Zach, when you work on this, could you investigate how to hook all this up to
 * the frontend stuff?
 */

public class Game {
  private String name;
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
  private HashMap<String, String> colorMap = new HashMap<>();
  private boolean gameOver = false;

  public Game(String name, int maxPlayers, int handSize, List<Player> players) {
    this.name = name;
    this.MAX_PLAYERS = maxPlayers;
    this.HAND_SIZE = handSize;
    this.players = players;
    this.deck = new Stack<Card>();
    this.trash = new Stack<Card>();
    this.tableCards = new ArrayList<Card>();
    this.votes = new ArrayList<Vote>();
    for (Player p : players) {
      colorMap.put(p.getChatName(), p.getColor());
    }
  }

  /**
   * @return the name of the game
   */
  public String getName() {
    return name;
  }

  /**
   * @return the current number of players
   */
  public int getNumberOfPlayers() {
    return players.size();
  }

  /**
   * @return the names of every player
   */
  public List<String> getPlayerNames() {
    List<String> toReturn = new ArrayList<>();
    for (Player player : players) {
      toReturn.add(player.getChatName());
    }
    return toReturn;
  }

  /**
   * @return every color used
   */
  public List<String> getColorsInUse() {
    List<String> toReturn = new ArrayList<>();
    for (Player player : players) {
      toReturn.add(player.getColor());
    }
    return toReturn;
  }

  /**
   * @return the maximum number of players
   */
  public int getMaxPlayers() {
    return MAX_PLAYERS;
  }

  /**
   * @return the maximum hand size
   */
  public int getHandSize() {
    return HAND_SIZE;
  }

  /**
   * @return the game's chat
   */
  public Chat getChat() {
    return this.chat;
  }

  /**
   * adds a line to the game's chat log
   */
  public void addToChat(String playerName, String message) {
    String color = colorMap.get(playerName);
    ChatLine line = new ChatLine(playerName, message, color);
    chat.addLine(line);
  }

  public String getChatString() {
    return chat.toString();
  }

  /**
   * @return whether or not the game is over
   */
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * @return the phase of the game
   */
  public Phase getPhase() {
    return this.phase;
  }

  /**
   * @return the game's current story
   */
  public String getStory() {
    return this.story;
  }

  /**
   * @param p the player to add
   * @return whether the player was successfully added
   */
  public boolean addPlayer(Player p) {
    if (players.size() < MAX_PLAYERS) {
      players.add(p);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param p the player to remove
   */
  public void removePlayer(Player p) {
    //TODO: figure out the best way to remove a player from the game
  }

  /**
   * sets up game board
   * deck is filled w/ all possible cards
   * trash is empty
   * all players given x cards depending on custom hand size
   */
  public void newGame() {
    this.deck = CardInitilizer.load(
        new File("src/main/resources/static/images/cards"));
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

  /**
   * @param s the story
   * @param c the card attributed to the story
   */
  public void submitStory(String s, Card c) {
    for (Player p: this.players) {
      if (p.isStoryteller()) {
        addCardToTable(p,c);
      }
    }
    this.story = s;
  }

  /**
   * Updates the phase to the voting phase.
   */
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
      Collections.sort(this.players);
      gameOver = true;
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

  /*private List<Player> determineWinners() {
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
  }*/

  /**
   * Give storyteller status to the next player in line, and revoke the current
   * storyteller's status.
   */
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
        break;
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
   * ABRAHAM: good question, probably the ID. this can all change if needed
   *
   * @param p the player adding the card
   * @param c the card to be added
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
   * updates phase in game.
   */
  public void updatePhase(Phase p) {
    this.phase = p;
  }

  /**
   * Class representing a vote, containing a player and a card
   */
  private class Vote {
    private final Player player;
    private final Card card;

    /**
     * @param player the player who cast the vote
     * @param card the card the vote was cast for
     */
    public Vote(Player player, Card card) {
      this.player = player;
      this.card = card;
    }

    /**
     * @param p Player in game
     * @return  List of cards
     */
    public List<Card> getPlayerHand(Player p) {
      return p.getHand();
    }
    
    /**
     * @return the player who cast the vote
     */
    public Player getPlayer() {
      return player;
    }
    
    /**
     * @param name    string name of player
     * @return        Player object
     */
    public Player getPlayerByName(String name) {
      for (Player p: players) {
        if (p.getChatName() == name) {
          return p;
        }
      }
      throw new InvalidParameterException("PLAYER DOESNT EXIST");
    }
    
    /**
     * @return list of all players
     */
    public List<Player> getPlayers() {
      return players;
    }
    
    

    /**
     * @return the card the vote was cast for
     */
    public Card getCard() {
      return card;
    }
  }
}
