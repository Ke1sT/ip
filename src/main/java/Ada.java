import java.util.ArrayList;
import java.util.Scanner;

public class Ada {
    public static void main(String[] args) {

        String WelcomeString = "Hello! I'm Ada\n"
                + " What can I do for you?\n";
        String GoodbyeString = "Bye. Hope to see you again soon!\n";

        display(WelcomeString);
        Scanner scanner = new Scanner(System.in);
        String userInput;
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            try {
                userInput = scanner.nextLine();
                Command command = Command.valueOf(userInput.split(" ")[0].toUpperCase());
                if (command == Command.BYE) {
                    break;
                }

                switch (command) {
                case LIST:
                    for (int i = 0; i < tasks.size(); i++) {
                        display((i + 1) + ". " + tasks.get(i).toString());
                    }
                    break;
                case MARK: {
                    int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        tasks.get(taskNumber).markAsDone();
                        display("Nice! I've marked this task as done:\n"
                                + tasks.get(taskNumber).toString());
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                case UNMARK: {
                    int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        tasks.get(taskNumber).unmarkAsDone();
                        display("OK, I've marked this task as not done yet:\n"
                                + tasks.get(taskNumber).toString());
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                case TODO:
                    String description = userInput.substring(5);
                    if (description.isEmpty()) {
                        throw new AdaException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(description));
                    display("Got it. I've added this task:\n"
                            + tasks.get(tasks.size() - 1).toString() + "\n"
                            + "Now you have " + tasks.size() + " tasks in the list.");
                    break;
                case DEADLINE: {
                    String[] parts = userInput.substring(9).split(" /by ");
                    if (parts.length == 2) {
                        tasks.add(new Deadline(parts[0], parts[1]));
                        display("Got it. I've added this task:\n"
                                + tasks.get(tasks.size() - 1).toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException("Invalid deadline format. Use: deadline <description> /by <time>");
                    }
                    break;
                }
                case EVENT: {
                    String[] parts = userInput.substring(6).split(" /from | /to ");
                    if (parts.length == 3) {
                        tasks.add(new Event(parts[0], parts[1], parts[2]));
                        display("Got it. I've added this task:\n"
                                + tasks.get(tasks.size() - 1).toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException(
                                "Invalid event format. Use: event <description> /from <start time> /to <end time>");
                    }
                    break;
                }
                case DELETE: {
                    int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        Task removedTask = tasks.remove(taskNumber);
                        display("Noted. I've removed this task:\n"
                                + removedTask.toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                default:
                    throw new AdaException("I'm sorry, but I don't know what that means.");
                }
            } catch (AdaException e) {
                display("Error: " + e.getMessage());
            }
        }

        display(GoodbyeString);
        scanner.close();

    }

    static void display(String message) {
        System.out.println("___________________________________________________________");
        System.out.println(message);
        System.out.println("___________________________________________________________");
    }
}
