package gamestuff;

/**
 * Class containing info that will be passed to the game's chat box
 */
public class ChatLine {
  private String message;
  private Player player;
  private String color;

  /**
   * Constructor for ChatLines.
   *
   * @param message
   *          the content of the message
   * @param playerName
   *          the name of the player who sent the message
   * @param color
   *          the player's color
   */
  public ChatLine(Player player, String message, String color) {
    this.message = message;
    this.player = player;
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
    return player.getChatName();
  }

  /**
   * @return the id of the player who sent the message
   */
  public String getPlayerId() {
    return player.getId();
  }

  /**
   * @return the color of the player who sent the message
   */
  public String getColor() {
    return color;
  }
}
