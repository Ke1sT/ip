package Ada.ui;

import java.util.Scanner;
/**
 * Handles user interaction: displays messages and reads commands.
 */
public class Ui {
    static final String WELCOME = "Hello! I'm Artificial Directory and Assistant, or Ada\n"
            + " What can I do for you?\n";
    static final String GOODBYE = "Bye. Hope to see you again soon!\n";
    private final Scanner scanner;
    /**
     * Constructs a UI, displays the welcome banner, and prepares input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a single line command from standard input.
     *
     * @return raw input line
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the goodbye banner.
     */
    public void goodbye() {
        display(GOODBYE);
    }
    /**
     * Displays a bordered message to standard output.
     *
     * @param message text to display
     */
    public static void display(String message) {
        System.out.println("___________________________________________________________");
        System.out.println(message);
        System.out.println("___________________________________________________________");
    }
}
