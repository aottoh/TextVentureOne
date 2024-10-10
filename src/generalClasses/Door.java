package generalClasses;

import interactiveEnvrionments.LockIntrEnv;
import inventoryItems.Key;
import ventureRooms.Room;

import java.util.ArrayList;
import java.util.Objects;

public class Door extends LockIntrEnv {

    ArrayList<Room> doorRooms;

    public Door(String doorName, String doorDescription, String doorID, boolean doorLocked){
        super(doorName, doorDescription, doorID, doorLocked, "");
        this.doorRooms = new ArrayList<Room>();
    }


    /////////////
    // Getters //
    /////////////

    public ArrayList<Room> getDoorRooms(){
        return doorRooms;
    }


    /////////////////////////////////
    // Post Initialization Setters //
    /////////////////////////////////

    public void addRoomToDoor(Room room){
        this.doorRooms.add(room);
    }
}
