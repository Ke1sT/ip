import java.util.Scanner;
public class Ui {
    final String WELCOME = "Hello! I'm Artificial Directory and Assistant, or Ada\n"
            + " What can I do for you?\n";
    final String GOODBYE = "Bye. Hope to see you again soon!\n";
    private final Scanner scanner;
    
    public Ui() {
        display(WELCOME);
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void goodbye() {
        display(GOODBYE);
    }
    
    public static void display(String message) {
        System.out.println("___________________________________________________________");
        System.out.println(message);
        System.out.println("___________________________________________________________");
    }
}