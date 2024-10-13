package generalClasses;//import colorfulConsole.IntegratedCommandConsole;
import generalInterfaces.Describable;
import generalInterfaces.ItemContainer;
import interactiveEnvrionments.IntrEnv;
import interactiveEnvrionments.LockIntrEnv;
import inventoryItems.*;
import ventureRooms.Room;

import java.util.Objects;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Exec extends JFrame{

    ////////////////////
    // Game Variables //
    ////////////////////

    static Avatar avatar = new Avatar();
    static Game game = new Game();
    static boolean running = true;

    ///////////////////////
    // Console Variables //
    ///////////////////////

    private static JTextPane consoleOutput;
    private static StyledDocument document;
    private String prompt = "\n> ";  // Terminal prompt symbol
    private static String newline = "\n  ";
    private int commandStart = 0;     // Track where user can start typing
    private boolean isTyping = false;  // Track typing state

    private static Color keyColor = new Color(255, 117, 0, 163); // Way to set my own colors!
    private static Color goldColor = new Color(255, 199, 0);
    private static Color itemColor = new Color(0,0,0);
    private static Color envColor = new Color(0,0,0);
    private static Color invalidColor = new Color(126, 126, 126);


    /////////////////////////
    // Console Constructor //
    /////////////////////////

    public Exec() {
        // Setup the JFrame (Window)
        setTitle("TextVentureTWO");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setResizable(false);

        // Setup the JTextPane for input/output (editable console)
        consoleOutput = new JTextPane();
        document = consoleOutput.getStyledDocument();
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        consoleOutput.setBackground(Color.WHITE);  // Set background to black
        consoleOutput.setForeground(Color.BLACK);  // Set default text color to white
        consoleOutput.setCaretColor(Color.BLACK); // Set caret color to white for visibility

        // Initial welcome message and first prompt
        appendToConsole("Welcome to RoomVenture - Explore the Rooms!", Color.BLACK);
        appendPrompt();
        /*
        appendToConsole("You find yourself in a dimly lit room. You can't make out any source of light.", Color.CYAN);
        appendPrompt();
         */

        // Add a key listener to handle the Enter key press and command input
        consoleOutput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // If the user presses Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();  // Prevent the newline from being added automatically
                    processCommand();
                }
                // Prevent deletion of previous outputs
                if (consoleOutput.getCaretPosition() <= commandStart &&
                        (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
                    e.consume(); // Prevent backspace and delete keys if at or before commandStart
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                // Enable typing state
                isTyping = true;
            }
        });

        // Make the console focusable and add mouse listener to request focus
        consoleOutput.setFocusable(true);
        consoleOutput.requestFocusInWindow();

        // Create a scroll pane for the console output
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Layout the components in the JFrame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // Show the window and ensure it's visible
        setVisible(true);
    }

    /////////////////////
    // Console Methods //
    /////////////////////

    public static void appendToConsole(String text, Color color, boolean isBold, boolean isItalic) {
        // Method to append colored text to the console output
        Style style = consoleOutput.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);

        if (isBold) {
            StyleConstants.setBold(style, true);
        }

        if (isItalic) {
            StyleConstants.setItalic(style, true);
        }

        try {
            document.insertString(document.getLength(), text, style);  // Append without newline
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Auto-scroll to the bottom after new input
        consoleOutput.setCaretPosition(document.getLength());
    }

    public static void appendToConsole(String text, Color color) {
        // Overloaded method to provide black console text automatically
        appendToConsole(text, color, false, false);
    }

    public static void appendToConsole(String text) {
        // Overloaded method to provide black console text automatically
        appendToConsole(text, Color.BLACK, false, false);
    }

    public static void appendToConsole() {
        // Overloaded method to append an empty line to console
        appendToConsole("\n", Color.BLACK, false, false);
    }

    public void appendPrompt() {
        // Append prompt to console and track where user can start typing
        appendToConsole("\n" + prompt, Color.BLACK);  // New prompt on a new line
        commandStart = document.getLength();  // Update the starting point for command input
        consoleOutput.setCaretPosition(commandStart); // Move caret to the command start position
    }

    public void processCommand() {
        // Method to handle commands
        try {
            // Get the text in the document from the command start
            String consoleText = document.getText(commandStart, document.getLength() - commandStart).trim();

            // Handle the command and give feedback
            if (!consoleText.isEmpty()) {
                handleCommand(consoleText);
            }

            // Append a new prompt for the next command
            appendPrompt();

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    //////////////////////
    // command handling //
    //////////////////////

    public static void handleCommand(String command) {
        /* handles the commands entered by the user over the console. All available commands are described
        in the first help case.
        */
        switch (command.split(" ")[0]){
            case "help" -> handleHelp(command);
            case "describe" -> handleDescribe(command);
            case "list" -> handleList(command);
            case "take" -> handleTake(command);
            case "drop" -> handleDrop(command);
            case "inventory", "my" -> handleAvatar(command);
            case "open" -> handleOpen(command);
            case "unlock" -> handleUnlock(command);
            case "enter" -> handleEnter(command);
            case "quit" -> handleQuit(command);
            default -> System.out.println("No valid command found.");
        }
    }

    private static void handleHelp(String command){
        appendToConsole(newline + "------------------------------");
        appendToConsole(newline + "######### HELP MENU ##########");
        appendToConsole(newline + "------------------------------");
        appendToConsole();
        appendToConsole(newline + "describe room        : Returns the description of the room you are currently in.");
        appendToConsole(newline + "describe [OBJECTNAME]: Returns the description of the given object in the room, or your inventory.");
        appendToConsole(newline + "list items           : Returns a list of all items available in the room.");
        appendToConsole(newline + "list gold            : Returns the amount of gold available in the room.");
        appendToConsole(newline + "list doors           : Returns a list of all doors in the room.");
        appendToConsole(newline + "list environment     : Returns a list of interactive objects in the room.");
        //appendToConsole(newline + "inspect [OBJECTNAME] : Returns more details about a given object.");
        appendToConsole(newline + "take [ITEMNAME]      : Picks up any item from the room that you enter as parameter.");
        appendToConsole(newline + "take all             : Picks up all items in the room.");
        appendToConsole(newline + "drop [ITEMNAME]      : Drops the item from your inventory that you enter as parameter.");
        appendToConsole(newline + "inventory            : Prints out the list of items on your inventory.");
        appendToConsole(newline + "my gold              : Prints out how much gold you currently possess.");
        appendToConsole(newline + "open [OBJECTNAME]    : Opens a given unlocked object, possibly adds items to the room.");
        appendToConsole(newline + "unlock [OBJECTNAME]  : Unlocks provided object such as a door or a piece of furniture - if you have the right key.");
        appendToConsole(newline + "enter [OBJECTNAME]   : Enters provided object such as doors or furniture - if it's unlocked.");
        //appendToConsole(newline + "save game            : Saves your current game progress.");
        //appendToConsole(newline + "load game            : Loads game from your last safe.");
        appendToConsole(newline + "quit game            : Quits the game.");
        appendToConsole();
        appendToConsole(newline + "------------------------------");
        appendToConsole(newline + "######## END OF HELP #########");
        appendToConsole(newline + "------------------------------");
        appendToConsole();
    }

    private static void handleDescribe(String command){
        if (command.equals("describe")) {
            appendToConsole(newline + "What do you want to have described?", invalidColor);
        } else if (command.equals("describe room")) {
            appendToConsole(newline + game.getCurrentRoom().getRoomDescription());
        } else {
            // Extract object name after "describe "
            String objectName = command.substring(9);  // Get substring after "describe "
            Describable describable = game.findDescribableByName(objectName, avatar);  // Method to find the item by name
            if (describable != null) {
                appendToConsole(newline + describable.getDescription());  // Prints the item's description
            } else {
                appendToConsole(newline + "No such object found.");
            }
        }
    }

    private static void handleList(String command){
        switch (command) {
            case "list items" -> game.getCurrentRoom().listRoomItems();
            case "list gold" -> game.getCurrentRoom().listRoomGold();
            case "list doors" -> game.getCurrentRoom().listRoomDoors();
            case "list environment" -> game.getCurrentRoom().listRoomIntrEnvs();
            default -> appendToConsole(newline + "Command not found.");
        }
    }

    private static void handleTake(String command){
        if (command.equals("take")){
            Exec.appendToConsole(newline + "Take what..?", invalidColor);
        } else if (command.equals("take gold")){
            avatar.setAvGold(avatar.getAvGold() + game.getCurrentRoom().getRoomGold());
            Exec.appendToConsole(newline + game.getCurrentRoom().getRoomGold() + " Gold taken.");
            game.getCurrentRoom().setRoomGold(0);
        } else if (command.equals("take all")) {
            if(!game.getCurrentRoom().getRoomVisibleItems().isEmpty()){
                for (Item item : game.getCurrentRoom().getRoomVisibleItems()) {
                    avatar.addToAvatarInventory(item);
                }
                game.getCurrentRoom().resetRoomItems();
                avatar.setAvGold(avatar.getAvGold() + game.getCurrentRoom().getRoomGold());
                game.getCurrentRoom().setRoomGold(0);
                Exec.appendToConsole(newline + "All items picked up.");
            } else {
                Exec.appendToConsole(newline + "No items to pick up.");
            }
        } else {
            // Extract object name after "take "
            String describeObjectName = command.substring(5);  // Get substring after "describe "
            Item item = game.findRoomItemByName(describeObjectName);  // Method to find the item by name

            if (item != null) {
                avatar.addToAvatarInventory(item);
                game.getCurrentRoom().removeItemFromRoom(item);
                Exec.appendToConsole(newline + item.getDescribableName() + " picked up.");  // Print the item's description
            } else {
                Exec.appendToConsole(newline + "No such object found.");
            }
        }
    }

    private static void handleDrop(String command){
        if (command.equals("drop")){
            appendToConsole(newline + "Please enter a valid DROP command.", invalidColor);
        } else {
            String dropObjectName = command.substring(5);  // Get substring after "describe "
            Item item = game.findAvatarItemByName(dropObjectName, avatar);  // Method to find the item by name

            if (item != null) {
                game.getCurrentRoom().addItemToRoom(item);
                avatar.removeFromAvatarInventory(item);
                appendToConsole(newline + item.getDescribableName() + " dropped.");  // Print the item's description
            } else {
                appendToConsole(newline + "No such object found.");
            }
        }
    }

    private static void handleAvatar(String command){
        switch (command){
            case "inventory":
                avatar.listAvatarInventory();
                break;
            case "my gold":
                if (avatar.getAvGold() > 0) {
                    appendToConsole(newline + avatar.getAvGold() + " Gold in inventory.");
                } else {
                    appendToConsole(newline + "You don't have any gold.");
                }
                break;
            default:
                appendToConsole(newline + "I didn't understand.", invalidColor);
                break;
        }

    }

    private static void handleOpen(String command){
        if (command.equals("open")) {
            appendToConsole(newline + "Specify what you want to open.", invalidColor);
        } else {
            String openObjectName = command.substring(5);
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(openObjectName);
            if (intrEnv instanceof LockIntrEnv){
                if (((LockIntrEnv) intrEnv).getLockIntrEnvLocked()){
                    appendToConsole(newline + intrEnv.getIntrEnvName() + " is locked.");
                } else if (intrEnv instanceof Door) {
                    appendToConsole(newline + "Don't you rather want to enter the " + intrEnv.getIntrEnvName() + "?");
                } else if (intrEnv instanceof ItemContainer) {
                    if (((ItemContainer) intrEnv).getICOpened()) {
                        appendToConsole(newline + "You already opened " + intrEnv.getIntrEnvName() + ".");
                    }
                    else {
                        if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() == 0){
                            appendToConsole(newline + intrEnv.getIntrEnvName() + " is empty.");
                        } else if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() > 0) {
                            game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                            ((ItemContainer) intrEnv).setICGold(0);
                            appendToConsole(newline + intrEnv.getIntrEnvName() + " 's " + ((ItemContainer) intrEnv).getICGold() + " gold added to the room.");
                        } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() == 0) {
                            for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                game.getCurrentRoom().addItemToRoom(item);
                            }
                            ((ItemContainer) intrEnv).resetICItems();
                            appendToConsole(newline + intrEnv.getIntrEnvName() + "'s items added to the room");
                        } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() > 0) {
                            for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                game.getCurrentRoom().addItemToRoom(item);
                            }
                            game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                            ((ItemContainer) intrEnv).setICGold(0);
                            ((ItemContainer) intrEnv).resetICItems();
                            appendToConsole(newline + intrEnv.getIntrEnvName() + "'s items and gold added to the room");
                        }
                        ((ItemContainer) intrEnv).setICOpened(true);
                    }
                }
            } else {
                appendToConsole(newline + openObjectName + " cannot be opened.");
            }
        }
    }

    private static void handleUnlock(String command){
        if (command.equals("unlock")) {
            appendToConsole(newline + "What are you trying to unlock...?", invalidColor);
        } else {
            String unlockObjectName = command.substring(7);  // Get substring after "unlock "
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(unlockObjectName);
            String requiredKeyID;
            if (intrEnv instanceof LockIntrEnv) {
                if (!((LockIntrEnv) intrEnv).getLockIntrEnvLocked()) {
                    appendToConsole(newline + intrEnv.getIntrEnvName() + " is already unlocked");
                } else {
                    requiredKeyID = ((LockIntrEnv) intrEnv).getLockIntrEnvKeyID();
                    if (avatar.getAvInventory() == null) {
                        appendToConsole(newline + "You don't have any keys.");
                    } else {
                        boolean foundKey = false;  // Flag to track if the key is found and used
                        for (Item item : avatar.getAvInventory()) {
                            if (item instanceof Key) {
                                if (item.getItemID().equals(requiredKeyID)) {
                                    ((LockIntrEnv) intrEnv).unlockLockIntrEnv();
                                    appendToConsole(newline + intrEnv.getIntrEnvName() + " unlocked.");
                                    foundKey = true;  // Set flag to true if the correct key is found
                                    break;  // Exit the loop once the object is unlocked
                                }
                            }
                        }
                        if (!foundKey) {
                            appendToConsole(newline + "You don't have the correct key.");
                        }
                    }
                }
            } else {
                appendToConsole(newline + unlockObjectName + " cannot be unlocked.");
            }
        }
    }

    private static void handleEnter(String command){
        if (command.equals("enter")) {
            appendToConsole(newline + "What do you want to enter?", invalidColor);
        } else {
            String enterDoorName = command.substring(6);  // Get substring after "unlock "
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(enterDoorName);
            if (intrEnv instanceof Door){
                if (((Door) intrEnv).getLockIntrEnvLocked()){
                    appendToConsole(newline + intrEnv.getIntrEnvName() + " is locked.");
                } else {
                    for (Room room : ((Door) intrEnv).getDoorRooms()){
                        if (room != game.getCurrentRoom()){
                            game.changeCurrentRoom(room);
                            if (Objects.equals(game.getCurrentRoom().getRoomID(), "exitRoom")){
                                appendToConsole(newline + "Congratulations! You finished the game!");
                                appendToConsole(newline + "You collected " + avatar.getAvGold() + " of 85 possible Gold.");
                                running = false;
                                break;
                            }
                            appendToConsole(newline + "You entered " + room.getRoomName());
                            appendToConsole(newline + room.getRoomDescription());
                            break;
                        }
                    }
                }
            } else if (intrEnv == null) {
                appendToConsole(newline + "There is no " + enterDoorName);
            } else {
                appendToConsole(newline + "You cannot enter " + enterDoorName);
            }
        }
    }

    private static void handleQuit(String command){
        if (command.equals("quit")) {
            appendToConsole(newline + "Please enter \"quit game\" to quit the game");
        } else {
            running = false;
            System.exit(0);
        }
    }


    //////////
    // main //
    //////////

    public static void main(String args[]){
        //Code
        /*
        Scanner reader = new Scanner(System.in);
        String response;
        boolean next = false;
         */

        SwingUtilities.invokeLater(() -> new Exec());

        /*
        System.out.println();
        System.out.println("ROOMVENTURE");
        System.out.println();
        System.out.println("Explore the rooms");
        System.out.println();
        //System.out.println("In order to see a list of all commands, please type \"help\"");
        System.out.println("Press ENTER to continue.");
        reader.nextLine();
         */

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

        /*
        avatar.setAvName("TESTER");
        System.out.println("Welcome " + avatar.getAvName());
        System.out.println();
         */

        /*
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
         */
    }
}
