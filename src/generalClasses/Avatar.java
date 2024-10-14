package generalClasses;
import inventoryItems.Item;

import java.util.ArrayList;

public class Avatar {
    String avName;
    int avGold;
    ArrayList<Item> avInventory;

    public Avatar() {
        this.avName = "";
        this.avGold = 0;
        this.avInventory = new ArrayList<Item>();
    }


    /////////////
    // Getters //
    /////////////

    public String getAvName() {
        return avName;
    }

    public int getAvGold() {
        return avGold;
    }

    public ArrayList<Item> getAvInventory() {
        return avInventory;
    }


    /////////////
    // Setters //
    /////////////

    public void setAvName(String name) {
        this.avName = name;
    }

    public void setAvGold(int gold) {
        this.avGold = gold;
    }

    // No setters added for avInventory so far. Doesn't seem necessary as of now.


    //////////////////
    // Game Methods //
    //////////////////

    public void addToAvatarInventory(Item item) {
        this.avInventory.add(item);
    }

    public void removeFromAvatarInventory(Item item) {
        boolean found = false;
        for (int i = 0; i < this.avInventory.size(); i++) {
            if (this.avInventory.get(i).getItemID().equals(item.getItemID())) {
                this.avInventory.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Interactive Environment not found: " + item.getItemID());
        }
    }

    public void listAvatarInventory() {
        if(avInventory.isEmpty()){
            Exec.appendLnToConsole("There are no items in your inventory.");
        } else {
            for(Item item : avInventory){
                Exec.appendLnToConsole(item.getDescribableName() + " ");
            }
        }
    }
}
