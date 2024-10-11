//import colorfulConsole.IntegratedCommandConsole;
import generalInterfaces.Describable;
import generalInterfaces.ItemContainer;
import interactiveEnvrionments.IntrEnv;
import interactiveEnvrionments.LockIntrEnv;
import inventoryItems.*;
import generalClasses.*;
import ventureRooms.Room;

import java.util.Scanner;

public class Exec {

    //Scanner reader = new Scanner(System.in);
    static Avatar avatar = new Avatar(0);
    static Game game = new Game();
    static boolean running = true;

    public static void handleCommand(String command) {
        /* handles the commands entered by the user over the console. All available commands are described
        in the first help case.
        */
        switch (command.split(" ")[0]) {  // Checks  the first word in the command
            case "help":
                System.out.println("------------------------------");
                System.out.println("######### HELP MENU ##########");
                System.out.println("------------------------------");
                System.out.println();
                System.out.println("describe room        : Returns the description of the room you are currently in.");
                System.out.println("describe [OBJECTNAME]: Returns the description of the given object in the room, or your inventory.");
                System.out.println("list items           : Returns a list of all items available in the room.");
                System.out.println("list gold            : Returns the amount of gold available in the room.");
                System.out.println("list doors           : Returns a list of all doors in the room.");
                System.out.println("list environment     : Returns a list of interactive objects in the room.");
                //System.out.println("inspect [OBJECTNAME] : Returns more details about a given object.");
                System.out.println("take [ITEMNAME]      : Picks up any item from the room that you enter as parameter.");
                System.out.println("take all             : Picks up all items in the room.");
                System.out.println("drop [ITEMNAME]      : Drops the item from your inventory that you enter as parameter.");
                System.out.println("inventory            : Prints out the list of items on your inventory.");
                System.out.println("my gold              : Prints out how much gold you currently possess.");
                System.out.println("open [OBJECTNAME]    : Opens a given unlocked object, possibly adds items to the room.");
                System.out.println("unlock [OBJECTNAME]  : Unlocks provided object such as a door or a piece of furniture - if you have the right key.");
                System.out.println("enter [OBJECTNAME]   : Enters provided object such as doors or furniture - if it's unlocked.");
                //System.out.println("save game            : Saves your current game progress.");
                //System.out.println("load game            : Loads game from your last safe.");
                System.out.println("quit game            : Quits the game.");
                System.out.println();
                System.out.println("------------------------------");
                System.out.println("######## END OF HELP #########");
                System.out.println("------------------------------");
                System.out.println();
                break;
            case "describe":
                if (command.equals("describe")) {
                    System.out.println("Please enter Object to describe.");
                } else if (command.equals("describe room")) {
                    System.out.println(game.getCurrentRoom().getRoomDescription());
                } else {
                    // Extract object name after "describe "
                    String objectName = command.substring(9);  // Get substring after "describe "
                    Describable describable = game.findDescribableByName(objectName, avatar);  // Method to find the item by name

                    if (describable != null) {
                        System.out.println(describable.getDescription());  // Prints the item's description
                    } else {
                        System.out.println("No such object found.");
                    }
                }
                break;
            case "list":
                if (command.equals("list items")) {
                    game.getCurrentRoom().listRoomItems();
                } else if (command.equals("list gold")) {
                    game.getCurrentRoom().listRoomGold();
                } else if (command.equals("list doors")) {
                    game.getCurrentRoom().listRoomDoors();
                } else if (command.equals("list environment")) {
                    game.getCurrentRoom().listRoomIntrEnvs();
                } else {
                    System.out.println("Command not found.");
                }
                break;
            case "take":
                if (command.equals("take")){
                    System.out.println("Please enter a valid TAKE command.");
                } else if (command.equals("take gold")){
                    avatar.setAvGold(avatar.getAvGold() + game.getCurrentRoom().getRoomGold());
                    System.out.println(game.getCurrentRoom().getRoomGold() + " Gold taken.");
                    game.getCurrentRoom().setRoomGold(0);
                } else if (command.equals("take all")) {
                    if(!game.getCurrentRoom().getRoomVisibleItems().isEmpty()){
                        for (Item item : game.getCurrentRoom().getRoomVisibleItems()) {
                            avatar.addToAvatarInventory(item);
                        }
                        game.getCurrentRoom().resetRoomItems();
                        System.out.println("All items picked up.");
                    } else {
                        System.out.println("No items to pick up.");
                    }

                } else {
                    // Extract object name after "take "
                    String describeObjectName = command.substring(5);  // Get substring after "describe "
                    Item item = game.findRoomItemByName(describeObjectName);  // Method to find the item by name

                    if (item != null) {
                        avatar.addToAvatarInventory(item);
                        game.getCurrentRoom().removeItemFromRoom(item);
                        System.out.println(item.getDescribableName() + " picked up.");  // Print the item's description
                    } else {
                        System.out.println("No such object found.");
                    }
                }
                break;
            case "drop":
                if (command.equals("drop")){
                    System.out.println("Please enter a valid DROP command.");
                } else {
                    String dropObjectName = command.substring(5);  // Get substring after "describe "
                    Item item = game.findAvatarItemByName(dropObjectName, avatar);  // Method to find the item by name

                    if (item != null) {
                        game.getCurrentRoom().addItemToRoom(item);
                        avatar.removeFromAvatarInventory(item);
                        System.out.println(item.getDescribableName() + " dropped.");  // Print the item's description
                    } else {
                        System.out.println("No such object found.");
                    }
                }
                break;
            case "inventory":
                avatar.listAvatarInventory();
                break;
            case "my":
                if (command.equals("my gold")){
                    if (avatar.getAvGold() > 0) {
                        System.out.println(avatar.getAvGold() + " Gold in inventory.");
                    } else {
                        System.out.println("You don't have any gold.");
                    }
                } else {
                    System.out.println("No valid command found.");
                }
                break;
            case "open":
                if (command.equals("open")) {
                    System.out.println("Specify what you want to open.");
                } else {
                    String openObjectName = command.substring(5);
                    IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(openObjectName);
                    if (intrEnv instanceof LockIntrEnv){
                        if (((LockIntrEnv) intrEnv).getLockIntrEnvLocked()){
                            System.out.println(intrEnv.getIntrEnvName() + " is locked.");
                        } else if (intrEnv instanceof Door) {
                            System.out.println("Don't you rather want to enter the " + intrEnv.getIntrEnvName() + "?");
                        } else if (intrEnv instanceof ItemContainer) {
                            if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() == 0){
                                System.out.println(intrEnv.getIntrEnvName() + " is empty.");
                            } else if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() > 0) {
                                game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                                ((ItemContainer) intrEnv).setICGold(0);
                                System.out.println(intrEnv.getIntrEnvName() + " 's " + ((ItemContainer) intrEnv).getICGold() + " gold added to the room.");
                            } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() == 0) {
                                for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                    game.getCurrentRoom().addItemToRoom(item);
                                }
                                ((ItemContainer) intrEnv).resetICItems();
                                System.out.println(intrEnv.getIntrEnvName() + "'s items added to the room");
                            } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() > 0) {
                                for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                    game.getCurrentRoom().addItemToRoom(item);
                                }
                                game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                                ((ItemContainer) intrEnv).setICGold(0);
                                ((ItemContainer) intrEnv).resetICItems();
                                System.out.println(intrEnv.getIntrEnvName() + "'s items and gold added to the room");
                            }
                        }
                    } else {
                        System.out.println(openObjectName + " cannot be opened.");
                    }
                }
                break;
            case "unlock":
                if (command.equals("unlock")) {
                    System.out.println("What are you trying to unlock...?");
                } else {
                    String unlockObjectName = command.substring(7);  // Get substring after "unlock "
                    IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(unlockObjectName);
                    String requiredKeyID;
                    if (intrEnv instanceof LockIntrEnv) {
                        if (!((LockIntrEnv) intrEnv).getLockIntrEnvLocked()) {
                            System.out.println(intrEnv.getIntrEnvName() + " is already unlocked");
                            break;
                        } else {
                            requiredKeyID = ((LockIntrEnv) intrEnv).getLockIntrEnvKeyID();
                            if (avatar.getAvInventory() == null) {
                                System.out.println("You don't have any keys.");
                            } else {
                                boolean foundKey = false;  // Flag to track if the key is found and used
                                for (Item item : avatar.getAvInventory()) {
                                    if (item instanceof Key) {
                                        if (item.getItemID().equals(requiredKeyID)) {
                                            ((LockIntrEnv) intrEnv).unlockLockIntrEnv();
                                            System.out.println(intrEnv.getIntrEnvName() + " unlocked.");
                                            foundKey = true;  // Set flag to true if the correct key is found
                                            break;  // Exit the loop once the object is unlocked
                                        }
                                    }
                                }
                                if (!foundKey) {
                                    System.out.println("You don't have the correct key.");
                                }
                            }
                        }
                    } else {
                        System.out.println(unlockObjectName + " cannot be unlocked.");
                    }
                }
                break;
            case "enter":
                if (command.equals("enter")) {
                    System.out.println("What do you want to enter?");
                } else {
                    String enterDoorName = command.substring(6);  // Get substring after "unlock "
                    IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(enterDoorName);
                    if (intrEnv instanceof Door){
                        if (((Door) intrEnv).getLockIntrEnvLocked() == true){
                            System.out.println(intrEnv.getIntrEnvName() + " is locked.");
                        } else {
                            for (Room room : ((Door) intrEnv).getDoorRooms()){
                                if (room != game.getCurrentRoom()){
                                    game.changeCurrentRoom(room);
                                    System.out.println("You entered " + room.getRoomName());
                                    System.out.println(room.getRoomDescription());
                                    break;
                                }
                            }
                        }
                    } else if (intrEnv == null) {
                        System.out.println("There is no " + enterDoorName);
                    } else {
                        System.out.println("You cannot enter " + enterDoorName);
                    }
                }
                break;
            case "quit":
                if (command.equals("quit")) {
                    System.out.println("Please enter \"quit game\" to quit the game");
                    break;
                } else {
                    running = false;
                }
                break;
            default:
                System.out.println("No valid command found.");
                break;
        }
    }


    public static void main(String args[]){
        //Code
        Scanner reader = new Scanner(System.in);
        String response;
        boolean next = false;

        System.out.println();
        System.out.println("ROOMVENTURE");
        System.out.println();
        System.out.println("Explore the rooms");
        System.out.println();
        //System.out.println("In order to see a list of all commands, please type \"help\"");
        System.out.println("Press ENTER to continue.");
        reader.nextLine();

        /*
        System.out.println("------------------------------");
        System.out.println();
        System.out.println("You wake up, no memory of nothing. You appear to be in a dark room. Though you can't make out any source of light, you find yourself in a dimly lit room. ");
        System.out.println("---");
        System.out.println("You try to concentrate, remember something about yourself. All that comes to you, is your name.");
        System.out.print("Enter your name: ");
        response = reader.next();
        reader.nextLine();
        avatar.setAvName(response);
        */
        avatar.setAvName("TESTER");
        System.out.println("Welcome " + avatar.getAvName());
        System.out.println();

        System.out.println("Type any command or \"help\" for help.");
        while (running){
            response = reader.nextLine().trim();

            if(!running){
                break;
            } else {
                handleCommand(response);
                //System.out.println("Press ENTER to continue. Or type \"help\" or any other command.");
            }

        }

        System.out.println("Game over.");

        reader.close();
        //SwingUtilities.invokeLater(() -> new IntegratedCommandConsole());

    }
}
