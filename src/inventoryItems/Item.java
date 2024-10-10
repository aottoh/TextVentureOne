package inventoryItems;

import generalInterfaces.Describable;

public class Item implements Describable {

    //Class attributes
    public String itemName;
    public String itemDescription;
    public String itemID;
    public int itemValue;

    //Constructor method
    public Item(String itemName, String itemDescription, String itemID, int itemValue){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemID = itemID;
        this.itemValue = itemValue;
    }

    public String getDescribableName(){
        return itemName;
    }

    @Override
    public String getDescription(){
        return itemDescription;
    }

    public int getItemValue() {
        return itemValue;
    }

    public String getItemID(){
        return itemID;
    }

}
