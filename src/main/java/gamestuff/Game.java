package gamestuff;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import edu.brown.cs.dixit.DixitGameSubscriber;
import edu.brown.cs.dixit.DixitServer;

/*
 * IMPORTANT NOTE/TODO:
 * the game object as it is now cannot handle multiple threads. Once we've
 * got multiple clients making ajax requests, we need to find some way to keep
 * everything here from exploding.
 *
 * Zach, when you work on this, could you investigate how to hook all this up to
 * the frontend stuff?
 */

public class Game {
  private Announcer announcer;
  private String name;
  private final int HAND_SIZE;
  private final int MAX_PLAYERS;
  // we should have min players too, which is always 3
  private List<Player> players;
  private String story = "";
  private Phase phase = Phase.PREGAME;
  private Chat chat = new Chat();
  private Stack<Card> deck = new Stack<Card>();
  private Stack<Card> trash = new Stack<Card>();
  // private List<Card> tableCards = new ArrayList<Card>();
  private List<Vote> votes = new ArrayList<Vote>();
  private BiMap<Card, Player> tableCards = HashBiMap.create ();;
  private Map<String, Card> cardIdMap = new HashMap<>();
  private Map<String, Player> playerIdMap = new HashMap<>();
  private Map<String, String> colorMap = new HashMap<>();
  private Map<Player, Boolean> playerReadyMap = new HashMap<>();
  private DixitGameSubscriber subscriber = DixitServer.getDixitGameSubscriber();

  public Game(String name, int maxPlayers, int handSize, List<Player> players) {
    this.name = name;
    this.MAX_PLAYERS = maxPlayers;
    this.HAND_SIZE = handSize;
    this.players = players;
    for (Player p : players) {
      playerIdMap.put(p.getId(), p);
      colorMap.put(p.getChatName(), p.getColor());
    }
    this.deck = CardInitilizer.load(new File(
        "src/main/resources/static/images/cards"));
    for (Card card : deck) {
      cardIdMap.put(card.getId(), card);
    }
    announcer = new Announcer(chat, this);
    announcer.gameCreated();
  }

  /**
   * @return the name of the game
   */
  public String getName() {
    return name;
  }

  /**
   * @param id
   *          a player id
   * @return the id of the player to return
   */
  public synchronized Player getPlayerWithId(
      String id) {
    return playerIdMap.get(id);
  }

  /**
   * @param id a card id
   * @return the card with that id
   */
  public Card getCardWithId(
      String id) {
    return cardIdMap.get(id);
  }

  /**
   * @return the current number of players
   */
  public synchronized int getNumberOfPlayers() {
    return players.size();
  }

  /**
   * @return the names of every player
   */
  public synchronized List<String> getPlayerNames() {
    List<String> toReturn = new ArrayList<>();
    for (Player player : players) {
      toReturn.add(player.getChatName());
    }
    return toReturn;
  }

  /**
   * @return every color used
   */
  public synchronized List<String> getColorsInUse() {
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
   *
   * @param card the card to remove the vote for
   * @return whether the removal was successful
   */
  public synchronized boolean removeVote(
      String playerId) {
    if (this.phase != Phase.VOTING) {
      return false;
    }
    //announcer needs player name, not id
    for (Vote v : votes) {
      if (v.player.getId().equals(playerId)) {
        votes.remove(v);
      }
    }
    return true;
  }

  /**
   * @param playerId the id of the player who's card we're removing
   * @return whether the removal was successful. removal fails if the phase is
   * wrong or if the card being removed is the story card.
   */
  public synchronized boolean removeNonStoryCard(
      String playerId) {
    if (this.phase != Phase.NONSTORYCARDS) {
      return false;
    }
    Player player = playerIdMap.get(playerId);
    Card card = tableCards.inverse().get(player);
    if (card.getStoryteller()) {
      return false;
    }
    tableCards.remove(card);
    player.draw(card);
    subscriber.handChanged(this, player);
    subscriber.tableCardsChanged(this);
    return true;
  }

  /**
   * @return the game's chat
   */
  public Chat getChat() {
    return this.chat;
  }

  /**
   * adds a line to the game's chat
   * @param player the player who sent the message
   * @param message the content of the message
   */
  public synchronized void addToChat(
      Player player,
      String message) {
    String color = colorMap.get(player.getChatName());
    ChatLine line = new ChatLine(player, message, color);
    chat.addLine(line);
    subscriber.chatChanged(this);
  }

  /**
   * @return a string representation of the chat
   */
  public synchronized String getChatString() {
    return chat.toString();
  }

  /**
   * @return the phase of the game
   */
  public synchronized Phase getPhase() {
    return this.phase;
  }

  /**
   * @return the game's current story
   */
  public synchronized String getStory() {
    return this.story;
  }
  
  /**
   * @return game's current storyteller
   */
  public Player getStoryteller() {
    Player storyteller = null;
    for (Player p : getPlayers()) {
      if (p.isStoryteller()) {
        storyteller = p;
      }
    }
    return storyteller;
  }

  /**
   * Adds a player to the game. Will fail if the game is at capacity or if a
   * player with that ID already exists.
   *
   * @param p
   *          the player to add
   * @return whether the player was successfully added
   */
  public synchronized boolean addPlayer(
      Player p) {
    if (players.size() < MAX_PLAYERS && playerIdMap.get(p.getId()) == null
        && this.phase == Phase.PREGAME) {
      players.add(p);
      playerIdMap.put(p.getId(), p);
      subscriber.playerAdded(this, p);
      announcer.newPlayer(p);
      if (players.size() == MAX_PLAYERS) {
        startGame();
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param p
   *          the player to remove
   */
  public synchronized void removePlayer(
      Player p) {
    // TODO: figure out the best way to remove a player from the game
  }

  /**
   * sets up game board deck is filled w/ all possible cards trash is empty all
   * players given x cards depending on custom hand size.
   */
  public synchronized void startGame() {
    Collections.shuffle(this.deck);
    for (Player p : this.players) {
      trashPlayerCards(p);
    }
    refillDeck();
    for (int i = 0; i < this.HAND_SIZE; i++) {
      for (Player p : this.players) {
        p.draw(this.deck.pop());
        subscriber.handChanged(this, p);
      }
    }
    // Assign an arbitrary storyteller.
    Player player = players.get(0);
    player.setIsStoryteller(true);
    subscriber.playerChanged(this, player);
    updatePhase(Phase.STORYTELLER);
    announcer.gameStart();
  }

  /**
   * When first player volunteers to share story, Player is set to storyteller.
   * Story is stored.
   *
   * phase to NONSTORYCARDS and continues as normal
   *
   * @param player
   *          Player who submitted first story
   * @param s
   *          Story submitted
   * @param c the card that the story is referring to
   */
  public synchronized void firstStory(
      Player player,
      String s,
      Card c) {
    for (Player p : this.players) {
      if (player.equals(p)) {
        p.setIsStoryteller(true);
      } else {
        p.setIsStoryteller(false);
      }
      subscriber.playerChanged(this, p);
    }
    updatePhase(Phase.STORYTELLER);
    submitStory(s, c);
    updatePhase(Phase.NONSTORYCARDS);
  }

  /**
   * @param s
   *          the story
   * @param c
   *          the card attributed to the story
   * @return whether the submission was successful
   */
  public synchronized boolean submitStory(
      String s,
      Card c) {
    if ((this.phase != Phase.STORYTELLER) || (s.equals(""))) {
      return false;
    }
    for (Player p : this.players) {
      if (p.isStoryteller()) {
        addCardToTable(p, c);
      }
    }
    this.story = s;
    updatePhase(Phase.NONSTORYCARDS);
    announcer.nonStoryPhase();
    return true;
  }

  /**
   * Updates the phase to the voting phase.
   */
  public synchronized void votingPhase() {
    updatePhase(Phase.VOTING);
    subscriber.tableCardsChanged(this);
    updatePhase(Phase.VOTING);
    announcer.advanceToVotingPhase();
  }

  /**
   * Casts a vote for a certain card.
   * @param p the player who cast the vote
   * @param c the card that the vote is being cast for
   * @return whether the vote was successfully cast
   */
  public synchronized boolean castVote(Player p, Card c) {
    if ((this.phase != Phase.VOTING) || p.isStoryteller()
        || this.tableCards.get(c).equals(p)) {
      return false;
    }
    Vote vote = new Vote(p, c);
    votes.add(vote);
    announcer.submitVote(p);
    if (this.votes.size() == this.players.size() - 1) {
      calculateScores();
    }
    return true;
  }

  /**
   * @param player the player who's cards are being trashed
   */
  private synchronized void trashPlayerCards(
      Player player) {
    List<Card> hand = player.getHand();
    for (Card card : hand) {
      trash.push(card);
      player.removeFromHand(card);
    }
    subscriber.handChanged(this, player);
  }

  /**
   * Tallies up the votes and increases the scores of the players accordingly.
   *
   * If nobody or everybody finds the correct picture, the storyteller scores 0,
   * and each of the other players scores 2. Otherwise the storyteller and all
   * players who found the correct answer score 3. Players other than the
   * storyteller score 1 point for each vote their own pictures receive.
   */
  private synchronized void calculateScores() {
    updatePhase(Phase.SCORING);
    int storyVotes = 0;
    for (Vote v : this.votes) {
      Card voteCard = v.getCard();
      if (voteCard.getStoryteller()) {
        storyVotes++;
      }
    }
    Player storyTeller = getStoryteller();
    boolean allStoryVotes = storyVotes == players.size() - 1;
    boolean noStoryVotes = storyVotes == 0;

    if (allStoryVotes || noStoryVotes) {
      for (Player p : players) {
        if (!p.isStoryteller()) {
          p.incrementScore(2);
          subscriber.playerChanged(this, p);
        }
      }
    }
    if (!allStoryVotes) {
      updatePhase(Phase.SCORING);
      boolean storyHasBeenVoted = false;
      for (Vote v : this.votes) {
        Card voteCard = v.getCard();
        if (voteCard.getStoryteller()) {
          v.getPlayer().incrementScore(2);
          if (!storyHasBeenVoted) {
            storyTeller.incrementScore(2);
            storyHasBeenVoted = true;
          } else {
            storyTeller.incrementScore(1);
          }
          subscriber.playerChanged(this, v.getPlayer());
          subscriber.playerChanged(this, storyTeller);
        } else {
          Player votedFor = tableCards.get(voteCard);
          votedFor.incrementScore(1);
          subscriber.playerChanged(this, votedFor);
        }
      }
    }
    boolean gameOver = false;
    for (Player p : players) {
      if (p.getScore() > 29) {
        gameOver = true;
      }
    }
    if (gameOver) {
      gameOver();
    } else {
      prepareForNextRound();
    }
  }

  private synchronized void prepareForNextRound() {
    // game ends when the deck is empty
    if (deck.isEmpty()) {
      gameOver();
    } else {
      for (Player p : this.players) {
        p.draw(this.deck.pop());
        subscriber.playerChanged(this, p);
      }
      trashTable();
      this.story = "";
      votes.clear();
      updatePhase(Phase.WAITING);
    }
  }

  /**
   * @param player the player to confirm as ready
   * @return whether the confirmation was successful
   */
  public boolean confirmPlayerReady(Player player) {
    if (!this.phase.equals(Phase.WAITING) && !this.phase.equals(Phase.SCORING)) {
      return false;
    }
    playerReadyMap.put(player, true);
    if (allPlayersReady()) {
      playerReadyMap.clear();
      updatePhase(Phase.STORYTELLER);
    }
    return true;
  }

  /**
   * @return whether or not every player is ready
   */
  private boolean allPlayersReady() {
    List<Boolean> values = new ArrayList<>(playerReadyMap.values());
    return (values.size() == players.size() && !values.contains(false));
  }

  /**
   * begins a new round of the game after the previous round has ended
   */
  public synchronized void beginNewRound() {
    if (!this.phase.equals(Phase.WAITING)) {
      return;
    }
    cycleStoryteller();
    updatePhase(Phase.STORYTELLER);
    announcer.storytellerPhase();
  }
  
  private void gameOver() {
    Collections.sort(this.players);
    updatePhase(Phase.GAMEOVER);
  }
  
  /*
   * private List<Player> determineWinners() { int highestScore = 0; for (Player
   * p : players){ if (p.getScore() > highestScore) { highestScore =
   * p.getScore(); } } List<Player> winningPlayers = new ArrayList<Player>();
   * for (Player p : players){ if (p.getScore() == highestScore) {
   * winningPlayers.add(p); } } return winningPlayers; }
   */

  /**
   * Give storyteller status to the next player in line, and revoke the current
   * storyteller's status.
   */
  private synchronized void cycleStoryteller() {
    for (int i = 0; i < players.size(); i++) {
      Player current = players.get(i);
      if (current.isStoryteller()) {
        current.setIsStoryteller(false);
        subscriber.playerChanged(this, current);
        if (i == players.size() - 1) {
          players.get(0).setIsStoryteller(true);
          subscriber.playerChanged(this, players.get(0));
        } else {
          players.get(i + 1).setIsStoryteller(true);
          subscriber.playerChanged(this, players.get(i + 1));
        }
        break;
      }
    }
  }

  /**
   * Puts all the cards in the table in the trash.
   */
  public synchronized void trashTable() {
    trash.addAll(this.tableCards.keySet());
    this.tableCards.clear();
    subscriber.tableCardsChanged(this);
  }

  /**
   *
   * @param p
   *          the player adding the card
   * @param c
   *          the card to be added
   * @return whether the addition was successful
   */
  public synchronized boolean addCardToTable(
      Player p,
      Card c) {
    if (this.phase != Phase.STORYTELLER && this.phase != Phase.NONSTORYCARDS) {
      return false;
    }
    this.tableCards.put(c, p);
    p.removeFromHand(c);
    p.draw(drawFromDeck());
    subscriber.handChanged(this, p);
    subscriber.tableCardsChanged(this);
    if (this.phase == Phase.NONSTORYCARDS) {
      announcer.submitNonStoryCard(p);
    }
    if (this.tableCards.size() == this.players.size()) {
      votingPhase();
    }
    return true;
  }

  /**
   * @return a Card from the deck
   */
  public synchronized Card drawFromDeck() {
    if (deck.isEmpty()) {
      this.refillDeck();
    }
    return this.deck.pop();
  }

  /**
   * Moves every card in the trash to the deck and shuffles them.
   */
  public synchronized void refillDeck() {
    this.deck.addAll(this.trash);
    Collections.shuffle(this.deck);
    this.trash.clear();
  }

  /**
   * Updates the phase of the game.
   * @param p the plase the game is being updated to
   */
  public synchronized void updatePhase(
      Phase p) {
    this.phase = p;
    subscriber.gameChanged(this);
  }

  /**
   * @param p
   *          Player in game
   * @return List of cards
   */
  public synchronized List<Card> getPlayerHand(
      Player p) {
    return p.getHand();
  }

  /**
   * @param name
   *          string name of player
   * @return Player object
   */
  public synchronized Player getPlayerByName(
      String name) {
    for (Player p : players) {
      if (p.getChatName() == name) {
        return p;
      }
    }
    throw new InvalidParameterException("PLAYER DOESNT EXIST");
  }

  /**
   * @return list of all players
   */
  public synchronized List<Player> getPlayers() {
    return players;
  }

  /**
   * Retrieves cards placed on the table.
   *
   * @return Table cards.
   */
  public synchronized List<Card> getTableCards() {
    return new ArrayList<Card>(tableCards.keySet());
  }
  
  public int getNumberOfVotes() {
    return votes.size();
  }

  /**
   * Class representing a vote, containing a player and a card
   */
  private class Vote {
    private final Player player;
    private final Card card;

    /**
     * @param player
     *          the player who cast the vote
     * @param card
     *          the card the vote was cast for
     */
    public Vote(Player player, Card card) {
      this.player = player;
      this.card = card;
    }

    /**
     * @return the player who cast the vote
     */
    public Player getPlayer() {
      return player;
    }

    /**
     * @return the card the vote was cast for
     */
    public Card getCard() {
      return card;
    }
  }
}
