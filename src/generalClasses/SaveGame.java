package generalClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGame {
    public static void saveGame(Game game, Avatar avatar, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(avatar);  // Save the avatar
            oos.writeObject(game);     // Save the entire game object
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }
}
