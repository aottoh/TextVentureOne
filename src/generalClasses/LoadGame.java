package generalClasses;

import java.io.*;

public class LoadGame {
    public static Game loadGame(String filename) {
        Game game = null;
        Avatar avatar = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            avatar = (Avatar) ois.readObject();  // Load the avatar
            game = (Game) ois.readObject();      // Load the entire game object
            game.setAvatar(avatar);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
        return game;  // Return the loaded game
    }
}
