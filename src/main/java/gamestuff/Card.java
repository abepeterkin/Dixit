package gamestuff;

public class Card {
  private final String ID;
  private final String IMAGE;
  private boolean isStoryteller = false;

  /**
   * @return the card ID
   */
  public String getId() {
    return ID;
  }

  /**
   * @return the name of the card's image
   */
  public String getImage() {
    return IMAGE;
  }

  /**
   * @param isStoryteller whether the card is the card referred to by the
   * storyteller's clue
   */
  public void setStoryteller(boolean isStoryteller) {
    this.isStoryteller = isStoryteller;
  }

  /**
   * @return whether the player is the storyteller
   */
  public boolean getStoryteller() {
    return isStoryteller;
  }

  /**
   * @param id the id of the card
   * @param image the name of the card's image
   */
  public Card(String id, String image) {
    this.ID = id;
    this.IMAGE = image;
  }

}
