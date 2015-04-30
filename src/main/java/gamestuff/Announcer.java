package gamestuff;

import java.util.Calendar;

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
    announce(name + " has joined the game!");
    if (playersLeft > 0) {
      announce("Waiting on " + Integer.toString(playersLeft) + " more player(s)...");
    }
  }

  public void gameStart() {
    announce("A new game has begun!");
    storytellerPhase();
  }
  
  public void gameOver() {
    announce("GAME OVER!");
  }

  public void storytellerPhase() {
    String teller = g.getStoryteller().getChatName();
    announce("Waiting on " + teller + "'s story...");
  }

  public void nonStoryPhase() {
    String teller = g.getStoryteller().getChatName();
    String story = g.getStory();
    int cardsLeft = g.getNumberOfPlayers() - g.getTableCards().size();
    announce(teller + " has submitted their story! " + "\"" + story + "\"");
    announce("The game has advanced to card selection phase!");
    announce("Waiting on " + cardsLeft + " more cards...");
  }

  public void submitNonStoryCard(Player p) {
    String name = p.getChatName();
    int cardsLeft = g.getNumberOfPlayers() - g.getTableCards().size();
    System.out.println(cardsLeft);
    announce (name + " has submitted a card!");
    if (cardsLeft > 0) {
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
    if (votesLeft > 0) {
      announce("Waiting on " + votesLeft + " more votes...");
    }
  }

  public void removeVote(Player p) {
    String name = p.getChatName();
    int votesLeft = g.getNumberOfPlayers() - g.getNumberOfVotes() - 1;
    announce(name + " has removed their vote!");
    announce("Waiting on " + votesLeft + " more votes...");
  }
  
  public void advanceToScoringPhase() {
    announce("The game has advanced to voting phase!");
  }
  
  public void incrementScore(Player p, int inc) {
    String name = p.getChatName();
    if (inc > 0) {
      announce(name + " gained " + inc + " points this round!");
    } else {
      announce(name + " didnt gain any points this round :/");
    }
  }
  
  public void advanceToWaitingPhase() {
    announce("Once all players are ready to advance, the next round will begin.");
  }
  
  public void submitReady(Player p) {
    String name = p.getChatName();
    int left = g.getMaxPlayers() - g.numberOfPlayersReady();
    announce(name + " is ready for next round.");
    if (left > 0) {
      announce("Waiting for " + left + " more players...");
    }
  }
  
  public void newRound() {
    announce("All players are ready: advancing to next round...");
    storytellerPhase();
  }

  private void announce(String s) {
    Calendar now = Calendar.getInstance();
    int hour = now.get(Calendar.HOUR_OF_DAY) % 12;
    int minute = now.get(Calendar.MINUTE);
    String zero = "";
    if (minute < 10) {
      zero = "0";
    }
    String message = "[" + Integer.toString(hour)
            + ":" + zero + Integer.toString(minute) + "] " + s;
    ChatLine line = new ChatLine(console, message, color);
    c.addLine(line);
    DixitServer.getDixitGameSubscriber().chatChanged(g);
  }







}
