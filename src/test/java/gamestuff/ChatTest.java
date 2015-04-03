package gamestuff;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChatTest {

  @Test
  public void addLines() {
    Chat chat = new Chat();
    assertTrue(chat.getLines().size() == 0);
    for (int i = 0; i < 30; i++) {
      chat.addLine(new ChatLine("Nobody", "" + i, Color.BLUE));
    }
    assertTrue(chat.getLines().size() == 30);
    assertTrue(chat.getLines().get(0).getMessage().equals("0"));
    chat.addLine(new ChatLine("Somebody", "30", Color.RED));
    assertTrue(chat.getLines().size() == 30);
    assertTrue(chat.getLines().get(0).getMessage().equals("1"));
  }

  @Test
  public void addLinesCustomMax() {
    Chat chat = new Chat(20);
    assertTrue(chat.getLines().size() == 0);
    for (int i = 0; i < 30; i++) {
      chat.addLine(new ChatLine("Nobody", "" + i, Color.BLUE));
    }
    assertTrue(chat.getLines().get(0).getMessage().equals("10"));
    assertTrue(chat.getLines().size() == 20);
    chat.addLine(new ChatLine("Somebody", "30", Color.RED));
    assertTrue(chat.getLines().size() == 20);
    assertTrue(chat.getLines().get(0).getMessage().equals("11"));
  }
}
