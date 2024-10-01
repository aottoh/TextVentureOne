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
    static Game game = new Game();

    public static void handleCommand(String command) {

        switch (command){
            case "help":
                System.out.println("------------------------------");
                System.out.println("######### HELP MENU ##########");
                System.out.println("------------------------------");
                System.out.println();
                System.out.println("describe room        : Returns the description of the room you are currently in.");
                System.out.println("describe [OBJECTNAME]: Returns the description of any object in the room, or you inventory that you enter as parameter.");
                System.out.println("list items           : Returns a list of all items available in the room");
                System.out.println("list environment     : Returns a list of all interactive objects in the room");
                System.out.println("take [ITEMNAME]      : Picks up any item from the room that you enter as parameter.");
                System.out.println("take all             : Picks up all items in the room.");
                System.out.println("drop [ITEMNAME]      : Drops the item from your inventory that you enter as parameter.");
                System.out.println("inventory            : Prints out the list of items on your inventory.");
                System.out.println("gold                 : Prints out how much gold you currently possess.");
                System.out.println();
                System.out.println("------------------------------");
                System.out.println("######## END OF HELP #########");
                System.out.println("------------------------------");
                System.out.println();
                break;
            case "describe room":
                System.out.println(game.getCurrentRoom().getRoomDescription());
                break;
            case "describe [OBJECTNAME]":
                break;
            case "list items":
                game.getCurrentRoom().listRoomItems();
                break;
            default:
                System.out.println("No valid command found.");
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
        Avatar avatar = new Avatar(response, 0, new ArrayList<Item>());
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
        System.out.println("Hope you enjoyed");
        System.out.println("Please rate five star.");



        reader.close();
        //SwingUtilities.invokeLater(() -> new IntegratedCommandConsole());

    }
}
