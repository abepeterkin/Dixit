package gamestuff;

/** Class containing info that will be passed to the game's chat box
 */
public class ChatLine {
  private String message;
  private String playerName;
  private Color color;

  /** Constructor for ChatLines.
   *
   * @param message the content of the message
   * @param playerName the name of the player who sent the message
   * @param color the player's color
   */
  public ChatLine(String playerName, String message, Color color) {
    this.message = message;
    this.playerName = playerName;
    this.color = color;
  }

  /**
   * @return the message in the line
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the name of the player who sent the message
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * @return the color of the player who sent the message
   */
  public Color getColor() {
    return color;
  }
}
