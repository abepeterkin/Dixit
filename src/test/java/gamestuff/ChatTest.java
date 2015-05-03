package gamestuff;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChatTest {

  @Test
  public void addLines() {
    Player tempPlayer1 = new Player("1", "Nobody", "blue");
    Player tempPlayer2 = new Player("2", "Somebody", "red");

    Chat chat = new Chat();
    assertTrue(chat.getLines().size() == 0);
    for (int i = 0; i < 30; i++) {
      chat.addLine(new ChatLine(tempPlayer1, "" + i, "blue"));
    }
    assertTrue(chat.getLines().size() == 30);
    assertTrue(chat.getLines().get(0).getMessage().equals("0"));
    chat.addLine(new ChatLine(tempPlayer2, "30", "red"));
    assertTrue(chat.getLines().size() == 30);
    assertTrue(chat.getLines().get(0).getMessage().equals("1"));
  }

  @Test
  public void addLinesCustomMax() {
    Player tempPlayer1 = new Player("1", "Nobody", "blue");
    Player tempPlayer2 = new Player("2", "Somebody", "red");

    Chat chat = new Chat();
    assertTrue(chat.getLines().size() == 0);
    for (int i = 0; i < 30; i++) {
      chat.addLine(new ChatLine(tempPlayer1, "" + i, "blue"));
    }
    assertTrue(chat.getLines().get(0).getMessage().equals("10"));
    assertTrue(chat.getLines().size() == 20);
    chat.addLine(new ChatLine(tempPlayer2, "30", "red"));
    assertTrue(chat.getLines().size() == 20);
    assertTrue(chat.getLines().get(0).getMessage().equals("11"));
  }
}
