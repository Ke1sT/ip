import java.util.Scanner;

public class Ada {
    public static void main(String[] args) {
        
        String WelcomeString = "____________________________________________________________\n"
                + "Hello! I'm Ada\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";
        String GoodbyeString = "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
        System.out.println(WelcomeString);

        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            userInput = scanner.nextLine();
            System.out.println(userInput
            + "____________________________________________________________\n");
        } while (!userInput.equalsIgnoreCase("bye"));
        System.out.println(GoodbyeString);
        scanner.close();
        
    }
}
