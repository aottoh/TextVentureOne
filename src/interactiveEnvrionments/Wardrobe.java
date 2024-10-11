package interactiveEnvrionments;

import generalInterfaces.Describable;
import generalInterfaces.ItemContainer;
import inventoryItems.Item;
import ventureRooms.Room;

import java.util.ArrayList;

public class Wardrobe extends LockIntrEnv implements Describable, ItemContainer {

    int wardrobeGold;
    ArrayList<Item> wardrobeItems;
    String wardrobeKeyID;
    Room wardrobeRoom;
    boolean wardrobeOpened;

    public Wardrobe(String wardrobeName, String wardrobeDescription, String wardrobeID, boolean wardrobeLocked, int wardrobeGold){
        super(wardrobeName, wardrobeDescription, wardrobeID, wardrobeLocked, "");
        this.wardrobeItems = new ArrayList<Item>();
        this.wardrobeGold = wardrobeGold;
        this.wardrobeRoom = null;
        this.wardrobeOpened = false;
    }


    /////////////
    // Getters //
    /////////////

    @Override
    public ArrayList<Item> getICItems(){
        return wardrobeItems;
    }

    @Override
    public int getICGold(){
        return wardrobeGold;
    }

    @Override
    public boolean getICOpened(){
        return wardrobeOpened;
    }

    /////////////
    // Setters //
    /////////////

    @Override
    public void setICGold(int gold){
        this.wardrobeGold = gold;
    }

    @Override
    public void resetICItems(){
        this.wardrobeItems.clear();
    }

    @Override
    public void setICOpened(boolean opened){
        this.wardrobeOpened = opened;
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
