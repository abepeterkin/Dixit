package edu.brown.cs.dixit;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

public class Dixit {
  private static final int DEFAULT_PORT = 2345;

  public static void runSparkSever(Integer port) {
    port = port != null ? port : DEFAULT_PORT;
    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/", new MainHandler(), new FreeMarkerEngine());
  }

  private static class MainHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Dixit");
      return new ModelAndView(variables, "dixit.ftl");
    }

  }
}
