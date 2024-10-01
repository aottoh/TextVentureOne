//import colorfulConsole.IntegratedCommandConsole;
import inventoryItems.*;
import ventureRooms.*;
import inventoryItems.*;
import generalClasses.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Exec {

    //Scanner reader = new Scanner(System.in);
    static Avatar avatar = new Avatar(0);
    static Game game = new Game();

    public static void handleCommand(String command) {

        switch (command.split(" ")[0]) {  // Check the first word in the command
            case "help":
                System.out.println("------------------------------");
                System.out.println("######### HELP MENU ##########");
                System.out.println("------------------------------");
                System.out.println();
                System.out.println("describe room        : Returns the description of the room you are currently in.");
                System.out.println("describe [OBJECTNAME]: Returns the description of any object in the room, or your inventory that you enter as parameter.");
                System.out.println("list items           : Returns a list of all items available in the room");
                System.out.println("list environment     : Returns a list of all interactive objects in the room");
                System.out.println("take [ITEMNAME]      : Picks up any item from the room that you enter as parameter.");
                System.out.println("take all             : Picks up all items in the room.");
                System.out.println("drop [ITEMNAME]      : Drops the item from your inventory that you enter as parameter.");
                System.out.println("inventory            : Prints out the list of items on your inventory.");
                System.out.println("my gold              : Prints out how much gold you currently possess.");
                System.out.println("unlock [OBJECTNAME]  : Unlocks provided object such as a door or a piece of furniture - if you have the right key.");
                System.out.println("enter [OBJECTNAME]   : Enters provided object such as doors or furniture - if it's unlocked.");
                System.out.println("save game            : Saves your current game progress.");
                System.out.println("load game            : Loads game from your last safe.");
                System.out.println();
                System.out.println("------------------------------");
                System.out.println("######## END OF HELP #########");
                System.out.println("------------------------------");
                System.out.println();
                break;
            case "describe":
                if (command.equals("describe room")) {
                    System.out.println(game.getCurrentRoom().getRoomDescription());
                } else {
                    // Extract object name after "describe "
                    String objectName = command.substring(9);  // Get substring after "describe "
                    Item item = game.findItemByName(objectName, avatar);  // Method to find the item by name

                    if (item != null) {
                        System.out.println(item.getItemDescription());  // Print the item's description
                    } else {
                        System.out.println("No such object found.");
                    }
                }
                break;
            case "list":
                if (command.equals("list items")) {
                    game.getCurrentRoom().listRoomItems();
                } else if (command.equals("list environment")) {
                    // Add logic for listing interactive environments
                } else {
                    System.out.println("Command not found.");
                }
                break;
            case "take":
                if (command.equals("take all")) {
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
                    String objectName = command.substring(5);  // Get substring after "describe "
                    Item item = game.findRoomItemByName(objectName);  // Method to find the item by name

                    if (item != null) {
                        avatar.addToAvatarInventory(item);
                        game.getCurrentRoom().removeItemFromRoom(item);
                        System.out.println(item.getItemName() + " picked up.");  // Print the item's description
                    } else {
                        System.out.println("No such object found.");
                    }
                }
                break;
            case "drop":
                String objectName = command.substring(5);  // Get substring after "describe "
                Item item = game.findAvatarItemByName(objectName, avatar);  // Method to find the item by name

                if (item != null) {
                    game.getCurrentRoom().addItemToRoom(item);
                    avatar.removeFromAvatarInventory(item);
                    System.out.println(item.getItemName() + " dropped.");  // Print the item's description
                } else {
                    System.out.println("No such object found.");
                }
                break;
            case "inventory":
                avatar.listAvatarInventory();
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

        System.out.println("------------------------------");
        System.out.println();
        System.out.println("You wake up, no memory of nothing. You appear to be in a dark room. Though you can't make out any source of light, you find yourself in a dimly lit room. ");
        System.out.println("---");
        System.out.println("You try to concentrate, remember something about yourself. All that comes to you, is your name.");
        System.out.print("Enter your name: ");
        response = reader.next();
        reader.nextLine();
        avatar.setAvName(response);
        System.out.println("Welcome " + avatar.getAvName());
        System.out.println();

        System.out.println("Type any command or \"help\" for help.");
        while (true){
            response = reader.nextLine().trim();

            if(response.isEmpty()){
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
