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
	void handChanged(Game game, Player player);

	/**
	 * Should be called if a shallow player property has changed (ex: score).
	 * 
	 * @param game
	 *          The game containing the player.
	 * @param player
	 *          The player whose properties have changed.
	 */
	void playerChanged(Game game, Player player);

	/**
	 * Should be called if a shallow game property has changed (ex: phase).
	 *
	 * @param game
	 *          The game which has changed.
	 */
	void gameChanged(Game game);

	/**
	 * Should be called if a game's chat has changed.
	 * 
	 * @param game
	 *          The game whose chat has changed.
	 */
	void chatChanged(Game game);

}
