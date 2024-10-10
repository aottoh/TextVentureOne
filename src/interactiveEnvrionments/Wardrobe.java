package interactiveEnvrionments;

import inventoryItems.Item;
import ventureRooms.Room;

import java.util.ArrayList;

public class Wardrobe extends LockIntrEnv{

    int wardrobeGold;
    ArrayList<Item> wardrobeItems;
    String wardrobeKeyID;
    Room wardrobeRoom;

    public Wardrobe(String wardrobeName, String wardrobeDescription, String wardrobeID, boolean wardrobeLocked, int wardrobeGold){
        super(wardrobeName, wardrobeDescription, wardrobeID, wardrobeLocked, "");
        this.wardrobeItems = new ArrayList<Item>();
        this.wardrobeGold = wardrobeGold;
        this.wardrobeRoom = null;
    }


    /////////////
    // Getters //
    /////////////

    public ArrayList<Item> getWardrobeItems(){
        return wardrobeItems;
    }

    public int getWardrobeGold(){
        return wardrobeGold;
    }


    /////////////////////////////////
    // Post Initialization Setters //
    /////////////////////////////////

    public void addItemToWardrobe(Item item){
        this.wardrobeItems.add(item);
    }

    public void addKeyIDtoWardrobe(String keyID){
        this.wardrobeKeyID = keyID;
    }

    public void addRoomToWardobe(Room room){
        this.wardrobeRoom = room;
    }


    //////////////////
    // Game methods //
    //////////////////

    public void removeItemFromWardrobe(String itemID) {
        boolean found = false;
        for (int i = 0; i < this.wardrobeItems.size(); i++) {
            if (this.wardrobeItems.get(i).getItemID().equals(itemID)) {
                this.wardrobeItems.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Interactive Environment not found: " + itemID);
        }
    }


}
