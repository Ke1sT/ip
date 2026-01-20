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

        while (true) {
            userInput = scanner.nextLine();
            if (userInput.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i).toString());
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
            } else if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5);
                tasks.add(new Todo(description));
                System.out.println("Got it. I've added this task:\n"
                    + tasks.get(tasks.size() - 1).toString() + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.");
            } else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ");
                if (parts.length == 2) {
                    tasks.add(new Deadline(parts[0], parts[1]));
                    System.out.println("Got it. I've added this task:\n"
                        + tasks.get(tasks.size() - 1).toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    System.out.println("Invalid deadline format. Use: deadline <description> /by <time>");
                }
            } else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ");
                if (parts.length == 3) {
                    tasks.add(new Event(parts[0], parts[1], parts[2]));
                    System.out.println("Got it. I've added this task:\n"
                        + tasks.get(tasks.size() - 1).toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    System.out.println("Invalid event format. Use: event <description> /from <start time> /to <end time>");
                }
            } else if (userInput.equals("bye")) {
                break;
            }
            else {
                System.err.println("added: " + userInput);
                tasks.add(new Task(userInput));
            }
        }

        
        System.out.println(GoodbyeString);
        scanner.close();
        
    }
}
