package gamestuff;

public class Card {
  private final int ID;
  private final String IMAGE;
  private boolean isStoryteller = false;

  public Card(int id, String image) {
    this.ID = id;
    this.IMAGE = image;
  }
  
  public int getId() {
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

}
