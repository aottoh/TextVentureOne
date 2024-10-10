package generalClasses;

import generalInterfaces.Describable;
import interactiveEnvrionments.Wardrobe;
import inventoryItems.Item;
import inventoryItems.Key;
import ventureRooms.Room;

import java.util.ArrayList;

public class Game {

    private ArrayList<Room> gameRooms;
    private Room currentRoom;

    public Game () {

        instantiateGameObjects();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public ArrayList<Item> getAvatarInventory(Avatar avatar){
        return avatar.getAvInventory();
    }

    public Room getRoom(String roomID) {
        for (Room room : gameRooms) {
            // Compare room names (case-sensitive)
            if (room.getRoomName().equals(roomID)) {
                return room; // Return the room if found
            }
        }
        return null; // Return null if no room with the given name is found
    }

    public void changeCurrentRoom(Room nextRoom){
        this.currentRoom = nextRoom;
        System.out.println("You entered " + nextRoom.getRoomName());
    }

    public Item findItemByName(String name, Avatar avatar) {
        // Search in room
        for (Item item : getCurrentRoom().getRoomVisibleItems()) {
            if (item.getDescribableName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        for (Item item : avatar.avInventory) {
            if (item.getDescribableName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;  // No item found
    }

    public Describable findDescribableByName(String name, Avatar avatar){

        for (Describable describable : getCurrentRoom().getRoomDescribableObjects()){
            if(describable.getDescribableName().equalsIgnoreCase(name)){
                return describable;
            }
        }

        for (Item item : avatar.avInventory){
            if (item.getDescribableName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public Item findRoomItemByName(String name) {
        for (Item item : getCurrentRoom().getRoomVisibleItems()) {
            if (item.getDescribableName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public Item findAvatarItemByName(String name, Avatar avatar) {
        for (Item item : avatar.getAvInventory()) {
            if (item.getDescribableName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void instantiateGameObjects() {

        /////////////////////////
        // DEFINING GAME ROOMS //
        /////////////////////////
        // Items, Interactive Environments and Doors will be added to the room, once each of these objects are initialized

        //Room One (r1)- Dark Chamber
        String r1Name = "Dark Chamber";
        String r1Description = "A dimly lit chamber. You can't make out any source of light but you see an old Rusty Door North of you.";
        String r1ID = "r01";
        int r1Gold = 5;

        //Room Two (r2)- Wooden Chamber
        String r2Name = "Wooden Chamber";
        String r2Description = "A wooden paneled room. From floor to ceiling. It give you library or bar vibes, if it weren't so empty. There are doors West, East and South of you.";
        String r2ID = "r02";
        int r2Gold = 10;

        //Room Three (r3)- Copper Chamber
        String r3Name = "Hallway Chamber";
        String r3Description = "A room that appears to be quite empty. Just like a simple hallway. Or as if the architect ran out of ideas what to do with this room. There are doors ro the North and East.";
        String r3ID = "r03";
        int r3Gold = 0;

        //Room Four (r4)- Mystery Chamber
        String r4Name = "Mystery Chamber";
        String r4Description = "A mysteriously flickering light. You can make out a Wardrobe to your West. The only door is the one you came from - South of you.";
        String r4ID = "r04";
        int r4Gold = 15;

        //Room Five (r5)- Silver Chamber
        String r5Name = "Silver Chamber";
        String r5Description = "Wow. A very bright room! The silver chandelier on the ceiling is illuminating the entire room. It even dazzles you in the first moment. There is a Wardrobe to you South. Doors are West and North of you.";
        String r5ID = "r05";
        int r5Gold = 5;

        //Room Six (r6)- Black Chamber
        String r6Name = "Black Chamber";
        String r6Description = "From brightness to darkness. You see almost nothing. The only source of light comes from beneath the previous door. There appears to be a door North and South of you.";
        String r6ID = "r06";
        int r6Gold = 0;

        //Initializing Rooms
        Room r1 = new Room(r1Name, r1Description, r1ID, r1Gold);
        Room r2 = new Room(r2Name, r2Description, r2ID, r2Gold);
        Room r3 = new Room(r3Name, r3Description, r3ID, r3Gold);
        Room r4 = new Room(r4Name, r4Description, r4ID, r4Gold);
        Room r5 = new Room(r5Name, r5Description, r5ID, r5Gold);
        Room r6 = new Room(r6Name, r6Description, r6ID, r6Gold);


        /////////////////////////
        // DEFINING GAME ITEMS //
        /////////////////////////

        // Key 1 (k1) - Rusty Key - In Room One
        String k1Name = "Rusty Key";
        String k1Description = "A seemingly old rusty key. You wonder how it hasn't fallen apart yet.";
        String k1ID = "keyr1r2";
        int k1Value = 0;


        // Key 2 (k2) - Copper Key - In Room Two
        String k2Name = "Copper Key";
        String k2Description = "A lightweight key, a little shiny. It seems to be made out of copper.";
        String k2ID = "keyr3r4";
        int k2Value = 2;

        // Key 3 (k3) - Silver Key - In Room Two
        String k3Name = "Silver Key";
        String k3Description = "A key made of silver. Though only a key, it seems to have some value.";
        String k3ID = "keyr2r5";
        int k3Value = 5;

        // Key 4 (k4) - Wooden Key - In Room Three
        String k4Name = "Wooden Key";
        String k4Description = "A heavy wooden key. You wonder what it might open.";
        String k4ID = "keyw1";
        int k4Value = 1;

        // Key 5 - Black Key - In Wardrobe One
        String k5Name = "Black Key";
        String k5Description = "A purely black key. Absorbing all color and light, you have never seen something truly as black";
        String k5ID = "keyd6exit";
        int k5Value = 0;

        //Initializing Items
        Key k1 = new Key(k1Name,k1Description, k1ID, k1Value);
        Key k2 = new Key(k2Name,k2Description, k2ID, k2Value);
        Key k3 = new Key(k3Name,k3Description, k3ID, k3Value);
        Key k4 = new Key(k4Name,k4Description, k4ID, k4Value);
        Key k5 = new Key(k5Name,k5Description, k5ID, k5Value);


        /////////////////////////
        // DEFINING GAME Doors //
        /////////////////////////

        // Doors

        // Door 1 - Rusty Door - Connecting R1 and R2
        String d1Name = "Rusty Door";
        String d1Description = "An old rusty door.";
        String d1ID = "dr1r2";
        boolean d1Locked = true;

        // Door 2 - Rusty Door - Connecting R2 and R3
        String d2Name = "Ivory Door";
        String d2Description = "A door as bright as ivory.";
        String d2ID = "dr2r3";
        boolean d2Locked = false;

        // Door 3 - Rusty Door - Connecting R3 and R4
        String d3Name = "Copper Door";
        String d3Description = "A metalic door. Possibly made of copper.";
        String d3ID = "dr3r4";
        boolean d3Locked = true;

        // Door 4 - Wooden Door - Connecting R2 and R5
        String d4Name = "Wooden Door";
        String d4Description = "A heavy wooden door.";
        String d4ID = "dr2r5";
        boolean d4Locked = true;

        // Door 5 - Silver Door - Connecting R5 and R6
        String d5Name = "Silver Door";
        String d5Description = "A Silver door. You wonder what it must have been worth.";
        String d5ID = "dr5r6";
        boolean d5Locked = false;

        // Door 6 - Black Door - Connecting R6 to Exit
        String d6Name = "Black Door";
        String d6Description = "A door so black, you can barely see the keyhole.";
        String d6ID = "dr6";
        boolean d6Locked = true;

        // Instantiating Doors
        Door d1 = new Door(d1Name, d1Description, d1ID, d1Locked);
        Door d2 = new Door(d2Name, d2Description, d2ID, d2Locked);
        Door d3 = new Door(d3Name, d3Description, d3ID, d3Locked);
        Door d4 = new Door(d4Name, d4Description, d4ID, d4Locked);
        Door d5 = new Door(d5Name, d5Description, d5ID, d5Locked);
        Door d6 = new Door(d6Name, d6Description, d6ID, d6Locked);


        ////////////////////////////////////////////
        // DEFINING GAME INTERACTIVE ENVIRONMENTS //
        ////////////////////////////////////////////

        // Wardrobes

        // Wardrobe 1
        String w1Name = "Mystery Wardrobe";
        String w1Description = "Surprise.. a mysterious wardrobe... you can't identify it's material.";
        String w1ID = "w01";
        boolean w1Locked = false;
        int w1Gold = 0;

        // Wardrobe 2
        String w2Name = "Silver Wardrobe";
        String w2Description = "A wardrobe made of silver.";
        String w2ID = "w02";
        boolean w2Locked = false;
        int w2Gold = 50;

        // Instantiating Wardobes
        Wardrobe w1 = new Wardrobe(w1Name, w1Description, w1ID, w1Locked, w1Gold);
        Wardrobe w2 = new Wardrobe(w2Name, w2Description, w2ID, w2Locked, w2Gold);


        //////////////////////////////////////////////
        // ADDING ITEMS TO INTERACTIVE ENVIRONMENTS //
        //////////////////////////////////////////////

        // Wardrobe 1
        w1.addItemToWardrobe(k5);
        w1.addKeyIDtoWardrobe(k4ID);
        w1.addRoomToWardobe(r4);

        // Wardrobe 2
        w2.addRoomToWardobe(r5);

        /////////////////////////////
        // ADDING OBJECTS TO ROOMS //
        /////////////////////////////

        // Room One (r1)
        r1.addItemToRoom(k1);
        r1.addDoorToRoom(d1);

        // Room Two (r2)
        r2.addItemToRoom(k2);
        r2.addItemToRoom(k3);
        r2.addDoorToRoom(d1);
        r2.addDoorToRoom(d2);
        r2.addDoorToRoom(d4);

        // Room Three (r3)
        r3.addItemToRoom(k4);
        r3.addDoorToRoom(d2);
        r3.addDoorToRoom(d3);

        // Room Four (r4)
        r4.addDoorToRoom(d3);
        r4.addIntrEnvToRoom(w1);

        // Room Five (r5)
        r5.addDoorToRoom(d4);
        r5.addDoorToRoom(d5);
        r5.addIntrEnvToRoom(w2);

        // Room Six (r6)
        r6.addDoorToRoom(d5);
        r6.addDoorToRoom(d6);


        /////////////////////////////
        // ADDING ROOMS TO DOORS ////
        /////////////////////////////

        // Door One (d1)
        d1.addRoomToDoor(r1);
        d1.addRoomToDoor(r2);
        d1.addKeyIDtoLockIntrEnv(k1ID);

        // Door Two (d2)
        d2.addRoomToDoor(r2);
        d2.addRoomToDoor(r3);

        // Door Three (d3)
        d3.addRoomToDoor(r3);
        d3.addRoomToDoor(r4);
        d3.addKeyIDtoLockIntrEnv(k2ID);

        // Door Four (d4)
        d4.addRoomToDoor(r2);
        d4.addRoomToDoor(r5);
        d4.addKeyIDtoLockIntrEnv(k3ID);

        // Door Five (d5)
        d5.addRoomToDoor(r5);
        d5.addRoomToDoor(r6);

        // Door Six (d6)
        d6.addRoomToDoor(r6);
        d6.addKeyIDtoLockIntrEnv(k5ID);


        //////////////////////////
        // ADDING ROOMS TO GAME //
        //////////////////////////

        gameRooms = new ArrayList<Room>() {{
            add(r1);
            add(r2);
            add(r3);
            add(r4);
            add(r5);
            add(r6);
        }};

        currentRoom = r1;

    }
}
