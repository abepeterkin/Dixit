package gamestuff;

/**
 * Class representing a vote, containing a player and a card
 */
public class Vote {
  private final Player player;
  private final Card card;

  /**
   * @param player
   *          the player who cast the vote
   * @param card
   *          the card the vote was cast for
   */
  public Vote(Player player, Card card) {
    this.player = player;
    this.card = card;
  }

  /**
   * @return the player who cast the vote
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * @return the card the vote was cast for
   */
  public Card getCard() {
    return card;
  }
}
