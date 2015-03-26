package edu.brown.cs.dixit;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {

  public static void main(String[] args) {
    OptionParser parser = new OptionParser();
    OptionSpec<Integer> port = parser.accepts("port").withRequiredArg()
        .ofType(Integer.class);
    try {
      OptionSet options = parser.parse(args);
      Dixit.runSparkSever(port.value(options));
    } catch (RuntimeException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }
}
