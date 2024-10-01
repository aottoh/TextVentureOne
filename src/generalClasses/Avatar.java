package generalClasses;
import inventoryItems.Item;

import java.util.ArrayList;

public class Avatar {
    String avName;
    int avGold;
    ArrayList<Item> avInventory;

    public Avatar(String avName, int avGold, ArrayList<Item> avInventory){
        this.avName = avName;
        this.avGold = avGold;
        this.avInventory = avInventory;
    }

    public String getAvName(){
        return avName;
    }

    public int getAvGold(){
        return avGold;
    }

    public ArrayList<Item> getAvInventory(){
        return avInventory;
    }
}
