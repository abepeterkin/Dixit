package gamestuff;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Chat {
  private List<ChatLine> lines = new ArrayList<ChatLine>();
  private final int MAX_LINES;

  /**
   * Constructor for Chat with no arguments, maximum lines set to 30
   *
   */
  public Chat() {
    MAX_LINES = 30;
  }

  /**
   * Constructor for Chat that takes the maximum number of lines as an argument
   *
   * @param maxLines
   *          the maximum number of lines the chat can have
   */
  public Chat(int maxLines) {
    MAX_LINES = maxLines;
  }

  /**
   * Adds a line to the chat. If the chat has grown too large, an older line is
   * removed.
   * 
   * @param chatLine
   *          the line to be added
   */
  synchronized public void addLine(
      ChatLine chatLine) {
    lines.add(chatLine);
    if (lines.size() > MAX_LINES) {
      lines.remove(0);
    }
    System.out.println(chatLine.getMessage());
  }

  /**
   * @return every line in the chat
   */
  synchronized public List<ChatLine> getLines() {
    return ImmutableList.copyOf(lines);
  }

  @Override
  synchronized public String toString() {
    StringBuilder builder = new StringBuilder();
    for (ChatLine line : lines) {
      builder.append(line.getPlayerName() + ": " + line.getMessage() + "\n");
    }
    return builder.toString();
  }

}
