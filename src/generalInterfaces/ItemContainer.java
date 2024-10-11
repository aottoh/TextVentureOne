package generalInterfaces;

import inventoryItems.Item;

import java.util.ArrayList;

public interface ItemContainer {

    /////////////
    // Getters //
    /////////////

    public ArrayList<Item> getICItems();
    public int getICGold();
    public boolean getICOpened();


    /////////////
    // Setters //
    /////////////

    public void setICGold(int gold);
    public void resetICItems();
    public void setICOpened(boolean opened);
}
