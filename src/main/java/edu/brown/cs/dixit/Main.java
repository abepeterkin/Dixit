package edu.brown.cs.dixit;

import gamestuff.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import com.google.gson.Gson;

/**
 * The main class. Contains every currently running game of dixit.
 */
public final class Main {

  public static final Gson GSON = new Gson();
  private static Map<String, Game> gameMap = new HashMap<String, Game>();
  private static int nextId = 0;
  private static final long DELETE_GAME_DELAY = 12 * 60 * 60 * 1000;

  /**
   * @param args
   *          the arguments to run main with
   */
  public static void main(
      String[] args) {
    OptionParser parser = new OptionParser();
    OptionSpec<Integer> port = parser.accepts("port").withRequiredArg()
        .ofType(Integer.class);
    try {
      OptionSet options = parser.parse(args);
      DixitServer.runSparkSever(port.value(options));
    } catch (RuntimeException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
    int delay = 2000; // milliseconds
    ActionListener taskPerformer = new DixitTimerEvent();
    new Timer(delay, taskPerformer).start();
  }

  /**
   * Called every couple of seconds.
   */
  private static class DixitTimerEvent implements ActionListener {
    public void actionPerformed(
        ActionEvent evt) {
      List<String> tempNameList = Main.getGameNameList();
      int index = 0;
      while (index < tempNameList.size()) {
        String tempName = tempNameList.get(index);
        Game tempGame = Main.getGame(tempName);
        long tempDelay = System.currentTimeMillis()
            - tempGame.getLastUpdateTime();
        if (tempDelay > Main.DELETE_GAME_DELAY) {
          Main.removeGame(tempName);
        }
        index++;
      }
    }
  }

  /**
   * Returns the next id to be assigned to a player, and increments the value so
   * that the next player will get a new id.
   *
   * @return an id for a new player
   */
  public static synchronized String newId() {
    String toReturn = Integer.toString(nextId);
    nextId++;
    return toReturn;
  }

  /**
   * @param name
   *          the name of a game
   * @return whether there exists a game with that name
   */
  public static synchronized boolean gameExists(
      String name) {
    // System.out.println(gameMap.keySet());
    return gameMap.containsKey(name);
  }

  /**
   * Creates a new game.
   * 
   * @param name
   *          the name to give the game
   * @param game
   *          the game to add
   */
  public static synchronized void addGame(
      String name,
      Game game) {
    gameMap.put(name, game);
  }

  /**
   * @param name
   *          the name of the game to retrieve
   * @return the game that was found, or null if the game does not exist
   */
  public static synchronized Game getGame(
      String name) {
    return gameMap.get(name);
  }

  /**
   * @param name
   *          the name of the game to remove
   */
  public static synchronized void removeGame(
      String name) {
    gameMap.remove(name);
  }

  /**
   * @return a list of every name used for a game
   */
  public static synchronized List<String> getGameNameList() {
    return new ArrayList<String>(gameMap.keySet());
  }

  /**
   * @return a list of every game the server is running
   */
  public static synchronized List<Game> getGameList() {
    return new ArrayList<Game>(gameMap.values());
  }

  /**
   * Private constructor for the style checker.
   */
  private Main() {
    // does nothing
  }
}
