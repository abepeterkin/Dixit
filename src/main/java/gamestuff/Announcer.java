package gamestuff;

import edu.brown.cs.dixit.DixitServer;

public class Announcer {

  private Player console;
  private Chat c;
  private String color;
  private Game g;

  public Announcer(Chat c, Game g) {
    this.console = new Player("-1", "Game", "BLACK");
    this.c = c;
    this.color = "BLACK";
    this.g = g;
  }

  public void gameCreated() {
    String name = g.getPlayerNames().get(0);
    announce(name + " has created a game!");
    int playersLeft = g.getMaxPlayers() - 1;
    announce("Waiting on " + playersLeft + " more players...");
  }

  public void newPlayer(Player p) {
    String name = p.getChatName();
    int playersLeft = g.getMaxPlayers() - g.getNumberOfPlayers();
    announce("Alas! " + name + " has joined the game!");
    if (playersLeft > 0) {
      announce("Waiting on " + Integer.toString(playersLeft) + " more player(s)...");
    }
  }

  public void gameStart() {
    announce("A new game has begun!");
  }

  public void storytellerPhase() {
    String teller = g.getStoryteller().getChatName();
    announce("Waiting on " + teller + "'s story...");
  }

  public void nonStoryPhase() {
    String teller = g.getStoryteller().getChatName();
    String story = g.getStory();
    int cardsLeft = g.getNumberOfPlayers() - g.getTableCards().size();
    announce(teller + " has submitted their story! " + story);
    announce("The game has advanced to card selection phase!");
    announce("Waiting on " + cardsLeft + " more cards...");
  }

  public void submitNonStoryCard(Player p) {
    String name = p.getChatName();
    int cardsLeft = g.getNumberOfPlayers() - g.getTableCards().size();
    announce (name + " has submitted a card!");
    if (cardsLeft < 0) {
      announce("Waiting on " + cardsLeft + " more cards...");
    }
  }

  public void advanceToVotingPhase() {
    announce("The game as advanced to voting phase!");
    int votesLeft = g.getNumberOfPlayers() - g.getNumberOfVotes() - 1;
    announce("Waiting on " + votesLeft + " votes...");

  }

  public void submitVote(Player p) {
    String name = p.getChatName();
    int votesLeft = g.getNumberOfPlayers() - g.getNumberOfVotes() - 1;
    announce (name + " has cast their vote!");
    if (votesLeft < 0) {
      announce("Waiting on " + votesLeft + " more votes...");
    }
  }

  public void removeVote(Player p) {
    String name = p.getChatName();
    int votesLeft = g.getNumberOfPlayers() - g.getNumberOfVotes() - 1;
    announce (name + " has removed their vote!");
    announce("Waiting on " + votesLeft + " more votes...");
  }

  private void announce(String s) {
    ChatLine line = new ChatLine(console, s, color);
    c.addLine(line);
    DixitServer.getDixitGameSubscriber().chatChanged(g);
  }







}
