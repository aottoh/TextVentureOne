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

    /*
    JTextPane textPane = new JTextPane();
    StyledDocument doc = textPane.getStyledDocument();
    private Style normal = doc.addStyle("normal", null);
    private Style bold = doc.addStyle("bold", normal);
    */

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

    public static void appendLnToConsole(String text, Color color, boolean isBold, boolean isItalic){
        appendToConsole("\n  " + text, color, isBold, isItalic);
    }

    public static void appendLnToConsole(String text, Color color){
        appendToConsole("\n  " + text, color);
    }

    public static void appendLnToConsole(String text) {
        appendToConsole("\n  " + text);
    }

    public static void appendLnToConsole(){
        appendToConsole();
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

    public static void printDescriptionWithBoldDoors(StyledDocument doc, String description, Style bold, Style normal) {
        // List of door types to bold
        String[] boldWords = {"Rusty Door", "Ivory Door", "Wooden Door", "Copper Door", "Silver Door", "Black Door", "North", "East", "South", "West"};

        try {
            int currentPos = 0;

            // Loop through the description, finding doors and applying styles
            for (String boldWord : boldWords) {
                int doorIndex = description.indexOf(boldWord, currentPos);
                if (doorIndex != -1) {
                    // Insert the text before the door in normal style
                    doc.insertString(doc.getLength(), description.substring(currentPos, doorIndex), normal);
                    // Insert the door in bold style
                    doc.insertString(doc.getLength(), boldWord, bold);
                    // Update current position
                    currentPos = doorIndex + boldWord.length();
                }
            }

            // Insert any remaining text after the last door in normal style
            if (currentPos < description.length()) {
                doc.insertString(doc.getLength(), description.substring(currentPos), normal);
            }

            // Add a newline to separate descriptions
            doc.insertString(doc.getLength(), "\n\n", normal);

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
        appendLnToConsole();
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
            default -> appendLnToConsole("No valid command found.");
        }
    }

    private static void handleHelp(String command){
        appendLnToConsole("------------------------------");
        appendLnToConsole("######### HELP MENU ##########");
        appendLnToConsole("------------------------------");
        appendLnToConsole();
        appendLnToConsole("describe room        : Returns the description of the room you are currently in.");
        appendLnToConsole("describe [OBJECTNAME]: Returns the description of the given object in the room, or your inventory.");
        appendLnToConsole("list items           : Returns a list of all items available in the room.");
        appendLnToConsole("list gold            : Returns the amount of gold available in the room.");
        appendLnToConsole("list doors           : Returns a list of all doors in the room.");
        appendLnToConsole("list environment     : Returns a list of interactive objects in the room.");
        //appendLnToConsole("inspect [OBJECTNAME] : Returns more details about a given object.");
        appendLnToConsole("take [ITEMNAME]      : Picks up any item from the room that you enter as parameter.");
        appendLnToConsole("take all             : Picks up all items in the room.");
        appendLnToConsole("drop [ITEMNAME]      : Drops the item from your inventory that you enter as parameter.");
        appendLnToConsole("inventory            : Prints out the list of items on your inventory.");
        appendLnToConsole("my gold              : Prints out how much gold you currently possess.");
        appendLnToConsole("open [OBJECTNAME]    : Opens a given unlocked object, possibly adds items to the room.");
        appendLnToConsole("unlock [OBJECTNAME]  : Unlocks provided object such as a door or a piece of furniture - if you have the right key.");
        appendLnToConsole("enter [OBJECTNAME]   : Enters provided object such as doors or furniture - if it's unlocked.");
        //appendLnToConsole("save game            : Saves your current game progress.");
        //appendLnToConsole("load game            : Loads game from your last safe.");
        appendLnToConsole("quit game            : Quits the game.");
        appendLnToConsole();
        appendLnToConsole("------------------------------");
        appendLnToConsole("######## END OF HELP #########");
        appendLnToConsole("------------------------------");
    }

    private static void handleDescribe(String command){
        if (command.equals("describe")) {
            appendLnToConsole("What do you want to have described?", invalidColor);
        } else if (command.equals("describe room")) {
            appendLnToConsole(game.getCurrentRoom().getRoomDescription());
            //printDescriptionWithBoldDoors(doc, game.getCurrentRoom().getRoomDescription(), bold, normal);
        } else {
            // Extract object name after "describe "
            String objectName = command.substring(9);  // Get substring after "describe "
            Describable describable = game.findDescribableByName(objectName, avatar);  // Method to find the item by name
            if (describable != null) {
                appendLnToConsole(describable.getDescription());  // Prints the item's description
            } else {
                appendLnToConsole("No such object found.");
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
            Exec.appendLnToConsole("Take what..?", invalidColor);
        } else if (command.equals("take gold")){
            avatar.setAvGold(avatar.getAvGold() + game.getCurrentRoom().getRoomGold());
            Exec.appendLnToConsole(game.getCurrentRoom().getRoomGold() + " Gold taken.");
            game.getCurrentRoom().setRoomGold(0);
        } else if (command.equals("take all")) {
            if(!game.getCurrentRoom().getRoomVisibleItems().isEmpty()){
                for (Item item : game.getCurrentRoom().getRoomVisibleItems()) {
                    avatar.addToAvatarInventory(item);
                }
                game.getCurrentRoom().resetRoomItems();
                avatar.setAvGold(avatar.getAvGold() + game.getCurrentRoom().getRoomGold());
                game.getCurrentRoom().setRoomGold(0);
                Exec.appendLnToConsole("All items picked up.");
            } else {
                Exec.appendLnToConsole("No items to pick up.");
            }
        } else {
            // Extract object name after "take "
            String describeObjectName = command.substring(5);  // Get substring after "describe "
            Item item = game.findRoomItemByName(describeObjectName);  // Method to find the item by name

            if (item != null) {
                avatar.addToAvatarInventory(item);
                game.getCurrentRoom().removeItemFromRoom(item);
                Exec.appendLnToConsole(item.getDescribableName() + " picked up.");  // Print the item's description
            } else {
                Exec.appendLnToConsole("No such object found.");
            }
        }
    }

    private static void handleDrop(String command){
        if (command.equals("drop")){
            appendLnToConsole("Please enter a valid DROP command.", invalidColor);
        } else {
            String dropObjectName = command.substring(5);  // Get substring after "describe "
            Item item = game.findAvatarItemByName(dropObjectName, avatar);  // Method to find the item by name

            if (item != null) {
                game.getCurrentRoom().addItemToRoom(item);
                avatar.removeFromAvatarInventory(item);
                appendLnToConsole(item.getDescribableName() + " dropped.");  // Print the item's description
            } else {
                appendLnToConsole("No such object found.");
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
                    appendLnToConsole(avatar.getAvGold() + " Gold in inventory.");
                } else {
                    appendLnToConsole("You don't have any gold.");
                }
                break;
            default:
                appendLnToConsole("I didn't understand.", invalidColor);
                break;
        }

    }

    private static void handleOpen(String command){
        if (command.equals("open")) {
            appendLnToConsole("Specify what you want to open.", invalidColor);
        } else {
            String openObjectName = command.substring(5);
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(openObjectName);
            if (intrEnv instanceof LockIntrEnv){
                if (((LockIntrEnv) intrEnv).getLockIntrEnvLocked()){
                    appendLnToConsole(intrEnv.getIntrEnvName() + " is locked.");
                } else if (intrEnv instanceof Door) {
                    appendLnToConsole("Don't you rather want to enter the " + intrEnv.getIntrEnvName() + "?");
                } else if (intrEnv instanceof ItemContainer) {
                    if (((ItemContainer) intrEnv).getICOpened()) {
                        appendLnToConsole("You already opened " + intrEnv.getIntrEnvName() + ".");
                    }
                    else {
                        if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() == 0){
                            appendLnToConsole(intrEnv.getIntrEnvName() + " is empty.");
                        } else if (((ItemContainer) intrEnv).getICItems() == null & ((ItemContainer) intrEnv).getICGold() > 0) {
                            game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                            ((ItemContainer) intrEnv).setICGold(0);
                            appendLnToConsole(intrEnv.getIntrEnvName() + " 's " + ((ItemContainer) intrEnv).getICGold() + " gold added to the room.");
                        } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() == 0) {
                            for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                game.getCurrentRoom().addItemToRoom(item);
                            }
                            ((ItemContainer) intrEnv).resetICItems();
                            appendLnToConsole(intrEnv.getIntrEnvName() + "'s items added to the room");
                        } else if (((ItemContainer) intrEnv).getICItems() != null & ((ItemContainer) intrEnv).getICGold() > 0) {
                            for (Item item : ((ItemContainer) intrEnv).getICItems()) {
                                game.getCurrentRoom().addItemToRoom(item);
                            }
                            game.getCurrentRoom().setRoomGold(game.getCurrentRoom().getRoomGold() + ((ItemContainer) intrEnv).getICGold());
                            ((ItemContainer) intrEnv).setICGold(0);
                            ((ItemContainer) intrEnv).resetICItems();
                            appendLnToConsole(intrEnv.getIntrEnvName() + "'s items and gold added to the room");
                        }
                        ((ItemContainer) intrEnv).setICOpened(true);
                    }
                }
            } else {
                appendLnToConsole(openObjectName + " cannot be opened.");
            }
        }
    }

    private static void handleUnlock(String command){
        if (command.equals("unlock")) {
            appendLnToConsole("What are you trying to unlock...?", invalidColor);
        } else {
            String unlockObjectName = command.substring(7);  // Get substring after "unlock "
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(unlockObjectName);
            String requiredKeyID;
            if (intrEnv instanceof LockIntrEnv) {
                if (!((LockIntrEnv) intrEnv).getLockIntrEnvLocked()) {
                    appendLnToConsole(intrEnv.getIntrEnvName() + " is already unlocked");
                } else {
                    requiredKeyID = ((LockIntrEnv) intrEnv).getLockIntrEnvKeyID();
                    if (avatar.getAvInventory() == null) {
                        appendLnToConsole("You don't have any keys.");
                    } else {
                        boolean foundKey = false;  // Flag to track if the key is found and used
                        for (Item item : avatar.getAvInventory()) {
                            if (item instanceof Key) {
                                if (item.getItemID().equals(requiredKeyID)) {
                                    ((LockIntrEnv) intrEnv).unlockLockIntrEnv();
                                    appendLnToConsole(intrEnv.getIntrEnvName() + " unlocked.");
                                    foundKey = true;  // Set flag to true if the correct key is found
                                    break;  // Exit the loop once the object is unlocked
                                }
                            }
                        }
                        if (!foundKey) {
                            appendLnToConsole("You don't have the correct key.");
                        }
                    }
                }
            } else {
                appendLnToConsole(unlockObjectName + " cannot be unlocked.");
            }
        }
    }

    private static void handleEnter(String command){
        if (command.equals("enter")) {
            appendLnToConsole("What do you want to enter?", invalidColor);
        } else {
            String enterDoorName = command.substring(6);  // Get substring after "unlock "
            IntrEnv intrEnv = game.getCurrentRoom().findIntrEnvByName(enterDoorName);
            if (intrEnv instanceof Door){
                if (((Door) intrEnv).getLockIntrEnvLocked()){
                    appendLnToConsole(intrEnv.getIntrEnvName() + " is locked.");
                } else {
                    for (Room room : ((Door) intrEnv).getDoorRooms()){
                        if (room != game.getCurrentRoom()){
                            game.changeCurrentRoom(room);
                            if (Objects.equals(game.getCurrentRoom().getRoomID(), "exitRoom")){
                                appendLnToConsole("Congratulations! You finished the game!");
                                appendLnToConsole("You collected " + avatar.getAvGold() + " of 85 possible Gold.");
                                running = false;
                                break;
                            }
                            appendLnToConsole("You entered " + room.getRoomName());
                            appendLnToConsole(room.getRoomDescription());
                            break;
                        }
                    }
                }
            } else if (intrEnv == null) {
                appendLnToConsole("There is no " + enterDoorName);
            } else {
                appendLnToConsole("You cannot enter " + enterDoorName);
            }
        }
    }

    private static void handleQuit(String command){
        if (command.equals("quit")) {
            appendLnToConsole("Please enter \"quit game\" to quit the game");
        } else {
            running = false;
            System.exit(0);
        }
    }


    //////////
    // main //
    //////////

    public static void main(String args[]){

        SwingUtilities.invokeLater(() -> new Exec());

    }
}
