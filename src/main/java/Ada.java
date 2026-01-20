import java.util.ArrayList;
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
        ArrayList<String> savedInput = new ArrayList<>();

        do {
            userInput = scanner.nextLine();
            if (userInput.equals("list")) {
                for (int i = 0; i < savedInput.size(); i++) {
                    System.out.println((i + 1) + ". " + savedInput.get(i));
                }
            } else {
                System.err.println("added: " + userInput);
                savedInput.add(userInput);
            }
        } while (!userInput.equals("bye"));

        
        System.out.println(GoodbyeString);
        scanner.close();
        
    }
}
