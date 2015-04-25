package gamestuff;
import java.io.File;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import edu.brown.cs.dixit.Main;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

  @Test
  public void PlayerConstructionTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    assertTrue(p.getScore() == 0);
    assertTrue(p.getChatName().equals("Chris"));
    assertTrue(p.getColor().equals("BLUE"));
    assertTrue(p.isStoryteller() == false);
    assertTrue(p.cardsInHand() == 0);
  }

  @Test
  public void IncrementScoreTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    p.incrementScore(5);
    assertTrue(p.getScore() == 5);
    p.incrementScore(7);
    assertTrue(p.getScore() == 12);
  }

  @Test
  public void ComparePlayerGreaterTest() {
    Player p1 = new Player(Main.newId(), "Chris", "BLUE");
    p1.incrementScore(5);
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    p2.incrementScore(4);
    assertTrue(p1.compareTo(p2) > 0);
  }

  @Test
  public void ComparePlayerEqualsTest() {
    Player p1 = new Player(Main.newId(), "Chris", "BLUE");
    p1.incrementScore(12);
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    p2.incrementScore(12);
    assertTrue(p1.compareTo(p2) == 0);
  }

  @Test
  public void ComparePlayerLessTest() {
    Player p1 = new Player(Main.newId(), "Chris", "BLUE");
    p1.incrementScore(7);
    Player p2 = new Player(Main.newId(), "Esteban", "RED");
    p2.incrementScore(12);
    assertTrue(p1.compareTo(p2) < 0);
  }

  @Test
  public void DrawCardTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    Stack<Card> deck = CardInitilizer.load(
            new File("src/test/java/gamestuff/testimages"));
    p.draw(deck.pop());
    p.draw(deck.pop());
    p.draw(deck.pop());
    assertTrue(p.cardsInHand() == 3);
  }

  @Test
  public void RemoveCardTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    Stack<Card> deck = CardInitilizer.load(
            new File("src/test/java/gamestuff/testimages"));
    Card c1, c2, c3;
    c1 = deck.pop();
    c2 = deck.pop();
    c3 = deck.pop();
    p.draw(c1);
    p.draw(c2);
    p.draw(c3);
    p.removeFromHand(c2);
    List<Card> hand = p.getHand();
    assertTrue(hand.contains(c1));
    assertTrue(hand.contains(c3));
    assertTrue(!hand.contains(c2));
    assertTrue(hand.size() == 2);
  }

  @Test
  public void NotStoryTellerTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    Stack<Card> deck = CardInitilizer.load(
            new File("src/test/java/gamestuff/testimages"));
    p.draw(deck.pop());
    p.draw(deck.pop());
    p.draw(deck.pop());
    List<Card> hand = p.getHand();
    for (Card c: hand) {
      assertTrue(c.getStoryteller() == false);
    }
  }

  @Test
  public void SetStoryTellerTest() {
    Player p = new Player(Main.newId(), "Chris", "BLUE");
    Stack<Card> deck = CardInitilizer.load(
            new File("src/test/java/gamestuff/testimages"));
    p.draw(deck.pop());
    p.draw(deck.pop());
    p.draw(deck.pop());
    p.setIsStoryteller(true);
    List<Card> hand = p.getHand();
    for (Card c: hand) {
      assertTrue(c.getStoryteller() == true);
    }
    assertTrue(p.isStoryteller() == true);
  }


}
