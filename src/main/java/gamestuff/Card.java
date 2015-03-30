package gamestuff;

import java.io.File;

public class Card {
  private final int ID;
  private final File IMAGE;
  private boolean isStoryteller = false;

  public int getId() {
    return ID;
  }

  public File getImage() {
    return IMAGE;
  }

  public void setStoryteller(boolean isStoryteller) {
    this.isStoryteller = isStoryteller;
  }

  public boolean getStoryteller() {
    return isStoryteller;
  }

  public Card(int id, File image) {
    this.ID = id;
    this.IMAGE = image;
  }

}
