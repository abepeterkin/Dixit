package gamestuff;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.dixit.DixitSerializationUtil;
import edu.brown.cs.dixit.Main;

public class GameTest {

  @Test
  public void GameConstructionTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    Player p4 = new Player(Main.newId(), "Abraham", "YELLOW");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    players.add(p4);
    Game g = new Game("CoolGame", 6, 5, players);
    assertTrue(g.getName().equals("CoolGame"));
    assertTrue(g.getHandSize() == 5);
    assertTrue(g.getNumberOfPlayers() == 4);
    assertTrue(g.getMaxPlayers() == 6);
    List<String> names = g.getPlayerNames();
    assertTrue(names.contains("Zach"));
    assertTrue(names.contains("Esteban"));
    assertTrue(names.contains("Jack"));
    assertTrue(names.contains("Abraham"));
    List<String> colors = g.getColorsInUse();
    assertTrue(colors.size() == 4);
    assertTrue(colors.contains("BLUE"));
    assertTrue(colors.contains("RED"));
    assertTrue(colors.contains("GREEN"));
    assertTrue(colors.contains("YELLOW"));
  }

  @Test
  public void GetPlayerByNameTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    Player p4 = new Player(Main.newId(), "Abraham", "YELLOW");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    players.add(p4);
    Game g = new Game("CoolGame", 6, 5, players);
    assertTrue(p1.equals(g.getPlayerByName("Zach")));
    assertTrue(p2.equals(g.getPlayerByName("Esteban")));
    assertTrue(p3.equals(g.getPlayerByName("Jack")));
    assertTrue(p4.equals(g.getPlayerByName("Abraham")));
  }

  @Test
  public void AddPlayerUnderMaxTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 6, 5, players);
    Player p4 = new Player(Main.newId(), "Abraham", "YELLOW");
    assertTrue(g.addPlayer(p4));
  }

  @Test
  public void AddPlayerOverMaxTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 5, players);
    Player p4 = new Player(Main.newId(), "Abraham", "YELLOW");
    assertTrue(g.addPlayer(p4) == false);
  }

  @Test
  public void PhaseAfterNewGameTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 5, players);
    g.startGame();
    assertTrue(g.getPhase().equals(Phase.STORYTELLER));
  }

  @Test
  public void HandSize5Test() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 5, players);
    g.startGame();
    List<Card> h1 = g.getPlayerHand(p1);
    List<Card> h2 = g.getPlayerHand(p2);
    List<Card> h3 = g.getPlayerHand(p3);
    assertTrue(h1.size() == 5);
    assertTrue(h2.size() == 5);
    assertTrue(h3.size() == 5);
  }

  @Test
  public void HandSize7Test() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    List<Card> h1 = g.getPlayerHand(p1);
    List<Card> h2 = g.getPlayerHand(p2);
    List<Card> h3 = g.getPlayerHand(p3);
    assertTrue(h1.size() == 7);
    assertTrue(h2.size() == 7);
    assertTrue(h3.size() == 7);
  }

  // TEST: Cannot submit blank story ""

  // @Test
  // public void FirstStoryTest() {
  // List<Player> players = new ArrayList<Player>();
  // Player p1 = new Player(Main.newId(), "Zach", "BLUE");
  // Player p2 = new Player(Main.newId(), "Esteban", "RED");
  // Player p3 = new Player(Main.newId(), "Jack", "GREEN");
  // players.add(p1);
  // players.add(p2);
  // players.add(p3);
  // Game g = new Game("CoolGame", 3, 7, players);
  // g.startGame();
  // Card storyCard = g.getPlayerHand(p2).get(0);
  // g.firstStory(p2, "Test Story", storyCard);
  // assertTrue(g.getStory().equals("Test Story"));
  // assertTrue(g.getPhase() == Phase.NONSTORYCARDS);
  // assertTrue(g.getTableCards().get(0).equals(storyCard));
  // assertTrue(g.getTableCards().size() == 1);
  // assertTrue(p1.isStoryteller() == false);
  // assertTrue(p2.isStoryteller() == true);
  // assertTrue(p3.isStoryteller() == false);
  // }

  /**
   * Poorly named test method: ensures that storyteller cannot addCardToTable
   * once his/her card is active
   * 
   * #FIX: Right now storyteller can play card during NonStoryTeller phase.
   */
  // @Test
  // public void NonStoryStorytellerBlockTest() {
  // List<Player> players = new ArrayList<Player>();
  // Player p1 = new Player(Main.newId(), "Zach", "BLUE");
  // Player p2 = new Player(Main.newId(), "Esteban", "RED");
  // Player p3 = new Player(Main.newId(), "Jack", "GREEN");
  // players.add(p1);
  // players.add(p2);
  // players.add(p3);
  // Game g = new Game("CoolGame", 3, 7, players);
  // g.newGame();
  // Card storyCard = g.getPlayerHand(p2).get(0);
  // Card illegalCard = g.getPlayerHand(p2).get(3);
  // g.firstStory(p2, "Test Story", storyCard);
  // boolean isLegal = g.addCardToTable(p2, illegalCard);
  // assertTrue(!isLegal);
  // assertTrue(g.getTableCards().size() == 1);
  // }

  /**
   * Tests adding one card from non story player.
   */
  @Test
  public void NSTAddCardTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card legalCard = g.getPlayerHand(p3).get(3);
    g.firstStory(p2, "Test Story", storyCard);
    boolean isLegal = g.addCardToTable(p3, legalCard);
    assertTrue(isLegal);
    assertTrue(g.getTableCards().size() == 2);
    assertTrue(g.getTableCards().contains(storyCard));
    assertTrue(g.getTableCards().contains(legalCard));
    assertTrue(g.getPhase() == Phase.NONSTORYCARDS);
  }

  @Test
  public void AdvanceToVotingTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    List<Card> t = g.getTableCards();
    assertTrue(t.contains(storyCard));
    assertTrue(t.contains(nonStory1));
    assertTrue(t.contains(nonStory2));
    assertTrue(t.size() == 3);
    assertTrue(g.getPhase() == Phase.VOTING);
    assertTrue(g.getStory().equals("Test Story"));
  }

  /**
   * Tests that all addCardToTable calls are blocked during Voting Phase
   */
  @Test
  public void VotingBlocksTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.updatePhase(Phase.VOTING);
    boolean move1 = g.addCardToTable(p2, storyCard);
    boolean move2 = g.addCardToTable(p3, nonStory1);
    boolean move3 = g.addCardToTable(p1, nonStory2);
    assertTrue(g.getTableCards().size() == 0);
    assertTrue(!move1);
    assertTrue(!move2);
    assertTrue(!move3);
  }

  /**
   * Tests that storyteller cannot vote in voting phase
   */
  @Test
  public void StorytellerVoteBlockTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    boolean isLegal = g.castVote(p2, storyCard);
    assertTrue(!isLegal);
  }

  /**
   * Test that players cannot vote for own cards
   */
  @Test
  public void SelfVoteBlockTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    boolean isLegal1 = g.castVote(p3, nonStory1);
    boolean isLegal2 = g.castVote(p1, nonStory2);
    assertTrue(!isLegal1);
    assertTrue(!isLegal2);
  }

  /**
   * If all votes for storycard, then all players should receive two points and
   * storyteller should receive 0 points.
   * 
   */
  @Test
  public void ScoreAllVotesForStoryTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    g.castVote(p3, storyCard);
    g.castVote(p1, storyCard);
    assertTrue(p3.getScore() == 2);
    assertTrue(p1.getScore() == 2);
    assertTrue(p2.getScore() == 0);
  }

  /**
   * If NO votes for storycard, then all players should receive two points and
   * storyteller should receive 0 points. Players should also receive a point
   * for each vote on their card.
   * 
   */
  @Test
  public void ScoreNoVotesForStoryTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    g.castVote(p3, nonStory2);
    g.castVote(p1, nonStory1);
    assertTrue(p3.getScore() == 3);
    assertTrue(p1.getScore() == 3);
    assertTrue(p2.getScore() == 0);
  }

  /**
   * StoryTeller should receive 2 points for first vote and 1 for each extra.
   * NST should receive 2 points for correct guess and 1 for votes on his/her
   * card.
   */
  @Test
  public void OneStoryOneNonVoteTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    g.castVote(p3, nonStory2);
    g.castVote(p1, storyCard);
    assertTrue(p3.getScore() == 0);
    assertTrue(p1.getScore() == 3);
    assertTrue(p2.getScore() == 2);
  }

  /**
   * Tests that after all votes are in, game cleans up, removes cards from
   * table, clears Story cycles story teller, and begins new round in
   * StoryTeller phase
   */
  @Test
  public void AdvanceToNextRound() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    g.castVote(p3, storyCard);
    g.castVote(p1, storyCard);
    System.out.println(DixitSerializationUtil.serializePhase(g.getPhase()));
    assertTrue(g.getPhase() == Phase.WAITING);
    assertTrue(g.confirmPlayerReady(p1));
    assertTrue(g.confirmPlayerReady(p2));
    assertTrue(g.confirmPlayerReady(p3));
    assertTrue(g.getTableCards().size() == 0);
    assertTrue(g.getPhase() == Phase.STORYTELLER);
    assertTrue(g.getStory().equals(""));
    assertTrue(p3.isStoryteller());
    assertTrue(!p1.isStoryteller());
    assertTrue(!p2.isStoryteller());
  }

  @Test
  public void ScoreTestFourPlayers() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    Player p4 = new Player(Main.newId(), "Abraham", "PURPLE");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    players.add(p4);
    Game g = new Game("CoolGame", 5, 7, players);
    g.startGame();
    Card s = g.getPlayerHand(p1).get(0);
    Card c1 = g.getPlayerHand(p2).get(3);
    Card c2 = g.getPlayerHand(p3).get(5);
    Card c3 = g.getPlayerHand(p4).get(4);
    g.firstStory(p1, "Test Story", s);
    g.addCardToTable(p1, s);
    g.addCardToTable(p2, c1);
    g.addCardToTable(p3, c2);
    g.addCardToTable(p4, c3);
    g.castVote(p2, s);
    g.castVote(p3, c1);
    g.castVote(p4, s);
    assertTrue(p1.getScore() == 3);
    assertTrue(p2.getScore() == 3);
    assertTrue(p3.getScore() == 0);
    assertTrue(p4.getScore() == 2);
  }

  @Test
  public void GameOverTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    p1.incrementScore(29);
    Game g = new Game("CoolGame", 3, 7, players);
    g.startGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    Card nonStory1 = g.getPlayerHand(p3).get(3);
    Card nonStory2 = g.getPlayerHand(p1).get(5);
    g.firstStory(p2, "Test Story", storyCard);
    g.addCardToTable(p3, nonStory1);
    g.addCardToTable(p1, nonStory2);
    g.castVote(p3, nonStory2);
    g.castVote(p1, storyCard);
    assertTrue(g.getPhase() == Phase.WAITING);
    g.confirmPlayerReady(p1);
    g.confirmPlayerReady(p3);
    g.confirmPlayerReady(p2);
    assertTrue(g.getPhase() == Phase.GAMEOVER);
  }

}
