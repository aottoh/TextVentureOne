package ventureRooms;

import java.io.Serializable;
import java.util.ArrayList;

import generalClasses.Door;
import generalInterfaces.Describable;
import inventoryItems.Item;
import interactiveEnvrionments.IntrEnv;
import generalClasses.Exec;

public class Room implements Serializable {
    String roomName;
    String roomDescription;
    String roomID;
    int roomGold;
    public ArrayList<Item> roomItems;
    ArrayList<IntrEnv> roomIntrEnv;
    ArrayList<Door> roomDoors;
    public ArrayList<Item> roomVisibleItems; // Required to display all available items in the room, even after unlocking further items, whiting interactive environments, for example
    public ArrayList<Describable> roomDescribableObjects;

    public Room(String roomName, String roomDescription, String roomID, int roomGold) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomGold = roomGold;
        this.roomID = roomID;
        this.roomItems = new ArrayList<Item>();
        this.roomIntrEnv = new ArrayList<IntrEnv>();
        this.roomDoors = new ArrayList<Door>();
        this.roomVisibleItems = new ArrayList<Item>();
        this.roomDescribableObjects = new ArrayList<Describable>();
    }


    /////////////
    // Getters //
    /////////////

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public int getRoomGold() {
        return roomGold;
    }

    public String getRoomID() {
        return roomID;
    }

    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }

    public ArrayList<IntrEnv> getRoomIntrEnv() {
        return roomIntrEnv;
    }

    public ArrayList<Door> getRoomDoors() {
        return roomDoors;
    }

    public ArrayList<Item> getRoomVisibleItems() {
        return roomVisibleItems;
    }

    public ArrayList<Describable> getRoomDescribableObjects() {
        return roomDescribableObjects;
    }


    /////////////
    // Setters //
    /////////////

    public void setRoomName(String name){
        this.roomName = name;
    }

    public void setRoomDescription(String description) {
        this.roomDescription = description;
    }

    public void setRoomGold (int gold){
        this.roomGold = gold;
    }


    /////////////////////////////////
    // Post Initialization Setters //
    /////////////////////////////////

    public void addItemToRoom(Item item) {
        this.roomItems.add(item);
        this.roomVisibleItems.add(item);
        this.roomDescribableObjects.add(item);
    }

    public void addDoorToRoom(Door door) {
        this.roomDoors.add(door);
        this.roomIntrEnv.add(door);
        this.getRoomDescribableObjects().add(door);
    }

    public void addIntrEnvToRoom(IntrEnv intrEnv){
        this.roomIntrEnv.add(intrEnv);
        this.roomDescribableObjects.add(intrEnv);
    }


    //////////////////
    // Game methods //
    //////////////////

    // For Gold
    public void listRoomGold(){
        if (this.roomGold == 0){
            Exec.appendLnToConsole("There is no Gold in the room");
        } else {
            Exec.appendLnToConsole(this.roomGold + " Gold");
        }
    }

    // For Items

    public void removeItemFromRoom(Item item) {
        boolean found = false;
        boolean found2 = false;
        for (int i = 0; i < this.roomItems.size(); i++) {
            if (this.roomItems.get(i).getItemID().equals(item.getItemID())) {
                this.roomItems.remove(i);
                found = true;
                break;
            }
        }

        for (int i = 0; i < this.roomVisibleItems.size(); i++) {
            if (this.roomVisibleItems.get(i).getItemID().equals(item.getItemID())) {
                this.roomVisibleItems.remove(i);
                found = true;
                break;
            }
        }

        if (!found) {
            Exec.appendToConsole("Item not found: " + item.getItemID());
        }
    }

    public void listRoomItems(){
        if(this.roomItems.isEmpty()){
            Exec.appendLnToConsole("There are no items in the room.");
        } else {
            for(Item item : roomItems){
                Exec.appendLnToConsole(item.getDescribableName() + " ");
            }
        }
    }

    public void resetRoomItems() {
        this.roomItems.clear();
        this.roomVisibleItems.clear();
    }

    // For Interactive Environments

    public void removeIntrEnvFromRoom(String intrEnvID) {
        boolean found = false;
        for (int i = 0; i < this.roomIntrEnv.size(); i++) {
            if (this.roomIntrEnv.get(i).getIntrEnvID().equals(intrEnvID)) {
                this.roomIntrEnv.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            Exec.appendToConsole("Interactive Environment not found: " + intrEnvID);
        }
    }

    public void listRoomIntrEnvs() {
        if(this.roomIntrEnv.isEmpty()){
            Exec.appendLnToConsole("There are no interactive environments in the room.");
        } else {
            for(IntrEnv intrEnv : this.roomIntrEnv){
                Exec.appendLnToConsole(intrEnv.getDescribableName() + " ");
            }
        }
    }

    public IntrEnv findIntrEnvByName(String name){
        if(this.roomIntrEnv.isEmpty()){
            return null;
        } else {
            for(IntrEnv intrEnv : this.roomIntrEnv){
                if (intrEnv.getIntrEnvName().equalsIgnoreCase(name)){
                    return intrEnv;
                }
            }
        }
        return null;
    }

    // Specifically for Doors

    public void listRoomDoors() {
        if(this.roomDoors.isEmpty()){
            Exec.appendLnToConsole("There are no doors in the room.");
        } else {
            for(Door door : this.roomDoors){
                Exec.appendLnToConsole(door.getDescribableName() + " ");
            }
        }
    }
}
