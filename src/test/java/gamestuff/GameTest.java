package gamestuff;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
    g.newGame();
    assertTrue(g.getPhase().equals(Phase.PREGAME));
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
    g.newGame();
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
    g.newGame();
    List<Card> h1 = g.getPlayerHand(p1);
    List<Card> h2 = g.getPlayerHand(p2);
    List<Card> h3 = g.getPlayerHand(p3);
    assertTrue(h1.size() == 7);
    assertTrue(h2.size() == 7);
    assertTrue(h3.size() == 7);
  }
  
  @Test
  public void FirstStoryTest() {
    List<Player> players = new ArrayList<Player>();
    Player p1 = new Player(Main.newId(), "Zach", "BLUE");
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    Player p3 = new Player(Main.newId(), "Jack", "GREEN");
    players.add(p1);
    players.add(p2);
    players.add(p3);
    Game g = new Game("CoolGame", 3, 7, players);
    g.newGame();
    Card storyCard = g.getPlayerHand(p2).get(0);
    g.firstStory(p2, "Test Story", storyCard);
    assertTrue(g.getStory().equals("Test Story"));
    assertTrue(g.getPhase() == Phase.NONSTORYCARDS);
    assertTrue(g.getTableCards().get(0).equals(storyCard));
  }

}
