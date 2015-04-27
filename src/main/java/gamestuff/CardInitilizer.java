package gamestuff;

import java.io.File;
import java.util.Stack;

/** Class for initilizing decks of cards.
 */
public class CardInitilizer {

  /** Takes a folder containing image files and creates a list of Cards
   * Corresponding to those images.
   *
   * @param imageFolder the folder containing the images that will be turned into files
   * @return a stack of Cards made from the files in the folder
   */
  public static Stack<Card> load(File imageFolder) {
    Stack<Card> toReturn = new Stack<Card>();
    int idCount = 0;
    for (final File file : imageFolder.listFiles()) {
        if (file.isFile()
            && (file.getName().endsWith(".jpg")
            || file.getName().endsWith(".jpeg")
            || file.getName().endsWith(".jpe")
            || file.getName().endsWith(".jfif")
            || file.getName().endsWith(".png"))) {
          toReturn.push(new Card(Integer.toString(idCount), file.getName()));
          idCount++;
        }
    }
    return toReturn;
  }
}
