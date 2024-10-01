package interactiveEnvrionments;

import inventoryItems.Item;

import java.util.ArrayList;

public class Wardrobe extends IntrEnv{

    int wardrobeGold;
    ArrayList<Item> wardrobeItems;
    String wardrobeKeyID;

    public Wardrobe(String wardrobeName, String wardrobeDescription, String wardrobeID, boolean wardrobeLocked, int wardrobeGold){
        super(wardrobeName, wardrobeDescription, wardrobeID, wardrobeLocked, false);
        this.wardrobeItems = new ArrayList<Item>();
        this.wardrobeGold = wardrobeGold;
        this.wardrobeKeyID = "";
    }

    // Getters
    public ArrayList<Item> getWardrobeItems(){
        return wardrobeItems;
    }

    public int getWardrobeGold(){
        return wardrobeGold;
    }

    // Post Initialization Setters
    public void addItemToWardrobe(Item item){
        this.wardrobeItems.add(item);
    }

    public void addKeyIDtoWardrobe(String keyID){
        this.wardrobeKeyID = keyID;
    }

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
