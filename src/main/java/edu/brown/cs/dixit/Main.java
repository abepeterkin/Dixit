package edu.brown.cs.dixit;

import gamestuff.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import com.google.gson.Gson;

public class Main {

  public static final Gson GSON = new Gson();
  private static Map<String, Game> gameMap = new HashMap<String, Game>();
  private static int nextId = 0;

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
  }

  /**
   * Returns the next id to be assigned to a player, and increments the value so
   * that the next player will get a new id.
   *
   * @return an id for a new player
   */
  synchronized public static String newId() {
    String toReturn = Integer.toString(nextId);
    nextId++;
    return toReturn;
  }

  synchronized public static boolean gameExists(
      String name) {
    // System.out.println(gameMap.keySet());
    return gameMap.containsKey(name);
  }

  synchronized public static void addGame(
      String name,
      Game game) {
    gameMap.put(name, game);
  }

  synchronized public static Game getGame(
      String name) {
    return gameMap.get(name);
  }

  synchronized public static void removeGame(
      String name) {
    gameMap.remove(name);
  }

  synchronized public static List<String> getGameNameList() {
    return new ArrayList<String>(gameMap.keySet());
  }

  synchronized public static List<Game> getGameList() {
    return new ArrayList<Game>(gameMap.values());
  }

}
