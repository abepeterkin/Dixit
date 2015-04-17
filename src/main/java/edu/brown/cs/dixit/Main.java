package edu.brown.cs.dixit;

import gamestuff.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {

  private static Map<String, Game> gameMap = new HashMap<String, Game>();

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

  public static boolean gameExists(
      String name) {
    return gameMap.containsKey(name);
  }

  public static void addGame(
      String name,
      Game game) {
    gameMap.put(name, game);
  }

  public static Game getGame(
      String name) {
    return gameMap.get(name);
  }

  public static void removeGame(
      String name) {
    gameMap.remove(name);
  }

  public static List<String> getGameNameList() {
    return new ArrayList<String>(gameMap.keySet());
  }

}
