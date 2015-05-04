package edu.brown.cs.dixit;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import edu.brown.cs.dixit.pages.AddChatRequest;
import edu.brown.cs.dixit.pages.AddNonStoryCardRequest;
import edu.brown.cs.dixit.pages.AddPlayerRequest;
import edu.brown.cs.dixit.pages.AddScoreRequest;
import edu.brown.cs.dixit.pages.AddStoryCardRequest;
import edu.brown.cs.dixit.pages.CreateGameRequest;
import edu.brown.cs.dixit.pages.DixitFinalScoresPage;
import edu.brown.cs.dixit.pages.DixitHomePage;
import edu.brown.cs.dixit.pages.DixitJoinGamePage;
import edu.brown.cs.dixit.pages.DixitJoinOptionsPage;
import edu.brown.cs.dixit.pages.DixitMainPage;
import edu.brown.cs.dixit.pages.DixitNewGamePage;
import edu.brown.cs.dixit.pages.GetGameRequest;
import edu.brown.cs.dixit.pages.GetUpdateRequest;
import edu.brown.cs.dixit.pages.PlayerReadyRequest;
import edu.brown.cs.dixit.pages.RemoveNonStoryCardRequest;
import edu.brown.cs.dixit.pages.RemoveVoteForCardRequest;
import edu.brown.cs.dixit.pages.SeeCurrentGamesRequest;
import edu.brown.cs.dixit.pages.VoteForCardRequest;

/**
 * Runs the spark server of the Dixit game.
 */
public class DixitServer {
  private static final int DEFAULT_PORT = 2345;
  private static GetUpdateRequest getUpdateRequest = new GetUpdateRequest();

  /**
   * Initializes all of the pages.
   *
   * @param port
   *          The port on which to run the server.
   */
  public static void runSparkSever(
      Integer port) {
    port = port != null ? port : DEFAULT_PORT;
    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/board", new DixitMainPage(), new FreeMarkerEngine());
    Spark.post("/createGame", new CreateGameRequest(), new FreeMarkerEngine());
    Spark.get("/seeCurrentGames", new SeeCurrentGamesRequest());
    Spark.post("/addPlayer", new AddPlayerRequest(), new FreeMarkerEngine());
    Spark.get("/getGame", new GetGameRequest());
    Spark.get("/getUpdate", getUpdateRequest);
    Spark.post("/addStoryCard", new AddStoryCardRequest());
    Spark.post("/addNonStoryCard", new AddNonStoryCardRequest());
    Spark.post("/removeNonStoryCard", new RemoveNonStoryCardRequest());
    Spark.post("/voteForCard", new VoteForCardRequest());
    Spark.post("/removeVoteForCard", new RemoveVoteForCardRequest());
    Spark.post("/addChat", new AddChatRequest());
    Spark.post("/playerReady", new PlayerReadyRequest());
    Spark.post("/addScore", new AddScoreRequest());
    Spark.get("/joinOptions/:gameName", new DixitJoinOptionsPage(),
        new FreeMarkerEngine());
    Spark.get("/finalScores/:gameName", new DixitFinalScoresPage(),
        new FreeMarkerEngine());
    Spark.get("/", new DixitHomePage(), new FreeMarkerEngine());
    Spark.get("/newGamePage", new DixitNewGamePage(), new FreeMarkerEngine());
    Spark.get("/joinGamePage", new DixitJoinGamePage(), new FreeMarkerEngine());
  }

  /**
   * Retrieves the Dixit game subscriber. This object is used to notify clients
   * when a change has occurred in the game.
   *
   * @return The game subscriber.
   */
  public static DixitGameSubscriber getDixitGameSubscriber() {
    return getUpdateRequest;
  }

}
