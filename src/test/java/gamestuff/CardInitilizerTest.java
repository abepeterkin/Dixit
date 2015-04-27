package gamestuff;

import java.io.File;
import java.util.Stack;

import org.junit.Test;

public class CardInitilizerTest {

  @Test
  public void load() {
    Stack<Card> deck = CardInitilizer.load(new File(
        "src/test/java/gamestuff/testimages"));
    StringBuilder cardStringBuilder = new StringBuilder();
    for (Card c : deck) {
      cardStringBuilder.append("\n" + c.getId() + " " + c.getImage());
    }
    String cardString = cardStringBuilder.toString();
    String correctString = "\n0 test1.jpg" + "\n1 test2.jpe" + "\n2 test3.jpeg"
        + "\n3 test4.jfif" + "\n4 test5.png";
    assert (cardString.equals(correctString));
  }
}
