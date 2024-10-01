package ventureRooms;

import java.util.ArrayList;

import interactiveEnvrionments.Door;
import inventoryItems.Item;
import interactiveEnvrionments.IntrEnv;

public class Room {
    String roomName;
    String roomDescription;
    String roomID;
    int roomGold;
    ArrayList<Item> roomItems;
    ArrayList<IntrEnv> roomIntrEnv;
    ArrayList<Door> roomDoors;

    public Room(String roomName, String roomDescription, String roomID, int roomGold) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomGold = roomGold;
        this.roomID = roomID;
        this.roomItems = new ArrayList<Item>();
        this.roomIntrEnv = new ArrayList<IntrEnv>();
        this.roomDoors = new ArrayList<Door>();
    }

    // Getters
    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public int getRoomGold() {
        return roomGold;
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

    // Setters
    public void setRoomName(String name){
        this.roomName = name;
    }

    public void setRoomDescription(String description) {
        this.roomDescription = description;
    }

    public void setRoomGold (int gold){
        this.roomGold = gold;
    }

    // Post Initialization Setters
    public void addItemToRoom(Item item) {
        this.roomItems.add(item);
    }

    public void addDoorToRoom(Door door) {
        this.roomDoors.add(door);
    }

    public void addIntrEnvToRoom(IntrEnv intrEnv){
        this.roomIntrEnv.add(intrEnv);
    }

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
            System.out.println("Interactive Environment not found: " + intrEnvID);
        }
    }

    public void removeItemFromRoom(String itemID) {
        boolean found = false;
        for (int i = 0; i < this.roomItems.size(); i++) {
            if (this.roomItems.get(i).getItemID().equals(itemID)) {
                this.roomItems.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Interactive Environment not found: " + itemID);
        }
    }


    // Game methods
    public void listRoomItems(){
        System.out.println(roomItems.size());
        if(roomItems.isEmpty()){
            System.out.println("There are no items in the room.");
        } else {
            for(Item item : roomItems){
                System.out.print(item.getItemName() + " ");
            }
        }
    }
}
