package gamestuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * QUESTIONS:
 *  How do we organize voting?
 *  How do we organize table cards by position?
 *  How do we organize players to rotate storyteller?
 * @author zhammer
 *
 */
public class Game {
  private int MAX_CARDS;
  private int numPlayers;
  private Collection<Player> players;
  private String story;
  private Phase phase;
  private Chat chat = new Chat();
  private Stack<Card> deck;
  private Stack<Card> trash;
  private List<Card> tableCards;
  private List<Vote> votes;
  private int handSize;

  public Game(int numPlayers, int maxCards, int handSize, Collection<Player> players) {
    this.numPlayers = numPlayers;
    this.MAX_CARDS = maxCards;
    this.players = players;
    this.handSize = handSize;
    this.deck = new Stack<Card>();
    this.trash = new Stack<Card>();
    //perhaps tableCards should more effectively store position, aka 1,2,3,4 for voting?
    this.tableCards = new ArrayList<Card>();
    this.votes = new ArrayList<Vote>();
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
    for(int i = 0; i < this.handSize; i++) {
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
      if (p.getIsStoryteller()) {
        addCardToTable(p,c);
      }
    }
    this.story = s;
  }
  
  /**
   * how exactly are we doing votes? for now just using vote class and we can update
   * 
   */
  public void votingPhase() {
    Collections.shuffle(this.tableCards);
    updatePhase(Phase.VOTING);
  }
  
  /**
   * again we need to figure out how we're doing votes, player will submit vote
   * if votes full, advance to scoring
   * 
   * @param p
   * @param v
   */
  public void playerVote(Player p, Vote v) {
    //cast vote
    if (this.votes.size() == this.players.size() - 1) {
      scoringPhase();
    }
  }
  
  /**
   * we need to discuss how votes will work to do this
   */
  public void scoringPhase() {
    for (Card c: this.tableCards) {
      //discuss voting mechanism
      //all scores will be tallied based on game rules
    }
    updatePhase(Phase.SCORING);
  }
  
  /**
   * wait i imagine til some timer on front end after scoring phase?
   */
  public void cleanUp() {
    //max score player wins if over maxScore, should we use sorted list?
    //(if winner)
    if (false) {
      
    } else {
      trashTable();
      this.story = null;
      votes.clear();
      //select newstoryteller
      updatePhase(Phase.STORYTELLER);
    }
  }
  
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
    this.tableCards.add(p.playCard(c));
    p.draw(drawFromDeck());
    if (this.tableCards.size() == this.players.size()) {
      votingPhase();
    }
  }
  
  public Card drawFromDeck() {
    if (deck.isEmpty()) {
      this.refillDeck();
    }
    return this.deck.pop();
  }
  
  public void refillDeck() {
    this.deck.addAll(this.trash);
    Collections.shuffle(this.deck);
    this.trash.clear();
  }      
  
  /**
   * for front-end to show showing card on trash
   * again confused, is this necessary?
   * @return
   */
  public Card topOfTrash() {
    if (this.trash.isEmpty()) {
      return null;
    } else {
      return this.trash.peek();
    }
  }
  
  /**
   * updates phase in game and in all players
   */
  public void updatePhase(Phase p) {
    this.phase = p;
    for (Player player: this.players) {
      player.setPhase(p);
    }
  }
}
