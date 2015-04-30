package edu.brown.cs.dixit;

import gamestuff.Game;
import gamestuff.Player;

/**
 * Receives information about changes in games.
 */
public interface DixitGameSubscriber {

  /**
   * Should be called if a player's hand changes.
   *
   * @param game
   *          The game containing the player.
   * @param player
   *          The player whose hand changed.
   */
  void handChanged(
      Game game,
      Player player);

  /**
   * Should be called if a shallow player property has changed (ex: score).
   *
   * @param game
   *          The game containing the player.
   * @param player
   *          The player whose properties have changed.
   */
  void playerChanged(
      Game game,
      Player player);

  /**
   * Should be called if the table cards have changed.
   *
   * @param game
   *          The game containing the table cards.
   */
  void tableCardsChanged(
      Game game);

  /**
   * Should be called if a shallow game property has changed (ex: phase).
   *
   * @param game
   *          The game which has changed.
   */
  void gameChanged(
      Game game);

  /**
   * Should be called if a game's chat has changed.
   *
   * @param game
   *          The game whose chat has changed.
   */
  void chatChanged(
      Game game);

  /**
   * Should be called if a player gets added.
   * 
   * @param game
   *          the game the player is added to
   * @param player
   *          the player who is added
   */
  void playerAdded(
      Game game,
      Player player);

  /**
   * Should be called if the votes change.
   *
   * @param game
   *          The game whose votes have changed.
   */
  void votesChanged(
      Game game);

}
