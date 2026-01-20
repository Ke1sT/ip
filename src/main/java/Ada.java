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
        ArrayList<Task> tasks = new ArrayList<>();

        do {
            userInput = scanner.nextLine();
            if (userInput.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "."
                    + "["  + tasks.get(i).getStatusIcon() + "] "
                    + tasks.get(i).toString());
                }
            } else if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n");
                    System.out.println(tasks.get(taskNumber).toString());
                } else {
                    System.out.println("Invalid task number.");
                }
            } else if (userInput.equals("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).unmarkAsDone();
                    System.out.println("OK, I've marked this task as not done yet:\n");
                    System.out.println(tasks.get(taskNumber).toString());
                } else {
                    System.out.println("Invalid task number.");
                }
            } else {
                System.err.println("added: " + userInput);
                tasks.add(new Task(userInput));
            }
        } while (!userInput.equals("bye"));

        
        System.out.println(GoodbyeString);
        scanner.close();
        
    }
}
