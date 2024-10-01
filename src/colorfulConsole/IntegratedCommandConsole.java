package colorfulConsole;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegratedCommandConsole extends JFrame {

    private JTextPane consoleOutput;
    private StyledDocument document;
    private String prompt = "> ";  // Terminal prompt symbol
    private int commandStart = 0;     // Track where user can start typing
    private boolean isTyping = false;  // Track typing state

    public IntegratedCommandConsole() {
        // Setup the JFrame (Window)
        setTitle("RoomVenture");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setResizable(false);

        // Setup the JTextPane for input/output (editable console)
        consoleOutput = new JTextPane();
        document = consoleOutput.getStyledDocument();
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        consoleOutput.setBackground(Color.BLACK);  // Set background to black
        consoleOutput.setForeground(Color.WHITE);  // Set default text color to white
        consoleOutput.setCaretColor(Color.WHITE); // Set caret color to white for visibility

        // Initial welcome message and first prompt
        appendToConsole("Welcome to RoomVenture - Explore the Rooms! \n", Color.GREEN);
        appendToConsole("You find yourself in a dimly lit room. You can't make out any source of light.", Color.CYAN);
        appendPrompt();

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

    // Method to append colored text to the console output
    private void appendToConsole(String text, Color color) {
        Style style = consoleOutput.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);

        try {
            document.insertString(document.getLength(), text, style);  // Append without newline
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Auto-scroll to the bottom after new input
        consoleOutput.setCaretPosition(document.getLength());
    }

    // Append prompt to console and track where user can start typing
    private void appendPrompt() {
        appendToConsole("\n" + prompt, Color.WHITE);  // New prompt on a new line
        commandStart = document.getLength();  // Update the starting point for command input
        consoleOutput.setCaretPosition(commandStart); // Move caret to the command start position
    }

    // Method to handle commands
    private void processCommand() {
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

    // Command handler that processes user input
    private void handleCommand(String command) {
        switch (command.toLowerCase()) {
            case "help":
                appendToConsole("\nAvailable commands: help, echo [text], clear, exit", Color.GREEN);
                break;
            case "clear":
                consoleOutput.setText("");  // Clear the console output
                appendPrompt();  // Append new prompt after clearing
                break;
            case "exit":
                appendToConsole("\nExiting...", Color.RED);
                System.exit(0);  // Exit the application
                break;
            default:
                if (command.startsWith("echo ")) {
                    String textToEcho = command.substring(5);
                    appendToConsole("\n" + textToEcho, Color.YELLOW);
                } else {
                    appendToConsole("\nUnknown command: " + command, Color.RED);
                }
        }
    }
}
