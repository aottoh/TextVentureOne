package interactiveEnvrionments;

import ventureRooms.Room;

import java.util.ArrayList;

public class Door extends IntrEnv{

    ArrayList<Room> doorRooms;
    String doorKeyID;

    public Door(String doorName, String doorDescription, String doorID, boolean doorLocked){
        super(doorName, doorDescription, doorID, doorLocked, true);
        this.doorRooms = new ArrayList<Room>();
        this.doorKeyID = "";
    }

    // Getters
    public ArrayList<Room> getDoorRooms(){
        return doorRooms;
    }

    public String getDoorKeyID(){
        return doorKeyID;
    }

    // Setters
    // Required post initialization of rooms and keys.
    public void addRoomToDoor(Room room){
        this.doorRooms.add(room);
    }

    public void addKeyIDtoDoor(String keyID){
        this.doorKeyID = keyID;
    }
}
