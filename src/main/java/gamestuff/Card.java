package gamestuff;

public class Card {
  private final String ID;
  private final String IMAGE;
  private boolean isStoryteller = false;
  
  public String getId() {
    return ID;
  }

  public String getImage() {
    return IMAGE;
  }

  public void setStoryteller(boolean isStoryteller) {
    this.isStoryteller = isStoryteller;
  }

  public boolean getStoryteller() {
    return isStoryteller;
  }

  public Card(String id, String image) {
    this.ID = id;
    this.IMAGE = image;
  }

}
