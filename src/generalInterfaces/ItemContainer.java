package generalInterfaces;

import inventoryItems.Item;

import java.util.ArrayList;

public interface ItemContainer {

    /////////////
    // Getters //
    /////////////

    public ArrayList<Item> getICItems();
    public int getICGold();


    /////////////
    // Setters //
    /////////////

    public void setICGold(int gold);
    public void resetICItems();
}
