package edu.brown.cs.dixit;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import edu.brown.cs.dixit.pages.AddChatRequest;
import edu.brown.cs.dixit.pages.AddNonStoryCardRequest;
import edu.brown.cs.dixit.pages.AddPlayerRequest;
import edu.brown.cs.dixit.pages.AddStoryCardRequest;
import edu.brown.cs.dixit.pages.CreateGameRequest;
import edu.brown.cs.dixit.pages.DixitHomePage;
import edu.brown.cs.dixit.pages.DixitJoinGamePage;
import edu.brown.cs.dixit.pages.DixitJoinOptionsPage;
import edu.brown.cs.dixit.pages.DixitMainPage;
import edu.brown.cs.dixit.pages.DixitNewGamePage;
import edu.brown.cs.dixit.pages.GetGameRequest;
import edu.brown.cs.dixit.pages.GetUpdateRequest;
import edu.brown.cs.dixit.pages.RemoveNonStoryCardRequest;
import edu.brown.cs.dixit.pages.RemoveVoteForCardRequest;
import edu.brown.cs.dixit.pages.SeeCurrentGamesRequest;
import edu.brown.cs.dixit.pages.VoteForCardRequest;

public class DixitServer {
  private static final int DEFAULT_PORT = 2345;
  private static GetUpdateRequest getUpdateRequest = new GetUpdateRequest();

  public static void runSparkSever(
      Integer port) {
    port = port != null ? port : DEFAULT_PORT;
    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/board", new DixitMainPage(), new FreeMarkerEngine());
    Spark.post("/createGame", new CreateGameRequest(), new FreeMarkerEngine());
    Spark.get("/seeCurrentGames", new SeeCurrentGamesRequest());
    Spark.post("/addPlayer", new AddPlayerRequest(), new FreeMarkerEngine());
    Spark.get("/getGame", new GetGameRequest(), new FreeMarkerEngine());
    Spark.get("/getUpdate", getUpdateRequest, new FreeMarkerEngine());
    Spark.post("/addStoryCard", new AddStoryCardRequest(),
        new FreeMarkerEngine());
    Spark.post("/addNonStoryCard", new AddNonStoryCardRequest(),
        new FreeMarkerEngine());
    Spark.post("/removeNonStoryCard", new RemoveNonStoryCardRequest(),
        new FreeMarkerEngine());
    Spark.post("/voteForCard", new VoteForCardRequest(), new FreeMarkerEngine());
    Spark.post("/removeVoteForCard", new RemoveVoteForCardRequest(),
        new FreeMarkerEngine());
    Spark.post("/addChat", new AddChatRequest(), new FreeMarkerEngine());
    Spark.get("/joinOptions/:gameName", new DixitJoinOptionsPage(),
        new FreeMarkerEngine());
    Spark.get("/", new DixitHomePage(), new FreeMarkerEngine());
    Spark.get("/newGamePage", new DixitNewGamePage(), new FreeMarkerEngine());
    Spark.get("/joinGamePage", new DixitJoinGamePage(), new FreeMarkerEngine());
  }

  public static DixitGameSubscriber getDixitGameSubscriber() {
    return getUpdateRequest;
  }

}
